package net.lopymine.specificslots.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;

import net.fabricmc.api.*;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import net.lopymine.specificslots.utils.Painters;

import java.util.function.Consumer;
import org.jetbrains.annotations.*;

public class WSlot extends WWidget {
    private final int index;
    private ArmorSlot.ArmorSlotType armorSlotType = null;
    private boolean isOn = false;
    @Nullable
    private Consumer<Boolean> onToggle = null;
    @Nullable
    private Item item = Items.AIR;

    private boolean dragging = false;
    private int draggingButton = -1;
    private int dragX = 0;
    private int dragY = 0;

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
        if (isArmor()) return InputResult.IGNORED;
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (dragging) {
            dragging = false;
        } else {
            if (button == 1) {
                this.resetSlot();
                this.onToggle(false);
            } else {
                this.isOn = !this.isOn;
                this.onToggle(this.isOn);
            }
        }

        return InputResult.PROCESSED;
    }

    public void resetSlot() {
        this.item = Items.AIR;
        this.isOn = false;
    }

    @Override
    public boolean canFocus() {
        return !isArmor();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        Painters.drawSlot(context, x, y, shouldRenderInDarkMode());

        if ((isOn || isFocused()) && !isArmor()) {
            ScreenDrawing.texturedRect(context, x, y, 18, 18, Painters.selectedSlot, 0xFFFFFFFF);
        }

        if (isArmor()) {
            ScreenDrawing.texturedRect(context, x + 1, y + 1, 16, 16, ArmorSlot.getTexture(this.armorSlotType), 0xFFFFFFFF);
        } else if (item != null) {
            if (!dragging || draggingButton == 2) {
                context.drawItem(item.getDefaultStack(), x + 1, y + 1);
            }
            if (dragging) {
                context.drawItem(item.getDefaultStack(), x + this.dragX, y + this.dragY);
            }
        }

        if (isHovered()) {
            HandledScreen.drawSlotHighlight(context, x + 1, y + 1, 0);
        }
    }


    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
        if (this.item != Items.AIR) {
            this.dragX = x;
            this.dragY = y;
            this.dragging = true;
            this.draggingButton = button;
        }
        return InputResult.PROCESSED;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onMouseUp(int x, int y, int button) {
        if (!dragging) return super.onMouseUp(x, y, button);
        draggingButton = -1;

        if (host == null) return InputResult.IGNORED;

        WPanel root = host.getRootPanel();

        int containerX = x + this.getAbsoluteX();
        int containerY = y + this.getAbsoluteY();

        MinecraftClient client = MinecraftClient.getInstance();
        assert client.currentScreen != null;
        int screenCentreX = client.currentScreen.width / 2;
        int screenCentreY = client.currentScreen.height / 2;
        if ((containerX > screenCentreX + 88 || containerX < screenCentreX - 83) || (containerY > screenCentreY + 88 || containerY < screenCentreY - 83)) {
            this.item = Items.AIR;
        } else {
            WWidget hit = root.hit(containerX, containerY);
            if (hit == null) return InputResult.IGNORED;

            if (hit instanceof WSlot slot) {
                if (!slot.isArmor()) {
                    if (button == 2) {
                        slot.setItem(this.item);
                    } else {
                        Item temp = slot.item;
                        slot.setItem(this.item);
                        this.item = temp;
                    }
                }
                return InputResult.PROCESSED;
            }
        }
        return InputResult.IGNORED;
    }

    public WSlot setArmorType(ArmorSlot.ArmorSlotType type) {
        this.armorSlotType = type;
        return this;
    }

    public boolean isArmor() {
        return this.armorSlotType != null;
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

    public WSlot setItem(Item item) {
        if (!isArmor()) {
            this.item = item;
        }
        return this;
    }

    public Item getItem() {
        return item;
    }

    public int getIndex() {
        return index;
    }
}
