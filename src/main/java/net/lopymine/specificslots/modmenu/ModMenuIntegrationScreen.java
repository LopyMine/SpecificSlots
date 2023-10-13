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

        ConfigCategory wrongSlots = configBuilder.getOrCreateCategory(Text.translatable("specific_slots.mod_menu.wrong_slots"));

        SubCategoryBuilder ghostItemsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.ghost_items_subcategory"));

        ghostItemsColor.add(0, entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.ghost_items.toggle"), config.enableRenderGhostItems)
                .setSaveConsumer(bl -> config.enableRenderGhostItems = bl)
                .setDefaultValue(defaultConfig.enableRenderGhostItems)
                .build());

        ghostItemsColor.add(1, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.ghost_items.color"), config.ghostItemsColor)
                .setSaveConsumer(color -> config.ghostItemsColor = color)
                .setDefaultValue(defaultConfig.ghostItemsColor)
                .build());

        ghostItemsColor.add(2, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.ghost_items.alpha"), config.ghostItemsAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.ghostItemsAlpha = alpha)
                .setDefaultValue(defaultConfig.ghostItemsAlpha)
                .build());

        wrongSlots.addEntry(ghostItemsColor.build());

        SubCategoryBuilder wrongSlotsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.wrong_subcategory"));

        wrongSlotsColor.add(0, entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.toggle"), config.enableHighlightWrongSlots)
                .setSaveConsumer(bl -> config.enableHighlightWrongSlots = bl)
                .setDefaultValue(defaultConfig.enableHighlightWrongSlots)
                .build());

        wrongSlotsColor.add(1, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.color"), config.wrongHighlightColor)
                .setSaveConsumer(color -> config.wrongHighlightColor = color)
                .setDefaultValue(defaultConfig.wrongHighlightColor)
                .build());

        wrongSlotsColor.add(2, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_wrong_slots.alpha"), config.wrongHighlightAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.wrongHighlightAlpha = alpha)
                .setDefaultValue(defaultConfig.wrongHighlightAlpha)
                .build());

        wrongSlots.addEntry(wrongSlotsColor.build());

        SubCategoryBuilder emptySlotsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.empty_subcategory"));

        emptySlotsColor.add(0, entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.highlight_empty_slots.toggle"), config.enableHighlightEmptySlots)
                .setSaveConsumer(bl -> config.enableHighlightEmptySlots = bl)
                .setDefaultValue(defaultConfig.enableHighlightEmptySlots)
                .build());

        emptySlotsColor.add(1, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_empty_slots.color"), config.emptyHighlightColor)
                .setSaveConsumer(color -> config.emptyHighlightColor = color)
                .setDefaultValue(defaultConfig.emptyHighlightColor)
                .build());

        emptySlotsColor.add(2, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_empty_slots.alpha"), config.emptyHighlightAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.emptyHighlightAlpha = alpha)
                .setDefaultValue(defaultConfig.emptyHighlightAlpha)
                .build());

        wrongSlots.addEntry(emptySlotsColor.build());

        SubCategoryBuilder rightSlotsColor = entryBuilder.startSubCategory(Text.translatable("specific_slots.mod_menu.wrong_slots.right_subcategory"));

        rightSlotsColor.add(0, entryBuilder.startBooleanToggle(Text.translatable("specific_slots.mod_menu.highlight_right_slots.toggle"), config.enableHighlightRightSlots)
                .setSaveConsumer(bl -> config.enableHighlightRightSlots = bl)
                .setDefaultValue(defaultConfig.enableHighlightRightSlots)
                .build());

        rightSlotsColor.add(1, entryBuilder.startColorField(Text.translatable("specific_slots.mod_menu.highlight_right_slots.color"), config.rightHighlightColor)
                .setSaveConsumer(color -> config.rightHighlightColor = color)
                .setDefaultValue(defaultConfig.rightHighlightColor)
                .build());

        rightSlotsColor.add(2, entryBuilder.startIntSlider(Text.translatable("specific_slots.mod_menu.highlight_right_slots.alpha"), config.rightHighlightAlpha, 0, 100)
                .setSaveConsumer(alpha -> config.rightHighlightAlpha = alpha)
                .setDefaultValue(defaultConfig.rightHighlightAlpha)
                .build());

        wrongSlots.addEntry(rightSlotsColor.build());

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
