package com.eeverest.mixin;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private static final TagKey<Item> DISARMED_DROPPABLE = TagKey.of(Registries.ITEM.getKey(), new Identifier("observations", "disarmed_droppable"));

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0538");
    private static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("e9b1c3fa-df91-4bfe-8d5b-3d1f3a7f9bd1");
    private static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("9f8e5cfb-b5a4-4f53-8f39-f6f71d2aaf3d");
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER = UUID.fromString("f8b44b35-1a62-4e0e-8dbd-bf2b9b6e5c5b");

    @Inject(method = "isUsingItem", at = @At("HEAD"), cancellable = true)
    private void preventEatingDrinking(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ItemStack stack = entity.getActiveItem();

        if (entity instanceof PlayerEntity player) {
            if (TraitComponent.get(player).hasTrait(Trait.NUTRITIONAL)) {
                if (stack.isFood() || stack.getItem() instanceof PotionItem) {
                    cir.setReturnValue(false);
                }
            } if (TraitComponent.get(player).hasTrait(Trait.DISARMED)) {
                if (stack.isIn(DISARMED_DROPPABLE)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "attackLivingEntity", at = @At("HEAD"), cancellable = true)
    private void preventAttackingWithTaggedItem(@NotNull LivingEntity target, CallbackInfo ci) {
        LivingEntity attacker = target.getAttacker();

        if (attacker instanceof PlayerEntity player) {
            ItemStack stack = attacker.getMainHandStack();
            if (TraitComponent.get(player).hasTrait(Trait.DISARMED)) {
                if (stack.isIn(DISARMED_DROPPABLE)) {
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void traitmod$matrixSpeed(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof PlayerEntity player) {
            boolean ACCELERATION_MATRIX = TraitComponent.get(player).hasTrait(Trait.ACCELERATION_MATRIX);
            if (ACCELERATION_MATRIX) {



                if (player.getVelocity().x > 0.9 || player.getVelocity().z > 0.9) {
                    updateAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER, EntityAttributes.GENERIC_ATTACK_SPEED.getDefaultValue() * 3);
                }
                if (player.getVelocity().x > 1.35 || player.getVelocity().z > 1.35) {
                    Box playerBox = player.getBoundingBox().expand(1.5);

                    for (Entity checkedEntity : player.getWorld().getEntitiesByClass(Entity.class, playerBox, e -> e != player)) {
                        double collisionDamage = player.getVelocity().length() * 1.5;
                        entity.damage(player.getDamageSources().playerAttack(player), (float) collisionDamage);

                        Vec3d knockback = checkedEntity.getPos().subtract(player.getPos()).normalize().multiply(collisionDamage * 0.5);
                        checkedEntity.addVelocity(knockback.x, knockback.y, knockback.z);
                    }
                }
                if (player.getVelocity().x > 0.9 || player.getVelocity().z > 2.025) {
                    if (player.getWorld().getBlockState(player.getBlockPos()).getBlock() == Blocks.WATER || player.getWorld().getBlockState(player.getBlockPos()).getBlock() == Blocks.LAVA) {
                        player.setVelocity(player.getVelocity().multiply(1, 0, 1));
                    }
                }
            }
        }
    }

    @Unique
    private void updateAttribute(EntityAttribute attribute, UUID modifierId, double multiplier) {
        LivingEntity player = (LivingEntity) (Object) this;

        if (player.getAttributeInstance(attribute) == null) return;

        player.getAttributeInstance(attribute).removeModifier(modifierId);
        if (multiplier > 0.0) {
            player.getAttributeInstance(attribute).addPersistentModifier(
                    new EntityAttributeModifier(modifierId, "Trait Bonus", multiplier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
    }

    @WrapOperation(method = {"travel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F")})
    public float eatmyass$oilSlipperiness(Block instance, Operation<Float> original) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof PlayerEntity player) {
            boolean SLIPPERY = TraitComponent.get(player).hasTrait(Trait.SLIPPERY);
            if (SLIPPERY) {
                return 0.996f;
            }
        }
        else {
            return original.call(instance);
        }
        return original.call(instance);
    }

    /**
     * @author medow
     * @reason mdow
     */
    @Overwrite
    public float getStepHeight() {
        LivingEntity entity = (LivingEntity) (Object) this;

        float f = super.getStepHeight();
        if (entity instanceof PlayerEntity player && TraitComponent.get(player).hasTrait(Trait.TALL)) return 1.5f;

        return entity.getControllingPassenger() instanceof PlayerEntity ? Math.max(f, 1.F) : f;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void slowlyGainHunger(CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity player) {
            if (TraitComponent.get(player).hasTrait(Trait.PHOTOSYNTHESIS) || TraitComponent.get(player).hasTrait(Trait.NUTRITIONAL)) {
                if (TraitComponent.get(player).hasTrait(Trait.PHOTOSYNTHESIS)) {
                    if (isExposedToSun()) {
                        Random random = player.getRandom();
                        int chance = random.nextInt(1200);
                        if (player.getHungerManager().getFoodLevel() < 20 && chance == 1) { // 1 in 1200 chance per tick (~1 bar per minute)
                            player.getHungerManager().add(2, 0.0f);
                        }
                    }
                } else {
                    Random random = player.getRandom();
                    int chance = random.nextInt(1200);
                    if (player.getHungerManager().getFoodLevel() < 20 && chance == 1) { // 1 in 1200 chance per tick (~1 bar per minute)
                        player.getHungerManager().add(2, 0.0f);
                    }
                }
            }
        }
    }

    @Unique
    public boolean isExposedToSun() {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getEntityWorld();
        BlockPos playerPos = player.getBlockPos();
        boolean isDaytime = world.isDay();
        int lightLevel = world.getLightLevel(playerPos.up());
        boolean isInSunlight = lightLevel >= 15;
        boolean isNotUnderBlock = !world.getBlockState(playerPos.up()).isOpaqueFullCube(world, playerPos.up());

        return isDaytime && isInSunlight && isNotUnderBlock;
    }
}

