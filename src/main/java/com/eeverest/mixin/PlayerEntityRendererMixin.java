package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void onRenderLeftArm(AbstractClientPlayerEntity player, CallbackInfo ci, @Local PlayerEntityModel playerEntityModel) {
        if (TraitComponent.get(player).hasTrait(Trait.DISARMED)) {
            playerEntityModel.leftArm.visible = false;
            playerEntityModel.leftSleeve.visible = false;
        }
    }
}
