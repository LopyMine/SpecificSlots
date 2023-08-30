package net.lopymine.specificslots;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfig;
import net.lopymine.specificslots.config.defalt.DefaultSpecificConfigManager;
import net.lopymine.specificslots.config.SpecificConfig;
import net.lopymine.specificslots.config.SpecificConfigManager;
import net.minecraft.client.gui.hud.InGameHud;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class SpecificSlots implements ModInitializer {

    public static final String ID = "specificslots";
    public static final Logger logger = LogUtils.getLogger();
    private static final File DEFAULT_MOD_CONFIG = new File(FabricLoader.getInstance().getConfigDir().toFile(), "specificslots.json5");
    private static final File DEFAULT_INVENTORY_CONFIG = new File(Path.of(FabricLoader.getInstance().getConfigDir() + "/specific_slots/").toFile(), "default.json");
    public static SpecificConfig config = SpecificConfigManager.EMPTY;
    public static DefaultSpecificConfig defaultConfig;

    @Override
    public void onInitialize() {
        logger.info("Starting initialize Specific Slots...");
        if (!DEFAULT_MOD_CONFIG.exists()) {
            logger.info("Default config not found, maybe it's first launch with Specific Slots. Starting to create a new config...");
            SpecificConfig config = SpecificConfigManager.EMPTY;
            DefaultSpecificConfigManager.setDefaultConfig(new DefaultSpecificConfig(config.getFileName()));
            if (!DEFAULT_INVENTORY_CONFIG.exists()) {
                SpecificConfigManager.writeConfig(config);
            }
        } else {
            logger.info("Starting to load a default config...");
        }

        config = DefaultSpecificConfigManager.getInventoryConfig();
        defaultConfig = DefaultSpecificConfigManager.getDefaultConfig();

        //ItemUtils.getMinecraftItems().forEach(item -> {
        //    if (GhostItems.getTexture(item) == null) System.out.println(item.getTranslationKey());
        //});
    }

    public static String ID() {
        return ID;
    }
}
