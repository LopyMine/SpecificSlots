package net.lopymine.specificslots.handlers;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.screen.ScreenHandler;

import org.jetbrains.annotations.Nullable;

public interface HandledScreenHelper {
    boolean shouldDrawInventory();

    boolean shouldDrawHotBar();

    int getHotBarX();

    int getHotBarY();

    int getInventoryX();

    int getInventoryY();

    void updateSlotPos();

    @Nullable
    static HandledScreenHelper of(ScreenHandler handler, Screen screen) {
        if (screen == null) return null;
        if (screen instanceof CreativeInventoryScreen) {
            return new CreativeInventoryHandledScreenHelper<>(handler, screen);
        }
        if (screen instanceof InventoryScreen) {
            return new InventoryHandledScreenHelper<>(handler, screen);
        }
        return new BasedHandledScreenHelper<>(handler, screen);
    }
}
