package dev.yyuh.femboyclient.client.mixin;

import dev.yyuh.femboyclient.client.gui.CustomTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At(
            value = "INVOKE",
            target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;",
            ordinal = 1),
            cancellable = true)
    private void getClientTitle(CallbackInfoReturnable<String> callback) {
        callback.setReturnValue("FemboyClient - Minecraft 1.21.1");
    }

//    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
//    private void onSetScreen(Screen screen, CallbackInfo ci) {
//        if (screen instanceof TitleScreen) {
//            ci.cancel();
//            MinecraftClient.getInstance().setScreen(new CustomTitleScreen());
//        }
//    }
}