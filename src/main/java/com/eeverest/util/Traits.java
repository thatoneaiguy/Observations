package com.eeverest.util;

import com.eeverest.gui.Trait;
//import com.eeverest.gui.TraitSelectLocalData;
import com.eeverest.gui.TraitsSelectWindow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.util.UUID;

//public abstract class Traits {
//    public Traits() {}
//
//    public static Trait getTrait(UUID uuid) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        Screen var3 = client.currentScreen;
//        if (var3 instanceof TraitsSelectWindow traitsSelectWindow) {
//            if (uuid.equals(client.player.getUuid())) {
//                return ((TraitSelectLocalData)traitsSelectWindow.getData()).getTrait();
//            }
//        }
//
////        Optional<PlushOnHeadSupporterData> optional = Ratatouille.PLUSH_ON_HEAD_DATA.get(uuid);
////        if (optional.isPresent()) {
////            try {
////                return dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics.Plush.fromString(((PlushOnHeadSupporterData)optional.get()).plush());
////            } catch (IllegalArgumentException var4) {
////            }
////        }
//
//        return Trait.NONE;
//    }
//}
