package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RenderEntityEvent extends Event {
    private final Entity entity;
    private final double cameraX;
    private final double cameraY;
    private final double cameraZ;
    private final float tickDelta;
    private final MatrixStack matrices;
    private final VertexConsumerProvider vertexConsumers;
    private final CallbackInfo ci;

    public RenderEntityEvent(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        this.entity = entity;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.tickDelta = tickDelta;
        this.matrices = matrices;
        this.vertexConsumers = vertexConsumers;
        this.ci = ci;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getCameraX() {
        return cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public double getCameraZ() {
        return cameraZ;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public VertexConsumerProvider getVertexConsumers() {
        return vertexConsumers;
    }

    public CallbackInfo getCi() {
        return ci;
    }
}