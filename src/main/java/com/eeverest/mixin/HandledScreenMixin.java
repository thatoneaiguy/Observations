package com.eeverest.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Inject(method = "onMouseClick*", at = @At("HEAD"), cancellable = true)
    private void preventOffHandClick(Slot slot, int slotId, int button, int actionType, CallbackInfo ci) {
        if (slot != null && slot.getIndex() == 45) {
            ci.cancel(); // Cancels clicking on the off-hand slot
        }
    }
}*/
