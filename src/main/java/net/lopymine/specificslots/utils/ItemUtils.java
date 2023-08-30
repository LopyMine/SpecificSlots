package net.lopymine.specificslots.utils;

import net.lopymine.specificslots.config.SpecificConfig;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ItemUtils {
    private static final LinkedHashSet<Item> items = new LinkedHashSet<>();
    public static LinkedHashSet<Item> getMinecraftItems() {
        if(!items.isEmpty()) return items;

        Registries.ITEM.iterator().forEachRemaining(items::add);
        return items;
    }

    public static Item getItemByName(String name){
        String mod = name.substring(0,name.indexOf(':'));
        String path = name.substring(name.lastIndexOf(':')+1);
        return getItemByName(mod,path);
    }

    public static Item getItemByName(String mod, String path){
        String modId = mod;
        if(mod.equals("vanilla")) modId = "minecraft";
        return Registries.ITEM.get(new Identifier(modId,path));
    }

    public static String getItemName(Item item) {
        return item.getTranslationKey().substring(item.getTranslationKey().indexOf('.')+1).replaceAll("\\.",":");
    }

    public static List<Item> getItemsFromConfig(SpecificConfig config){
        List<Item> items = new ArrayList<>();
        config.getInventory().forEach(s -> items.add(getItemByName(s)));
        config.getHotBar().forEach(s -> items.add(getItemByName(s)));
        return items;
    }

}
