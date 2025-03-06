package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class BeginRenderTickEvent extends Event {
    private final long timeMillis;
    private final CallbackInfoReturnable<Integer> cir;

    public BeginRenderTickEvent(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        this.timeMillis = timeMillis;
        this.cir = cir;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public CallbackInfoReturnable<Integer> getCir() {
        return cir;
    }
}
