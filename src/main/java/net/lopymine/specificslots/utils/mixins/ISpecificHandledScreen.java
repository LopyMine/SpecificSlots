package net.lopymine.specificslots.utils.mixins;

import net.lopymine.specificslots.config.inventory.InventoryConfig;

public interface ISpecificHandledScreen {
    InventoryConfig getInventoryConfig();

    void setInventoryConfig(InventoryConfig inventoryConfig);
}
