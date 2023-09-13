package net.lopymine.specificslots.utils.mixins;

import net.minecraft.client.item.TooltipData;

@FunctionalInterface
public interface IWarningScreen {
    void setTooltipData(TooltipData data);
}
