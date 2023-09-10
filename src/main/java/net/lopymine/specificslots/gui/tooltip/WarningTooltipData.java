package net.lopymine.specificslots.gui.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public record WarningTooltipData(ItemStack requiredItem, ItemStack providedItem, int index) implements TooltipData {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarningTooltipData data = (WarningTooltipData) o;

        return ItemStack.areItemsEqual(this.providedItem, data.providedItem()) && ItemStack.areItemsEqual(this.requiredItem, data.requiredItem()) && this.index == data.index();
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiredItem, providedItem, index);
    }
}
