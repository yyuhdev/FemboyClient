package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public class FlightModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.flight";
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public FlightModule() {
        super("Flight", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;

        if(!enabled) {
            MinecraftClient.getInstance().player.getAbilities().flying = false;
        }
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

        if(mc.player == null) return;
        if(mc.player.isSwimming()) return;
        if(mc.player.isDead()) return;

        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().flying = true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    public static boolean isToggled() {
        return enabled;
    }
}
