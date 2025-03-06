package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ToolTipRenderEvent extends Event {
    private final DrawContext context;
    private final CallbackInfo ci;

    public ToolTipRenderEvent(DrawContext context, CallbackInfo ci) {
        this.context = context;
        this.ci = ci;
    }

    public DrawContext getDrawContext() {
        return this.context;
    }

    public CallbackInfo getCallBackInfo() {
        return this.ci;
    }
}
