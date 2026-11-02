package com.example.mixin.client;

import com.example.ExampleModClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ExampleClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ExampleModClient.onClientTick();
    }

    @Inject(method = "applyItemArmTransform", at = @At("TAIL"))
    private void modifyItemPosition(PoseStack poseStack, HumanoidArm arm, float equipProgress, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (ExampleModClient.swordVisuals && mc.player != null && arm == mc.player.getMainArm() && !mc.player.getMainHandItem().isEmpty()) {
            // Позиция меча из меню
            if (ExampleModClient.posMode == 0) poseStack.translate(0.18F, -0.12F, -0.25F);
            else if (ExampleModClient.posMode == 1) poseStack.translate(0.15F, -0.28F, -0.15F);
            else if (ExampleModClient.posMode == 2) poseStack.translate(-0.08F, -0.10F, -0.20F);

            // Размер меча из меню
            float[] scales = {0.60F, 0.75F, 0.90F, 1.15F};
            float s = scales[ExampleModClient.scaleMode];
            poseStack.scale(s, s, s);
        }
    }

    @Inject(method = "applyItemArmAttackTransform", at = @At("TAIL"))
    private void modifyItemSwing(PoseStack poseStack, HumanoidArm arm, float swingProgress, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (ExampleModClient.swordVisuals && mc.player != null && arm == mc.player.getMainArm() && !mc.player.getMainHandItem().isEmpty() && swingProgress > 0.0F) {
            float angle = (float) Math.sin(swingProgress * Math.PI);
            if (ExampleModClient.swingMode == 0) { // Плавный PvP
                poseStack.mulPose(Axis.YP.rotationDegrees(angle * 35.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(angle * -20.0F));
            } else if (ExampleModClient.swingMode == 1) { // Жесткий Слэш
                poseStack.mulPose(Axis.XP.rotationDegrees(angle * -55.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(angle * 60.0F));
            } else if (ExampleModClient.swingMode == 2) { // Спин 360
                poseStack.mulPose(Axis.ZP.rotationDegrees(swingProgress * 360.0F));
            }
        }
    }
}
