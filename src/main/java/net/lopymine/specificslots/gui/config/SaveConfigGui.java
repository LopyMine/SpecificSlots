package net.lopymine.specificslots.gui.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.util.TriState;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;

import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.config.inventory.*;
import net.lopymine.specificslots.gui.panels.list.*;
import net.lopymine.specificslots.gui.widgets.WSlot;
import net.lopymine.specificslots.utils.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.*;

import static net.lopymine.specificslots.config.inventory.InventoryConfigManager.JSON_FORMAT;

public class SaveConfigGui extends LightweightGuiDescription {
    private final int width = 180;
    private final int height = 180;
    private final BiConsumer<InventoryConfig, WConfigListPanel> configurator;
    private final SpecificConfig defaultConfig;
    private final Screen parent;
    private WListPanelExt<InventoryConfig, WConfigListPanel> list;
    public final static Pattern FILE_PATTERN = Pattern.compile("^[[a-zA-Z0-9А-Яа-яЁё_],\\s-]+\\.[A-Za-z]{4}$");
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
    private List<InventoryConfig> configs;
    private WLabel empty;

    public SaveConfigGui(Screen parent, List<WSlot> inventory, List<WSlot> hotBar, SpecificConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
        this.parent = parent;

        configs = InventoryConfigManager.getConfigs();
        root.setSize(width, height);
        root.setInsets(Insets.ROOT_PANEL);

        WTextField field = new WTextField(Text.translatable("specific_slots.file_name"))
                .setChangedListener((s) -> {
                    if (!s.isEmpty()) {
                        configs = getSearch(configs, s);
                        updateList();
                    } else {
                        configs = InventoryConfigManager.getConfigs();
                        updateList();
                    }
                })
                .setMaxLength(50);

        root.add(field, 15, 25, 150, 20);

        WLabel label = new WLabel(Text.translatable("specific_slots.save_config"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);

        root.add(label, 0, 0, width, 15);

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

                    this.saveConfig(field.getText(), inventory, hotBar);
                    configs = InventoryConfigManager.getConfigs();
                    updateList();
                });

        root.add(save, 15, 50, 150, 20);

        configurator = (InventoryConfig inventoryConfig, WConfigListPanel list) -> {
            String name = inventoryConfig.getName().replaceAll(JSON_FORMAT, "");

            list.button.setLabel(DrawUtils.cutString(name, 16));
            list.button.setOnClick(() -> {
                field.setText(name.replaceAll(".json", ""));
            });

            list.setButtonTooltip(name);

            list.remove.setOnClick(() -> {
                InventoryConfigManager.removeConfig(inventoryConfig);
                configs = InventoryConfigManager.getConfigs();
                updateList();
            });
        };

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.setListItemHeight(20);
        list.setBackgroundPainter(DrawUtils.listBackgroundPainter);
        root.add(list, 0, 75, width, height - 75);

        setRootPanel(root);
        root.validate(this);
    }

    private void saveConfig(String text, List<WSlot> inventory, List<WSlot> hotBar) {
        String s = text + JSON_FORMAT;
        Matcher matcher = FILE_PATTERN.matcher(s);
        if (!matcher.matches()) return;

        InventoryConfig config = new InventoryConfig(MinecraftClient.getInstance().getSession().getUsername(), ItemUtils.getItemsFromButtons(inventory), ItemUtils.getItemsFromButtons(hotBar), s);
        InventoryConfigManager.writeConfig(config);

        SpecificConfigManager.setConfig(defaultConfig, config);
    }

    private void updateList() {
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
        list.setBackgroundPainter(DrawUtils.listBackgroundPainter);
        list.layout();
        list.validate(this);

        root.add(list, 0, 75, width, height - 75);
    }

    private List<InventoryConfig> getSearch(List<InventoryConfig> configs, String text) {
        List<InventoryConfig> list = new ArrayList<>();
        for (InventoryConfig config : configs) {
            if (config.getName().contains(text)) {
                list.add(config);
            }
        }
        return list;
    }

    @Override
    public TriState isDarkMode() {
        return TriState.of(defaultConfig.isDarkMode);
    }


}
