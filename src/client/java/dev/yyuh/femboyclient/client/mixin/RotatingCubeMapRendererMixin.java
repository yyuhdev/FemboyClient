package dev.yyuh.femboyclient.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RotatingCubeMapRenderer.class)
public class RotatingCubeMapRendererMixin {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of("femboyclient", "background.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, int width, int height, float alpha, float tickDelta, CallbackInfo ci) {
        int textureWidth = 1920;
        int textureHeight = 1080;
        context.drawTexture(BACKGROUND_TEXTURE, 0, 0, 0, 0, width, height, textureWidth, textureHeight);
        ci.cancel();
    }
}
