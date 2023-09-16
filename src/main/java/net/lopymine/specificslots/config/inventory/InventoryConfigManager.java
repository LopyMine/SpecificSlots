package net.lopymine.specificslots.config.inventory;

import com.google.gson.*;
import net.minecraft.client.MinecraftClient;

import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.*;

import java.io.*;
import java.util.*;

import static net.lopymine.specificslots.SpecificSlots.logger;

public class InventoryConfigManager {
    public static final String PATH_TO_CONFIG = "config/specific_slots/";
    public static final String JSON_FORMAT = ".json";
    public static final InventoryConfig EMPTY = getEmpty();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Checks inventory config folder.
     * <p> If config folder is not found, it creates.
     */
    public static void checkConfigsFolder() {
        File path = new File(PATH_TO_CONFIG);
        if (path.exists()) return; // config folder found

        if (path.mkdirs()) {
            logger.info("Config folder created");
        } else {
            logger.info("Failed to create config folder");
        }
    }

    /**
     * Gets inventory config.
     *
     * @param name the inventory config name
     * @return the inventory config
     */
    public static InventoryConfig read(String name){
        checkConfigsFolder();

        try (FileReader reader = new FileReader(PATH_TO_CONFIG + name.replaceAll(JSON_FORMAT, "") + JSON_FORMAT)) {
            return gson.fromJson(reader, InventoryConfig.class).setName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EMPTY; // return empty config
    }

    /**
     * Gets inventory config from Specific Slots config.
     * <p> If inventory config is not found, it will return a default inventory config and set it to the Specific Slots config.
     *
     * @param config the Specific Slots config
     * @return the inventory config
     */
    public static InventoryConfig readFromConfig(SpecificConfig config){
        checkConfigsFolder();

        String name = config.getInventoryConfig();

        try (FileReader reader = new FileReader(PATH_TO_CONFIG + name)) {
            return gson.fromJson(reader, InventoryConfig.class).setName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!SpecificSlots.DEFAULT_INVENTORY_CONFIG.exists()) writeConfig(EMPTY); // if the default inventory config is not found, creates a new
        InventoryConfig inventoryConfigDefault = read(EMPTY.getName()); // read the default inventory config

        SpecificConfigManager.setConfig(config, inventoryConfigDefault); // sets the default inventory config

        return inventoryConfigDefault; // return empty config
    }

    /**
     * Gets all inventory configs.
     * @return the inventory configs
     */
    public static List<InventoryConfig> getConfigs(){
        checkConfigsFolder();

        File folder = new File(PATH_TO_CONFIG);
        File[] files = folder.listFiles();
        List<InventoryConfig> configs = new ArrayList<>();

        if (files == null) return configs;

        for (File file : files) {
            if(!file.getName().endsWith(".json")) continue;

            try (FileReader reader = new FileReader(file)) {
                InventoryConfig config = gson.fromJson(reader, InventoryConfig.class).setName(file.getName());

                if(config != null) configs.add(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return configs;
    }
    /**
     * Writes a new inventory config.
    */
    public static void writeConfig(InventoryConfig config){
        checkConfigsFolder();

        String configName = config.getName();

        config.setName(null);
        String json = gson.toJson(config, InventoryConfig.class);
        config.setName(configName);

        try (FileWriter writer = new FileWriter(PATH_TO_CONFIG + configName)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes inventory config.
     */
    public static void removeConfig(InventoryConfig config){
        checkConfigsFolder();

        String name = config.getName();

        File file = new File(PATH_TO_CONFIG + name);

        if (file.delete()) {
            logger.warn("Config {} deleted", name);
        } else {
            logger.warn("Failed to delete {} config", name);
        }
    }

    private static InventoryConfig getEmpty(){
        List<String> emptyInventory = new ArrayList<>();
        List<String> emptyHotBar = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            emptyInventory.add("minecraft:air");
        }
        for (int i = 0; i < 9; i++) {
            emptyHotBar.add("minecraft:air");
        }

        return new InventoryConfig(MinecraftClient.getInstance().getSession().getUsername(), emptyInventory, emptyHotBar, "default" + JSON_FORMAT);
    }
}
