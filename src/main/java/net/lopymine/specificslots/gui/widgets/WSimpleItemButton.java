package net.lopymine.specificslots.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import net.fabricmc.api.*;

import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import net.lopymine.specificslots.textures.ShadowItems;

import org.jetbrains.annotations.Nullable;

public class WSimpleItemButton extends WWidget {
    private boolean dragging = false;
    private final Item item;
    private final WItem icon;
    private int dragX = 0;
    private int dragY = 0;
    @Nullable
    private Runnable onClick;

    public WSimpleItemButton(Item item) {
        this.item = item;
        this.icon = new WItem(new ItemStack(item));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        icon.paint(context, x, y, mouseX, mouseY);

        if (dragging)
            icon.paint(context, this.dragX + x, this.dragY + y, mouseX, mouseY);

        if (isHovered() || isFocused())
            HandledScreen.drawSlotHighlight(context, x + 1, y + 1, 0);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (onClick != null) onClick.run();
        return InputResult.PROCESSED;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        tooltip.add(Text.of(item.getName().getString()));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
        this.dragX = x;
        this.dragY = y;
        this.dragging = true;
        return InputResult.PROCESSED;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onMouseUp(int x, int y, int button) {
        if (!dragging) return super.onMouseUp(x, y, button);
        dragging = false;

        if (host == null) return InputResult.IGNORED;

        WPanel root = host.getRootPanel();

        int containerX = x + this.getAbsoluteX();
        int containerY = y + this.getAbsoluteY();

        WWidget hit = root.hit(containerX, containerY);
        if (hit == null) return InputResult.IGNORED;

        if (hit instanceof WSlot slot) {
            slot.setTexture(ShadowItems.getTexture(this.item))
                    .setItem(this.item);

            return InputResult.PROCESSED;
        }

        return InputResult.IGNORED;
    }

    public WSimpleItemButton setOnClick(@Nullable Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

}
