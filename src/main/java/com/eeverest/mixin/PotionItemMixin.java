package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {
    @Inject(method = "getUseAction", at = @At("HEAD"), cancellable = true)
    private void noDrinking(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if (stack.getHolder() instanceof PlayerEntity player) {
            if (TraitComponent.get(player).hasTrait(Trait.NUTRITIONAL)) {
                cir.setReturnValue(UseAction.NONE);
            }
        }
    }
}
