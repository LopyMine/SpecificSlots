package net.lopymine.specificslots.gui.config;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.fabricmc.fabric.api.util.TriState;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.SpecificConfigManager;
import net.lopymine.specificslots.gui.panels.list.WConfigListPanel;
import net.lopymine.specificslots.gui.panels.list.WListPanelExt;
import net.lopymine.specificslots.gui.screen.SpecificScreen;
import net.lopymine.specificslots.utils.DrawUtils;
import net.lopymine.specificslots.gui.SpecificSlotsGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LoadConfigGui extends LightweightGuiDescription {
    private final int width = 180;
    private final int height = 180;
    private final DefaultSpecificConfig defaultConfig;
    private List<SpecificConfig> configs;
    private WListPanelExt<SpecificConfig, WConfigListPanel> list;
    private final WPlainPanel root;
    private WLabel empty;
    private final Screen parent;
    private final BiConsumer<SpecificConfig, WConfigListPanel> configurator;

    public LoadConfigGui(Screen back, Screen parent, DefaultSpecificConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
        this.parent = back;
        root = new WPlainPanel() {
            @Override
            public InputResult onKeyPressed(int ch, int key, int modifiers) {
                if (ch == 256) {
                    MinecraftClient.getInstance().setScreen(LoadConfigGui.this.parent);
                    return InputResult.PROCESSED;
                }
                return InputResult.IGNORED;
            }

            @Override
            public InputResult onMouseScroll(int x, int y, double amount) {
                if(list == null) return InputResult.IGNORED;
                return list.onMouseScroll(x, y, amount);
            }
        };
        root.setSize(width, height);
        root.setInsets(Insets.ROOT_PANEL);

        WTextField field = new WTextField(Text.translatable("specific_slots.file_name"));
        field.setChangedListener((s) -> {
            if (!s.isEmpty()) {
                configs = getSearch(configs, s);
                updateList();
            } else {
                configs = SpecificConfigManager.getConfigs();
                updateList();
            }
        });
        field.setMaxLength(50);
        root.add(field, 15, 25, 150, 20);

        WLabel label = new WLabel(Text.translatable("specific_slots.load_config"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        label.setVerticalAlignment(VerticalAlignment.BOTTOM);
        root.add(label, 0, 0, width, 15);

        configs = SpecificConfigManager.getConfigs();

        configurator = (SpecificConfig config, WConfigListPanel list) -> {
            String configName = config.getName();

            list.button.setLabel(DrawUtils.cutString(configName, 16));

            list.button.setOnClick(() -> {
                DefaultSpecificConfigManager.setInventoryConfig(config);
                MinecraftClient.getInstance().setScreen(new SpecificScreen(new SpecificSlotsGui(config, parent)));
            });

            list.setButtonTooltip(configName);

            list.remove.setOnClick(() -> {
                SpecificConfigManager.removeConfig(configName);
                configs = SpecificConfigManager.getConfigs();
                updateList();
            });
        };

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.setListItemHeight(20);
        list.setBackgroundPainter(DrawUtils.listBackgroundPainter);
        root.add(list, 0, 55, width, height - 50);

        setRootPanel(root);
        root.validate(this);
    }

    private void updateList() {
        root.remove(empty);
        root.remove(list);

        if (configs.isEmpty()) {
            empty = new WLabel(Text.translatable("specific_slots.empty_search"));
            empty.setHorizontalAlignment(HorizontalAlignment.CENTER);
            empty.setVerticalAlignment(VerticalAlignment.CENTER);
            root.add(empty, 90, 110);
        }

        list = new WListPanelExt<>(configs, WConfigListPanel::new, configurator);
        list.getScrollBar().setHost(this);
        list.setListItemHeight(20);
        list.setBackgroundPainter(DrawUtils.listBackgroundPainter);
        list.layout();
        list.validate(this);
        root.add(list, 0, 55, width, height - 50);
    }

    private ArrayList<SpecificConfig> getSearch(List<SpecificConfig> configs, String text) {
        ArrayList<SpecificConfig> list = new ArrayList<>();
        for (SpecificConfig config : configs) {
            if (config != null) {
                if (config.getFileName() == null) continue;
                if (config.getName().contains(text)) list.add(config);
            }
        }
        return list;
    }

    @Override
    public TriState isDarkMode() {
        return TriState.of(defaultConfig.isDarkMode);
    }
}
