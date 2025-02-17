package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ExponentialEatingMixin {
    @Unique
    private static int foodEaten = 1;
    @Unique
    private static long lastEatTime = 0;

    @ModifyReturnValue(method = "getMaxUseTime", at = @At("RETURN"))
    private int modifyEatingSpeed(int original) {
        if (!((ItemStack) (Object) this).isFood()) { // Ensure its food
            return original;
        }

        if (!(((ItemStack) (Object) this).getHolder() instanceof PlayerEntity player && TraitComponent.get(player).hasTrait(Trait.VOID))) {
            return original;
        }

        if (foodEaten > 16) {
            foodEaten = 1;
        }

        int newTime = (int) (original * Math.pow(0.9, foodEaten));
        foodEaten++;
        System.out.println(newTime);
        return newTime;
    }
}
