package net.lopymine.specificslots.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.config.inventory.InventoryConfigManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SpecificConfigManager {
    public static final String PATH_TO_CONFIG = "config/";
    public static final String CONFIG_NAME = "specificslots";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final String FILE_FORMAT = ".json5";

    /**
     * Sets config for Specific Slots.
     * <p>
     * Automatically sets the config to {@link SpecificSlots#config }.
     *
     * @param config the default config
    */
    public static void setConfig(SpecificConfig config) {
        String json = gson.toJson(config, SpecificConfig.class);

        try (FileWriter writer = new FileWriter(PATH_TO_CONFIG + CONFIG_NAME + FILE_FORMAT)) {
            writer.write(json);

            SpecificSlots.config = config;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets config for Specific Slots.
     * <p>
     * Automatically sets the config to {@link SpecificSlots#config }.
     * <p>
     * Automatically sets the inventory config to {@link SpecificSlots#inventoryConfig }.
     *
     * @param config the default config
     * @param inventoryConfig the inventory config
     */
    public static void setConfig(SpecificConfig config, InventoryConfig inventoryConfig) {
        config.setInventoryConfig(inventoryConfig.getName());

        String json = gson.toJson(config, SpecificConfig.class);

        try (FileWriter writer = new FileWriter(PATH_TO_CONFIG + CONFIG_NAME + FILE_FORMAT)) {
            writer.write(json);

            SpecificSlots.config = config;
            SpecificSlots.inventoryConfig = inventoryConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets inventory config from Specific Slots config.
     * <p> If Specific Slots config is not found, it will return and create a new config.
     * <p> If inventory config is not found, it will return a new config.
     *
     * @return the inventory config
     */
    public static InventoryConfig getInventoryConfig() {
        return InventoryConfigManager.readFromConfig(getConfig());
    }



    /**
     * Gets Specific Slots config.
     * <p> If Specific Slots config is not found, it will return and create a new config.
     *
     * @return the config
     */
    public static SpecificConfig getConfig() {

        try (FileReader reader = new FileReader(PATH_TO_CONFIG + CONFIG_NAME + FILE_FORMAT)) {
            return gson.fromJson(reader, SpecificConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SpecificConfig config = new SpecificConfig(InventoryConfigManager.EMPTY.getName());
        setConfig(config); //Specific Slots config not found? create a new one!

        return config;
    }
}
