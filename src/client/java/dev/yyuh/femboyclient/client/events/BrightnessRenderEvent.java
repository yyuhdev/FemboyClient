package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class BrightnessRenderEvent extends Event {
    private final CallbackInfoReturnable<Float> cir;

    public BrightnessRenderEvent(CallbackInfoReturnable<Float> cir) {
        this.cir = cir;
    }

    public CallbackInfoReturnable<Float> getCir() {
        return cir;
    }
}
