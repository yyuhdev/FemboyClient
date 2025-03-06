package dev.yyuh.femboyclient.client.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.events.*;
import dev.yyuh.femboyclient.client.module.AstolfoHudModule;
import dev.yyuh.femboyclient.client.module.HideItemToolTipModule;
import dev.yyuh.femboyclient.client.module.NoArmorBarModule;
import dev.yyuh.femboyclient.client.module.NoFoodBarModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow public abstract void tick(boolean paused);

    @Shadow @Final private MinecraftClient client;
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "renderMainHud", at = @At("HEAD"))
    private void renderMainHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(mc.player == null) return;
        HudRenderEvent event = new HudRenderEvent(context, tickCounter, ci);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    public void changeItemDisplay(DrawContext context, CallbackInfo ci) {
        if(mc.player == null) return;
        ToolTipRenderEvent event = new ToolTipRenderEvent(context, ci);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        if(mc.player == null) return;
        RenderFoodEvent event = new RenderFoodEvent(context, player, top, right, ci);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        RenderHealthBarEvent event = new RenderHealthBarEvent(context, player, x, y, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, blinking, ci);
        FemboyClient.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @WrapWithCondition(
            method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderArmor(Lnet/minecraft/client/gui/" +
                            "DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIII)V"
            )
    )
    public boolean onGrabArmorAmount(DrawContext context, PlayerEntity player, int i, int j, int k, int x) {
        RenderArmorBarEvent event = new RenderArmorBarEvent(context, player, i, j, k, x);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) return false;
        return true;
    }
}
