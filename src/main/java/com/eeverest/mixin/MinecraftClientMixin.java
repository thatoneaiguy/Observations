package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Unique
    private static final UUID TARGET_UUID = UUID.fromString("9a5abccf-5013-423d-b137-453b13f07cab");
    @Unique
    private static final int LOOK_TIME_THRESHOLD = 200;

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    private int lookingTicks = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        if (client.world == null || client.player == null) return;

        ClientPlayerEntity player = client.player;
        PlayerEntity target = client.world.getPlayerByUuid(TARGET_UUID);

        if (!TraitComponent.get(player).hasTrait(Trait.INFINITE_POOLS)) return;

        if (target == null) {
            lookingTicks = 0;
            return;
        }

        // Check if player is looking at the target's head
        if (isLookingAtHead(player, target)) {
            lookingTicks++;
        } else {
            lookingTicks = 0;
        }

        // After 10 seconds of looking, apply effects
        if (lookingTicks >= LOOK_TIME_THRESHOLD) {
            slowDownPlayer(player);
        }
    }

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void traitmod$clientGlow(Entity entityy, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (player != null && entityy instanceof PlayerEntity living && !living.isSneaking() && !living.isInvisible() && entityy.distanceTo(player) < 64 && TraitComponent.get(player).hasTrait(Trait.BLOOD_SCENT) && living.isWet()) {
            cir.setReturnValue(true);
        }

        if (player != null && entityy instanceof PlayerEntity living && !living.isSneaking() && !living.isInvisible() && entityy.distanceTo(player) < 64 && TraitComponent.get(player).hasTrait(Trait.LINE_OF_SIGHT)) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private boolean isLookingAtHead(ClientPlayerEntity player, PlayerEntity target) {
        Vec3d playerPos = player.getEyePos();
        Vec3d targetPos = target.getPos().add(0, target.getStandingEyeHeight(), 0); // Target's head position
        Vec3d direction = targetPos.subtract(playerPos).normalize();

        Vec3d playerDirection = Vec3d.fromPolar(player.getPitch(), player.getYaw());

        return playerDirection.dotProduct(direction) > 0.98; // Threshold for "looking"
    }

    @Unique
    private void slowDownPlayer(ClientPlayerEntity player) {
        StatusEffectInstance slow = new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0, true, false);
        player.addStatusEffect(slow);
    }
}