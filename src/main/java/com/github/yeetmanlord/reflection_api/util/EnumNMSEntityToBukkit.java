package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.sql.Ref;


public enum EnumNMSEntityToBukkit {
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", "ZombieVillager", "zombie_villager", "ZOMBIE_SPAWN_EGG"),

    SKELETON_HORSE("SKELETON_HORSE", "skeleton_horse", "skeleton_horse", "SKELETON_HORSE_SPAWN_EGG"),

    ZOMBIE_HORSE("ZOMBIE_HORSE", "zombie_horse", "zombie_horse", "ZOMBIE_HORSE_SPAWN_EGG"),

    DONKEY("DONKEY", "donkey", "donkey", "DONKEY_SPAWN_EGG"),

    MULE("MULE", "mule", "mule", "MULE_SPAWN_EGG"),

    EVOKER("EVOKER", "evoker", "evoker", "EVOKER_SPAWN_EGG"),

    VEX("VEX", "vex", "vex", "VEX_SPAWN_EGG"),

    VINDICATOR("VINDICATOR", "vindicator", "vindicator", "VINDICATOR_SPAWN_EGG"),

    ILLUSIONER("ILLUSIONER", "illusioner", "illusioner", "GHAST_SPAWN_EGG"),

    CREEPER("CREEPER", "Creeper", "creeper", "CREEPER_SPAWN_EGG"),

    SKELETON("SKELETON", "Skeleton", "skeleton", "SKELETON_SPAWN_EGG"),

    SPIDER("SPIDER", "Spider", "spider", "SPIDER_SPAWN_EGG"),

    GIANT("GIANT", "Giant", "giant", "ZOMBIE_SPAWN_EGG"),

    ZOMBIE("ZOMBIE", "Zombie", "zombie", "ZOMBIE_SPAWN_EGG"),

    SLIME("SLIME", "Slime", "slime", "SLIME_SPAWN_EGG"),

    GHAST("GHAST", "Ghast", "ghast", "GHAST_SPAWN_EGG"),

    /**
     * Accounts for all variants
     */
    ZOMBIE_PIGMAN(ReflectionApi.version.isNewer(ReflectionApi.v1_16) ? "ZOMBIFIED_PIGLIN" : ReflectionApi.version.isNewer(ReflectionApi.v1_13) ? "ZOMBIE_PIGMAN" : "PIG_ZOMBIE", "PigZombie", ReflectionApi.version.isNewer(ReflectionApi.v1_16) ? "zombified_piglin" : "zombie_pigman", ReflectionApi.version.isNewer(ReflectionApi.v1_16) ? "ZOMBIFIED_PIGLIN_SPAWN_EGG" : "ZOMBIE_PIGMAN_SPAWN_EGG"),

    ENDERMAN("ENDERMAN", "Enderman", "enderman"),

    CAVE_SPIDER("CAVE_SPIDER", "CaveSpider", "cave_spider"),

    SILVERFISH("SILVERFISH", "Silverfish", "silverfish"),

    BLAZE("BLAZE", "Blaze", "blaze"),

    MAGMA_CUBE("MAGMA_CUBE", "LavaSlime", "magma_cube"),

    ENDER_DRAGON("ENDER_DRAGON", "EnderDragon", "ender_dragon", ReflectionApi.version.isNewer(ReflectionApi.v1_20) ? "ENDER_DRAGON_SPAWN_EGG" : "GHAST_SPAWN_EGG"),

    WITHER("WITHER", "WitherBoss", "wither", ReflectionApi.version.isNewer(ReflectionApi.v1_20) ? "WITHER_SPAWN_EGG" : "WITHER_SKELETON_SPAWN_EGG"),

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

    MUSHROOM_COW("MUSHROOM_COW", "MushroomCow", "mooshroom", "MOOSHROOM_SPAWN_EGG"),

    SNOWMAN("SNOWMAN", "SnowMan", "snow_golem", "GHAST_SPAWN_EGG"),

    OCELOT("OCELOT", "Ozelot", "ocelot"),

    IRON_GOLEM("IRON_GOLEM", "VillagerGolem", "iron_golem", "GHAST_SPAWN_EGG"),

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

    /**
     * 1.16+ Only
     */
    ZOMBIFIED_PIGLIN("ZOMBIFIED_PIGLIN", "zombified_piglin", "zombified_piglin"),

    PIGLIN_BRUTE("PIGLIN_BRUTE", "piglin_brute", "piglin_brute"),

    GOAT("GOAT", "goat", "goat"),

    AXOLOTL("AXOLOTL", "axolotl", "axolotl"),

    GLOW_SQUID("GLOW_SQUID", "glow_squid", "glow_squid"),

    ALLAY("ALLAY", "allay", "allay"),

    WARDEN("WARDEN", "warden", "warden"),

    WITHER_SKELETON("WITHER_SKELETON", "wither_skeleton", "wither_skeleton"),

    FROG("FROG", "frog", "frog"),

    TADPOLE("TADPOLE", "tadpole", "tadpole"),

    SHULKER("SHULKER", "Shulker", "shulker");


    private String bukkitType, legacyNmsType, nmsType, spawnEggMaterial;

    EnumNMSEntityToBukkit(String bukkitType, String legacyNmsType, String nmsType, String spawnEggMaterial) {
        this.bukkitType = bukkitType;
        this.legacyNmsType = legacyNmsType;
        this.nmsType = nmsType;
        this.spawnEggMaterial = spawnEggMaterial;
    }

    EnumNMSEntityToBukkit(String bukkitType, String legacyNmsType, String nmsType) {
        this(bukkitType, legacyNmsType, nmsType, bukkitType + "_SPAWN_EGG");
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
        if (entityType.name().equalsIgnoreCase("pig_zombie") || entityType.name().equalsIgnoreCase("zombified_piglin")) {
            return ZOMBIE_PIGMAN;
        }
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

    public String getSpawnEggMaterial() {
        return spawnEggMaterial;
    }
}
