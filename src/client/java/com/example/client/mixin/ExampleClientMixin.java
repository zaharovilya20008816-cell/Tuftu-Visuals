package com.example.client.mixin;

import com.example.client.ExampleModClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        ExampleModClient.onClientTick();
    }
}
