package dev.yyuh.femboyclient.client.mixin;

import dev.yyuh.femboyclient.client.module.ItemSizeModule;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD")
    )
    private void beforeRenderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
                                  MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                  int light, int overlay, BakedModel model, CallbackInfo ci) {
        if(!ItemSizeModule.isToggled()) return;
        if (stack.isEmpty()) return;

        if (renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND ||
                renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND ||
                renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND ||
                renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {

            matrices.push();
            matrices.scale(0.7f, 0.7f, 0.7f);
        }
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("RETURN")
    )
    private void afterRenderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
                                 MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                 int light, int overlay, BakedModel model, CallbackInfo ci) {
        if(!ItemSizeModule.isToggled()) return;
        if (stack.isEmpty()) return;

        if (renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND ||
                renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND ||
                renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND ||
                renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {

            matrices.pop();
        }
    }
}