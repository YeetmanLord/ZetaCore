package com.github.yeetmanlord.zeta_core.api.util;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class CommandUtil {

    /**
     * Parses an integer from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the parsed integer and whether it was successful.
     */
    public static Parsed<Integer> parseInteger(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Integer.parseInt(string.trim()), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0, false);
    }

    /**
     * Parses a double from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the parsed double and whether it was successful.
     */
    public static Parsed<Double> parseDouble(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Double.parseDouble(string.trim()), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0D, false);
    }

    /**
     * Parses a float from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the parsed float and whether it was successful.
     */
    public static Parsed<Float> parseFloat(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(Float.parseFloat(string.trim()), true);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(0F, false);
    }

    /**
     * Parses a boolean from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the parsed boolean and whether it was successful.
     */
    public static Parsed<Boolean> parseBoolean(CommandSender sender, String string, String errorMessage) {
        string = string.trim();
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1") || string.equalsIgnoreCase("yes")) {
            return new Parsed<>(true, true);
        } else if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0") || string.equalsIgnoreCase("no")) {
            return new Parsed<>(false, true);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
            return new Parsed<>(false, false);
        }
    }

    /**
     * Parses a chat color from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the parsed chat color and whether it was successful.
     */
    public static Parsed<ChatColor> parseChatColor(CommandSender sender, String string, String errorMessage) {
        try {
            return new Parsed<>(ChatColor.valueOf(string.toUpperCase().trim()), true);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
        return new Parsed<>(ChatColor.WHITE, false);
    }

    /**
     * Parses a material from a string, and sends a message to the sender if it fails
     *
     * @param sender       The sender to send the message to
     * @param string       The string to parse
     * @param errorMessage The message to send if the parse fails
     * @return A {@link Parsed} object containing the material float and whether it was successful.
     */
    public static Parsed<Material> parseMaterial(CommandSender sender, String string, String errorMessage) {
        Material material = VersionMaterial.getFromString(string);

        if (material != null) {
            return new Parsed<>(material, true);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));

            return new Parsed<>(Material.AIR, false);
        }
    }

    /**
     * A class that contains a parsed value and whether it was successful
     * @param <Type> The type of the parsed value
     */
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