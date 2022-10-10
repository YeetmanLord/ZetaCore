package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VersionMaterial {
    public static final HashMap<String, VersionMaterial> stringMaterialMap = new HashMap<>();
    public static final boolean IS_FLAT = ReflectionApi.version.isNewer("1.13");
    private String legacyMaterial, flatMaterial;
    private byte data;

    public VersionMaterial(String legacyMaterial, String flatMaterial, byte data) {
        this.legacyMaterial = legacyMaterial.toUpperCase();
        this.flatMaterial = flatMaterial.toUpperCase();
        this.data = data;
        stringMaterialMap.put(flatMaterial.toUpperCase(), this);
    }

    public String getLegacyMaterial() {
        return legacyMaterial;
    }

    public String getFlatMaterial() {
        return flatMaterial;
    }

    public byte getData() {
        return data;
    }

    public Material getMaterial() {
        if (IS_FLAT) {
            return Material.valueOf(this.flatMaterial.toLowerCase());
        } else {
            return Material.valueOf(this.legacyMaterial.toUpperCase());
        }
    }

    public ItemStack getItem() {
        if (IS_FLAT) {
            return new ItemStack(getMaterial());
        } else {
            return new ItemStack(getMaterial(), 1, this.data);
        }
    }

    public static VersionMaterial valueOf(String material) {
        return stringMaterialMap.get(material.toUpperCase());
    }

    public static final VersionMaterial WHITE_WOOL = new VersionMaterial("wool", "white_wool", (byte) 0);
    public static final VersionMaterial ORANGE_WOOL = new VersionMaterial("wool", "orange_wool", (byte) 1);
    public static final VersionMaterial MAGENTA_WOOL = new VersionMaterial("wool", "magenta_wool", (byte) 2);
    public static final VersionMaterial LIGHT_BLUE_WOOL = new VersionMaterial("wool", "light_blue_wool", (byte) 3);
    public static final VersionMaterial YELLOW_WOOL = new VersionMaterial("wool", "yellow_wool", (byte) 4);
    public static final VersionMaterial LIME_WOOL = new VersionMaterial("wool", "lime_wool", (byte) 5);
    public static final VersionMaterial PINK_WOOL = new VersionMaterial("wool", "pink_wool", (byte) 6);
    public static final VersionMaterial GRAY_WOOL = new VersionMaterial("wool", "gray_wool", (byte) 7);
    public static final VersionMaterial LIGHT_GRAY_WOOL = new VersionMaterial("wool", "light_gray_wool", (byte) 8);
    public static final VersionMaterial CYAN_WOOL = new VersionMaterial("wool", "cyan_wool", (byte) 9);
    public static final VersionMaterial PURPLE_WOOL = new VersionMaterial("wool", "purple_wool", (byte) 10);
    public static final VersionMaterial BLUE_WOOL = new VersionMaterial("wool", "blue_wool", (byte) 11);
    public static final VersionMaterial BROWN_WOOL = new VersionMaterial("wool", "brown_wool", (byte) 12);
    public static final VersionMaterial GREEN_WOOL = new VersionMaterial("wool", "green_wool", (byte) 13);
    public static final VersionMaterial RED_WOOL = new VersionMaterial("wool", "red_wool", (byte) 14);
    public static final VersionMaterial BLACK_WOOL = new VersionMaterial("wool", "black_wool", (byte) 15);

    public static final VersionMaterial WHITE_BED = new VersionMaterial("bed", "white_bed", (byte) 0);
    public static final VersionMaterial ORANGE_BED = new VersionMaterial("bed", "orange_bed", (byte) 1);
    public static final VersionMaterial MAGENTA_BED = new VersionMaterial("bed", "magenta_bed", (byte) 2);
    public static final VersionMaterial LIGHT_BLUE_BED = new VersionMaterial("bed", "light_blue_bed", (byte) 3);
    public static final VersionMaterial YELLOW_BED = new VersionMaterial("bed", "yellow_bed", (byte) 4);
    public static final VersionMaterial LIME_BED = new VersionMaterial("bed", "lime_bed", (byte) 5);
    public static final VersionMaterial PINK_BED = new VersionMaterial("bed", "pink_bed", (byte) 6);
    public static final VersionMaterial GRAY_BED = new VersionMaterial("bed", "gray_bed", (byte) 7);
    public static final VersionMaterial LIGHT_GRAY_BED = new VersionMaterial("bed", "light_gray_bed", (byte) 8);
    public static final VersionMaterial CYAN_BED = new VersionMaterial("bed", "cyan_bed", (byte) 9);
    public static final VersionMaterial PURPLE_BED = new VersionMaterial("bed", "purple_bed", (byte) 10);
    public static final VersionMaterial BLUE_BED = new VersionMaterial("bed", "blue_bed", (byte) 11);
    public static final VersionMaterial BROWN_BED = new VersionMaterial("bed", "brown_bed", (byte) 12);
    public static final VersionMaterial GREEN_BED = new VersionMaterial("bed", "green_bed", (byte) 13);
    public static final VersionMaterial RED_BED = new VersionMaterial("bed", "red_bed", (byte) 14);
    public static final VersionMaterial BLACK_BED = new VersionMaterial("bed", "black_bed", (byte) 15);

    public static final VersionMaterial WHITE_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "white_stained_glass_pane", (byte) 0);
    public static final VersionMaterial ORANGE_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "orange_stained_glass_pane", (byte) 1);
    public static final VersionMaterial MAGENTA_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "magenta_stained_glass_pane", (byte) 2);
    public static final VersionMaterial LIGHT_BLUE_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "light_blue_stained_glass_pane", (byte) 3);
    public static final VersionMaterial YELLOW_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "yellow_stained_glass_pane", (byte) 4);
    public static final VersionMaterial LIME_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "lime_stained_glass_pane", (byte) 5);
    public static final VersionMaterial PINK_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "pink_stained_glass_pane", (byte) 6);
    public static final VersionMaterial GRAY_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "gray_stained_glass_pane", (byte) 7);
    public static final VersionMaterial LIGHT_GRAY_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "light_gray_stained_glass_pane", (byte) 8);
    public static final VersionMaterial CYAN_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "cyan_stained_glass_pane", (byte) 9);
    public static final VersionMaterial PURPLE_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "purple_stained_glass_pane", (byte) 10);
    public static final VersionMaterial BLUE_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "blue_stained_glass_pane", (byte) 11);
    public static final VersionMaterial BROWN_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "brown_stained_glass_pane", (byte) 12);
    public static final VersionMaterial GREEN_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "green_stained_glass_pane", (byte) 13);
    public static final VersionMaterial RED_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "red_stained_glass_pane", (byte) 14);
    public static final VersionMaterial BLACK_STAINED_GLASS_PANE = new VersionMaterial("stained_glass_pane", "black_stained_glass_pane", (byte) 15);

    public static final VersionMaterial SKELETON_HEAD = new VersionMaterial("skull_item", "skeleton_head", (byte) 0);
    public static final VersionMaterial WITHER_SKELETON_HEAD = new VersionMaterial("skull_item", "wither_skeleton_head", (byte) 1);
    public static final VersionMaterial ZOMBIE_HEAD = new VersionMaterial("skull_item", "zombie_head", (byte) 2);
    public static final VersionMaterial PLAYER_HEAD = new VersionMaterial("skull_item", "player_head", (byte) 3);
    public static final VersionMaterial CREEPER_HEAD = new VersionMaterial("skull_item", "creeper_head", (byte) 4);
    public static final VersionMaterial DRAGON_HEAD = new VersionMaterial("skull_item", "dragon_head", (byte) 5);


    public static final VersionMaterial WHITE_STAINED_GLASS = new VersionMaterial("stained_glass", "white_stained_glass", (byte) 0);
    public static final VersionMaterial ORANGE_STAINED_GLASS = new VersionMaterial("stained_glass", "orange_stained_glass", (byte) 1);
    public static final VersionMaterial MAGENTA_STAINED_GLASS = new VersionMaterial("stained_glass", "magenta_stained_glass", (byte) 2);
    public static final VersionMaterial LIGHT_BLUE_STAINED_GLASS = new VersionMaterial("stained_glass", "light_blue_stained_glass", (byte) 3);
    public static final VersionMaterial YELLOW_STAINED_GLASS = new VersionMaterial("stained_glass", "yellow_stained_glass", (byte) 4);
    public static final VersionMaterial LIME_STAINED_GLASS = new VersionMaterial("stained_glass", "lime_stained_glass", (byte) 5);
    public static final VersionMaterial PINK_STAINED_GLASS = new VersionMaterial("stained_glass", "pink_stained_glass", (byte) 6);
    public static final VersionMaterial GRAY_STAINED_GLASS = new VersionMaterial("stained_glass", "gray_stained_glass", (byte) 7);
    public static final VersionMaterial LIGHT_GRAY_STAINED_GLASS = new VersionMaterial("stained_glass", "light_gray_stained_glass", (byte) 8);
    public static final VersionMaterial CYAN_STAINED_GLASS = new VersionMaterial("stained_glass", "cyan_stained_glass", (byte) 9);
    public static final VersionMaterial PURPLE_STAINED_GLASS = new VersionMaterial("stained_glass", "purple_stained_glass", (byte) 10);
    public static final VersionMaterial BLUE_STAINED_GLASS = new VersionMaterial("stained_glass", "blue_stained_glass", (byte) 11);
    public static final VersionMaterial BROWN_STAINED_GLASS = new VersionMaterial("stained_glass", "brown_stained_glass", (byte) 12);
    public static final VersionMaterial GREEN_STAINED_GLASS = new VersionMaterial("stained_glass", "green_stained_glass", (byte) 13);
    public static final VersionMaterial RED_STAINED_GLASS = new VersionMaterial("stained_glass", "red_stained_glass", (byte) 14);
    public static final VersionMaterial BLACK_STAINED_GLASS = new VersionMaterial("stained_glass", "black_stained_glass", (byte) 15);

    public static final VersionMaterial NETHERITE_HELMET = new VersionMaterial("netherite_helmet", "netherite_helmet", (byte) 0);
    public static final VersionMaterial NETHERITE_CHESTPLATE = new VersionMaterial("netherite_chestplate", "netherite_chestplate", (byte) 0);
    public static final VersionMaterial NETHERITE_LEGGINGS = new VersionMaterial("netherite_leggings", "netherite_leggings", (byte) 0);
    public static final VersionMaterial NETHERITE_BOOTS = new VersionMaterial("netherite_boots", "netherite_boots", (byte) 0);
    public static final VersionMaterial NETHERITE_SWORD = new VersionMaterial("netherite_sword", "netherite_sword", (byte) 0);
    public static final VersionMaterial NETHERITE_PICKAXE = new VersionMaterial("netherite_pickaxe", "netherite_pickaxe", (byte) 0);
    public static final VersionMaterial NETHERITE_AXE = new VersionMaterial("netherite_axe", "netherite_axe", (byte) 0);
    public static final VersionMaterial NETHERITE_SHOVEL = new VersionMaterial("netherite_shovel", "netherite_shovel", (byte) 0);
    public static final VersionMaterial NETHERITE_HOE = new VersionMaterial("netherite_hoe", "netherite_hoe", (byte) 0);
    public static final VersionMaterial NETHERITE_INGOT = new VersionMaterial("netherite_ingot", "netherite_ingot", (byte) 0);
    public static final VersionMaterial NETHERITE_SCRAP = new VersionMaterial("netherite_scrap", "netherite_scrap", (byte) 0);
    public static final VersionMaterial ANCIENT_DEBRIS = new VersionMaterial("ancient_debris", "ancient_debris", (byte) 0);
    public static final VersionMaterial NETHERITE_BLOCK = new VersionMaterial("netherite_block", "netherite_block", (byte) 0);

    public static final VersionMaterial WOODEN_SWORD = new VersionMaterial("wood_sword", "wooden_sword", (byte) 0);
    public static final VersionMaterial WOODEN_SHOVEL = new VersionMaterial("wood_shovel", "wooden_shovel", (byte) 0);
    public static final VersionMaterial WOODEN_PICKAXE = new VersionMaterial("wood_pickaxe", "wooden_pickaxe", (byte) 0);
    public static final VersionMaterial WOODEN_AXE = new VersionMaterial("wood_axe", "wooden_axe", (byte) 0);
    public static final VersionMaterial WOODEN_HOE = new VersionMaterial("wood_hoe", "wooden_hoe", (byte) 0);

    public static final VersionMaterial GOLDEN_SWORD = new VersionMaterial("gold_sword", "golden_sword", (byte) 0);
    public static final VersionMaterial GOLDEN_SHOVEL = new VersionMaterial("gold_shovel", "golden_shovel", (byte) 0);
    public static final VersionMaterial GOLDEN_PICKAXE = new VersionMaterial("gold_pickaxe", "golden_pickaxe", (byte) 0);
    public static final VersionMaterial GOLDEN_AXE = new VersionMaterial("gold_axe", "golden_axe", (byte) 0);
    public static final VersionMaterial GOLDEN_HOE = new VersionMaterial("gold_hoe", "golden_hoe", (byte) 0);
    public static final VersionMaterial GOLDEN_HELMET = new VersionMaterial("gold_helmet", "golden_helmet", (byte) 0);
    public static final VersionMaterial GOLDEN_CHESTPLATE = new VersionMaterial("gold_chestplate", "golden_chestplate", (byte) 0);
    public static final VersionMaterial GOLDEN_LEGGINGS = new VersionMaterial("gold_leggings", "golden_leggings", (byte) 0);
    public static final VersionMaterial GOLDEN_BOOTS = new VersionMaterial("gold_boots", "golden_boots", (byte) 0);

    public static final VersionMaterial GRASS_BLOCK = new VersionMaterial("grass", "grass_block", (byte) 0);
    public static final VersionMaterial OAK_SIGN = new VersionMaterial("sign", "oak_sign", (byte) 0);
    public static final VersionMaterial SPRUCE_SIGN = new VersionMaterial("sign", "spruce_sign", (byte) 0);
    public static final VersionMaterial BIRCH_SIGN = new VersionMaterial("sign", "birch_sign", (byte) 0);
    public static final VersionMaterial JUNGLE_SIGN = new VersionMaterial("sign", "jungle_sign", (byte) 0);
    public static final VersionMaterial ACACIA_SIGN = new VersionMaterial("sign", "acacia_sign", (byte) 0);
    public static final VersionMaterial DARK_OAK_SIGN = new VersionMaterial("sign", "dark_oak_sign", (byte) 0);
    public static final VersionMaterial CRIMSON_SIGN = new VersionMaterial("sign", "crimson_sign", (byte) 0);
    public static final VersionMaterial WARPED_SIGN = new VersionMaterial("sign", "warped_sign", (byte) 0);

    public static final VersionMaterial COMMAND_BLOCK = new VersionMaterial("command", "command_block", (byte) 0);
    public static final VersionMaterial REPEATING_COMMAND_BLOCK = new VersionMaterial("command", "repeating_command_block", (byte) 0);
    public static final VersionMaterial CHAIN_COMMAND_BLOCK = new VersionMaterial("command", "chain_command_block", (byte) 0);
    public static final VersionMaterial WHITE_TERRACOTTA = new VersionMaterial("stained_clay", "white_terracotta", (byte) 0);
    public static final VersionMaterial ORANGE_TERRACOTTA = new VersionMaterial("stained_clay", "orange_terracotta", (byte) 1);
    public static final VersionMaterial MAGENTA_TERRACOTTA = new VersionMaterial("stained_clay", "magenta_terracotta", (byte) 2);
    public static final VersionMaterial LIGHT_BLUE_TERRACOTTA = new VersionMaterial("stained_clay", "light_blue_terracotta", (byte) 3);
    public static final VersionMaterial YELLOW_TERRACOTTA = new VersionMaterial("stained_clay", "yellow_terracotta", (byte) 4);
    public static final VersionMaterial LIME_TERRACOTTA = new VersionMaterial("stained_clay", "lime_terracotta", (byte) 5);
    public static final VersionMaterial PINK_TERRACOTTA = new VersionMaterial("stained_clay", "pink_terracotta", (byte) 6);
    public static final VersionMaterial GRAY_TERRACOTTA = new VersionMaterial("stained_clay", "gray_terracotta", (byte) 7);
    public static final VersionMaterial LIGHT_GRAY_TERRACOTTA = new VersionMaterial("stained_clay", "light_gray_terracotta", (byte) 8);
    public static final VersionMaterial CYAN_TERRACOTTA = new VersionMaterial("stained_clay", "cyan_terracotta", (byte) 9);
    public static final VersionMaterial PURPLE_TERRACOTTA = new VersionMaterial("stained_clay", "purple_terracotta", (byte) 10);
    public static final VersionMaterial BLUE_TERRACOTTA = new VersionMaterial("stained_clay", "blue_terracotta", (byte) 11);
    public static final VersionMaterial BROWN_TERRACOTTA = new VersionMaterial("stained_clay", "brown_terracotta", (byte) 12);
    public static final VersionMaterial GREEN_TERRACOTTA = new VersionMaterial("stained_clay", "green_terracotta", (byte) 13);
    public static final VersionMaterial RED_TERRACOTTA = new VersionMaterial("stained_clay", "red_terracotta", (byte) 14);
    public static final VersionMaterial BLACK_TERRACOTTA = new VersionMaterial("stained_clay", "black_terracotta", (byte) 15);
    public static final VersionMaterial TERRACOTTA = new VersionMaterial("hard_clay", "terracotta", (byte) 0);

    public static final VersionMaterial CLOCK = new VersionMaterial("watch", "clock", (byte) 0);
    public static final VersionMaterial ENCHANTING_TABLE = new VersionMaterial("enchantment_table", "enchanting_table", (byte) 0);

    public static final WhiteMonsterEggVersionMaterial WHITE_MONSTER_EGG = new WhiteMonsterEggVersionMaterial();

    public static final VersionMaterial WHITE_BANNER = new VersionMaterial("banner", "white_banner", (byte) 15);
    public static final VersionMaterial ORANGE_BANNER = new VersionMaterial("banner", "orange_banner", (byte) 14);
    public static final VersionMaterial MAGENTA_BANNER = new VersionMaterial("banner", "magenta_banner", (byte) 13);
    public static final VersionMaterial LIGHT_BLUE_BANNER = new VersionMaterial("banner", "light_blue_banner", (byte) 12);
    public static final VersionMaterial YELLOW_BANNER = new VersionMaterial("banner", "yellow_banner", (byte) 11);
    public static final VersionMaterial LIME_BANNER = new VersionMaterial("banner", "lime_banner", (byte) 10);
    public static final VersionMaterial PINK_BANNER = new VersionMaterial("banner", "pink_banner", (byte) 9);
    public static final VersionMaterial GRAY_BANNER = new VersionMaterial("banner", "gray_banner", (byte) 8);
    public static final VersionMaterial LIGHT_GRAY_BANNER = new VersionMaterial("banner", "light_gray_banner", (byte) 7);
    public static final VersionMaterial CYAN_BANNER = new VersionMaterial("banner", "cyan_banner", (byte) 6);
    public static final VersionMaterial PURPLE_BANNER = new VersionMaterial("banner", "purple_banner", (byte) 5);
    public static final VersionMaterial BLUE_BANNER = new VersionMaterial("banner", "blue_banner", (byte) 4);
    public static final VersionMaterial BROWN_BANNER = new VersionMaterial("banner", "brown_banner", (byte) 3);
    public static final VersionMaterial GREEN_BANNER = new VersionMaterial("banner", "green_banner", (byte) 2);
    public static final VersionMaterial RED_BANNER = new VersionMaterial("banner", "red_banner", (byte) 1);
    public static final VersionMaterial BLACK_BANNER = new VersionMaterial("banner", "black_banner", (byte) 0);


    public static VersionMaterial getWool(DyeColor color) {
        switch (color) {
            case WHITE:
                return WHITE_WOOL;
            case ORANGE:
                return ORANGE_WOOL;
            case MAGENTA:
                return MAGENTA_WOOL;
            case LIGHT_BLUE:
                return LIGHT_BLUE_WOOL;
            case YELLOW:
                return YELLOW_WOOL;
            case LIME:
                return LIME_WOOL;
            case PINK:
                return PINK_WOOL;
            case GRAY:
                return GRAY_WOOL;
            case SILVER:
                return LIGHT_GRAY_WOOL;
            case CYAN:
                return CYAN_WOOL;
            case PURPLE:
                return PURPLE_WOOL;
            case BLUE:
                return BLUE_WOOL;
            case BROWN:
                return BROWN_WOOL;
            case GREEN:
                return GREEN_WOOL;
            case RED:
                return RED_WOOL;
            case BLACK:
                return BLACK_WOOL;
            default:
                throw new IllegalArgumentException("Unknown color " + color);
        }
    }

    public static VersionMaterial getBed(DyeColor color) {
        if (ReflectionApi.version.isNewer("1.12")) {
            switch (color) {
                case WHITE:
                    return WHITE_BED;
                case ORANGE:
                    return ORANGE_BED;
                case MAGENTA:
                    return MAGENTA_BED;
                case LIGHT_BLUE:
                    return LIGHT_BLUE_BED;
                case YELLOW:
                    return YELLOW_BED;
                case LIME:
                    return LIME_BED;
                case PINK:
                    return PINK_BED;
                case GRAY:
                    return GRAY_BED;
                case SILVER:
                    return LIGHT_GRAY_BED;
                case CYAN:
                    return CYAN_BED;
                case PURPLE:
                    return PURPLE_BED;
                case BLUE:
                    return BLUE_BED;
                case BROWN:
                    return BROWN_BED;
                case GREEN:
                    return GREEN_BED;
                case RED:
                    return RED_BED;
                case BLACK:
                    return BLACK_BED;
                default:
                    throw new IllegalArgumentException("Unknown color " + color);
            }
        }
        else {
            return WHITE_BED;
        }
    }
}