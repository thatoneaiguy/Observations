package com.eeverest.gui.lib;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

public class ExitButtonWidget extends PressableWidget {
    Runnable runnable;
    private final TraitsScreenUVs texture;
    private final TraitsScreenUVs hoverTexture;
    private final boolean locked;

    ExitButtonWidget(int x, int y, Text text, TraitsScreenUVs texture, TraitsScreenUVs hoverTexture, Runnable runnable, boolean locked) {
        super(x, y, TraitsScreenUVs.CANCEL.getWidth(), TraitsScreenUVs.CANCEL.getHeight(), text);
        this.runnable = runnable;
        this.texture = texture;
        this.hoverTexture = hoverTexture;
        this.locked = locked;
        if (this.locked) {
            this.setTooltip(Tooltip.of(Text.translatable("tooltip.lore_spoiler")));
        }
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        TraitsScreenUVs icon = this.isMouseOver((double)mouseX, (double)mouseY) ? this.hoverTexture : this.texture;
        context.drawTexture(TraitsScreenUVs.GUI_TEXTURE, this.getX(), this.getY(), icon.getU(), icon.getV(), icon.getWidth(), icon.getHeight());
    }

    @Override
    public void onPress() {
        if (!this.locked) {
            this.runnable.run();
            Screen currentScreen = MinecraftClient.getInstance().currentScreen;
            if (currentScreen != null) {
                currentScreen.close();
            }
        }
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (!this.locked) {
            super.playDownSound(soundManager);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
