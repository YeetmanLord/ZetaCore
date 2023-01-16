package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.DyeColor;

public enum CrossVersionDyeColor {
    BLACK,
    RED,
    GREEN,
    BROWN,
    BLUE,
    PURPLE,
    CYAN,
    LIGHT_GRAY("SILVER"),
    GRAY,
    PINK,
    LIME,
    YELLOW,
    LIGHT_BLUE,
    MAGENTA,
    ORANGE,
    WHITE;

    private final String legacyName;

    CrossVersionDyeColor() {
        legacyName = this.name();
    }

    CrossVersionDyeColor(String legacy) {
        legacyName = legacy;
    }

    public static CrossVersionDyeColor fromBukkit(org.bukkit.DyeColor color) {
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
            if (color.name().equalsIgnoreCase("SILVER")) {
                return LIGHT_GRAY;
            }
        }
        return CrossVersionDyeColor.valueOf(color.name());
    }

    public static org.bukkit.DyeColor toBukkit(CrossVersionDyeColor color) {
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
            if (color == LIGHT_GRAY) {
                return org.bukkit.DyeColor.valueOf("SILVER");
            }
        }
        return org.bukkit.DyeColor.valueOf(color.name());
    }

    public String getLegacyName() {
        return legacyName;
    }

    public String getName() {
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
            return legacyName;
        }
        return name();
    }

    public DyeColor toBukkit() {
        return toBukkit(this);
    }
}
