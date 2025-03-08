package dev.yyuh.femboyclient.client.gui.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public abstract class Button extends Component {
    public final String label;
    public boolean toggled;

    public Button(String label, boolean toggled) {
        super(0, 0, 90, 20);
        this.label = label;
        this.toggled = toggled;
        load();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        int backgroundColor = toggled ? 0x80FF69B4 : 0x80333333;
        context.fill(x, y, x + width, y + height, backgroundColor);
        int borderColor = toggled ? 0xFFFF69B4 : 0xFF808080;
        context.fill(x, y, x + width, y + 1, borderColor);
        context.fill(x, y + height - 1, x + width, y + height, borderColor);
        context.fill(x, y, x + 1, y + height, borderColor);
        context.fill(x + width - 1, y, x + width, y + height, borderColor);

        if(isHovered(mouseX, mouseY)) context.fill(x, y, x + width, y + height, 0x20FFFFFF);

        context.drawText(MinecraftClient.getInstance().textRenderer, label, x + 4, y + 6, 0xFFFFFFFF, false);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= 0 && mouseX <= width && mouseY >= 0 && mouseY <= height) {
            if (mouseButton == 0) {
                onClick();
                save();
            } else if (mouseButton == 1) {

                if(createSettingsPanel() == null) return;
                MinecraftClient.getInstance().setScreen(createSettingsPanel());
            }
        }
    }


    @Override
    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void handleMouseScroll(double mouseX, double mouseY, double delta) {
    }

    public abstract void onClick();
    public abstract void save();
    public abstract void load();
    public abstract Screen createSettingsPanel();
}