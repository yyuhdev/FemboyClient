package dev.yyuh.femboyclient.client.gui.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HudTextList extends HudElement {
    private final List<Text> textList;
    private int textColor;
    private int backgroundColor;
    private boolean backgroundEnabled;
    private int padding = 5;

    MinecraftClient mc = MinecraftClient.getInstance();

    public HudTextList(int x, int y) {
        super(x, y, 0, 0);
        this.textList = new ArrayList<>();
        this.textColor = 0xFFFFFF; // Default text color (white)
        this.backgroundColor = 0x80000000; // Default background color (semi-transparent black)
        this.backgroundEnabled = true;
    }

    public void addText(Text text) {
        textList.add(text);
        updateDimensions(); // Recalculate width and height when text is added
    }

    public void clearText() {
        textList.clear();
        updateDimensions(); // Recalculate width and height when text is cleared
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundEnabled(boolean backgroundEnabled) {
        this.backgroundEnabled = backgroundEnabled;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        updateDimensions();
    }

    private void updateDimensions() {
        int maxWidth = 0;
        int totalHeight = 0;

        for (Text text : textList) {
            int textWidth = mc.textRenderer.getWidth(text);
            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }
            totalHeight += mc.textRenderer.fontHeight + 2;
        }

        setWidth(maxWidth + padding * 2);
        setHeight(totalHeight + padding * 2);
    }

    @Override
    public void render(DrawContext context) {
        if (backgroundEnabled) {
            context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor);
        }

        int yOffset = getY() + padding;
        for (Text text : textList) {
            context.drawTextWithShadow(mc.textRenderer, text, getX() + padding, yOffset, textColor);
            yOffset += mc.textRenderer.fontHeight + 2;
        }
    }
}