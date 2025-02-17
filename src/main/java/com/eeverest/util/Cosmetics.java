package com.eeverest.util;

import com.eeverest.gui.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.util.UUID;

public class Cosmetics {
    public Cosmetics() {}

    public static Cosmetic getCosmetic(UUID uuid) {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen var3 = client.currentScreen;
        if (var3 instanceof CosmeticSelectWindow traitsSelectWindow) {
            if (uuid.equals(client.player.getUuid())) {
                //return ((CosmeticLocalData)traitsSelectWindow.getData()).getCosmetic();
            }
        }

//        Optional<PlushOnHeadSupporterData> optional = Ratatouille.PLUSH_ON_HEAD_DATA.get(uuid);
//        if (optional.isPresent()) {
//            try {
//                return dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics.Plush.fromString(((PlushOnHeadSupporterData)optional.get()).plush());
//            } catch (IllegalArgumentException var4) {
//            }
//        }

        return Cosmetic.NONE;
    }
}
