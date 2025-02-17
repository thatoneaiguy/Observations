package com.eeverest.gui;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.lib.TraitsGUI;
import com.eeverest.util.TextUtils;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

import java.util.Arrays;

public class TraitsSelectWindow extends TraitsGUI {
    public static final String TITLE = "options.traits";

    public TraitsSelectWindow() {
        super(Text.translatable("options.traits"), null, false);
    }

    @Override
    protected void init() {
        super.init();

        if (client == null || client.player == null) {
            return;
        }

        TraitComponent traitComponent = TraitComponent.get(client.player);
        Trait[] visibleTraits = Arrays.stream(Trait.values())
                .filter(trait -> !trait.hidden)
                .toArray(Trait[]::new);

        int optionButtonWidth = 104;
        int optionButtonHeight = 20;

        this.addDrawableChild(CyclingButtonWidget.builder(value ->
                                Text.of(TextUtils.formatValueString(value.toString()))
                        )
                        .values(visibleTraits)
                        .initially(traitComponent.getTrait1())
                        .build(this.x + 72, this.y + 19, optionButtonWidth, optionButtonHeight,
                                Text.translatable("options.trait.trait"),
                                (button, value) -> traitComponent.setTrait1((Trait) value)
                        )
        );

        this.addDrawableChild(CyclingButtonWidget.builder(value ->
                                Text.of(TextUtils.formatValueString(value.toString()))
                        )
                        .values(visibleTraits)
                        .initially(traitComponent.getTrait2())
                        .build(this.x + 72, this.y + 41, optionButtonWidth, optionButtonHeight,
                                Text.translatable("options.trait.trait"),
                                (button, value) -> traitComponent.setTrait2((Trait) value)
                        )
        );

        this.addDrawableChild(CyclingButtonWidget.builder(value ->
                                Text.of(TextUtils.formatValueString(value.toString()))
                        )
                        .values(visibleTraits)
                        .initially(traitComponent.getTrait3())
                        .build(this.x + 72, this.y + 63, optionButtonWidth, optionButtonHeight,
                                Text.translatable("options.trait.trait"),
                                (button, value) -> traitComponent.setTrait3((Trait) value)
                        )
        );
    }
}
