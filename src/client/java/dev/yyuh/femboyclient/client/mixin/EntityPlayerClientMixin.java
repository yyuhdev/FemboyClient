package dev.yyuh.femboyclient.client.mixin;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class EntityPlayerClientMixin  {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", ordinal = 0), method = "tick()V")
    private void useOwnTicks(CallbackInfo ci) {
        if(mc.player == null) return;
        ClientPlayerTickEvent event = new ClientPlayerTickEvent(ci);
        FemboyClient.EVENT_BUS.post(event);
        if(event.isCancelled()) ci.cancel();
    }
}
