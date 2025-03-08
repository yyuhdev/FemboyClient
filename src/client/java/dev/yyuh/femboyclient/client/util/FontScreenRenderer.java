package dev.yyuh.femboyclient.client.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class FontScreenRenderer {
    private Font font;
    private int baseFontSize;
    private double lastGuiScale;
    private final Map<Character, GlyphInfo> glyphCache = new HashMap<>();
    private NativeImageBackedTexture fontTexture;
    private Identifier textureId;
    private int texWidth = 2048;
    private int texHeight = 2048;
    private int currentX = 0;
    private int currentY = 0;
    private int rowHeight = 0;
    private FontMetrics fontMetrics;

    private static class GlyphInfo {
        public final int x, y, width, height;
        public final float u1, v1, u2, v2;
        public final int advance;

        public GlyphInfo(int x, int y, int width, int height, int advance, int texWidth, int texHeight) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.advance = advance;
            this.u1 = (float) x / texWidth;
            this.v1 = (float) y / texHeight;
            this.u2 = (float) (x + width) / texWidth;
            this.v2 = (float) (y + height) / texHeight;
        }
    }

    public FontScreenRenderer(Font font, int baseSize) {
        this.baseFontSize = baseSize;
        updateFont(font.deriveFont((float) getScaledFontSize()));
    }


    private int getScaledFontSize() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options != null) {
            return (int) (baseFontSize * client.options.getGuiScale().getValue());
        }
        return baseFontSize;
    }

    private void checkGuiScale() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options == null) return;

        double currentScale = client.options.getGuiScale().getValue();
        if (currentScale != lastGuiScale) {
            lastGuiScale = currentScale;
            updateFont(font.deriveFont((float) getScaledFontSize()));
        }
    }

    private void updateFont(Font newFont) {
        this.font = newFont;
        this.fontMetrics = generateFontMetrics();
        clearGlyphCache();
        initializeTexture();
    }

    private FontMetrics generateFontMetrics() {
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tempImg.createGraphics();
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.dispose();
        return metrics;
    }

    private synchronized void initializeTexture() {
        try {
            if (fontTexture != null) fontTexture.close();

            NativeImage nativeImage = new NativeImage(texWidth, texHeight, false);
            nativeImage.fillRect(0, 0, texWidth, texHeight, 0); // Transparent

            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

            fontTexture = new NativeImageBackedTexture(nativeImage);
            textureId = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture(
                    "font_atlas_" + System.currentTimeMillis(), fontTexture
            );


            currentX = 0;
            currentY = 0;
            rowHeight = 0;

            for (char c = 32; c < 128; c++) cacheGlyph(c);
            fontTexture.upload();

        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearGlyphCache() {
        glyphCache.clear();
        currentX = 0;
        currentY = 0;
        rowHeight = 0;
    }

    private GlyphInfo cacheGlyph(char c) {
        if (glyphCache.containsKey(c)) return glyphCache.get(c);

        int charWidth = Math.max(1, fontMetrics.charWidth(c));
        int charHeight = Math.max(1, fontMetrics.getHeight());


        if (currentX + charWidth >= texWidth) {
            currentX = 0;
            currentY += rowHeight;
            rowHeight = 0;
        }
        if (currentY + charHeight >= texHeight) {
            texHeight *= 2; // Double texture height if full
            initializeTexture();
            return cacheGlyph(c);
        }

        // Render glyph to image
        BufferedImage charImage = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = charImage.createGraphics();
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0, textureId);

        g2d.drawString(String.valueOf(c), 0, fontMetrics.getAscent());
        g2d.dispose();

        // Copy to texture
        NativeImage nativeImage = fontTexture.getImage();
        for (int x = 0; x < charWidth; x++) {
            for (int y = 0; y < charHeight; y++) {
                int pixel = charImage.getRGB(x, y);
                nativeImage.setColor(currentX + x, currentY + y, pixel);
            }
        }

        GlyphInfo glyph = new GlyphInfo(currentX, currentY, charWidth, charHeight, charWidth, texWidth, texHeight);
        glyphCache.put(c, glyph);

        currentX += charWidth;
        rowHeight = Math.max(rowHeight, charHeight);
        return glyph;
    }

    public void drawText(Text text, float x, float y, int color, DrawContext context) {
        checkGuiScale();
        String str = text.getString();
        if (str.isEmpty()) return;

        float guiScaleFactor = (float) (1.0 / lastGuiScale);
        float renderScale = guiScaleFactor;

        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        RenderSystem.setShaderColor(red / 255f, green / 255f, blue / 255f, alpha / 255f);

        float posX = x;
        for (char c : str.toCharArray()) {
            GlyphInfo glyph = glyphCache.computeIfAbsent(c, k -> cacheGlyph(c));

            context.getMatrices().push();
            context.getMatrices().translate(posX, y - (fontMetrics.getAscent() * guiScaleFactor), 0);
            context.getMatrices().scale(renderScale, renderScale, 1f);

            context.drawTexture(textureId, 0, 0,
                    glyph.x, glyph.y, glyph.width, glyph.height, texWidth, texHeight);

            context.getMatrices().pop();
            posX += glyph.advance * renderScale;
        }

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // Reset color
    }

    public float getStringWidth(Text text) {
        checkGuiScale();
        String str = text.getString();
        float width = 0;
        float scale = (float) (1.0 / lastGuiScale);

        for (char c : str.toCharArray()) {
            GlyphInfo glyph = glyphCache.getOrDefault(c, cacheGlyph(c));
            width += glyph.advance * scale;
        }
        return width;
    }
    public static Font loadFont(String path, float size) {
        try (InputStream inputStream = FontScreenRenderer.class.getResourceAsStream("/assets/femboyclient/fonts/" + path)) {
            if (inputStream == null) {
                throw new RuntimeException("Font file not found: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return font.deriveFont(size);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font: " + path, e);
        }
    }

    // Call this when you're done with the font renderer to free resources
    public void destroy() {
        if (fontTexture != null) {
            fontTexture.close();
        }
    }
}