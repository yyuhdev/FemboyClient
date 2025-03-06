package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RenderFoodEvent extends Event {
    private final DrawContext context;
    private final PlayerEntity player;
    private final int top;
    private final int right;
    private final CallbackInfo ci;

    public RenderFoodEvent(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        this.context = context;
        this.player = player;
        this.top = top;
        this.right = right;
        this.ci = ci;
    }

    public DrawContext getContext() {
        return context;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public CallbackInfo getCi() {
        return ci;
    }
}
