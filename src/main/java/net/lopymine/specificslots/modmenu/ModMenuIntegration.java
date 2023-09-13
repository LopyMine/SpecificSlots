package net.lopymine.specificslots.modmenu;

import com.terraformersmc.modmenu.api.*;

import net.fabricmc.loader.api.*;

import net.lopymine.specificslots.gui.screen.SpecificScreen;

import java.util.HashSet;

public class ModMenuIntegration implements ModMenuApi {
    private final HashSet<String> mods = getMods();
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(mods.contains("cloth-config")){
            return ModMenuIntegrationScreen::createScreen;
        } else {
            return screen -> new SpecificScreen(new NoClothConfigScreen(screen));
        }
    }

    private HashSet<String> getMods(){
        HashSet<String> mods = new HashSet<>();

        for(ModContainer mod : FabricLoader.getInstance().getAllMods()){
            mods.add(mod.getMetadata().getId());
        }

        return mods;
    }
}
