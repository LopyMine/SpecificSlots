package net.lopymine.specificslots.config;

import me.shedaniel.math.Color;

import net.lopymine.specificslots.modmenu.enums.SortMode;

public class SpecificConfig {

    private final String WARNING = "PLEASE DON'T EDIT THIS FILE, THE GAME MAY CRASH";
    public SortMode sortMode = SortMode.ALL;
    private String inventoryConfig;
    public boolean renderSlotWithItem = false;
    public boolean enableHighlightWrongSlots = true;
    public boolean enableSpecificShiftSort = true;
    public boolean isDarkMode = false;
    public Integer color = 16711680;
    public Integer alpha = 30;
    public Integer depth = 1;

    public SpecificConfig(String config) {
        if (config == null) throw new NullPointerException("Null config");
        this.inventoryConfig = config;
    }

    public String getInventoryConfig() {
        return this.inventoryConfig;
    }

    public void setInventoryConfig(String defaultConfig) {
        this.inventoryConfig = defaultConfig;
    }

    public Integer getColor() {
        Color color = Color.ofTransparent(this.color);
        Color color_with_alpha = Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) alpha / 100 * 255));
        return color_with_alpha.getColor();
    }

}
