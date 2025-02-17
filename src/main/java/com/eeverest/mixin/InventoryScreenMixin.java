package com.eeverest.mixin;

import com.eeverest.Observations;
import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.eeverest.util.TextUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Unique
    private static final Identifier CUSTOM_TEXTURE = new Identifier(Observations.MOD_ID, "textures/gui/no_offhand.png");

    @Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
    private void hideOffHandSlot(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (TraitComponent.get(client.player).hasTrait(Trait.DISARMED)) {
            ci.cancel();

            int i = this.x;
            int j = this.y;
            context.drawTexture(CUSTOM_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
            drawEntity(context, i + 51, j + 75, 30, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.client.player);
        }
    }
}
