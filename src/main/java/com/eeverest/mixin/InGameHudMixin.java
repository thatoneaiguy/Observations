package com.eeverest.mixin;

import com.eeverest.Observations;
import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
//import com.eeverest.util.Traits;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Unique
    private static final Identifier STAR_HEART = new Identifier(Observations.MOD_ID, "textures/gui/star_hearts.png");

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void drawCustomHeart(DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean half, CallbackInfo ci) {
        if (!blinking && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player &&
                TraitComponent.get(player).hasTrait(Trait.TO_WISH_APON_A_SHOOTING_STAR)) {
            if(!type.toString().equals("CONTAINER")) {
                if (half)
                    context.drawTexture(STAR_HEART, x, y, 9, 0, 9, 9);
                else
                    context.drawTexture(STAR_HEART, x, y, 0, 0, 9, 9);
                ci.cancel();
            }
        }
    }
}
