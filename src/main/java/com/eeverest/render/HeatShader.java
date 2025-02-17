package com.eeverest.render;

import com.eeverest.Observations;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class HeatShader extends PostProcessor {
    public static final HeatShader INSTANCE = new HeatShader();

    static {
        INSTANCE.setActive(false);
    }

    @Override
    public Identifier getPostChainLocation() {
        return new Identifier(Observations.MOD_ID, "heat_post");
    }

    @Override
    public void beforeProcess(MatrixStack matrixStack) {

    }

    @Override
    public void afterProcess() {

    }
}
