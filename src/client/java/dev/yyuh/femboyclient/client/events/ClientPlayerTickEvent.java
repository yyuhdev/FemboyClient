package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ClientPlayerTickEvent extends Event {
    private final CallbackInfo ci;

    public ClientPlayerTickEvent(CallbackInfo ci) {
        this.ci = ci;
    }

    public CallbackInfo getCallBackInfo() {
        return this.ci;
    }
}
