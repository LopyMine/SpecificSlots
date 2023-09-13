package net.lopymine.specificslots.handlers;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.screen.ScreenHandler;

public class CreativeInventoryHandledScreenHelper<T extends ScreenHandler> extends InventoryHandledScreenHelper<T> {


    public CreativeInventoryHandledScreenHelper(T handler, Screen screen) {
        super(handler, screen);
    }

    @Override
    public boolean shouldDrawInventory() {
        return ((CreativeInventoryScreen) screen).isInventoryTabSelected();
    }

    @Override
    public int getInventoryY() {
        return 54;
    }

    @Override
    public int getInventoryX() {
        return 9;
    }
}
