package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import dev.yyuh.femboyclient.client.util.InventoryUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class FastAnchorModule extends Button {
    private static int state;
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.fast_anchor";
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public FastAnchorModule() {
        super("Fast Anchor", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
        state = 0;
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
    public Screen createSettingsPanel() {
        return null;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    public static boolean isToggled() {
        return enabled;
    }

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if(!enabled) return;

        HitResult hit = mc.crosshairTarget;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();

        if (mc.world.getBlockState(blockPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
            return;
        }

        if (state == 0) {
            InventoryUtils.selectItemFromHotbar(item -> item == Items.GLOWSTONE);
            if (!rightClick()) {
                return;
            }
            state++;
        } else if (state == 1) {
            InventoryUtils.selectItemFromHotbar(item -> item == Items.RESPAWN_ANCHOR);
            if (!rightClick()) {
                return;
            }
            state = 0;
        }
    }

    private static boolean rightClick() {
        HitResult hit = mc.crosshairTarget;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            return false;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHit);
        if (result == ActionResult.SUCCESS) {
            mc.player.swingHand(Hand.MAIN_HAND);
            return true;
        }
        return false;
    }
}