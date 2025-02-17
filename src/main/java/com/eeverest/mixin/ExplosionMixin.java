package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(TntEntity.class)
public abstract class ExplosionMixin {
    @Inject(method = "explode", at = @At("HEAD"))
    private void increaseTntExplosionStrength(CallbackInfo ci) {
        TntEntity tntEntity = (TntEntity) (Object) this;

        Entity igniter = tntEntity.getOwner();

        if (igniter instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) igniter;
            if (TraitComponent.get(player).hasTrait(Trait.THE_ROCKETS_RED_GLARE)) {
                tntEntity.getWorld().createExplosion(tntEntity, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), 6.0f, World.ExplosionSourceType.TNT);
            }
        }
    }
}