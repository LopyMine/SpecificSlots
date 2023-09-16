package net.lopymine.specificslots.modmenu;

import me.shedaniel.clothconfig2.api.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.modmenu.enums.SortMode;

public class ModMenuIntegrationScreen {
    public static Screen createScreen(Screen parentScreen) {
        SpecificConfig config = SpecificConfigManager.getConfig();

        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.of("Specific Slots"))
                .setSavingRunnable(() -> SpecificConfigManager.setConfig(config));

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
                .setSaveConsumer(bl -> config.enableHighlightWrongSlots = bl)
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

        ConfigCategory sorting = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.sort"));

        sorting.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.specific_shift_sort"), config.enableSpecificShiftSort)
                .setSaveConsumer(bl -> config.enableSpecificShiftSort = bl)
                .setTooltip(Text.translatable("specific_slots.mod_menu.specific_shift_sort.tooltip"))
                .setDefaultValue(true)
                .build());

        sorting.addEntry(entryBuilder.startSelector(Text.translatable("specific_slots.mod_menu.sort_mode"), SortMode.values(), config.sortMode)
                .setSaveConsumer(mode -> config.sortMode = mode)
                .setNameProvider(SortMode::getText)
                .setDefaultValue(SortMode.ALL)
                .build());

        return configBuilder.build();
    }
}
