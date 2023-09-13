package net.lopymine.specificslots;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.specificslots.config.*;
import net.lopymine.specificslots.config.inventory.*;
import net.lopymine.specificslots.textures.ShadowItems;
import net.lopymine.specificslots.utils.ItemUtils;

import java.io.File;
import java.nio.file.Path;

public class SpecificSlots implements ModInitializer {

    public static final String ID = "specificslots";
    public static final Logger logger = LogUtils.getLogger();
    private static final File DEFAULT_MOD_CONFIG = new File(FabricLoader.getInstance().getConfigDir().toFile(), "specificslots.json5");
    public static final File DEFAULT_INVENTORY_CONFIG = new File(Path.of(FabricLoader.getInstance().getConfigDir() + "/specific_slots/").toFile(), "default.json");
    public static InventoryConfig inventoryConfig = InventoryConfigManager.EMPTY;

    public static SpecificConfig config;

    @Override
    public void onInitialize() {
        logger.info("Starting initialize Specific Slots...");

        if (!DEFAULT_MOD_CONFIG.exists()) {
            logger.info("Default config not found, maybe it's first launch with Specific Slots. Starting to create a new configs...");

            SpecificConfigManager.setConfig(new SpecificConfig(inventoryConfig.getName()), inventoryConfig);

            if (!DEFAULT_INVENTORY_CONFIG.exists()) {
                InventoryConfigManager.writeConfig(inventoryConfig);
            }
        } else {
            logger.info("Starting to load a default configs...");
        }

        config = SpecificConfigManager.getConfig();
        inventoryConfig = SpecificConfigManager.getInventoryConfig();

        ItemUtils.getMinecraftItems().forEach(item -> {
            if (ShadowItems.getTexture(item) == null)
                System.out.println("Items." + item.getTranslationKey().substring(item.getTranslationKey().lastIndexOf('.') + 1).toUpperCase());
        });

    }

    public static String ID() {
        return ID;
    }
}
