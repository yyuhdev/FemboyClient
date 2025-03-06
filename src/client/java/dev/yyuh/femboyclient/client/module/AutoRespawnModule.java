package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class AutoRespawnModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.auto_respawn";
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public AutoRespawnModule() {
        super("Auto Respawn", enabled);
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

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if(!enabled) return;
        if (mc.player.isDead()) mc.player.requestRespawn();
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);
    }

    public static boolean isToggled() {
        return enabled;
    }
}
