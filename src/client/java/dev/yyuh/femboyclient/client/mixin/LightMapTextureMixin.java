package dev.yyuh.femboyclient.client.mixin;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.events.BrightnessRenderEvent;
import dev.yyuh.femboyclient.client.events.UseOnBlockEvent;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightMapTextureMixin {

    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> cir) {
        BrightnessRenderEvent event = new BrightnessRenderEvent(cir);
        FemboyClient.EVENT_BUS.post(event);
    }
}