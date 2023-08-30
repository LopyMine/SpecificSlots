package net.lopymine.specificslots.config.defalt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lopymine.specificslots.SpecificSlots;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.SpecificConfigManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultSpecificConfigManager {
    public static final String pathToConfig = "config/";
    public static final String configName = "specificslots";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final String jsonFormat = ".json5";

    public static void setDefaultConfig(DefaultSpecificConfig config) {
        SpecificSlots.defaultConfig = config;

        String json = gson.toJson(config, DefaultSpecificConfig.class);

        try (FileWriter writer = new FileWriter(pathToConfig + configName + jsonFormat)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DefaultSpecificConfig getDefaultConfig() {
        try (FileReader reader = new FileReader(pathToConfig + configName + jsonFormat)) {
            return gson.fromJson(reader, DefaultSpecificConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSpecificConfig("default" + jsonFormat);
    }

    public static SpecificConfig getInventoryConfig() {
        return SpecificConfigManager.read(getDefaultConfig().getInventoryConfig());
    }

    public static void setInventoryConfig(SpecificConfig config) {
        DefaultSpecificConfig defaultConfig = getDefaultConfig();
        defaultConfig.setInventoryConfig(config.getFileName());

        SpecificSlots.config = config;
        SpecificSlots.defaultConfig = defaultConfig;

        setDefaultConfig(defaultConfig);
    }
}
