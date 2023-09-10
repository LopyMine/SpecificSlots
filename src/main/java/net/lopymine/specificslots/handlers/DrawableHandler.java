package net.lopymine.specificslots.handlers;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public interface DrawableHandler {
    boolean shouldDrawInventory();

    boolean shouldDrawHotBar();

    int getHotBarX();

    int getHotBarY();

    int getInventoryX();

    int getInventoryY();

    void updateSlotPos();

    @Nullable
    static DrawableHandler getInstance(ScreenHandler handler, Screen screen) {
        if (screen == null) return null;
        if (screen instanceof CreativeInventoryScreen) {
            return new CreativeInventoryDrawableHandler<>(handler, screen);
        }
        if (screen instanceof InventoryScreen) {
            return new InventoryDrawableHandler<>(handler, screen);
        }
        return new BasedDrawableHandler<>(handler, screen);
    }
}
