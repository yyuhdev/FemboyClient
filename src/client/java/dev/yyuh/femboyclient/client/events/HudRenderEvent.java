package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HudRenderEvent extends Event {
    private final CallbackInfo ci;
    private final DrawContext context;
    private final RenderTickCounter tickCounter;

    public HudRenderEvent(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        this.context = context;
        this.ci = ci;
        this.tickCounter = tickCounter;
    }

    public DrawContext getDrawContext() {
        return this.context;
    }

    public RenderTickCounter getRenderTickCounter() {
        return this.tickCounter;
    }

    public CallbackInfo getCallBackInfo() {
        return this.ci;
    }

}
