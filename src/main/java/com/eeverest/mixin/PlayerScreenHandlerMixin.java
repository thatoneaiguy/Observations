package com.eeverest.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerScreenHandler.class)
public class PlayerScreenHandlerMixin {
    @Shadow @Final public static Identifier EMPTY_OFFHAND_ARMOR_SLOT;

//    @Redirect(method = "<init>", at = @At("TAIL"))
//    private ItemStack removeShieldSprite(PlayerScreenHandler instance) {
//        ServerPlayerEntity player = (ServerPlayerEntity) (Object) instance.owner;
//
//        return new ItemStack(Items.AIR);
//    }
}
