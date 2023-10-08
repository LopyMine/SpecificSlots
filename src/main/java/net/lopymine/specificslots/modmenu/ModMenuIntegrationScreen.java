package net.lopymine.specificslots.modmenu;

import me.shedaniel.clothconfig2.api.*;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.modmenu.enums.SortMode;

public class ModMenuIntegrationScreen {
    public static Screen createScreen(Screen parentScreen) {
        SpecificConfig config = SpecificConfigManager.getConfig();
        SpecificConfig defaultConfig = new SpecificConfig();

        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.of("Specific Slots"))
                .setSavingRunnable(() -> SpecificConfigManager.setConfig(config));

        ConfigCategory main = configBuilder.getOrCreateCategory(Text.of("Specific Slots"));
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

        main.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.dark_mode"), config.isDarkMode)
                .setSaveConsumer(darkMode -> config.isDarkMode = darkMode)
                .setDefaultValue(defaultConfig.isDarkMode)
                .build());

        ConfigCategory ghostSlots = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.shadow_items"));

        ghostSlots.addEntry(entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.depth"), config.depth, 1, 4)
                .setSaveConsumer(depth -> config.depth = depth)
                .setDefaultValue(defaultConfig.depth)
                .build());

        ConfigCategory wrongSlots = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.wrong_slots"));

        wrongSlots.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots"), config.enableHighlightWrongSlots)
                .setSaveConsumer(bl -> config.enableHighlightWrongSlots = bl)
                .setDefaultValue(defaultConfig.enableHighlightWrongSlots)
                .build());

        SubCategoryBuilder wrongSlotsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.wrong_subcategory"));

        wrongSlotsColor.add(0, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.color"), config.wrongHighlightColor)
                .setSaveConsumer(color -> config.wrongHighlightColor = color)
                .setDefaultValue(defaultConfig.wrongHighlightColor)
                .build());

        wrongSlotsColor.add(1, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.alpha"), config.wrongHighlightAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.wrongHighlightAlpha = alpha)
                .setDefaultValue(defaultConfig.wrongHighlightAlpha)
                .build());

        wrongSlots.addEntry(wrongSlotsColor.build());

        SubCategoryBuilder emptySlotsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.empty_subcategory"));

        emptySlotsColor.add(0, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_empty_slots.color"), config.emptyHighlightColor)
                .setSaveConsumer(color -> config.emptyHighlightColor = color)
                .setDefaultValue(defaultConfig.emptyHighlightColor)
                .build());

        emptySlotsColor.add(1, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_empty_slots.alpha"), config.emptyHighlightAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.emptyHighlightAlpha = alpha)
                .setDefaultValue(defaultConfig.emptyHighlightAlpha)
                .build());

        wrongSlots.addEntry(emptySlotsColor.build());

        ConfigCategory sorting = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.sort"));

        sorting.addEntry(entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.specific_shift_sort"), config.enableSpecificShiftSort)
                .setSaveConsumer(bl -> config.enableSpecificShiftSort = bl)
                .setTooltip(Text.translatable("specific_slots.mod_menu.specific_shift_sort.tooltip"))
                .setDefaultValue(defaultConfig.enableSpecificShiftSort)
                .build());

        sorting.addEntry(entryBuilder.startSelector(Text.translatable("specific_slots.mod_menu.sort_mode"), SortMode.values(), config.sortMode)
                .setSaveConsumer(mode -> config.sortMode = mode)
                .setNameProvider(SortMode::getText)
                .setDefaultValue(defaultConfig.sortMode)
                .build());

        return configBuilder.build();
    }
}
