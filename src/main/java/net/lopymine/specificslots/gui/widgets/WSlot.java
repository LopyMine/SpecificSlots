package net.lopymine.specificslots.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import net.fabricmc.api.*;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import net.lopymine.specificslots.utils.Painters;

import java.util.function.Consumer;
import org.jetbrains.annotations.*;

public class WSlot extends WWidget {
    private final int index;
    private boolean isArmor = false;
    private boolean isOn = false;
    @Nullable
    private Consumer<Boolean> onToggle = null;
    @Nullable
    private Identifier texture = null;
    private WGhostItemsShow showWidget;
    private Item item = Items.AIR;
    private int depth = 1;

    public WSlot(int index) {
        this.index = index;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(18, 18);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onClick(int x, int y, int button) {
        if (isArmor) return InputResult.IGNORED;
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (button == 1) {
            this.resetSlot();
            this.onToggle(false);
        } else {
            this.isOn = !this.isOn;
            this.onToggle(this.isOn);
        }

        return InputResult.PROCESSED;
    }

    public void resetSlot() {
        this.texture = null;
        this.item = Items.AIR;
        this.isOn = false;
    }

    @Override
    public boolean canFocus() {
        return !isArmor;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        Painters.drawSlot(context, x, y, shouldRenderInDarkMode());

        if ((isOn || isFocused()) && !isArmor) {
            ScreenDrawing.texturedRect(context, x, y, 18, 18, Painters.selectedSlot, 0xFFFFFFFF);
        }

        if (showWidget != null) {
            if (showWidget.isHovered() && !isArmor) {
                context.drawItem(item.getDefaultStack(), x + 1, y + 1);
            } else if (texture != null) {
                for (int i = 0; i < depth; i++) {
                    ScreenDrawing.texturedRect(context, x + 1, y + 1, 16, 16, texture, 0xFFFFFFFF);
                }
            }
        }

        if (isHovered()) {
            HandledScreen.drawSlotHighlight(context, x + 1, y + 1, 0);
        }
    }

    public WSlot setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public WSlot setArmor(boolean armor) {
        this.isArmor = armor;
        return this;
    }

    public boolean isArmor() {
        return isArmor;
    }

    public WSlot setToggle(boolean on) {
        this.isOn = on;
        return this;
    }

    public boolean getToggle() {
        return this.isOn;
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

    public WSlot setShowWidget(@NotNull WGhostItemsShow showWidget) {
        this.showWidget = showWidget;
        return this;
    }

    public WGhostItemsShow getShowWidget() {
        return showWidget;
    }

    public int getIndex() {
        return index;
    }
}
