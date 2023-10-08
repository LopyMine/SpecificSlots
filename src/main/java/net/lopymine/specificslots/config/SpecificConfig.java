package net.lopymine.specificslots.config;

import me.shedaniel.math.Color;

import net.lopymine.specificslots.gui.config.SaveConfigGui.ServerInventoryConfig;
import net.lopymine.specificslots.modmenu.enums.SortMode;

import java.util.*;

public class SpecificConfig {
    private final String WARNING = "PLEASE DONT EDIT THIS FILE, THE GAME MAY CRASH";
    public SortMode sortMode = SortMode.ALL;
    private String inventoryConfig;
    public Set<ServerInventoryConfig> serverInventoryConfigs = new HashSet<>();
    public boolean enableHighlightWrongSlots = true;
    public boolean enableSpecificShiftSort = true;
    public boolean isDarkMode = false;
    public Integer wrongHighlightColor = 16711680;
    public Integer emptyHighlightColor = 16711935;
    public Integer wrongHighlightAlpha = 30;
    public Integer emptyHighlightAlpha = 30;

    public SpecificConfig() {}

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

    public Integer getWrongHighlightColor() {
        Color color = Color.ofTransparent(this.wrongHighlightColor);
        Color color_with_alpha = Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) wrongHighlightAlpha / 100 * 255));
        return color_with_alpha.getColor();
    }

    public Integer getEmptyHighlightColor() {
        Color color = Color.ofTransparent(this.emptyHighlightColor);
        Color color_with_alpha = Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) emptyHighlightAlpha / 100 * 255));
        return color_with_alpha.getColor();
    }

    public void addServerInventoryConfig(ServerInventoryConfig serverInventoryConfig) {
        if(!serverInventoryConfigs.isEmpty()){
            List<ServerInventoryConfig> configs = new ArrayList<>(serverInventoryConfigs);
            for(ServerInventoryConfig config : configs){
                if(Objects.equals(config.ip(), serverInventoryConfig.ip())){
                    this.removeServerInventoryConfig(config);
                }
            }
        }
        serverInventoryConfigs.add(serverInventoryConfig);
    }

    public void removeServerInventoryConfig(ServerInventoryConfig serverInventoryConfig) {
        serverInventoryConfigs.remove(serverInventoryConfig);
    }
}
