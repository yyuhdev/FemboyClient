package dev.yyuh.femboyclient.client.module;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.events.HudRenderEvent;
import dev.yyuh.femboyclient.client.events.RenderEntityEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.render.ESPRenderer;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import dev.yyuh.femboyclient.client.util.RotationUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.stream.StreamSupport;

public class KillAuraModule extends Button {

    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.killaura";
    public static MinecraftClient mc = MinecraftClient.getInstance();

    private final double range = 5.0;
    private final boolean critical = true;
    private final double cooldown = 1.0;

    private Entity target;

    public KillAuraModule() {
        super("KillAura", enabled);
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
        if (!enabled || mc.player == null || !mc.player.isAlive()) return;
        target = findTarget();
        if (target == null) return;
        if (mc.player.getAttackCooldownProgress(0) < cooldown) return;
        if (critical) doCrit();

        RotationUtils.fakeLookAtEntity(target);
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    @Subscribe
    public void onHudRender(HudRenderEvent event) {
        if (!enabled || target == null || !(target instanceof LivingEntity)) return;

        LivingEntity livingTarget = (LivingEntity) target;
        FemboyClient.HEALTH_INDICATOR.setTarget(livingTarget); // Set the target for the health indicator

        // Render the HudHealthDisplay
        FemboyClient.HEALTH_INDICATOR.getHudHealthDisplay().render(event.getDrawContext());
    }

    @Subscribe
    public void onRender(RenderEntityEvent event) {
        if (!enabled || target == null) return;

        Box box = target.getBoundingBox();
        ESPRenderer.Color4b color = new ESPRenderer.Color4b(255, 0, 0, 255);
        ESPRenderer.drawBox(box, color, 2.0f, 0xFFFF0000);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private Entity findTarget() {
        return StreamSupport.stream(mc.world.getEntities().spliterator(), true)
                .filter(e -> e != mc.player)
                .filter(e -> e instanceof LivingEntity)
                .filter(e -> ((LivingEntity) e).getHealth() > 0.0f)
                .filter(e -> e.squaredDistanceTo(mc.player) <= range * range)
                .min(Comparator.comparingDouble(e -> RotationUtils.getAngleToLookVec(e.getBoundingBox().getCenter())))
                .orElse(null);
    }

    private void doCrit() {
        if (mc.player.isTouchingWater() || mc.player.isInLava()) return;

        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, false));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
    }
}