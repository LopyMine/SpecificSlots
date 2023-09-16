package net.lopymine.specificslots.gui.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.util.TriState;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.config.inventory.*;
import net.lopymine.specificslots.gui.SpecificGui;
import net.lopymine.specificslots.gui.panels.list.*;
import net.lopymine.specificslots.gui.widgets.*;
import net.lopymine.specificslots.utils.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.*;
import org.jetbrains.annotations.Nullable;

import static net.lopymine.specificslots.config.inventory.InventoryConfigManager.JSON_FORMAT;

public class SaveConfigGui extends LightweightGuiDescription {
    private final int width = 180;
    private final int height = 180;
    private final BiConsumer<InventoryConfig, WConfigListPanel> configurator;
    private SpecificConfig config;
    private final Screen parent;
    private WListPanelExt<InventoryConfig, WConfigListPanel> list;
    public final static Pattern FILE_PATTERN = Pattern.compile("^[[a-zA-Z0-9А-Яа-яЁё_],\\s-]+\\.[A-Za-z]{4}$");
    private List<InventoryConfig> configs;
    private WLabel empty;
    @Nullable
    private String server;
    private final WPlainPanel root = new WPlainPanel() {
        @Override
        public InputResult onKeyPressed(int ch, int key, int modifiers) {
            if (ch == 256 && SaveConfigGui.this.parent != null) {
                MinecraftClient.getInstance().setScreen(SaveConfigGui.this.parent);
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

    public SaveConfigGui(Screen parent, List<WSlot> inventory, List<WSlot> hotBar, SpecificConfig config) {
        this.config = config;
        this.parent = parent;
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

        WLabel label = new WLabel(Text.translatable("specific_slots.save_config"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);

        root.add(label, 16, 0, width - 32, 15);

        WPinButton pinButton = new WPinButton(server);
        if(bl) root.add(pinButton, width - 16, 0);

        WButton save = new WButton(ScreenTexts.DONE)
                .setOnClick(() -> {
                    if (field.getText().isEmpty()) {
                        return;
                    }
                    if (field.getText().charAt(field.getText().length() - 1) == ' ') {
                        return;
                    }
                    if (field.getText().charAt(0) == ' ') {
                        return;
                    }

                    this.saveConfig(field.getText(), inventory, hotBar, pinButton.getToggle());
                    updateList(InventoryConfigManager.getConfigs());
                });

        root.add(save, 15, 50, 150, 20);

        configurator = (InventoryConfig inventoryConfig, WConfigListPanel list) -> {
            boolean b = bl && this.config.serverInventoryConfigs.contains(new ServerInventoryConfig(server, inventoryConfig.getName()));
            String name = inventoryConfig.getName().replaceAll(JSON_FORMAT, "");

            list.button.setLabel(SpecificGui.cutString(name, 16));
            list.button.setOnClick(() -> {
                field.setText(name.replaceAll(".json", ""));
                if(b) pinButton.setToggle(true);
            });

            if (b) {
                list.button.setIcon(new TextureIcon(Painters.PIN));
            }

            list.setButtonTooltip(name);

            list.remove.setOnClick(() -> {
                InventoryConfigManager.removeConfig(inventoryConfig);
                updateList(InventoryConfigManager.getConfigs());
            });
        };

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.setListItemHeight(20);
        list.setBackgroundPainter(Painters.CONFIG_BACKGROUND_PAINTER);
        root.add(list, 0, 75, width, height - 75);

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

    private void saveConfig(String text, List<WSlot> inventory, List<WSlot> hotBar, boolean pin) {
        String s = text + JSON_FORMAT;
        Matcher matcher = FILE_PATTERN.matcher(s);
        if (!matcher.matches()) return;

        InventoryConfig inventoryConfig = new InventoryConfig(MinecraftClient.getInstance().getSession().getUsername(), ItemUtils.getItemsFromButtons(inventory), ItemUtils.getItemsFromButtons(hotBar), s);
        InventoryConfigManager.writeConfig(inventoryConfig);

        if (pin) {
            SpecificConfigManager.addServerInventoryConfig(this.config, new ServerInventoryConfig(server, inventoryConfig.getName()));
        } else if (server == null) {
            SpecificConfigManager.setConfig(this.config, inventoryConfig);
        } else {
            SpecificConfigManager.setConfig(this.config, inventoryConfig);
            SpecificConfigManager.removeServerInventoryConfig(this.config, new ServerInventoryConfig(server, inventoryConfig.getName()));
        }
        this.config = SpecificSlots.config;
    }

    private void updateList(List<InventoryConfig> configs) {
        root.remove(empty);
        root.remove(list);

        if (configs.isEmpty()) {
            empty = new WLabel(Text.translatable("specific_slots.empty_search"));
            empty.setHorizontalAlignment(HorizontalAlignment.CENTER);
            empty.setVerticalAlignment(VerticalAlignment.CENTER);
            root.add(empty, 0, 75, width, height - 75);
        }

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);

        list.setListItemHeight(20);
        list.setBackgroundPainter(Painters.CONFIG_BACKGROUND_PAINTER);
        list.layout();
        list.validate(this);

        root.add(list, 0, 75, width, height - 75);
    }

    private List<InventoryConfig> getSearch(List<InventoryConfig> configs, String text) {
        List<InventoryConfig> list = new ArrayList<>();

        for (InventoryConfig config : configs) {
            if (config.getName().replaceAll(".json", "").replaceAll("ё","е").toLowerCase().contains(text.toLowerCase().replaceAll("ё","е"))) {
                list.add(config);
            }
        }

        return list;
    }

    @Override
    public TriState isDarkMode() {
        return TriState.of(config.isDarkMode);
    }

    public record ServerInventoryConfig(String ip, String inventoryConfig) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ServerInventoryConfig config = (ServerInventoryConfig) o;
            return ip.equals(config.ip) && inventoryConfig.equals(config.inventoryConfig);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ip, inventoryConfig);
        }
    }
}
