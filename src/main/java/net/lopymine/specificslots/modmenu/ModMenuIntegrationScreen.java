package net.lopymine.specificslots.modmenu;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuIntegrationScreen {
    public static Screen createScreen(Screen parentScreen) {
        DefaultSpecificConfig config = DefaultSpecificConfigManager.getDefaultConfig();

        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.of("Specific Slots"))
                .setSavingRunnable(() -> DefaultSpecificConfigManager.setDefaultConfig(config));

        ConfigCategory main = configBuilder.getOrCreateCategory(Text.of("Specific Slots"));
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

        main.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.dark_mode"), config.isDarkMode)
                .setSaveConsumer(darkMode -> config.isDarkMode = darkMode)
                .setDefaultValue(false)
                .build());

        ConfigCategory ghostSlots = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.shadow_items"));

        ghostSlots.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.shadow_items"), config.renderSlotWithItem)
                .setSaveConsumer(show -> config.renderSlotWithItem = show)
                .setTooltip(Text.translatable("specific_slots.mod_menu.shadow_items.tooltip"))
                .setDefaultValue(false)
                .build());

        ghostSlots.addEntry(entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.depth"), config.depth, 1, 4)
                .setSaveConsumer(depth -> config.depth = depth)
                .setDefaultValue(1)
                .build());

        ConfigCategory wrongSlots = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.wrong_slots"));

        wrongSlots.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots"), config.enableHighlightWrongSlots)
                .setSaveConsumer(bool -> config.enableHighlightWrongSlots = bool)
                .setDefaultValue(true)
                .build());

        wrongSlots.addEntry(entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.color"), config.color)
                .setSaveConsumer(color -> config.color = color)
                .setDefaultValue(16711680)
                .build());

        wrongSlots.addEntry(entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.alpha"), config.alpha, 0, 100)
                .setSaveConsumer(alpha -> config.alpha = alpha)
                .setDefaultValue(30)
                .build());

        return configBuilder.build();
    }
}
