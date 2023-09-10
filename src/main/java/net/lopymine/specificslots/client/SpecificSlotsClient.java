package net.lopymine.specificslots.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.lopymine.specificslots.gui.tooltip.utils.TooltipComponents;
import net.lopymine.specificslots.gui.tooltip.utils.TooltipComponentsData;
import net.lopymine.specificslots.gui.tooltip.WarningButtonTooltipComponent;
import net.lopymine.specificslots.gui.tooltip.WarningTooltipData;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SpecificSlotsClient implements ClientModInitializer {

    /**
     * Runs the mod initializer on the client environment.
     */


    @Override
    public void onInitializeClient() {
        TooltipComponentCallback.EVENT.register(data -> {
            if(data instanceof TooltipComponentsData components){
                List<TooltipComponent> list = new ArrayList<>();
                for(WarningTooltipData tooltipData : components.list()){
                    list.add(new WarningButtonTooltipComponent(tooltipData.requiredItem(),tooltipData.providedItem(), tooltipData.index()));
                }
                if(list.size() > 4) list.add(new OrderedTextTooltipComponent(Text.of(". . .").asOrderedText()));
                return new TooltipComponents(list);
            }
            return null;
        });


    }
}
