package net.lopymine.specificslots.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.util.TriState;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.SpecificConfigManager;
import net.lopymine.specificslots.gui.panels.WItemsPanel;
import net.lopymine.specificslots.gui.panels.WPlayerInventoryPanel;
import net.lopymine.specificslots.gui.widgets.WGhostItemsShow;
import net.lopymine.specificslots.utils.ItemUtils;
import net.lopymine.specificslots.utils.mixins.ISpecificHandledScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.*;

import java.util.ArrayList;

public class SpecificSlotsGui extends LightweightGuiDescription {
    private final SpecificConfig defaultConfig = SpecificConfigManager.getConfig();

    public SpecificSlotsGui(InventoryConfig config, Screen parent) {
        MinecraftClient mc = MinecraftClient.getInstance();
        WPlainPanel root = new WPlainPanel();
        root.setInsets(Insets.ROOT_PANEL);

        if (mc.currentScreen == null) mc.setScreen(null);

        int w = mc.currentScreen.width;
        int h = mc.currentScreen.height;

        root.setSize(w, h);

        WButton back = new WButton(ScreenTexts.BACK) {
            @Override
            public boolean shouldRenderInDarkMode() {
                return SpecificSlotsGui.this.isDarkMode().get();
            }
        };
        back.setOnClick(() -> {
            if(parent instanceof ISpecificHandledScreen specificScreen){
                specificScreen.setInventoryConfig(SpecificSlots.inventoryConfig);
            }

            mc.setScreen(parent);
        });
        root.add(back, 5, h - 25, 80, 20);

        WGhostItemsShow itemsShow = new WGhostItemsShow();
        root.add(itemsShow, 87, h - 25, 20, 18);

        WPlayerInventoryPanel playerInventory = new WPlayerInventoryPanel(config, parent, itemsShow, defaultConfig);
        root.add(playerInventory, w / 2 - 88, h / 2 - 83);

        WItemsPanel itemsPanel = new WItemsPanel(w, h, playerInventory, new ArrayList<>(ItemUtils.getMinecraftItems()));
        root.add(itemsPanel, itemsPanel.getXPos(w), itemsPanel.getYPos(h), itemsPanel.getWidthPanel(), itemsPanel.getHeightPanel());

        setRootPanel(root);
        root.validate(this);
    }

    @Override
    public TriState isDarkMode() {
        return TriState.of(defaultConfig.isDarkMode);
    }

    @Override
    public void addPainters() {

    }
}
