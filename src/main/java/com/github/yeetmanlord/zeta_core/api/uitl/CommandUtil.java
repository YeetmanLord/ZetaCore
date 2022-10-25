package com.github.yeetmanlord.zeta_core.api.uitl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class CommandUtil {

    public static Parsed<Integer> parseInteger(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Integer.parseInt(string), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0, false);
    }

    public static Parsed<Double> parseDouble(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Double.parseDouble(string), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0D, false);
    }

    public static Parsed<Float> parseFloat(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Float.parseFloat(string), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0F, false);
    }

    public static Parsed<Boolean> parseBoolean(CommandSender sender, String string, String errorMessage) {
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1") || string.equalsIgnoreCase("yes")) {
            return new Parsed<>(true, true);
        } else if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0") || string.equalsIgnoreCase("no")) {
            return new Parsed<>(false, true);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
            return new Parsed<>(false, false);
        }
    }

    public static Parsed<ChatColor> parseChatColor(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(ChatColor.valueOf(string.toUpperCase()), true);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(ChatColor.WHITE, false);
    }

    public static Parsed<Material> parseMaterial(CommandSender sender, String string, String errorMessage) {
        Material material = Material.matchMaterial(string);

        if (material == null) {
            material = Bukkit.getUnsafe().getMaterialFromInternalName(string);
        }

        if (material != null) {
            return new Parsed<>(material, true);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));

            return new Parsed<>(Material.AIR, false);
        }
    }

    public static class Parsed<Type> {
        private Type value;
        private boolean success;

        public Parsed(Type value, boolean success) {
            this.value = value;
            this.success = success;
        }

        public Type getValue() {
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }


}