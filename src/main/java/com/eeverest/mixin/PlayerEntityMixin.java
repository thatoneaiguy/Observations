package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinInner;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract ActionResult interact(Entity entity, Hand hand);

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void launchTarget(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty()) {
            if (target != null && target.isAlive()) {
                if (TraitComponent.get(player).hasTrait(Trait.WEEEE)) {
                    target.addVelocity(0, 2, 0);
                }
            }
        }
        if (TraitComponent.get(player).hasTrait(Trait.STRONG_HANDS_EVEN_STRONGER_MORALS)) {
            if (target instanceof LivingEntity living) {
                if (living.getMaxHealth() - living.getHealth() >= 1) {
                    ((LivingEntity) target).damage(target.getDamageSources().playerAttack(player), -1);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void disableNaturalRegeneration(CallbackInfo ci) {
        if (((PlayerEntity) (Object) this).getHealth() < ((PlayerEntity) (Object) this).getMaxHealth() && TraitComponent.get((PlayerEntity) (Object) this).hasTrait(Trait.SOULLESS_CREATURE)) {
            ci.cancel();
        }
    }
}
