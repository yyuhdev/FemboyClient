package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class TriggerBotModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.trigger_bot";
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public TriggerBotModule() {
        super("Triggerbot", enabled);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
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
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if(!enabled) return;
        if (mc.player.getMainHandStack().isEmpty()) return;
        if (lookingAtSaidEntity()) {
            if (mc.player.getAttackCooldownProgress(0) == 1) {
                if (!lookingAt().isAlive()) return;
                mc.interactionManager.attackEntity(mc.player, lookingAt());
                mc.player.swingHand(mc.player.getActiveHand());
            }
        }
    }

    public static boolean isToggled() {
        return enabled;
    }

    private boolean lookingAtSaidEntity() {
        return mc.crosshairTarget instanceof EntityHitResult entity && (entity.getEntity() instanceof PlayerEntity);
    }

    private @Nullable Entity lookingAt() {
        return ((EntityHitResult) mc.crosshairTarget).getEntity();
    }
}