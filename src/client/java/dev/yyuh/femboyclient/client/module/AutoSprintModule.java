package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public class AutoSprintModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.auto_sprint";
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public AutoSprintModule() {
        super("Auto Sprint", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    @Override
    public void save() {
        ConfigUtils.save(CONFIG_PATH, enabled);
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);
    }

    @Override
    public Screen createSettingsPanel() {
        return null;
    }

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if(!enabled) return;
        if (mc.options.forwardKey.isPressed()) mc.player.setSprinting(true);
        if (mc.options.sneakKey.isPressed()) mc.player.setSprinting(false);
    }


    public static boolean isToggled() {
        return enabled;
    }
}
