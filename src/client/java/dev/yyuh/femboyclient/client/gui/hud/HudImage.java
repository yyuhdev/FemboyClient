package dev.yyuh.femboyclient.client.gui.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HudImage extends HudElement {
    private int x, y;
    private final int width, height;
    private final Identifier texture;
    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;

    public HudImage(int x, int y, int width, int height, Identifier texture) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void render(DrawContext context) {
        context.drawTexture(texture, x, y, 0, 0, width, height, width, height);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void startDragging(int mouseX, int mouseY) {
        dragging = true;
        dragOffsetX = mouseX - x;
        dragOffsetY = mouseY - y;
    }

    public void stopDragging() {
        dragging = false;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX - dragOffsetX;
            y = mouseY - dragOffsetY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}