package net.lopymine.specificslots.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.util.TriState;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.gui.panels.*;
import net.lopymine.specificslots.utils.ItemUtils;
import net.lopymine.specificslots.utils.mixins.ISpecificScreen;

import java.util.ArrayList;

public class SpecificGui extends LightweightGuiDescription {
    private final SpecificConfig defaultConfig = SpecificConfigManager.getConfig();
    private final Screen parent;

    public SpecificGui(InventoryConfig config, Screen parent) {
        this.parent = parent;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null){
            client.setScreen(null);
        }
        int w = client.currentScreen.width;
        int h = client.currentScreen.height;

        WPlainPanel root = new WPlainPanel(){
            @Override
            public InputResult onKeyPressed(int ch, int key, int modifiers) {
                if (ch == 256 && SpecificGui.this.parent != null) {
                    SpecificGui.this.updateScreenConfigAndOpen(SpecificGui.this.parent, client);
                    return InputResult.PROCESSED;
                }
                return InputResult.IGNORED;
            }
        }.setInsets(Insets.ROOT_PANEL);
        root.setSize(w, h);

        WButton back = new WButton(ScreenTexts.BACK);
        back.setOnClick(() -> updateScreenConfigAndOpen(parent, client));
        root.add(back, 5, h - 25, 80, 20);

        WPlayerInventoryPanel playerInventory = new WPlayerInventoryPanel(config, parent, defaultConfig);
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

    private void updateScreenConfigAndOpen(Screen parent, MinecraftClient client){
        if(parent instanceof ISpecificScreen specificScreen){
            specificScreen.setInventoryConfig(SpecificSlots.inventoryConfig);
        } else {
            SpecificSlots.logger.warn("Screen parent is not instance of SpecificScreen! {}", parent);
        }
        client.setScreen(parent);
    }

    public static Text cutString(String text, int length) {
        if (text.length() <= length) {
            return Text.of(text);
        } else {
            String cutText = text.substring(0, length);
            if (cutText.endsWith(" ")) {
                cutText = cutText.substring(0, cutText.length() - 1);
            }
            return Text.of(cutText + "...");
        }
    }
}
