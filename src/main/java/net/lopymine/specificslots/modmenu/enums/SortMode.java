package net.lopymine.specificslots.modmenu.enums;

import net.minecraft.text.Text;

public enum SortMode {
    ALL(Text.translatable("specific_slots.mod_menu.enum.sortMode.all")),
    INVENTORY(Text.translatable("specific_slots.mod_menu.enum.sortMode.inventory")),
    CONTAINER(Text.translatable("specific_slots.mod_menu.enum.sortMode.container")),
    OFF(Text.translatable("specific_slots.mod_menu.enum.sortMode.off"));

    private final Text text;

    SortMode(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
