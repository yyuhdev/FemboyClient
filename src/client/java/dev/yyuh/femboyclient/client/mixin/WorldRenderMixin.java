package dev.yyuh.femboyclient.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.events.RenderEntityEvent;
import dev.yyuh.femboyclient.client.render.ESPRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderMixin {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "renderEntity", at = @At("HEAD"))
    private void injectOutlineESP(
            Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci
    ) {
        if(mc.player == null) return;
        RenderEntityEvent event = new RenderEntityEvent(entity, cameraX, cameraY, cameraZ, tickDelta, matrices, vertexConsumers, ci);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) ci.cancel();
    }
}
