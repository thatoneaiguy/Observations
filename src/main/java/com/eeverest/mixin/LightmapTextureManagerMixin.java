package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyVariable(method = "update", at = @At("STORE"), ordinal = 6)
    private float enchancement$perception(float value) {
        if (TraitComponent.get(client.player).hasTrait(Trait.BLOOD_SCENT)) {
            return Math.max(1, value);
        }
        return value;
    }
}
