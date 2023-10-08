package net.lopymine.specificslots.autosort;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public class SlotInfoImpl implements ISlot {
    private Item configItem = Items.AIR;
    private Item inventoryItem = Items.AIR;
    public SlotInfoImpl(){
    }

    public boolean hasConfigItem() {
        return configItem != Items.AIR;
    }
    public boolean hasInventoryItem() {
        return inventoryItem != Items.AIR;
    }

    public boolean isWrong(){
        if(configItem == Items.AIR) return false;
        if(inventoryItem == Items.AIR) return false;
        return configItem != inventoryItem;
    }

    public void setConfigItem(Item configItem) {
        this.configItem = configItem;
    }

    public void setInventoryItem(Item inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Item getConfigItem() {
        return configItem;
    }

    public Item getInventoryItem() {
        return inventoryItem;
    }

    @Override
    public int getInventoryItemIndex() {
        return 0;
    }

    @Override
    public int getConfigItemIndex() {
        return 0;
    }

    @Unique
    @Nullable
    public static SlotInfoImpl getSlotInfo(boolean isHotBar, Item configItem, int slot, ClientPlayerEntity player) {
        if (player == null) {
            return null;
        }

        PlayerInventory inventory = player.getInventory();
        SlotInfoImpl slotInfo = new SlotInfoImpl();
        slotInfo.setConfigItem(configItem);

        List<ItemStack> itemList;
        if (isHotBar) {
            itemList = inventory.main.subList(0, 9);
        } else {
            itemList = inventory.main.subList(9, 36);
        }

        if (slot >= 0 && slot < itemList.size()) {
            Item item = itemList.get(slot).getItem();
            slotInfo.setInventoryItem(item);
        }

        return slotInfo;
    }
}
