package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;


public enum EnumNMSEntityToBukkit {
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", "ZombieVillager", "zombie_villager"),

    SKELETON_HORSE("SKELETON_HORSE", "skeleton_horse", "skeleton_horse"),

    ZOMBIE_HORSE("ZOMBIE_HORSE", "zombie_horse", "zombie_horse"),

    DONKEY("DONKEY", "donkey", "donkey"),

    MULE("MULE", "mule", "mule"),

    EVOKER("EVOKER", "evoker", "evoker"),

    VEX("VEX", "vex", "vex"),

    VINDICATOR("VINDICATOR", "vindicator", "vindicator"),

    ILLUSIONER("ILLUSIONER", "illusioner", "illusioner"),

    CREEPER("CREEPER", "Creeper", "creeper"),

    SKELETON("SKELETON", "Skeleton", "skeleton"),

    SPIDER("SPIDER", "Spider", "spider"),

    GIANT("GIANT", "Giant", "giant"),

    ZOMBIE("ZOMBIE", "Zombie", "zombie"),

    SLIME("SLIME", "Slime", "slime"),

    GHAST("GHAST", "Ghast", "ghast"),

    PIG_ZOMBIE("PIG_ZOMBIE", "PigZombie", "zombie_pigman"),

    ENDERMAN("ENDERMAN", "Enderman", "enderman"),

    CAVE_SPIDER("CAVE_SPIDER", "CaveSpider", "cave_spider"),

    SILVERFISH("SILVERFISH", "Silverfish", "silverfish"),

    BLAZE("BLAZE", "Blaze", "blaze"),

    MAGMA_CUBE("MAGMA_CUBE", "LavaSlime", "magma_cube"),

    ENDER_DRAGON("ENDER_DRAGON", "EnderDragon", "ender_dragon"),

    WITHER("WITHER", "WitherBoss", "wither"),

    BAT("BAT", "Bat", "bat"),

    WITCH("WITCH", "Witch", "witch"),

    ENDERMITE("ENDERMITE", "Endermite", "endermite"),

    GUARDIAN("GUARDIAN", "Guardian", "guardian"),

    ELDER_GUARDIAN("ELDER_GUARDIAN", "elder_guardian", "elder_guardian"),

    PIG("PIG", "Pig", "pig"),

    SHEEP("SHEEP", "Sheep", "sheep"),

    COW("COW", "Cow", "cow"),

    CHICKEN("CHICKEN", "Chicken", "chicken"),

    SQUID("SQUID", "Squid", "squid"),

    WOLF("WOLF", "Wolf", "wolf"),

    MUSHROOM_COW("MUSHROOM_COW", "MushroomCow", "mooshroom"),

    SNOWMAN("SNOWMAN", "SnowMan", "snow_golem"),

    OCELOT("OCELOT", "Ozelot", "ocelot"),

    IRON_GOLEM("IRON_GOLEM", "VillagerGolem", "iron_golem"),

    HORSE("HORSE", "EntityHorse", "horse"),

    RABBIT("RABBIT", "Rabbit", "rabbit"),

    POLAR_BEAR("POLAR_BEAR", "PolarBear", "polar_bear"),

    HUSK("HUSK", "husk", "husk"),

    STRAY("STRAY", "stray", "stray"),

    LLAMA("LLAMA", "llama", "llama"),

    LLAMA_SPIT("LLAMA_SPIT", "llama_spit", "llama_spit"),

    PARROT("PARROT", "parrot", "parrot"),

    VILLAGER("VILLAGER", "Villager", "villager"),

    TURTLE("TURTLE", "turtle", "turtle"),

    PHANTOM("PHANTOM", "phantom", "phantom"),

    COD("COD", "cod", "cod"),

    SALMON("SALMON", "salmon", "salmon"),

    PUFFERFISH("PUFFERFISH", "pufferfish", "pufferfish"),

    TROPICAL_FISH("TROPICAL_FISH", "tropical_fish", "tropical_fish"),

    DROWNED("DROWNED", "drowned", "drowned"),

    DOLPHIN("DOLPHIN", "dolphin", "dolphin"),

    CAT("CAT", "cat", "cat"),

    PANDA("PANDA", "panda", "panda"),

    PILLAGER("PILLAGER", "pillager", "pillager"),

    RAVAGER("RAVAGER", "ravager", "ravager"),

    TRADER_LLAMA("TRADER_LLAMA", "trader_llama", "trader_llama"),

    WANDERING_TRADER("WANDERING_TRADER", "wandering_trader", "wandering_trader"),

    FOX("FOX", "fox", "fox"),

    BEE("BEE", "bee", "bee"),

    HOGLIN("HOGLIN", "hoglin", "hoglin"),

    PIGLIN("PIGLIN", "piglin", "piglin"),

    ZOGLIN("ZOGLIN", "zoglin", "zoglin"),

    STRIDER("STRIDER", "strider", "strider"),

    ZOMBIFIED_PIGLIN("ZOMBIFIED_PIGLIN", "zombified_piglin", "zombified_piglin"),

    PIGLIN_BRUTE("PIGLIN_BRUTE", "piglin_brute", "piglin_brute"),

    GOAT("GOAT", "goat", "goat"),

    AXOLOTL("AXOLOTL", "axolotl", "axolotl"),

    GLOW_SQUID("GLOW_SQUID", "glow_squid", "glow_squid"),

    ALLAY("ALLAY", "allay", "allay"),

    WARDEN("WARDEN", "warden", "warden"),

    WITHER_SKELETON("WITHER_SKELETON", "wither_skeleton", "wither_skeleton"),

    FROG("FROG", "frog", "frog"),

    TADPOLE("TADPOLE", "tadpole", "tadpole");


    private String bukkitType, legacyNmsType, nmsType;

    EnumNMSEntityToBukkit(String bukkitType, String legacyNmsType, String nmsType) {
        this.bukkitType = bukkitType;
        this.legacyNmsType = legacyNmsType;
        this.nmsType = nmsType;
    }

    public String getBukkitType() {
        return bukkitType;
    }

    public String getNMSType() {
        return nmsType;
    }

    public String getLegacyNMSType() {
        return legacyNmsType;
    }

    public EntityType getEntityType() {
        return EntityType.valueOf(bukkitType);
    }

    public static EnumNMSEntityToBukkit getEnumNMSEntityToBukkit(EntityType entityType) {
        for (EnumNMSEntityToBukkit enumNMSEntityToBukkit : EnumNMSEntityToBukkit.values()) {
            if (enumNMSEntityToBukkit.bukkitType.equalsIgnoreCase(entityType.name())) {
                return enumNMSEntityToBukkit;
            }
        }
        return null;
    }

    public String getNmsEntityType() {
        if (ReflectionApi.version.isNewer("1.11")) {
            return nmsType.contains("minecraft:") ? nmsType : "minecraft:" + nmsType;
        } else {
            return legacyNmsType;
        }
    }


}
