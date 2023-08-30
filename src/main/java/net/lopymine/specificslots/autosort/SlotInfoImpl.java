package net.lopymine.specificslots.autosort;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

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

    public void setConfigItem(Item configItem, int index) {
        this.configItem = configItem;
    }

    public void setInventoryItem(Item inventoryItem, int index) {
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

}
