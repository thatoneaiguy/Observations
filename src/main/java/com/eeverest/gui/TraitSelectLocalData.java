package com.eeverest.gui;

import com.eeverest.Observations;
import com.eeverest.gui.lib.TraitsLocalData;
//import com.eeverest.util.Traits;
import com.eeverest.util.TraitsUpdateData;
import net.minecraft.client.MinecraftClient;

import java.util.UUID;

//public class TraitSelectLocalData implements TraitsLocalData {
//    private Trait trait;
//    private Trait trait2;
//    private Trait trait3;
//
//    public TraitSelectLocalData() {
//        this.trait = Trait.NONE;
//        this.trait2 = Trait.NONE;
//        this.trait3 = Trait.NONE;
//        UUID uuid = MinecraftClient.getInstance().player.getUuid();
//        this.setTrait(Traits.getTrait(uuid));
//    }
//
//    public Trait getTrait() {
//        return this.trait;
//    }
//
//    public Trait getTrait2() {
//        return this.trait2;
//    }
//
//    public Trait getTrait3() {
//        return this.trait3;
//    }
//
//    public void setTrait(Trait trait) {
//        this.trait = trait;
//    }
//
//    public void setTrait2(Trait trait2) {
//        this.trait2 = trait2;
//    }
//
//    public void setTrait3(Trait trait3) {
//        this.trait3 = trait3;
//    }
//
//    @Override
//    public void uploadToServer() {
//        TraitsUpdateData newData = new TraitsUpdateData(this.trait.name, this.trait2.name, this.trait3.name);
//        Observations.TRAIT_DATA.setData(newData);
//    }
//}
