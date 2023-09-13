package net.lopymine.specificslots.handlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.*;
import org.jetbrains.annotations.Nullable;

public class BasedHandledScreenHelper<T extends ScreenHandler> implements HandledScreenHelper {

    protected final T handler;
    protected final Screen screen;
    protected int hotBarX = 0;
    protected int hotBarY = 0;
    protected int inventoryX = 0;
    protected int inventoryY = 0;

    public BasedHandledScreenHelper(T handler, Screen screen) {
        this.handler = handler;
        this.screen = screen;
    }

    @Override
    public boolean shouldDrawInventory() {
        return true;
    }

    @Override
    public boolean shouldDrawHotBar() {
        return true;
    }

    @Override
    public int getHotBarX() {
        return this.hotBarX;
    }

    @Override
    public int getHotBarY() {
        return this.hotBarY;
    }

    public int getInventoryX() {
        return inventoryX;
    }

    public int getInventoryY() {
        return inventoryY;
    }

    @Override
    public void updateSlotPos() {
        Slot slot = getFirstInventorySlot(handler);

        if (slot != null) {
            this.inventoryX = slot.x;
            this.inventoryY = slot.y;
        }

        Slot hotBarSlot = getFirstHotBarSlot(handler);

        if (hotBarSlot != null) {
            this.hotBarX = hotBarSlot.x;
            this.hotBarY = hotBarSlot.y;
        }
    }

    @Nullable
    protected Slot getFirstInventorySlot(T handler) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        if (handler == null) return null;

        for (Slot slot : handler.slots) {
            if (slot.inventory == player.getInventory()) return slot;
        }

        return null;
    }

    @Nullable
    protected Slot getFirstHotBarSlot(T handler) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        if (handler == null) return null;

        List<Slot> slots = getPlayerSlots(player, handler);
        int index = slots.size() - 9;
        if(index > 0) return slots.get(index);

        return null;
    }

    protected List<Slot> getPlayerSlots(PlayerEntity player, T handler){
        List<Slot> slots = new ArrayList<>();

        for (Slot slot : handler.slots) {
            if (slot.inventory == player.getInventory()) slots.add(slot);
        }

        return slots;
    }

}
