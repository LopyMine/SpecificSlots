package net.lopymine.specificslots.autosort;

import net.minecraft.item.Item;

public interface ISlot {
    void setInventoryItem(Item item);
    void setConfigItem(Item item);
    Item getInventoryItem();
    Item getConfigItem();
    int getInventoryItemIndex();
    int getConfigItemIndex();
}
