package dev.yyuh.femboyclient.client.gui;

import dev.yyuh.femboyclient.client.gui.render.HudElement;
import dev.yyuh.femboyclient.client.module.AstolfoHudModule;
import dev.yyuh.femboyclient.client.module.HackListModule;
import dev.yyuh.femboyclient.client.module.submodules.HealthIndicator;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HudEditorScreen extends Screen {

    private final List<HudElement> hudElements = new ArrayList<>();
    private HudElement selectedElement = null;

    public HudEditorScreen() {
        super(Text.of("HUD Editor"));
    }

    @Override
    protected void init() {
        hudElements.add(AstolfoHudModule.ASTOLFO_HUD_ELEMENT);
        hudElements.add(HackListModule.HACK_LIST);
        hudElements.add(HealthIndicator.HEALTH_DISPLAY);
    }

    @Override
    public void close() {
        HealthIndicator.save();

        ConfigUtils.saveModuleStateAndPosition(HackListModule.CONFIG_PATH, HackListModule.enabled, HackListModule.HACK_LIST.getX(), HackListModule.HACK_LIST.getY());
        ConfigUtils.saveModuleStateAndPosition(AstolfoHudModule.CONFIG_PATH, AstolfoHudModule.enabled, AstolfoHudModule.ASTOLFO_HUD_ELEMENT.getX(), AstolfoHudModule.ASTOLFO_HUD_ELEMENT.getY());

        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        for (HudElement element : hudElements) {
            element.render(context);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (HudElement element : hudElements) {
            if (element.isMouseOver((int) mouseX, (int) mouseY)) {
                selectedElement = element;
                element.startDragging((int) mouseX, (int) mouseY);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (selectedElement != null) {
            selectedElement.stopDragging();
            selectedElement = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (selectedElement != null) {
            selectedElement.updatePosition((int) mouseX, (int) mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}