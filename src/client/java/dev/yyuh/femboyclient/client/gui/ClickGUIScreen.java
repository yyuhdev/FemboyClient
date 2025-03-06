package dev.yyuh.femboyclient.client.gui;

import dev.yyuh.femboyclient.client.FemboyClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGUIScreen extends Screen {

    public ClickGUIScreen() {
        super(Text.of("Click GUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        FemboyClient.clickGui.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        FemboyClient.clickGui.handleMouseClick((int) mouseX, (int) mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        FemboyClient.clickGui.handleMouseRelease((int) mouseX, (int) mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
