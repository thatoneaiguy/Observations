package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(BowItem.class)
public class BowMixin {
    private static final float SPEED_MULTIPLIER = 0.5f;

    @Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
    private void modifyBowChargeTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        PlayerEntity player = stack.getHolder() instanceof PlayerEntity ? (PlayerEntity) stack.getHolder() : null;

        if (player != null && TraitComponent.get(player) != null && TraitComponent.get(player).hasTrait(Trait.FASTEST_HANDS_IN_THE_SYSTEM)) {
            cir.setReturnValue((int) (cir.getReturnValue() * SPEED_MULTIPLIER));
        }

        if (player != null && TraitComponent.get(player).hasTrait(Trait.FASTEST_HANDS_IN_THE_SYSTEM)) {
            cir.setReturnValue(0);
        }
    }
}
