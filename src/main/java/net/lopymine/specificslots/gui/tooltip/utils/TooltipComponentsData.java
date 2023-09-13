package net.lopymine.specificslots.gui.tooltip.utils;

import net.minecraft.client.item.TooltipData;

import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;

import java.util.List;

public record TooltipComponentsData(List<WarningTooltipData> list) implements TooltipData {
}
