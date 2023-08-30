package net.lopymine.specificslots.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.lopymine.specificslots.gui.widgets.WSlot;
import net.lopymine.specificslots.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.lopymine.specificslots.SpecificSlots.logger;

public class SpecificConfigManager {
    public static final String pathToConfig = "config/specific_slots/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String jsonFormat = ".json";
    public static SpecificConfig EMPTY = getEmpty();
    private final static Pattern filePattern = Pattern.compile("^[[a-zA-Z0-9А-Яа-яЁё_],\\s-]+\\.[A-Za-z]{4}$");
    public static void checkOrCreateConfig(){
        File path = new File(pathToConfig);
        if(path.exists()) return;
        if(path.mkdirs()){
            logger.info("Config folder created");
        } else {
            logger.info("Failed to create config folder");
        }
    }

    public static SpecificConfig read(String name){
        checkOrCreateConfig();

        try (FileReader reader = new FileReader(pathToConfig + name.replaceAll(jsonFormat,"") + jsonFormat)) {
            return gson.fromJson(reader, SpecificConfig.class).setName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EMPTY.setName(name);
    }

    public static List<SpecificConfig> getConfigs(){
        checkOrCreateConfig();
        File folder = new File(pathToConfig);
        File[] files = folder.listFiles();
        List<SpecificConfig> configs = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if(!file.getName().endsWith(".json")) continue;
                try (FileReader reader = new FileReader(file)) {
                    SpecificConfig config = gson.fromJson(reader, SpecificConfig.class);
                    if(config != null){
                        configs.add(config.setName(file.getName()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return configs;
    }

    public static void writeConfig(SpecificConfig config){
        checkOrCreateConfig();
        String n = config.getFileName();
        config.setName(null);
        String json = gson.toJson(config, SpecificConfig.class);
        try (FileWriter writer = new FileWriter(pathToConfig + n.replaceAll(jsonFormat,"") + jsonFormat)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeConfig(String name, List<WSlot> inventory, List<WSlot> hotBar){
        checkOrCreateConfig();
        String s = name + jsonFormat;
        Matcher matcher = filePattern.matcher(s);

        if(!matcher.matches()) return;

        SpecificConfig config = new SpecificConfig(MinecraftClient.getInstance().getSession().getUsername(), getItemsFromButtons(inventory), getItemsFromButtons(hotBar)).setName(s);
        DefaultSpecificConfigManager.setInventoryConfig(config);
        config.setName(null);
        String json = gson.toJson(config, SpecificConfig.class);
        try (FileWriter writer = new FileWriter(pathToConfig + s)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeConfig(String name){
        checkOrCreateConfig();
        File file = new File(pathToConfig + name.replaceAll(jsonFormat,"") + jsonFormat);
        if (file.delete()) {
            logger.warn("Config {} deleted", name);
        } else {
            logger.warn("Failed to delete {} config", name);
        }
    }

    private static List<String> getItemsFromButtons(List<WSlot> list) {
        List<String> names = new ArrayList<>();
        list.forEach(button -> names.add(getItemName(button)));
        return names;
    }

    private static String getItemName(WSlot button) {
        if(button.getItem() == null) return "minecraft:air";
        Item item = button.getItem().asItem();
        return ItemUtils.getItemName(item);
    }

    private static SpecificConfig getEmpty(){
        List<String> EMPTY_INVENTORY = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            EMPTY_INVENTORY.add("minecraft:air");
        }
        List<String> EMPTY_HOTBAR = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            EMPTY_HOTBAR.add("minecraft:air");
        }
        return new SpecificConfig(MinecraftClient.getInstance().getSession().getUsername(),EMPTY_INVENTORY,EMPTY_HOTBAR).setName("default" + jsonFormat);
    }
}
