package dev.yyuh.femboyclient.client.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FontScreenRenderer {
    private final Font font;
    private final FontMetrics fontMetrics;

    public FontScreenRenderer(Font font, int size) {
        this.font = font.deriveFont((float) size);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        this.fontMetrics = g2d.getFontMetrics();
        g2d.dispose();
    }

    public static FontScreenRenderer getCustomFont(Identifier fontId, int size) {
        try {
            InputStream fontStream = MinecraftClient.getInstance().getResourceManager().getResource(fontId).get().getInputStream();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
            return new FontScreenRenderer(customFont, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void drawText(MatrixStack matrices, Text text, int x, int y, int color) {
        String str = text.getString();
        BufferedImage img = new BufferedImage(fontMetrics.stringWidth(str), fontMetrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        g2d.setColor(new java.awt.Color(color, true));
        g2d.drawString(str, 0, fontMetrics.getAscent());
        g2d.dispose();

        // Convert BufferedImage to Minecraft's texture and draw it
        // This part depends on your rendering library and setup
        // Example: drawImage(matrices, img, x, y);
    }
}