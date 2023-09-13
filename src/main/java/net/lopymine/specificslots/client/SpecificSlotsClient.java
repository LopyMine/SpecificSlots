package net.lopymine.specificslots.client;

import net.minecraft.client.gui.tooltip.*;
import net.minecraft.text.Text;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

import net.lopymine.specificslots.gui.tooltip.*;
import net.lopymine.specificslots.gui.tooltip.utils.*;

import java.util.*;

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
