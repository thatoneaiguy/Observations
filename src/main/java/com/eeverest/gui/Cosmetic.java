package com.eeverest.gui;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum Cosmetic {
    NONE(Items.AIR),
    GAS_MASK(Items.ACACIA_FENCE);

    public final String name;
    public final @NotNull Item item;

    private Cosmetic(Item item) {
        this.item = item;
        this.name = this.toString().toLowerCase(Locale.ROOT);
    }

    public static @Nullable Cosmetic fromString(String name) {
        for(Cosmetic plush : values()) {
            if (plush.name.equalsIgnoreCase(name)) {
                return plush;
            }
        }

        return null;
    }
}
