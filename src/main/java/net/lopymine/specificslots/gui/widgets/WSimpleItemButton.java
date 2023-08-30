package net.lopymine.specificslots.gui.widgets;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.lopymine.specificslots.textures.GhostItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static net.lopymine.specificslots.SpecificSlots.logger;

public class WSimpleItemButton extends WWidget {

    private Item item = Items.AIR;
    private final WItem icon;
    private boolean dragging = false;
    private int dragX = 0;
    private int dragY = 0;
    @Nullable
    private Runnable onClick;

    public WSimpleItemButton(Item item) {
        this.item = item;
        this.icon = new WItem(new ItemStack(item));
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        this.icon.paint(matrices, x, y, mouseX, mouseY);
        if (dragging) {
            this.icon.paint(matrices, this.dragX + x, this.dragY + y, mouseX, mouseY);
        }
        if (isHovered() || isFocused()) {
            HandledScreen.drawSlotHighlight(matrices, x + 1, y + 1, 0);
        }
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (onClick != null) onClick.run();
        return InputResult.PROCESSED;
    }

    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        if (item != null) {
            tooltip.add(Text.of(item.getName().getString()));
        }
    }

    public WSimpleItemButton setOnClick(@Nullable Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    @Override
    public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
        this.dragX = x;
        this.dragY = y;
        this.dragging = true;
        return InputResult.PROCESSED;
    }

    @Override
    public InputResult onMouseUp(int x, int y, int button) {
        if(!dragging) return super.onMouseUp(x, y, button);
        this.dragging = false;

        if (host == null) return InputResult.IGNORED;

        WPanel root = host.getRootPanel();

        int containerX = x + this.getAbsoluteX();
        int containerY = y + this.getAbsoluteY();

        WWidget hit = root.hit(containerX, containerY);
        if (hit == null) return InputResult.IGNORED;

        if (hit instanceof WSlot slot) {
            slot.setTexture(GhostItems.getTexture(this.item));
            slot.setItem(this.item);
            return InputResult.PROCESSED;
        }

        return InputResult.IGNORED;
    }
}
