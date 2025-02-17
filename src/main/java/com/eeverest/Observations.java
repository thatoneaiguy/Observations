package com.eeverest;

import com.eeverest.cca.StarComponent;
import com.eeverest.cca.TraitComponent;
import com.eeverest.cca.WaterComponent;
import com.eeverest.cmd.TraitCommand;
import com.eeverest.events.WorldTicks;
import com.eeverest.gui.TraitsSelectWindow;
import com.eeverest.render.HeatShader;
import com.eeverest.render.ShittyShader;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class Observations implements ModInitializer, EntityComponentInitializer {
	public static final String MOD_ID = "observations";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ComponentKey<StarComponent> STAR =
			ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "star"), StarComponent.class);
	public static final ComponentKey<WaterComponent> WATER =
			ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "water"), WaterComponent.class);
	public static final ComponentKey<TraitComponent> TRAIT =
			ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "trait"), TraitComponent.class);

	public static final float FOG_START = -8.0F;
	public static final float FOG_END = 1_000_000.0F;

//	public static final Identifier TRAIT_DATA_ID = id("trait");
//	public static final SyncToken<TraitsUpdateData> TRAIT_DATA;
//
//	public static final Identifier COSMETIC_DATA_ID = id("cosmetic");
//	public static final SyncToken<CosmeticUpdateData> COSMETIC_DATA;


	public static Identifier id(String string) {
		return new Identifier("observations", string);
	}

	@Override
	public void onInitialize() {
		TraitCommand.register();
		WorldTicks.register();

		DolphinEntity.createDolphinAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8);

		LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(PlayerEntity.class, STAR, StarComponent::new);
		registry.registerFor(PlayerEntity.class, WATER, WaterComponent::new);
		registry.registerFor(PlayerEntity.class, TRAIT, TraitComponent::new);
	}

//	static {
//		TRAIT_DATA = DataSyncAPI.register(TraitsUpdateData.class, id("trait"), TraitsUpdateData.CODEC);
//		COSMETIC_DATA = DataSyncAPI.register(CosmeticUpdateData.class, id("cosmetic"), CosmeticUpdateData.CODEC);
//	}
}