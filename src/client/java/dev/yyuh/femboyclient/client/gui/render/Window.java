package dev.yyuh.femboyclient.client.gui.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class Window {
    private final String title;
    private final List<Component> components;
    private int x, y, width, height;
    private boolean minimized;

    private static final int TITLE_BAR_HEIGHT = 20;
    private static final int PADDING = 5;
    private static final int COMPONENT_SPACING = 5;

    public Window(String title, List<Component> components) {
        this.title = title;
        this.components = components;
        this.width = 100;
        this.height = calculateHeight();
        this.minimized = false;
    }

    private int calculateHeight() {
        if (minimized) {
            return TITLE_BAR_HEIGHT;
        }
        int componentHeight = components.stream().mapToInt(Component::getHeight).sum();
        int spacing = COMPONENT_SPACING * (components.size() - 1);
        return TITLE_BAR_HEIGHT + componentHeight + spacing + 2 * PADDING;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        // Render the window background (translucent gray)
        int backgroundColor = 0x80333333; // 50% translucent gray (ARGB format: 0x80 for 50% transparency)
        context.fill(x, y, x + width, y + height, backgroundColor);

        // Render the pink border
        int borderColor = 0xFFFF69B4; // Pink color
        context.fill(x, y, x + width, y + 1, borderColor); // Top border
        context.fill(x, y + height - 1, x + width, y + height, borderColor); // Bottom border
        context.fill(x, y, x + 1, y + height, borderColor); // Left border
        context.fill(x + width - 1, y, x + width, y + height, borderColor); // Right border

        // Render the title
        context.drawText(MinecraftClient.getInstance().textRenderer, title, x + PADDING, y + 6, 0xFFFFFFFF, false);

        // Render the arrow in the right corner of the title bar
        String arrow = minimized ? "▼" : "▲"; // Down arrow when minimized, up arrow when not minimized
        int arrowX = x + width - PADDING - MinecraftClient.getInstance().textRenderer.getWidth(arrow); // Right-align the arrow
        int arrowY = y + 6; // Vertically center the arrow
        context.drawText(MinecraftClient.getInstance().textRenderer, arrow, arrowX, arrowY, 0xFFFFFFFF, false);

        // Render the components (buttons, etc.)
        if (!minimized) {
            int componentY = y + TITLE_BAR_HEIGHT + PADDING; // Start rendering components below the title bar with padding
            for (Component component : components) {
                component.setPosition(x + PADDING, componentY); // Add padding on the left
                component.render(context, mouseX, mouseY, partialTicks);

                componentY += component.getHeight() + COMPONENT_SPACING; // Add spacing between components
            }
        }
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        // Check if the title bar is clicked
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + TITLE_BAR_HEIGHT) {
            if (mouseButton == 0) { // Left click
                minimized = !minimized;
                this.height = calculateHeight(); // Update height when minimized/maximized
            }
        }

        // Handle clicks on components
        if (!minimized) {
            int componentY = y + TITLE_BAR_HEIGHT + PADDING; // Start of components (below the title bar with padding)
            for (Component component : components) {
                // Calculate the component's bounds
                int componentX = x + PADDING; // Add padding on the left
                int componentWidth = component.getWidth();
                int componentHeight = component.getHeight();

                // Check if the mouse is within the component's bounds
                if (mouseX >= componentX && mouseX <= componentX + componentWidth &&
                        mouseY >= componentY && mouseY <= componentY + componentHeight) {
                    // Translate mouse coordinates to component-local coordinates
                    int localMouseX = mouseX - componentX;
                    int localMouseY = mouseY - componentY;

                    // Pass the local coordinates to the component
                    component.handleMouseClick(localMouseX, localMouseY, mouseButton);
                }

                // Move to the next component's position
                componentY += componentHeight + COMPONENT_SPACING; // Add spacing between components
            }
        }
    }

    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (!minimized) {
            for (Component component : components) {
                component.handleMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void handleMouseScroll(double mouseX, double mouseY, double delta) {
        if (!minimized) {
            for (Component component : components) {
                component.handleMouseScroll(mouseX, mouseY, delta);
            }
        }
    }
}