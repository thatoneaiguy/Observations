package com.eeverest.cca;

import com.eeverest.Observations;
import com.eeverest.gui.Trait;
//import com.eeverest.render.HeatShader;
//import com.eeverest.render.ShittyShader;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.mixin.client.ShaderInstanceMixin;

import java.util.Random;
import java.util.UUID;

public class TraitComponent implements AutoSyncedComponent, CommonTickingComponent {
    private Trait trait1;
    private Trait trait2;
    private Trait trait3;
    private int cooldown;
    private final PlayerEntity player;

    private static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0538");
    private static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("e9b1c3fa-df91-4bfe-8d5b-3d1f3a7f9bd1");
    private static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("9f8e5cfb-b5a4-4f53-8f39-f6f71d2aaf3d");
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER = UUID.fromString("f8b44b35-1a62-4e0e-8dbd-bf2b9b6e5c5b");
    private static final UUID HP_MODIFIER = UUID.randomUUID();
    private static final UUID ARMOR_MODIFIER = UUID.randomUUID();

    private int timer = 0;

    public TraitComponent(PlayerEntity player) {
        this.player = player;
        this.cooldown = cooldown;
        this.trait1 = Trait.NONE;
        this.trait2 = Trait.NONE;
        this.trait3 = Trait.NONE;
    }

    public void sync() {
        Observations.TRAIT.sync(this.player);
    }

    public static TraitComponent get(@NotNull PlayerEntity player) {
        return Observations.TRAIT.get(player);
    }

    public void setTrait1(Trait trait1) {
        this.trait1 = trait1;
        sync();
    }

    public void setTrait2(Trait trait2) {
        this.trait2 = trait2;
        sync();
    }

    public void setTrait3(Trait trait3) {
        this.trait3 = trait3;
        sync();
    }

    public Trait getTrait1() {
        return trait1;
    }

    public Trait getTrait2() {
        return trait2;
    }

    public Trait getTrait3() {
        return trait3;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.trait1 = Trait.fromString(nbtCompound.getString("trait1key"));
        if (this.trait1 == null) this.trait1 = Trait.NONE;

        this.trait2 = Trait.fromString(nbtCompound.getString("trait2key"));
        if (this.trait2 == null) this.trait2 = Trait.NONE;

        this.trait3 = Trait.fromString(nbtCompound.getString("trait3key"));
        if (this.trait3 == null) this.trait3 = Trait.NONE;

        this.cooldown = nbtCompound.getInt("cooldown");
        sync();
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putString("trait1key", trait1.name);
        nbtCompound.putString("trait2key", trait2.name);
        nbtCompound.putString("trait3key", trait3.name);
        nbtCompound.putInt("cooldown", cooldown);
    }

