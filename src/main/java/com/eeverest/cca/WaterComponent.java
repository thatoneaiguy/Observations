package com.eeverest.cca;

import com.eeverest.Observations;
import com.eeverest.gui.Trait;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.util.profiler.Sampler;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class WaterComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    boolean glow = false;

    public WaterComponent(PlayerEntity player) {
        this.player = player;
    }

    public static WaterComponent get(@NotNull PlayerEntity player) {
        return Observations.WATER.get(player);
    }

    public boolean isWet(PlayerEntity player) {
        return player.isWet();
    }

    @Override
    public void tick() {
        World world = player.getWorld();

        glow = player.isWet();

        if (isWet(player) && TraitComponent.get(player).hasTrait(Trait.HALF_GILLS)) {
            StatusEffectInstance conduit = new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 20, 0, true, false);
            player.addStatusEffect(conduit);
        } else if (TraitComponent.get(player).hasTrait(Trait.HALF_GILLS)) {
            StatusEffectInstance weak = new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 0, true, false);
            player.addStatusEffect(weak);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {}

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {}
}
