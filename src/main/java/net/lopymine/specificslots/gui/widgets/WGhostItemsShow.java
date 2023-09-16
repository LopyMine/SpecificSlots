package net.lopymine.specificslots.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

import net.fabricmc.api.*;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;

import net.lopymine.specificslots.utils.Painters;

public class WGhostItemsShow extends WButton {
    private boolean isOn = false;

    public WGhostItemsShow() {
        super(new TextureIcon(Painters.SHOW_ITEMS));
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(20, 20);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        super.paint(context, x, y, mouseX, mouseY);
        if (this.isOn) setHovered(true);
        if (isHovered() || isFocused()) {
            this.setIcon(new TextureIcon(Painters.SHOW_ITEMS_FOCUS));
        } else {
            this.setIcon(new TextureIcon(Painters.SHOW_ITEMS));
        }

        ScreenDrawing.texturedRect(context, x + 15, y - 4, 9, 9, (isOn ? Painters.LOCKED : Painters.UNLOCKED), 0xFFFFFFFF);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.isOn = !this.isOn;
        return InputResult.PROCESSED;
    }
}
