package net.lopymine.specificslots.gui.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.util.TriState;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;

import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.config.inventory.*;
import net.lopymine.specificslots.gui.SpecificGui;
import net.lopymine.specificslots.gui.config.SaveConfigGui.ServerInventoryConfig;
import net.lopymine.specificslots.gui.panels.list.*;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.utils.Painters;

import java.util.*;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.Nullable;

public class LoadConfigGui extends LightweightGuiDescription {
    private final int width = 180;
    private final int height = 180;
    private final BiConsumer<InventoryConfig, WConfigListPanel> configurator;
    private SpecificConfig config;
    private final Screen parent;
    private WListPanelExt<InventoryConfig, WConfigListPanel> list;
    private final WPlainPanel root = new WPlainPanel() {
        @Override
        public InputResult onKeyPressed(int ch, int key, int modifiers) {
            if (ch == 256 && LoadConfigGui.this.parent != null) {
                MinecraftClient.getInstance().setScreen(LoadConfigGui.this.parent);
                return InputResult.PROCESSED;
            }
            return InputResult.IGNORED;
        }

        @Override
        public InputResult onMouseScroll(int x, int y, double amount) {
            if (list == null) return InputResult.IGNORED;
            return list.onMouseScroll(x, y, amount);
        }
    };
    private List<InventoryConfig> configs;
    private WLabel empty;
    @Nullable
    private String server;

    public LoadConfigGui(Screen back, Screen parent, SpecificConfig config) {
        this.config = config;
        this.parent = back;
        boolean bl = isOnServer();

        configs = InventoryConfigManager.getConfigs();
        root.setSize(width, height);
        root.setInsets(Insets.ROOT_PANEL);

        WTextField field = new WTextField(Text.translatable("specific_slots.file_name"))
                .setChangedListener((s) -> {
                    if (!s.isEmpty()) {
                        updateList(getSearch(configs, s));
                    } else {
                        updateList(InventoryConfigManager.getConfigs());
                    }
                })
                .setMaxLength(50);
        root.add(field, 15, 25, 150, 20);

        WLabel label = new WLabel(Text.translatable("specific_slots.load_config"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        root.add(label, 16, 0, width - 32, 15);

        configurator = (InventoryConfig inventoryConfig, WConfigListPanel list) -> {
            String name = inventoryConfig.getName().replaceAll(InventoryConfigManager.JSON_FORMAT, "");

            list.setButtonTooltip(name);

            list.button.setLabel(SpecificGui.cutString(name, 16));

            list.button.setOnClick(() -> {
                SpecificConfigManager.setConfig(this.config, inventoryConfig);

                MinecraftClient.getInstance().setScreen(new SpecificScreen(new SpecificGui(inventoryConfig, parent)));
            });

            if (bl && this.config.serverInventoryConfigs.contains(new ServerInventoryConfig(server, inventoryConfig.getName()))) {
                list.button.setIcon(new TextureIcon(Painters.PIN));
            }

            list.remove.setOnClick(() -> {
                InventoryConfigManager.removeConfig(inventoryConfig);
                updateList(InventoryConfigManager.getConfigs());
            });
        };

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.setListItemHeight(20);
        list.setBackgroundPainter(Painters.CONFIG_BACKGROUND_PAINTER);
        root.add(list, 0, 55, width, height - 50);

        setRootPanel(root);
        root.validate(this);
    }

    private boolean isOnServer() {
        if (server != null) return true;
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        if (networkHandler == null) return false;
        if (networkHandler.getServerInfo() == null) return false;

        this.server = networkHandler.getServerInfo().address;
        return true;
    }

    private void updateList(List<InventoryConfig> configs) {
        root.remove(empty);
        root.remove(list);

        if (configs.isEmpty()) {
            empty = new WLabel(Text.translatable("specific_slots.empty_search"));
            empty.setHorizontalAlignment(HorizontalAlignment.CENTER);
            empty.setVerticalAlignment(VerticalAlignment.CENTER);
            root.add(empty, 0, 55, width, height - 50);
        }

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.setListItemHeight(20);
        list.setBackgroundPainter(Painters.CONFIG_BACKGROUND_PAINTER);
        list.layout();
        list.validate(this);

        root.add(list, 0, 55, width, height - 50);
    }

    private ArrayList<InventoryConfig> getSearch(List<InventoryConfig> configs, String text) {
        ArrayList<InventoryConfig> list = new ArrayList<>();
        for (InventoryConfig config : configs) {
            if (config != null) {
                if (config.getName().contains(text)) list.add(config);
            }
        }
        return list;
    }

    @Override
    public TriState isDarkMode() {
        return TriState.of(config.isDarkMode);
    }
}
