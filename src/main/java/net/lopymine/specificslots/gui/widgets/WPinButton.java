package net.lopymine.specificslots.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import net.fabricmc.api.*;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import net.lopymine.specificslots.utils.Painters;

import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class WPinButton extends WWidget {
    @Nullable
    private final String server;
    private boolean isOn = false;
    @Nullable
    private Consumer<Boolean> onToggle = null;

    public WPinButton(@Nullable String server) {
        this.server = server;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(16, 16);
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public boolean canFocus() {
        return true;
    }
    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        ScreenDrawing.texturedRect(context, x, y, 16, 16, isOn || isFocused() ? Painters.PINNED : Painters.PIN, 0xFFFFFFFF);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.isOn = !this.isOn;
        this.onToggle(this.isOn);
        return InputResult.PROCESSED;
    }
    @Environment(EnvType.CLIENT)
    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        if(server == null) return;
        tooltip.add(Text.translatable("specific_slots.pin_config.text1"));
        tooltip.add(Text.translatable("specific_slots.pin_config.text2", server));
    }

    public WPinButton setOnToggle(@Nullable Consumer<Boolean> onToggle) {
        this.onToggle = onToggle;
        return this;
    }
    @Environment(EnvType.CLIENT)
    private void onToggle(boolean on) {
        if (this.onToggle != null) {
            this.onToggle.accept(on);
        }
    }

    public boolean getToggle() {
        return isOn;
    }

    public void setToggle(boolean b) {
        this.isOn = b;
    }
}
