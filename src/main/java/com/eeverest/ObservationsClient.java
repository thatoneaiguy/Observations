package com.eeverest;

import com.eeverest.gui.TraitsSelectWindow;
import com.eeverest.render.HeatShader;
import com.eeverest.render.ShittyShader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class ObservationsClient implements ClientModInitializer {
    private static boolean openTraitScreen;


    @Override
    public void onInitializeClient() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        //PostProcessHandler.addInstance(ShittyShader.INSTANCE);
        //PostProcessHandler.addInstance(HeatShader.INSTANCE);

        UseItemCallback.EVENT.register((UseItemCallback)(player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(Items.BARRIER.asItem()) && player.isSneaking())) {
                openTraitScreen = true;
            }

            return TypedActionResult.pass(stack);
        });

        WorldRenderEvents.LAST.register((WorldRenderEvents.Last)(context) -> {
            if (openTraitScreen) {
                minecraftClient.setScreen(new TraitsSelectWindow());
                openTraitScreen = false;
            }

        });
    }
}
