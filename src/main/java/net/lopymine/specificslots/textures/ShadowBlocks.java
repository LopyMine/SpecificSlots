package net.lopymine.specificslots.textures;

import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import net.lopymine.specificslots.SpecificSlots;

import java.util.*;
import org.jetbrains.annotations.Nullable;

public class ShadowBlocks {
    public static final Identifier ANVIL = new Identifier(SpecificSlots.ID(), "textures/blocks/anvil_i.png");
    public static final Identifier BANNER = new Identifier(SpecificSlots.ID(), "textures/blocks/banner_i.png");
    public static final Identifier BEACON = new Identifier(SpecificSlots.ID(), "textures/blocks/beacon_i.png");
    public static final Identifier BED = new Identifier(SpecificSlots.ID(), "textures/blocks/bed_i.png");
    public static final Identifier BLOCK = new Identifier(SpecificSlots.ID(), "textures/blocks/block_i.png");
    public static final Identifier BUTTON = new Identifier(SpecificSlots.ID(), "textures/blocks/button_i.png");
    public static final Identifier CALIBRATED_SCULK_SENSOR = new Identifier(SpecificSlots.ID(), "textures/blocks/calibrated_sculk_sensor_i.png");
    public static final Identifier CARPET = new Identifier(SpecificSlots.ID(), "textures/blocks/carpet_i.png");
    public static final Identifier CHEST = new Identifier(SpecificSlots.ID(), "textures/blocks/chest_i.png");
    public static final Identifier CONDUIT = new Identifier(SpecificSlots.ID(), "textures/blocks/conduit_i.png");
    public static final Identifier CREEPER_SCULL = new Identifier(SpecificSlots.ID(), "textures/blocks/creeper_scull_i.png");
    public static final Identifier DECORATED_POT = new Identifier(SpecificSlots.ID(), "textures/blocks/decorated_pot_i.png");
    public static final Identifier DRAGON_EGG = new Identifier(SpecificSlots.ID(), "textures/blocks/dragon_egg_i.png");
    public static final Identifier DRAGON_HEAD = new Identifier(SpecificSlots.ID(), "textures/blocks/dragon_head_i.png");
    public static final Identifier FENCE_GATE = new Identifier(SpecificSlots.ID(), "textures/blocks/fence_gate_i.png");
    public static final Identifier PIGLIN_SCULL = new Identifier(SpecificSlots.ID(), "textures/blocks/piglin_scull_i.png");
    public static final Identifier PLATE = new Identifier(SpecificSlots.ID(), "textures/blocks/plate_i.png");
    public static final Identifier SCAFFOLDING = new Identifier(SpecificSlots.ID(), "textures/blocks/scaffolding_i.png");
    public static final Identifier SCULK_SENSOR = new Identifier(SpecificSlots.ID(), "textures/blocks/sculk_sensor_i.png");
    public static final Identifier SCULK_SHRIEKER = new Identifier(SpecificSlots.ID(), "textures/blocks/sculk_shrieker_i.png");
    public static final Identifier SHIELD = new Identifier(SpecificSlots.ID(), "textures/blocks/shield_i.png");
    public static final Identifier SHULKER = new Identifier(SpecificSlots.ID(), "textures/blocks/shulker_i.png");
    public static final Identifier SKELETON_SCULL = new Identifier(SpecificSlots.ID(), "textures/blocks/skeleton_scull_i.png");
    public static final Identifier SLAB = new Identifier(SpecificSlots.ID(), "textures/blocks/slab_i.png");
    public static final Identifier SPAWNER = new Identifier(SpecificSlots.ID(), "textures/blocks/spawner_i.png");
    public static final Identifier STAIRS = new Identifier(SpecificSlots.ID(), "textures/blocks/stairs_i.png");
    public static final Identifier STEVE_SCULL = new Identifier(SpecificSlots.ID(), "textures/blocks/steve_scull_i.png");
    public static final Identifier TRAPDOOR = new Identifier(SpecificSlots.ID(), "textures/blocks/trapdoor_i.png");
    public static final Identifier WALL = new Identifier(SpecificSlots.ID(), "textures/blocks/wall_i.png");
    public static final Identifier END_ROD = new Identifier(SpecificSlots.ID(), "textures/blocks/end_rod_i.png");
    public static final Identifier LIGHTNING_ROD = new Identifier(SpecificSlots.ID(), "textures/blocks/lightning_rod_i.png");
    public static final Identifier AZALEA = new Identifier(SpecificSlots.ID(), "textures/blocks/azalia_i.png");
    public static final Identifier FLOWERING_AZALEA = new Identifier(SpecificSlots.ID(), "textures/blocks/flowering_azalia_i.png");
    public static final Identifier BIG_DRIPLEAF = new Identifier(SpecificSlots.ID(), "textures/blocks/big_dripleaf_i.png");
    public static final Identifier SMALL_DRIPLEAF = new Identifier(SpecificSlots.ID(), "textures/blocks/small_dripleaf_i.png");
    public static final Identifier LECTERN = new Identifier(SpecificSlots.ID(), "textures/blocks/lectern_i.png");
    public static final Identifier SPORE_BLOSSOM = new Identifier(SpecificSlots.ID(), "textures/blocks/spore_blossom_i.png");
    public static final Identifier STONECUTTER = new Identifier(SpecificSlots.ID(), "textures/blocks/stonecutter_i.png");
    public static final Identifier GRINDSTONE = new Identifier(SpecificSlots.ID(), "textures/blocks/grindstone_i.png");
    public static final Identifier FENCE = new Identifier(SpecificSlots.ID(), "textures/blocks/fence_i.png");
    private static final Map<HashSet<Item>, Identifier> BLOCK_TEXTURES = new HashMap<>();

