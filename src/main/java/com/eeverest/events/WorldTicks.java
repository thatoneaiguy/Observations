package com.eeverest.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.*;

public class WorldTicks {
    private static final UUID NICKEL = UUID.fromString("a7d1440e-da2f-4d7b-a33d-cf615ca7a0c2");
    private static final UUID VANNIE = UUID.fromString("c58ec595-82d5-476c-a5fc-be369f6708a2");
    private static final UUID PIPO = UUID.fromString("80b7c27a-0f55-4237-a213-a998a67dbf10");

    private static final Map<UUID, Double> originalSpeeds = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(WorldTicks::nickelTickEvents);
        ServerTickEvents.END_WORLD_TICK.register(WorldTicks::vannieTickEvents);
        ServerTickEvents.END_WORLD_TICK.register(WorldTicks::pipoTickEvents);
    }

    private static void vannieTickEvents(ServerWorld world) {
        ServerPlayerEntity targetPlayer = world.getServer().getPlayerManager().getPlayer(VANNIE);
        if (targetPlayer == null) return; // Player is offline

        if (targetPlayer.getVehicle() instanceof HorseEntity horse) {
            EntityAttributeInstance speedAttribute = horse.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

            if (speedAttribute != null) {
                UUID horseUUID = horse.getUuid();
                if (!originalSpeeds.containsKey(horseUUID)) {
                    // Store the original speed before modifying
                    originalSpeeds.put(horseUUID, speedAttribute.getBaseValue());
                }

                // Apply the 10% speed boost
                double newSpeed = originalSpeeds.get(horseUUID) + 0.15;
                speedAttribute.setBaseValue(newSpeed);
            }
        } else {
            // Define a bounding box around all loaded players to reduce unnecessary entity checks

            Box searchBox = new Box(-10000, -10000, -10000, 10000, 10000, 10000); // Covers all loaded chunks

            // Reset modified horse speeds
            world.getEntitiesByClass(HorseEntity.class , searchBox, horse -> originalSpeeds.containsKey(horse.getUuid()))
                    .forEach(horse -> {
                        EntityAttributeInstance speedAttribute = horse.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                        if (speedAttribute != null) {
                            UUID horseUUID = horse.getUuid();
                            speedAttribute.setBaseValue(originalSpeeds.get(horseUUID)); // Restore original speed
                            originalSpeeds.remove(horseUUID);
                        }
                    });
        }
    }

    private static void nickelTickEvents(ServerWorld world) {
        ServerPlayerEntity targetPlayer = world.getServer().getPlayerManager().getPlayer(NICKEL);
        if (targetPlayer == null) return;

        for (MobEntity mob : world.getEntitiesByClass(MobEntity.class, targetPlayer.getBoundingBox().expand(30), entity -> true)) {
            if (isUnderwaterMob(mob)) {
                disableAggro(mob, targetPlayer);
            } else if (mob instanceof DolphinEntity) {
                makeDolphinHostile((DolphinEntity) mob, targetPlayer);
            }
        }
    }

    private static void pipoTickEvents(ServerWorld world) {
        ServerPlayerEntity targetPlayer = world.getServer().getPlayerManager().getPlayer(PIPO);
        if (targetPlayer == null) return;

        for (MobEntity mob : world.getEntitiesByClass(MobEntity.class, targetPlayer.getBoundingBox().expand(30), entity -> true)) {
            if (isNetherMob(mob)) {
                disableAggro(mob, targetPlayer);
            }
        }
    }

    private static boolean isUnderwaterMob(MobEntity entity) {
        return entity.getType() == EntityType.GUARDIAN ||
                entity.getType() == EntityType.ELDER_GUARDIAN ||
                entity.getType() == EntityType.DROWNED ||
                entity.getType() == EntityType.COD ||
                entity.getType() == EntityType.SALMON ||
                entity.getType() == EntityType.PUFFERFISH ||
                entity.getType() == EntityType.TROPICAL_FISH ||
                entity.getType() == EntityType.SQUID ||
                entity.getType() == EntityType.GLOW_SQUID;
    }

    private static boolean isNetherMob(MobEntity entity) {
        return entity.getType() == EntityType.BLAZE
                || entity.getType() == EntityType.GHAST
                || entity.getType() == EntityType.MAGMA_CUBE
                || entity.getType() == EntityType.WITHER_SKELETON
                || entity.getType() == EntityType.ZOMBIFIED_PIGLIN
                || entity.getType() == EntityType.PIGLIN_BRUTE
                || entity.getType() == EntityType.PIGLIN;
    }

    private static void disableAggro(MobEntity mob, ServerPlayerEntity player) {
        if (mob instanceof HostileEntity hostileMob && hostileMob.getTarget() == player) {
            hostileMob.setTarget(null);
        }
    }

    private static void makeDolphinHostile(DolphinEntity dolphin, ServerPlayerEntity player) {
        if (dolphin.getTarget() == null || !dolphin.getTarget().equals(player)) {
            dolphin.setTarget(player);
        }
    }
}
