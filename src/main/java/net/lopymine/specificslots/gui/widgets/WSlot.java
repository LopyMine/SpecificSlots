package net.lopymine.specificslots.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WWidget;


import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.lopymine.specificslots.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class WSlot extends WWidget {
    private final int invIndex;
    public boolean isArmor = false;
    private boolean isOn = false;
    private WGhostItemsShow showWidget;
    @Nullable
    protected Consumer<Boolean> onToggle = null;
    @Nullable
    private Identifier texture = null;
    private Item item = Items.AIR;
    private int depth = 1;

    public WSlot(int invIndex) {
        this.invIndex = invIndex;
    }

    @Override
    public boolean canResize() {
        return false;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        if (isArmor) return InputResult.IGNORED;
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (button == 1) {
            this.texture = null;
            this.item = Items.AIR;
            this.isOn = false;
            onToggle(false);
            return InputResult.PROCESSED;
        }

        this.isOn = !this.isOn;
        onToggle(this.isOn);
        return InputResult.PROCESSED;
    }

    @Override
    public boolean canFocus() {
        return !isArmor;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        DrawUtils.drawSlot(matrices,x,y,shouldRenderInDarkMode());

        if (isOn || isFocused() && !isArmor) {
            ScreenDrawing.texturedRect(matrices, x, y, 18, 18, DrawUtils.selectedSlot, 0xFFFFFFFF);
        }
        if (showWidget != null) {
            if (showWidget.isHovered() && !isArmor) {
                MinecraftClient.getInstance().getItemRenderer().renderInGui(matrices, item.getDefaultStack(), x + 1, y + 1);
            } else if(texture != null){
                for (int i = 0; i < depth; i++) {
                    ScreenDrawing.texturedRect(matrices, x + 1, y + 1, 16, 16, texture, 0xFFFFFFFF);
                }
            }
        }

        if (isHovered()) {
            HandledScreen.drawSlotHighlight(matrices, x + 1, y + 1, 0);
        }

    }

    public WSlot setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public void setToggle(boolean on) {
        this.isOn = on;
    }

    public boolean getToggle() {
        return this.isOn;
    }

    public WSlot setArmor(boolean armor) {
        this.isArmor = armor;
        return this;
    }

    public WSlot setOnToggle(@Nullable Consumer<Boolean> onToggle) {
        this.onToggle = onToggle;
        return this;
    }

    private void onToggle(boolean on) {
        if (this.onToggle != null) {
            this.onToggle.accept(on);
        }
    }

    public WSlot setTexture(@Nullable Identifier texture) {
        this.texture = texture;
        return this;
    }

    @Nullable
    public Identifier getTexture() {
        return texture;
    }

    public WSlot setItem(Item item) {
        this.item = item;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public WSlot setShowWidget(WGhostItemsShow showWidget) {
        this.showWidget = showWidget;
        return this;
    }

    public WGhostItemsShow getShowWidget() {
        return showWidget;
    }

    public int getInvIndex() {
        return invIndex;
    }

}
