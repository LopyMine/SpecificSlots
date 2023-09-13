package net.lopymine.specificslots.utils;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import net.lopymine.specificslots.config.inventory.InventoryConfig;
import net.lopymine.specificslots.gui.widgets.WSlot;

import java.util.*;

public class ItemUtils {
    private static final LinkedHashSet<Item> ITEMS = new LinkedHashSet<>();
    public static LinkedHashSet<Item> getMinecraftItems() {
        if(!ITEMS.isEmpty()) return ITEMS;
        Registries.ITEM.iterator().forEachRemaining(ITEMS::add);

        return ITEMS;
    }

    public static Item getItemByName(String name){
        String mod = name.substring(0,name.indexOf(':'));
        String path = name.substring(name.lastIndexOf(':')+1);

        return getItemByName(mod,path);
    }

    public static Item getItemByName(String mod, String path){
        String modId = mod;
        if(mod.equals("vanilla")) modId = "minecraft";

        return Registries.ITEM.get(new Identifier(modId, path));
    }

    public static String getItemName(Item item) {
        return Registries.ITEM.getId(item).toString();
    }

    public static List<Item> getItemsFromConfig(InventoryConfig config){
        List<Item> items = new ArrayList<>();
        config.getInventory().forEach(s -> items.add(getItemByName(s)));
        config.getHotBar().forEach(s -> items.add(getItemByName(s)));

        return items;
    }

    public static List<String> getItemsFromButtons(List<WSlot> list) {
        List<String> names = new ArrayList<>();
        list.forEach(slot -> names.add(ItemUtils.getItemName(slot.getItem())));
        return names;
    }

    public static LinkedList<ItemStack> transformMainInventory(DefaultedList<ItemStack> main) {
        List<ItemStack> inventoryList = new ArrayList<>(main.subList(9, 36));
        List<ItemStack> hotBarList = new ArrayList<>(main.subList(0, 9).stream().toList());
        inventoryList.addAll(hotBarList);
        return new LinkedList<>(inventoryList);
    }
}
