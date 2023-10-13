package net.lopymine.specificslots.config;

import me.shedaniel.math.Color;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.gui.config.SaveConfigGui.ServerInventoryConfig;
import net.lopymine.specificslots.modmenu.enums.SortMode;

import java.util.*;

public class SpecificConfig {
    private final String WARNING = "PLEASE DONT EDIT THIS FILE, THE GAME MAY CRASH";
    public SortMode sortMode = SortMode.ALL;
    private String inventoryConfig;
    public Set<ServerInventoryConfig> serverInventoryConfigs = new HashSet<>();
    public boolean enableSpecificShiftSort = true;
    public boolean isDarkMode = false;
    public boolean enableHighlightWrongSlots = true;
    public Integer wrongHighlightColor = 16711680;
    public Integer wrongHighlightAlpha = 30;
    public boolean enableHighlightEmptySlots = false;
    public Integer emptyHighlightColor = 16711935;
    public Integer emptyHighlightAlpha = 10;
    public boolean enableHighlightRightSlots = false;
    public Integer rightHighlightColor = 8421631;
    public Integer rightHighlightAlpha = 30;
    public boolean enableRenderGhostItems = true;
    public Integer ghostItemsColor = 8421504;
    public Integer ghostItemsAlpha = 25;
    public boolean rainbow = false;

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

    public Integer getRightHighlightColor() {
        Color color = Color.ofTransparent(this.rightHighlightColor);
        Color color_with_alpha;
        if (SpecificSlots.config.rainbow) {
            int a = 0xFFF;
            int i = (int) (System.currentTimeMillis() % a);
            int r = Math.max(Math.min(Math.max(255 - ((a - i) / 6), 0), 255), Math.min(Math.max(255 - (i / 6), 0), 255));
            int g = Math.min(Math.max(255 - ((Math.max(i, a/3) - Math.min(i, a/3)) / 6), 0), 255);
            int b = Math.min(Math.max(255 - ((Math.max(i, (a/3) * 2) - Math.min(i, (a/3) * 2)) / 6), 0), 255);
            color_with_alpha = Color.ofRGBA(r, g, b, (int) ((float) rightHighlightAlpha / 100 * 255));
        } else {
            color_with_alpha = Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) rightHighlightAlpha / 100 * 255));
        }
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
