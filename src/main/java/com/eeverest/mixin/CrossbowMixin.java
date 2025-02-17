package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(CrossbowItem.class)
public class    CrossbowMixin {
    private static final float SPEED_MULTIPLIER = 0.01f; // 50% faster charging

    @Inject(method = "getPullTime", at = @At("RETURN"), cancellable = true)
    private static void modifyCrossbowLoadTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (!(stack.getHolder() instanceof PlayerEntity player)) return; // Ensure it's a player

        TraitComponent traitComponent = TraitComponent.get(player);
        if (traitComponent != null && traitComponent.hasTrait(Trait.FASTEST_HANDS_IN_THE_SYSTEM)) {
            cir.setReturnValue(0);
        }
    }
}
