package net.lopymine.specificslots.autosort;

import net.minecraft.item.Item;

public interface ISlot {
    void setInventoryItem(Item item, int index);
    void setConfigItem(Item item, int index);
    Item getInventoryItem();
    Item getConfigItem();
    int getInventoryItemIndex();
    int getConfigItemIndex();
}