    static {
        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.SKELETON_SKULL,
                Items.WITHER_SKELETON_SKULL
        )), SKELETON_SCULL);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.PLAYER_HEAD,
                Items.ZOMBIE_HEAD
        )), STEVE_SCULL);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.STONE,
                Items.GRANITE,
                Items.POLISHED_GRANITE,
                Items.DIORITE,
                Items.POLISHED_DIORITE,
                Items.ANDESITE,
                Items.POLISHED_ANDESITE,
                Items.DEEPSLATE,
                Items.COBBLED_DEEPSLATE,
                Items.POLISHED_DEEPSLATE,
                Items.CALCITE,
                Items.TUFF,
                Items.DRIPSTONE_BLOCK,
                Items.GRASS_BLOCK,
                Items.DIRT,
                Items.COARSE_DIRT,
                Items.PODZOL,
                Items.ROOTED_DIRT,
                Items.MUD,
                Items.CRIMSON_NYLIUM,
                Items.WARPED_NYLIUM,
                Items.COBBLESTONE,
                Items.OAK_PLANKS,
                Items.SPRUCE_PLANKS,
                Items.BIRCH_PLANKS,
                Items.JUNGLE_PLANKS,
                Items.ACACIA_PLANKS,
                Items.CHERRY_PLANKS,
                Items.DARK_OAK_PLANKS,
                Items.MANGROVE_PLANKS,
                Items.BAMBOO_PLANKS,
                Items.CRIMSON_PLANKS,
                Items.WARPED_PLANKS,
                Items.BAMBOO_MOSAIC,
                Items.BEDROCK,
                Items.SAND,
                Items.SUSPICIOUS_SAND,
                Items.RED_SAND,
                Items.GRAVEL,
                Items.COAL_ORE,
                Items.DEEPSLATE_COAL_ORE,
                Items.IRON_ORE,
                Items.DEEPSLATE_IRON_ORE,
                Items.COPPER_ORE,
                Items.DEEPSLATE_COPPER_ORE,
                Items.GOLD_ORE,
                Items.DEEPSLATE_GOLD_ORE,
                Items.REDSTONE_ORE,
                Items.DEEPSLATE_REDSTONE_ORE,
                Items.EMERALD_ORE,
                Items.DEEPSLATE_EMERALD_ORE,
                Items.LAPIS_ORE,
                Items.DEEPSLATE_LAPIS_ORE,
                Items.DIAMOND_ORE,
                Items.DEEPSLATE_DIAMOND_ORE,
                Items.NETHER_GOLD_ORE,
                Items.NETHER_QUARTZ_ORE,
                Items.ANCIENT_DEBRIS,
                Items.COAL_BLOCK,
                Items.RAW_IRON_BLOCK,
                Items.RAW_COPPER_BLOCK,
                Items.RAW_GOLD_BLOCK,
                Items.AMETHYST_BLOCK,
                Items.BUDDING_AMETHYST,
                Items.IRON_BLOCK,
                Items.COPPER_BLOCK,
                Items.GOLD_BLOCK,
                Items.DIAMOND_BLOCK,
                Items.NETHERITE_BLOCK,
                Items.EXPOSED_COPPER,
                Items.WEATHERED_COPPER,
                Items.OXIDIZED_COPPER,
                Items.CUT_COPPER,
                Items.EXPOSED_CUT_COPPER,
                Items.WEATHERED_CUT_COPPER,
                Items.OXIDIZED_CUT_COPPER,
                Items.WAXED_COPPER_BLOCK,
                Items.WAXED_EXPOSED_COPPER,
                Items.WAXED_WEATHERED_COPPER,
                Items.WAXED_OXIDIZED_COPPER,
                Items.WAXED_CUT_COPPER,
                Items.WAXED_EXPOSED_CUT_COPPER,
                Items.WAXED_WEATHERED_CUT_COPPER,
                Items.WAXED_OXIDIZED_CUT_COPPER,
                Items.OAK_LOG,
                Items.SPRUCE_LOG,
                Items.BIRCH_LOG,
                Items.JUNGLE_LOG,
                Items.ACACIA_LOG,
                Items.CHERRY_LOG,
                Items.DARK_OAK_LOG,
                Items.MANGROVE_LOG,
                Items.MANGROVE_ROOTS,
                Items.MUDDY_MANGROVE_ROOTS,
                Items.CRIMSON_STEM,
                Items.WARPED_STEM,
                Items.BAMBOO_BLOCK,
                Items.STRIPPED_OAK_LOG,
                Items.STRIPPED_SPRUCE_LOG,
                Items.STRIPPED_BIRCH_LOG,
                Items.STRIPPED_JUNGLE_LOG,
                Items.STRIPPED_ACACIA_LOG,
                Items.STRIPPED_CHERRY_LOG,
                Items.STRIPPED_DARK_OAK_LOG,
                Items.STRIPPED_MANGROVE_LOG,
                Items.STRIPPED_CRIMSON_STEM,
                Items.STRIPPED_WARPED_STEM,
                Items.STRIPPED_OAK_WOOD,
                Items.STRIPPED_SPRUCE_WOOD,
                Items.STRIPPED_BIRCH_WOOD,
                Items.STRIPPED_JUNGLE_WOOD,
                Items.STRIPPED_ACACIA_WOOD,
                Items.STRIPPED_CHERRY_WOOD,
                Items.STRIPPED_DARK_OAK_WOOD,
                Items.STRIPPED_MANGROVE_WOOD,
                Items.STRIPPED_CRIMSON_HYPHAE,
                Items.STRIPPED_WARPED_HYPHAE,
                Items.STRIPPED_BAMBOO_BLOCK,
                Items.OAK_WOOD,
                Items.SPRUCE_WOOD,
                Items.BIRCH_WOOD,
                Items.JUNGLE_WOOD,
                Items.ACACIA_WOOD,
                Items.CHERRY_WOOD,
                Items.DARK_OAK_WOOD,
                Items.MANGROVE_WOOD,
                Items.CRIMSON_HYPHAE,
                Items.WARPED_HYPHAE,
                Items.OAK_LEAVES,
                Items.SPRUCE_LEAVES,
                Items.BIRCH_LEAVES,
                Items.JUNGLE_LEAVES,
                Items.ACACIA_LEAVES,
                Items.CHERRY_LEAVES,
                Items.DARK_OAK_LEAVES,
                Items.MANGROVE_LEAVES,
                Items.AZALEA_LEAVES,
                Items.FLOWERING_AZALEA_LEAVES,
                Items.SPONGE,
                Items.WET_SPONGE,
                Items.GLASS,
                Items.TINTED_GLASS,
                Items.LAPIS_BLOCK,
                Items.SANDSTONE,
                Items.CHISELED_SANDSTONE,
                Items.CUT_SANDSTONE,
                Items.GRASS,
                Items.WHITE_WOOL,
                Items.ORANGE_WOOL,
                Items.MAGENTA_WOOL,
                Items.LIGHT_BLUE_WOOL,
                Items.YELLOW_WOOL,
                Items.LIME_WOOL,
                Items.PINK_WOOL,
                Items.GRAY_WOOL,
                Items.LIGHT_GRAY_WOOL,
                Items.CYAN_WOOL,
                Items.PURPLE_WOOL,
                Items.BLUE_WOOL,
                Items.BROWN_WOOL,
                Items.GREEN_WOOL,
                Items.RED_WOOL,
                Items.BLACK_WOOL,
                Items.DANDELION,
                Items.MOSS_BLOCK,
                Items.SMOOTH_QUARTZ,
                Items.SMOOTH_RED_SANDSTONE,
                Items.SMOOTH_SANDSTONE,
                Items.SMOOTH_STONE,
                Items.BRICKS,
                Items.BOOKSHELF,
                Items.CHISELED_BOOKSHELF,
                Items.MOSSY_COBBLESTONE,
                Items.OBSIDIAN,
                Items.CHORUS_PLANT,
                Items.CHORUS_FLOWER,
                Items.PURPUR_BLOCK,
                Items.PURPUR_PILLAR,
                Items.SPAWNER,
                Items.CRAFTING_TABLE,
                Items.FARMLAND,
                Items.FURNACE,
                Items.ICE,
                Items.SNOW_BLOCK,
                Items.CACTUS,
                Items.CLAY,
                Items.JUKEBOX,
                Items.PUMPKIN,
                Items.NETHERRACK,
                Items.SOUL_SAND,
                Items.SOUL_SOIL,
                Items.BASALT,
                Items.POLISHED_BASALT,
                Items.SMOOTH_BASALT,
                Items.GLOWSTONE,
                Items.INFESTED_STONE,
                Items.INFESTED_COBBLESTONE,
                Items.INFESTED_STONE_BRICKS,
                Items.INFESTED_MOSSY_STONE_BRICKS,
                Items.INFESTED_CRACKED_STONE_BRICKS,
                Items.INFESTED_CHISELED_STONE_BRICKS,
                Items.INFESTED_DEEPSLATE,
                Items.STONE_BRICKS,
                Items.MOSSY_STONE_BRICKS,
                Items.CRACKED_STONE_BRICKS,
                Items.CHISELED_STONE_BRICKS,
                Items.PACKED_MUD,
                Items.MUD_BRICKS,
                Items.DEEPSLATE_BRICKS,
                Items.CRACKED_DEEPSLATE_BRICKS,
                Items.DEEPSLATE_TILES,
                Items.CRACKED_DEEPSLATE_TILES,
                Items.CHISELED_DEEPSLATE,
                Items.REINFORCED_DEEPSLATE,
                Items.BROWN_MUSHROOM_BLOCK,
                Items.RED_MUSHROOM_BLOCK,
                Items.MUSHROOM_STEM,
                Items.MELON,
                Items.MYCELIUM,
                Items.NETHER_BRICKS,
                Items.CRACKED_NETHER_BRICKS,
                Items.CHISELED_NETHER_BRICKS,
                Items.ENCHANTING_TABLE,
                Items.END_PORTAL_FRAME,
                Items.END_STONE,
                Items.END_STONE_BRICKS,
                Items.EMERALD_BLOCK,
                Items.COMMAND_BLOCK,
                Items.CHISELED_QUARTZ_BLOCK,
                Items.QUARTZ_BLOCK,
                Items.QUARTZ_BRICKS,
                Items.QUARTZ_PILLAR,
                Items.WHITE_TERRACOTTA,
                Items.ORANGE_TERRACOTTA,
                Items.MAGENTA_TERRACOTTA,
                Items.LIGHT_BLUE_TERRACOTTA,
                Items.YELLOW_TERRACOTTA,
                Items.LIME_TERRACOTTA,
                Items.PINK_TERRACOTTA,
                Items.GRAY_TERRACOTTA,
                Items.LIGHT_GRAY_TERRACOTTA,
                Items.CYAN_TERRACOTTA,
                Items.PURPLE_TERRACOTTA,
                Items.BLUE_TERRACOTTA,
                Items.BROWN_TERRACOTTA,
                Items.GREEN_TERRACOTTA,
                Items.RED_TERRACOTTA,
                Items.BLACK_TERRACOTTA,
                Items.HAY_BLOCK,
                Items.TERRACOTTA,
                Items.PACKED_ICE,
                Items.DIRT_PATH,
                Items.PRISMARINE,
                Items.PRISMARINE_BRICKS,
                Items.DARK_PRISMARINE,
                Items.CHISELED_RED_SANDSTONE,
                Items.CUT_RED_SANDSTONE,
                Items.REPEATING_COMMAND_BLOCK,
                Items.CHAIN_COMMAND_BLOCK,
                Items.MAGMA_BLOCK,
                Items.NETHER_WART_BLOCK,
                Items.WARPED_WART_BLOCK,
                Items.RED_NETHER_BRICKS,
                Items.BONE_BLOCK,
                Items.WHITE_GLAZED_TERRACOTTA,
                Items.ORANGE_GLAZED_TERRACOTTA,
                Items.MAGENTA_GLAZED_TERRACOTTA,
                Items.LIGHT_BLUE_GLAZED_TERRACOTTA,
                Items.YELLOW_GLAZED_TERRACOTTA,
                Items.LIME_GLAZED_TERRACOTTA,
                Items.PINK_GLAZED_TERRACOTTA,
                Items.GRAY_GLAZED_TERRACOTTA,
                Items.LIGHT_GRAY_GLAZED_TERRACOTTA,
                Items.CYAN_GLAZED_TERRACOTTA,
                Items.PURPLE_GLAZED_TERRACOTTA,
                Items.BLUE_GLAZED_TERRACOTTA,
                Items.BROWN_GLAZED_TERRACOTTA,
                Items.GREEN_GLAZED_TERRACOTTA,
                Items.RED_GLAZED_TERRACOTTA,
                Items.BLACK_GLAZED_TERRACOTTA,
                Items.WHITE_CONCRETE,
                Items.ORANGE_CONCRETE,
                Items.MAGENTA_CONCRETE,
                Items.LIGHT_BLUE_CONCRETE,
                Items.YELLOW_CONCRETE,
                Items.LIME_CONCRETE,
                Items.PINK_CONCRETE,
                Items.GRAY_CONCRETE,
                Items.LIGHT_GRAY_CONCRETE,
                Items.CYAN_CONCRETE,
                Items.PURPLE_CONCRETE,
                Items.BLUE_CONCRETE,
                Items.BROWN_CONCRETE,
                Items.GREEN_CONCRETE,
                Items.RED_CONCRETE,
                Items.BLACK_CONCRETE,
                Items.WHITE_CONCRETE_POWDER,
                Items.ORANGE_CONCRETE_POWDER,
                Items.MAGENTA_CONCRETE_POWDER,
                Items.LIGHT_BLUE_CONCRETE_POWDER,
                Items.YELLOW_CONCRETE_POWDER,
                Items.LIME_CONCRETE_POWDER,
                Items.PINK_CONCRETE_POWDER,
                Items.GRAY_CONCRETE_POWDER,
                Items.LIGHT_GRAY_CONCRETE_POWDER,
                Items.CYAN_CONCRETE_POWDER,
                Items.PURPLE_CONCRETE_POWDER,
                Items.BLUE_CONCRETE_POWDER,
                Items.BROWN_CONCRETE_POWDER,
                Items.GREEN_CONCRETE_POWDER,
                Items.RED_CONCRETE_POWDER,
                Items.BLACK_CONCRETE_POWDER,
                Items.DEAD_TUBE_CORAL_BLOCK,
                Items.DEAD_BRAIN_CORAL_BLOCK,
                Items.DEAD_BUBBLE_CORAL_BLOCK,
                Items.DEAD_FIRE_CORAL_BLOCK,
                Items.DEAD_HORN_CORAL_BLOCK,
                Items.TUBE_CORAL_BLOCK,
                Items.BRAIN_CORAL_BLOCK,
                Items.BUBBLE_CORAL_BLOCK,
                Items.FIRE_CORAL_BLOCK,
                Items.HORN_CORAL_BLOCK,
                Items.BLUE_ICE,
                Items.REDSTONE_BLOCK,
                Items.PISTON,
                Items.STICKY_PISTON,
                Items.SLIME_BLOCK,
                Items.HONEY_BLOCK,
                Items.OBSERVER,
                Items.DISPENSER,
                Items.DROPPER,
                Items.TARGET,
                Items.TNT,
                Items.REDSTONE_LAMP,
                Items.NOTE_BLOCK,
                Items.DRIED_KELP_BLOCK,
                Items.LOOM,
                Items.COMPOSTER,
                Items.BARREL,
                Items.SMOKER,
                Items.BLAST_FURNACE,
                Items.CARTOGRAPHY_TABLE,
                Items.FLETCHING_TABLE,
                Items.SMITHING_TABLE,
                Items.SHROOMLIGHT,
                Items.BEE_NEST,
                Items.BEEHIVE,
                Items.HONEYCOMB_BLOCK,
                Items.LODESTONE,
                Items.CRYING_OBSIDIAN,
                Items.BLACKSTONE,
                Items.GILDED_BLACKSTONE,
                Items.POLISHED_BLACKSTONE,
                Items.CHISELED_POLISHED_BLACKSTONE,
                Items.POLISHED_BLACKSTONE_BRICKS,
                Items.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                Items.RESPAWN_ANCHOR,
                Items.WHITE_STAINED_GLASS,
                Items.ORANGE_STAINED_GLASS,
                Items.MAGENTA_STAINED_GLASS,
                Items.LIGHT_BLUE_STAINED_GLASS,
                Items.YELLOW_STAINED_GLASS,
                Items.LIME_STAINED_GLASS,
                Items.PINK_STAINED_GLASS,
                Items.GRAY_STAINED_GLASS,
                Items.LIGHT_GRAY_STAINED_GLASS,
                Items.CYAN_STAINED_GLASS,
                Items.PURPLE_STAINED_GLASS,
                Items.BLUE_STAINED_GLASS,
                Items.BROWN_STAINED_GLASS,
                Items.GREEN_STAINED_GLASS,
                Items.RED_STAINED_GLASS,
                Items.BLACK_STAINED_GLASS,
                Items.SCULK,
                Items.SCULK_CATALYST,
                Items.SEA_LANTERN,
                Items.RED_SANDSTONE,
                Items.JACK_O_LANTERN,
                Items.CARVED_PUMPKIN,
                Items.STRUCTURE_BLOCK,
                Items.JIGSAW,
                Items.OCHRE_FROGLIGHT,
                Items.VERDANT_FROGLIGHT,
                Items.PEARLESCENT_FROGLIGHT,
                Items.SUSPICIOUS_GRAVEL,
                Items.SUSPICIOUS_SAND
        )), BLOCK);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.CUT_COPPER_SLAB,
                Items.EXPOSED_CUT_COPPER_SLAB,
                Items.WEATHERED_CUT_COPPER_SLAB,
                Items.OXIDIZED_CUT_COPPER_SLAB,
                Items.WAXED_CUT_COPPER_SLAB,
                Items.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Items.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Items.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Items.OAK_SLAB,
                Items.SPRUCE_SLAB,
                Items.BIRCH_SLAB,
                Items.JUNGLE_SLAB,
                Items.ACACIA_SLAB,
                Items.CHERRY_SLAB,
                Items.DARK_OAK_SLAB,
                Items.MANGROVE_SLAB,
                Items.BAMBOO_SLAB,
                Items.BAMBOO_MOSAIC_SLAB,
                Items.CRIMSON_SLAB,
                Items.WARPED_SLAB,
                Items.STONE_SLAB,
                Items.SMOOTH_STONE_SLAB,
                Items.SANDSTONE_SLAB,
                Items.CUT_SANDSTONE_SLAB,
                Items.PETRIFIED_OAK_SLAB,
                Items.COBBLESTONE_SLAB,
                Items.BRICK_SLAB,
                Items.STONE_BRICK_SLAB,
                Items.MUD_BRICK_SLAB,
                Items.NETHER_BRICK_SLAB,
                Items.QUARTZ_SLAB,
                Items.RED_SANDSTONE_SLAB,
                Items.CUT_RED_SANDSTONE_SLAB,
                Items.PURPUR_SLAB,
                Items.PRISMARINE_SLAB,
                Items.PRISMARINE_BRICK_SLAB,
                Items.DARK_PRISMARINE_SLAB,
                Items.POLISHED_GRANITE_SLAB,
                Items.SMOOTH_RED_SANDSTONE_SLAB,
                Items.MOSSY_STONE_BRICK_SLAB,
                Items.POLISHED_DIORITE_SLAB,
                Items.MOSSY_COBBLESTONE_SLAB,
                Items.END_STONE_BRICK_SLAB,
                Items.SMOOTH_SANDSTONE_SLAB,
                Items.SMOOTH_QUARTZ_SLAB,
                Items.GRANITE_SLAB,
                Items.ANDESITE_SLAB,
                Items.RED_NETHER_BRICK_SLAB,
                Items.POLISHED_ANDESITE_SLAB,
                Items.DIORITE_SLAB,
                Items.COBBLED_DEEPSLATE_SLAB,
                Items.POLISHED_DEEPSLATE_SLAB,
                Items.DEEPSLATE_BRICK_SLAB,
                Items.DEEPSLATE_TILE_SLAB,
                Items.DAYLIGHT_DETECTOR,
                Items.BLACKSTONE_SLAB,
                Items.POLISHED_BLACKSTONE_SLAB,
                Items.POLISHED_BLACKSTONE_BRICK_SLAB
        )), SLAB);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.ANVIL,
                Items.CHIPPED_ANVIL,
                Items.DAMAGED_ANVIL
        )), ANVIL);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.CUT_COPPER_STAIRS,
                Items.EXPOSED_CUT_COPPER_STAIRS,
                Items.WEATHERED_CUT_COPPER_STAIRS,
                Items.OXIDIZED_CUT_COPPER_STAIRS,
                Items.WAXED_CUT_COPPER_STAIRS,
                Items.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Items.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Items.PURPUR_STAIRS,
                Items.COBBLESTONE_STAIRS,
                Items.BRICK_STAIRS,
                Items.STONE_BRICK_STAIRS,
                Items.MUD_BRICK_STAIRS,
                Items.NETHER_BRICK_STAIRS,
                Items.SANDSTONE_STAIRS,
                Items.OAK_STAIRS,
                Items.SPRUCE_STAIRS,
                Items.BIRCH_STAIRS,
                Items.JUNGLE_STAIRS,
                Items.ACACIA_STAIRS,
                Items.CHERRY_STAIRS,
                Items.DARK_OAK_STAIRS,
                Items.MANGROVE_STAIRS,
                Items.BAMBOO_STAIRS,
                Items.BAMBOO_MOSAIC_STAIRS,
                Items.CRIMSON_STAIRS,
                Items.WARPED_STAIRS,
                Items.QUARTZ_STAIRS,
                Items.PRISMARINE_STAIRS,
                Items.PRISMARINE_BRICK_STAIRS,
                Items.DARK_PRISMARINE_STAIRS,
                Items.RED_SANDSTONE_STAIRS,
                Items.POLISHED_GRANITE_STAIRS,
                Items.SMOOTH_RED_SANDSTONE_STAIRS,
                Items.MOSSY_STONE_BRICK_STAIRS,
                Items.POLISHED_DIORITE_STAIRS,
                Items.MOSSY_COBBLESTONE_STAIRS,
                Items.END_STONE_BRICK_STAIRS,
                Items.STONE_STAIRS,
                Items.SMOOTH_SANDSTONE_STAIRS,
                Items.SMOOTH_QUARTZ_STAIRS,
                Items.GRANITE_STAIRS,
                Items.ANDESITE_STAIRS,
                Items.RED_NETHER_BRICK_STAIRS,
                Items.POLISHED_ANDESITE_STAIRS,
                Items.DIORITE_STAIRS,
                Items.COBBLED_DEEPSLATE_STAIRS,
                Items.POLISHED_DEEPSLATE_STAIRS,
                Items.DEEPSLATE_BRICK_STAIRS,
                Items.DEEPSLATE_TILE_STAIRS,
                Items.BLACKSTONE_STAIRS,
                Items.POLISHED_BLACKSTONE_STAIRS,
                Items.POLISHED_BLACKSTONE_BRICK_STAIRS
        )), STAIRS);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.CHEST,
                Items.ENDER_CHEST,
                Items.TRAPPED_CHEST
        )), CHEST);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.WHITE_BANNER,
                Items.ORANGE_BANNER,
                Items.MAGENTA_BANNER,
                Items.LIGHT_BLUE_BANNER,
                Items.YELLOW_BANNER,
                Items.LIME_BANNER,
                Items.PINK_BANNER,
                Items.GRAY_BANNER,
                Items.LIGHT_GRAY_BANNER,
                Items.CYAN_BANNER,
                Items.PURPLE_BANNER,
                Items.BLUE_BANNER,
                Items.BROWN_BANNER,
                Items.GREEN_BANNER,
                Items.RED_BANNER,
                Items.BLACK_BANNER
        )), BANNER);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.SHULKER_BOX,
                Items.WHITE_SHULKER_BOX,
                Items.ORANGE_SHULKER_BOX,
                Items.MAGENTA_SHULKER_BOX,
                Items.LIGHT_BLUE_SHULKER_BOX,
                Items.YELLOW_SHULKER_BOX,
                Items.LIME_SHULKER_BOX,
                Items.PINK_SHULKER_BOX,
                Items.GRAY_SHULKER_BOX,
                Items.LIGHT_GRAY_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX,
                Items.PURPLE_SHULKER_BOX,
                Items.BLUE_SHULKER_BOX,
                Items.BROWN_SHULKER_BOX,
                Items.GREEN_SHULKER_BOX,
                Items.RED_SHULKER_BOX,
                Items.BLACK_SHULKER_BOX
        )), SHULKER);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.STONE_PRESSURE_PLATE,
                Items.POLISHED_BLACKSTONE_PRESSURE_PLATE,
                Items.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Items.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Items.OAK_PRESSURE_PLATE,
                Items.SPRUCE_PRESSURE_PLATE,
                Items.BIRCH_PRESSURE_PLATE,
                Items.JUNGLE_PRESSURE_PLATE,
                Items.ACACIA_PRESSURE_PLATE,
                Items.CHERRY_PRESSURE_PLATE,
                Items.DARK_OAK_PRESSURE_PLATE,
                Items.MANGROVE_PRESSURE_PLATE,
                Items.BAMBOO_PRESSURE_PLATE,
                Items.CRIMSON_PRESSURE_PLATE,
                Items.WARPED_PRESSURE_PLATE
        )), PLATE);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.WHITE_BED,
                Items.ORANGE_BED,
                Items.MAGENTA_BED,
                Items.LIGHT_BLUE_BED,
                Items.YELLOW_BED,
                Items.LIME_BED,
                Items.PINK_BED,
                Items.GRAY_BED,
                Items.LIGHT_GRAY_BED,
                Items.CYAN_BED,
                Items.PURPLE_BED,
                Items.BLUE_BED,
                Items.BROWN_BED,
                Items.GREEN_BED,
                Items.RED_BED,
                Items.BLACK_BED
        )), BED);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.IRON_TRAPDOOR,
                Items.OAK_TRAPDOOR,
                Items.SPRUCE_TRAPDOOR,
                Items.BIRCH_TRAPDOOR,
                Items.JUNGLE_TRAPDOOR,
                Items.ACACIA_TRAPDOOR,
                Items.CHERRY_TRAPDOOR,
                Items.DARK_OAK_TRAPDOOR,
                Items.MANGROVE_TRAPDOOR,
                Items.BAMBOO_TRAPDOOR,
                Items.CRIMSON_TRAPDOOR,
                Items.WARPED_TRAPDOOR
        )), TRAPDOOR);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.MOSS_CARPET,
                Items.WHITE_CARPET,
                Items.ORANGE_CARPET,
                Items.MAGENTA_CARPET,
                Items.LIGHT_BLUE_CARPET,
                Items.YELLOW_CARPET,
                Items.LIME_CARPET,
                Items.PINK_CARPET,
                Items.GRAY_CARPET,
                Items.LIGHT_GRAY_CARPET,
                Items.CYAN_CARPET,
                Items.PURPLE_CARPET,
                Items.BLUE_CARPET,
                Items.BROWN_CARPET,
                Items.GREEN_CARPET,
                Items.RED_CARPET,
                Items.BLACK_CARPET,
                Items.SNOW
        )), CARPET);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.STONE_BUTTON,
                Items.POLISHED_BLACKSTONE_BUTTON,
                Items.OAK_BUTTON,
                Items.SPRUCE_BUTTON,
                Items.BIRCH_BUTTON,
                Items.JUNGLE_BUTTON,
                Items.ACACIA_BUTTON,
                Items.CHERRY_BUTTON,
                Items.DARK_OAK_BUTTON,
                Items.MANGROVE_BUTTON,
                Items.BAMBOO_BUTTON,
                Items.CRIMSON_BUTTON,
                Items.WARPED_BUTTON
        )), BUTTON);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.OAK_FENCE,
                Items.SPRUCE_FENCE,
                Items.BIRCH_FENCE,
                Items.JUNGLE_FENCE,
                Items.ACACIA_FENCE,
                Items.CHERRY_FENCE,
                Items.DARK_OAK_FENCE,
                Items.MANGROVE_FENCE,
                Items.BAMBOO_FENCE,
                Items.CRIMSON_FENCE,
                Items.WARPED_FENCE,
                Items.NETHER_BRICK_FENCE
        )), FENCE);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.OAK_FENCE_GATE,
                Items.SPRUCE_FENCE_GATE,
                Items.BIRCH_FENCE_GATE,
                Items.JUNGLE_FENCE_GATE,
                Items.ACACIA_FENCE_GATE,
                Items.CHERRY_FENCE_GATE,
                Items.DARK_OAK_FENCE_GATE,
                Items.MANGROVE_FENCE_GATE,
                Items.BAMBOO_FENCE_GATE,
                Items.CRIMSON_FENCE_GATE,
                Items.WARPED_FENCE_GATE
        )), FENCE_GATE);

        BLOCK_TEXTURES.put(new HashSet<>(Arrays.asList(
                Items.COBBLESTONE_WALL,
                Items.MOSSY_COBBLESTONE_WALL,
                Items.BRICK_WALL,
                Items.PRISMARINE_WALL,
                Items.RED_SANDSTONE_WALL,
                Items.MOSSY_STONE_BRICK_WALL,
                Items.GRANITE_WALL,
                Items.STONE_BRICK_WALL,
                Items.MUD_BRICK_WALL,
                Items.NETHER_BRICK_WALL,
                Items.ANDESITE_WALL,
                Items.RED_NETHER_BRICK_WALL,
                Items.SANDSTONE_WALL,
                Items.END_STONE_BRICK_WALL,
                Items.DIORITE_WALL,
                Items.BLACKSTONE_WALL,
                Items.POLISHED_BLACKSTONE_WALL,
                Items.POLISHED_BLACKSTONE_BRICK_WALL,
                Items.COBBLED_DEEPSLATE_WALL,
                Items.POLISHED_DEEPSLATE_WALL,
                Items.DEEPSLATE_BRICK_WALL,
                Items.DEEPSLATE_TILE_WALL
        )), WALL);

        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.BEACON)), BEACON);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.DRAGON_HEAD)), DRAGON_HEAD);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SCAFFOLDING)), SCAFFOLDING);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.DRAGON_EGG)), DRAGON_EGG);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.DECORATED_POT)), DECORATED_POT);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SCULK_SENSOR)), SCULK_SENSOR);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SCULK_SHRIEKER)), SCULK_SHRIEKER);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.CONDUIT)), CONDUIT);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SPAWNER)), SPAWNER);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.PIGLIN_HEAD)), PIGLIN_SCULL);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.CREEPER_HEAD)), CREEPER_SCULL);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SHIELD)), SHIELD);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.CALIBRATED_SCULK_SENSOR)), CALIBRATED_SCULK_SENSOR);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.LIGHTNING_ROD)), LIGHTNING_ROD);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.END_ROD)), END_ROD);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.FLOWERING_AZALEA)), FLOWERING_AZALEA);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.AZALEA)), AZALEA);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.BIG_DRIPLEAF)), BIG_DRIPLEAF);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SMALL_DRIPLEAF)), SMALL_DRIPLEAF);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.LECTERN)), LECTERN);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.STONECUTTER)), STONECUTTER);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.SPORE_BLOSSOM)), SPORE_BLOSSOM);
        BLOCK_TEXTURES.put(new HashSet<>(Collections.singletonList(Items.GRINDSTONE)), GRINDSTONE);

    }

    @Nullable
    public static Identifier getTexture(Item item) {

        for (HashSet<Item> itemList : BLOCK_TEXTURES.keySet()) {
            if (itemList.contains(item)) {
                return BLOCK_TEXTURES.get(itemList);
            }
        }
        return null;
    }
}
