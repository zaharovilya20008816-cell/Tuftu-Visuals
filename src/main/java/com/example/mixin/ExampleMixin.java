package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class ExampleClientMixin {

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void modifySwordVisuals(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (ExampleModClient.customSwordViewModel && hand == Hand.MAIN_HAND && item.getItem() instanceof SwordItem) {
            matrices.translate(0.15f, -0.1f, -0.2f);
            matrices.scale(0.75f, 0.75f, 0.75f);
            if (swingProgress > 0.0F) {
                float angle = (float) Math.sin(swingProgress * Math.PI);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle * 35.0F));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle * -20.0F));
            }
        }
    }
}
