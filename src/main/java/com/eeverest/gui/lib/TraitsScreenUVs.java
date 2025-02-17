package com.eeverest.gui.lib;

import com.eeverest.Observations;
import net.minecraft.util.Identifier;

public enum TraitsScreenUVs {
    BACKGROUND(0, 0, 182, 135),
    CANCEL(0, 135, 18, 18),
    CANCEL_HOVER(0, 153, 18, 18),
    CONFIRM(18, 135, 18, 18),
    CONFIRM_HOVER(18, 153, 18, 18),
    LOCKED(36, 135, 18, 18);

    public static final Identifier GUI_TEXTURE = Observations.id("textures/gui/cosmetics.png");
    private final int u;
    private final int v;
    private final int width;
    private final int height;

    private TraitsScreenUVs(int u, int v, int width, int height) {
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}