    @Override
    public void tick() {
        if (player == null) return;

        applyDivineIntervention();
        applyGassedUp();
        applyFatass();
        applyHeavyHitter();
        applyTheif();
        applyMorality();
        matritxspeed();
        rafstone();
        rafWeakness();
        rafWantsToLive();
        goldArteries();
        applyGoldArmorEffects();
        smokedLungs();
        melon();
        noEnzymes();

        int random = player.getWorld().random.nextInt(40);
        if (random == 1) {
            player.setNoGravity(false);
        }


        if (hasTrait(Trait.MAGMA_COVERED)) {
            if (!player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                if (!player.getWorld().isClient) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 0, false, false));
                }
            }

            if (isInLava(player)) {
                if (player.isSprinting()) {
                    Vec3d look = player.getRotationVector();
                    double boostFactor = 0.1;
                    player.addVelocity(look.x * boostFactor, look.y * boostFactor, look.z * boostFactor);
                }
            }
        } else {
            //HeatShader.INSTANCE.setActive(false);
        } if (hasTrait(Trait.COMFORTING_WARMTH)) {
            if (!player.getWorld().isClient) {
                if (isInLava(player)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 1, false, false));
                }
                if (player.isOnFire()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 0, false, false));
                }
            }
        }
    }

    @Override
    public void clientTick() {
        if (hasTrait(Trait.MAGMA_COVERED)) {
            //HeatShader.INSTANCE.setActive(true);
        } else {
            //HeatShader.INSTANCE.setActive(false);
        }

        if (hasTrait(Trait.SHAPES_AND_COLOURS)) {
            //applyShapesAndColors();
        }
    }

    private boolean isInLava(PlayerEntity player) {
        World world = player.getEntityWorld();
        BlockPos pos = player.getBlockPos();
        return world.getFluidState(pos).getFluid() == Fluids.LAVA;
    }

    private void applyTheif() {
        PlayerEntity victim = getNearestPlayerInRange(player, player.getWorld(), 35);
        Trait trait;
        int random = player.getRandom().nextInt(3) + 1;
        int chance = player.getRandom().nextInt(1200) + 1;

        if (victim != null && victim != player) {
            if (hasTrait(Trait.CRESCENT_THIEF)) {
                if (chance == 1) {
                    switch (random) {
                        case 1:
                            trait = TraitComponent.get(victim).getTrait1();
                            setTrait2(trait);
                            break;
                        case 2:
                            trait = TraitComponent.get(victim).getTrait2();
                            setTrait2(trait);
                            break;
                        case 3:
                            trait = TraitComponent.get(victim).getTrait3();
                            setTrait2(trait);
                            break;
                    }
                }
            }
        } else {
            setTrait2(Trait.NONE);
        }
    }

    private void noEnzymes() {
        if (hasTrait(Trait.NO_ENZYMES)) {
            player.getHungerManager().setFoodLevel(20);
            if (!player.getWorld().isClient) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2, true, false));
            }
        }
    }

    public static PlayerEntity getNearestPlayerInRange(PlayerEntity targetPlayer, World world, double radius) {
        PlayerEntity nearestPlayer = null;
        double nearestDistanceSquared = Double.MAX_VALUE;

        for (PlayerEntity player : world.getPlayers()) {
            double distanceSquared = player.squaredDistanceTo(targetPlayer); // Get squared distance

            if (distanceSquared <= radius * radius && distanceSquared < nearestDistanceSquared) {
                nearestPlayer = player;
                nearestDistanceSquared = distanceSquared;
            }
        }

        return nearestPlayer;
    }

    public boolean isExposedToSun(PlayerEntity player) {
        World world = player.getEntityWorld();
        BlockPos playerPos = player.getBlockPos();
        boolean isDaytime = world.isDay();
        int lightLevel = world.getLightLevel(playerPos.up());
        boolean isInSunlight = lightLevel >= 15;
        boolean isNotUnderBlock = !world.getBlockState(playerPos.up()).isOpaqueFullCube(world, playerPos.up());

        return isDaytime && isInSunlight && isNotUnderBlock;
    }

    private void applyMorality() {
        if (isExposedToSun(player) && hasTrait(Trait.STRONG_HANDS_EVEN_STRONGER_MORALS)) {
            updateAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 1.3);
        }
    }

    private void melon() {
        if (hasTrait(Trait.ACROBATICS)) {
            if (!player.getWorld().isClient) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
            }
            updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, 1.2);
        }
    }

    private void rafstone() {
        if (hasTrait(Trait.STRONGER_THAN_STONE)) {
            updateAttribute(EntityAttributes.GENERIC_MAX_HEALTH, HP_MODIFIER, 1.4);
            updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, 0.25);
        }
    }

    private void rafWantsToLive() {
        if (hasTrait(Trait.WILL_TO_LIVE_ON)) {
            double currentHealth = player.getHealth();
            double maxHealth = player.getMaxHealth();
            double lostHealth = maxHealth - currentHealth;

            double statBoost = Math.min(0.04 * lostHealth, 2.0);

            updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, statBoost);
            updateAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER, statBoost);
            updateAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, statBoost);
        }
    }

    private void rafWeakness() {
        if (hasTrait(Trait.INFECTION)) {
            if (player.isInLava()) {
                player.damage(player.getDamageSources().inFire(), (float)(5.0 * 1.5));
            }
            if (player.isFrozen()) {
                int random = player.getRandom().nextBetween(1, 100);
                if (random >= 33) {
                    player.heal(0.5f);

                }
            }
        }
    }

    private void smokedLungs() {
        if (hasTrait(Trait.SMOKED_LUNGS)) {
            if (cooldown > 10) {
                cooldown++;
            }
            else {
                if (player.getAir() > 0) {
                    player.setAir(player.getAir() - 2);
                }
            }
        }
    }

    private void goldArteries() {
        if (hasTrait(Trait.AURIC_ARTERIES)) {
            updateAttribute(EntityAttributes.GENERIC_ARMOR, ARMOR_MODIFIER, 1.2);
        }
    }

    private void applyGoldArmorEffects() {
        if (hasTrait(Trait.PRIVILEGED_PLATES)) {
            ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

            if (helmet.getItem() instanceof ArmorItem && ((ArmorItem) helmet.getItem()).getMaterial() == ArmorMaterials.GOLD) {
                int chance = player.getRandom().nextInt(40) + 1;
                if (chance == 1) player.heal(0.5f);

            }

            if (chestplate.getItem() instanceof ArmorItem && ((ArmorItem) chestplate.getItem()).getMaterial() == ArmorMaterials.GOLD) {
                if (player.isWet()) {
                    player.setVelocity(player.getVelocity().x, -0.5, player.getVelocity().z);
                }
            }

            if (leggings.getItem() instanceof ArmorItem && ((ArmorItem) leggings.getItem()).getMaterial() == ArmorMaterials.GOLD) {
                if (player.isSprinting()) {
                    player.addVelocity(player.getVelocity().x * 1.5, player.getVelocity().y, player.getVelocity().z * 1.5);
                }
            }

            if (boots.getItem() instanceof ArmorItem && ((ArmorItem) boots.getItem()).getMaterial() == ArmorMaterials.GOLD) {
                if (player.fallDistance > 0 && player.fallDistance > 2) {
                    player.setNoGravity(true);
                }
            }
        }
    }

    private void matritxspeed() {
        if (hasTrait(Trait.ACCELERATION_MATRIX)) {
            double currentSpeed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);

            if (currentSpeed < 3.0) {
                EntityAttributeModifier speedModifier = new EntityAttributeModifier(MOVEMENT_SPEED_MODIFIER, "Frictionless Speed Boost", 0.05, EntityAttributeModifier.Operation.ADDITION);
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(speedModifier);

                // If the player's speed exceeds the max speed, set it to the max
                if (currentSpeed + 0.05 > 3.0) {
                    player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(3.0);
                }
            }
        }
    }

        private void applyDivineIntervention() {
        boolean hasDivine = hasTrait(Trait.DIVINE_INTERVENTION);
        boolean hasAdrenaline = hasTrait(Trait.ADRENALINE) && player.getHealth() <= 6;

        double multiplier = hasAdrenaline ? 0.25 : hasDivine ? 0.15 : 0.0;

        updateAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, multiplier);
        updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, multiplier);
        updateAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER, multiplier);
    }

    private void applyShapesAndColors() {
        if (hasTrait(Trait.SHAPES_AND_COLOURS)) {
            timer++;

            if (timer >= 300 * 20 && timer <= 360 * 20) {
                if (new Random().nextBoolean()) {
                    //ShittyShader.INSTANCE.setActive(true);
                }
            }

            if (timer > 360) {
                timer = 0;
                //ShittyShader.INSTANCE.setActive(false);
            }
        }
    }

    private void applyGassedUp() {
        if (hasTrait(Trait.GASSED_UP)) {
            updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, 0.2);
        }
    }

    private void applyHeavyHitter() {
        if (hasTrait(Trait.HEAVY_HITTER)) {
            updateAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 1.3);
            updateAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 0.5);
        }
    }

    private void applyFatass() {
        if (hasTrait(Trait.WEIGHED_DOWN)) {
            updateAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_MODIFIER, 200);
            updateAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER, 0.85);
        }
    }

    private void updateAttribute(EntityAttribute attribute, UUID modifierId, double multiplier) {
        if (player.getAttributeInstance(attribute) == null) return;

        player.getAttributeInstance(attribute).removeModifier(modifierId);
        if (multiplier > 0.0) {
            player.getAttributeInstance(attribute).addPersistentModifier(
                    new EntityAttributeModifier(modifierId, "Trait Bonus", multiplier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
    }

    public boolean hasTrait(Trait trait) {
        return trait1 == trait || trait2 == trait || trait3 == trait;
    }
}
