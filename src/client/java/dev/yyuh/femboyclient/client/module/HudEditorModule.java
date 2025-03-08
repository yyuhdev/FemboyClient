package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.gui.HudEditorScreen;
import dev.yyuh.femboyclient.client.gui.render.Button;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/*

I just made this so there's a button in the ClickGUI type shit

 */
public class HudEditorModule extends Button {

    public HudEditorModule() {
        super("Open HUD Editor", false);
    }

    @Override
    public void onClick() {
        MinecraftClient.getInstance().setScreen(new HudEditorScreen());
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

    @Override
    public Screen createSettingsPanel() {
        return null;
    }
}
