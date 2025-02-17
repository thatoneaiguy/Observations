package com.eeverest.render.feature;

import com.eeverest.gui.Cosmetic;
import com.eeverest.util.Cosmetics;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;

public class GasMaskFeatureRenderer <T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private final HeldItemRenderer heldItemRenderer;

    public GasMaskFeatureRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof PlayerEntity player) {
            Cosmetic plush = Cosmetics.getCosmetic(player.getUuid());
            if (plush != Cosmetic.NONE) {
                matrices.push();
                ((ModelWithHead)this.getContextModel()).getHead().rotate(matrices);
                matrices.translate(0.0F, -0.25F, 0.0F);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
                float scale = 0.625F;
                matrices.scale(scale, -scale, -scale);
                this.heldItemRenderer.renderItem(entity, plush.item.getDefaultStack(), ModelTransformationMode.HEAD, false, matrices, vertexConsumers, light);
                matrices.pop();
            }
        }
    }
}
