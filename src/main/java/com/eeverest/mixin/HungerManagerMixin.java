package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
    private void noEating(Item item, ItemStack stack, CallbackInfo ci) {
        if (stack.getHolder() instanceof PlayerEntity player) {
            if (TraitComponent.get(player).hasTrait(Trait.NUTRITIONAL) || TraitComponent.get(player).hasTrait(Trait.STRONG_HANDS_EVEN_STRONGER_MORALS)) {
                ci.cancel();
            }
        }
    }
}
