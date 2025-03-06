package dev.yyuh.femboyclient.client.gui.render;

import net.minecraft.client.gui.DrawContext;

public abstract class Component {
    protected int x, y, width, height;

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float partialTicks);

    public abstract void handleMouseClick(int mouseX, int mouseY, int mouseButton);

    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        // No action by default
    }

    public void handleMouseScroll(double mouseX, double mouseY, double delta) {
        // No action by default
    }
}