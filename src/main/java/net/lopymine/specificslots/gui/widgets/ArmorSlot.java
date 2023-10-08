package net.lopymine.specificslots.gui.widgets;

import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ArmorSlot {
    public enum ArmorSlotType {
        HELMET,
        CHESTPLATE,
        LEGS,
        BOOTS,
        OFFHAND
    }

    public static final Identifier HELMET = new Identifier("minecraft", "textures/item/empty_armor_slot_helmet.png");
    public static final Identifier CHESTPLATE = new Identifier("minecraft", "textures/item/empty_armor_slot_chestplate.png");
    public static final Identifier LEGS = new Identifier("minecraft", "textures/item/empty_armor_slot_leggings.png");
    public static final Identifier BOOTS = new Identifier("minecraft", "textures/item/empty_armor_slot_boots.png");
    public static final Identifier OFFHAND = new Identifier("minecraft", "textures/item/empty_armor_slot_shield.png");
    private static final List<Identifier> identifiers = List.of(HELMET, CHESTPLATE, LEGS, BOOTS, OFFHAND);

    public static Identifier getTexture(ArmorSlotType type) {
        return identifiers.get(Arrays.stream(ArmorSlotType.values()).toList().indexOf(type));
    }
}
