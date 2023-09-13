package net.lopymine.specificslots.handlers;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import org.jetbrains.annotations.Nullable;

public class InventoryHandledScreenHelper<T extends ScreenHandler> extends BasedHandledScreenHelper<T> {

    public InventoryHandledScreenHelper(T handler, Screen screen) {
        super(handler, screen);

    }

    @Override
    public int getInventoryX() {
        return 8;
    }

    @Override
    public int getInventoryY() {
        return 84;
    }

    @Override
    public int getHotBarX() {
        return getInventoryX();
    }

    @Override
    public int getHotBarY() {
        return getInventoryY() + 58;
    }

    @Override
    public void updateSlotPos() {

    }

    @Override
    protected @Nullable Slot getFirstInventorySlot(T handler) {
        return null;
    }

    @Override
    protected @Nullable Slot getFirstHotBarSlot(T handler) {
        return null;
    }
}
