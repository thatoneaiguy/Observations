package com.eeverest.mixin;

import com.eeverest.Observations;
import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("RETURN"))
    private static void applyFogModifyDistance(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo info) {
        final CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        final Entity entity = camera.getFocusedEntity();

        if (entity instanceof PlayerEntity player) {
            if (TraitComponent.get(player).hasTrait(Trait.MAGMA_COVERED) && entity.isInLava() ) {
                RenderSystem.setShaderFogStart(Observations.FOG_START);
                RenderSystem.setShaderFogEnd(Observations.FOG_END);
                RenderSystem.setShaderFogShape(FogShape.CYLINDER);
            }
        }
    }
}
