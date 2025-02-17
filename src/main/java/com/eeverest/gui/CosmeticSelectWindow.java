package com.eeverest.gui;

import com.eeverest.gui.lib.TraitsGUI;
import net.minecraft.text.Text;

public class CosmeticSelectWindow extends TraitsGUI {
    public CosmeticSelectWindow() {
        super(Text.translatable("options.traits"), null, false);
    }

    @Override
    protected void init() {
        super.init();

        super.init();

        Cosmetic[] traits = Cosmetic.values();
        int optionButtonWidth = 104;
        int optionButtonHeight = 20;
        //this.addDrawableChild(CyclingButtonWidget.builder((value) -> Text.of(TextUtils.formatValueString(value.toString()))).values(traits).initially(((TraitComponent)this.data).getCosmetic()).build(this.x + 72, this.y + 19, optionButtonWidth, optionButtonHeight, Text.translatable("options.trait.trait"), (button, value) -> ((CosmeticLocalData)this.data).setCosmetic((Cosmetic) value)));
    }
}
