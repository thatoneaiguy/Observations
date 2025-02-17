package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.fabric.mixin.dimension.EntityMixin;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {
//    @Unique
//    private static final float DEFAULT_SLIPPERINESS = 0.996F;
//
//    @ModifyReturnValue(method = "getSlipperiness", at = @At("RETURN"))
//    public float eatmyass$oilSlipperiness(float original) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        PlayerEntity player = client.player;
//
//        if (player == null) {
//            return original;
//        }
//
//        boolean TRAITBOOLEAN = TraitComponent.get(player).hasTrait(Trait.SLIPPERY);
//
//        return TRAITBOOLEAN ? DEFAULT_SLIPPERINESS : original;
//    }
}