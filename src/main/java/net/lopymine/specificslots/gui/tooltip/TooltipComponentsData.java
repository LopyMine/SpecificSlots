package net.lopymine.specificslots.gui.tooltip;

import net.minecraft.client.item.TooltipData;

import java.util.List;

public record TooltipComponentsData(List<WarningTooltipData> list) implements TooltipData {
}
