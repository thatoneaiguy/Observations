package com.eeverest.gui.lib;

import com.eeverest.cca.TraitComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public abstract class TraitsGUI<T extends TraitsLocalData> extends Screen {
    protected T data;
    private final PlayerEntity player;
    protected int x;
    protected int y;
    boolean locked;

    public TraitsGUI(Text title, T data, boolean locked) {
        super(title);
        MinecraftClient client = MinecraftClient.getInstance();
        this.player = client.player;
        this.locked = locked;
    }

    @Override
    protected void init() {
        this.x = this.width / 2 - TraitsScreenUVs.BACKGROUND.getWidth() / 2;
        this.y = this.height / 2 - TraitsScreenUVs.BACKGROUND.getHeight() / 2;
        this.addDrawableChild(new ExitButtonWidget(this.x + 104, this.y + 109, Text.empty(), TraitsScreenUVs.CANCEL, TraitsScreenUVs.CANCEL_HOVER, () -> {
        }, false));
        this.addDrawableChild(new ExitButtonWidget(
                this.x + 128, this.y + 109,
                Text.literal("TEST"),
                this.locked ? TraitsScreenUVs.LOCKED : TraitsScreenUVs.CONFIRM,
                this.locked ? TraitsScreenUVs.LOCKED : TraitsScreenUVs.CONFIRM_HOVER,
                () -> TraitComponent.get(client.player).sync(),
                this.locked
        ));

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context);

        context.drawTexture(TraitsScreenUVs.GUI_TEXTURE, this.x, this.y, 0, 0, TraitsScreenUVs.BACKGROUND.getWidth(), TraitsScreenUVs.BACKGROUND.getHeight());
        if (this.player != null) {
            InventoryScreen.drawEntity(context, this.x + 39, this.y + 123, 46, (float)(this.x + 39 - mouseX) / 10.0F, (float)(this.y + 46 - mouseY) / 10.0F, this.player);
        }

        context.drawText(this.textRenderer, this.title, this.width / 2 - this.textRenderer.getWidth(this.title) / 2, this.y + 7, 4210752, false);
        super.render(context, mouseX, mouseY, delta);
    }
}
