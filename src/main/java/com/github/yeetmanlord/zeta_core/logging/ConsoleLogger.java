package com.github.yeetmanlord.zeta_core.logging;

import javax.annotation.Nullable;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;

/**
 * Custom {@link ZetaPlugin} logger. It supports colors and formatting unlike Log4J logger.
 * Technically a wrapper for Bukkit's logger. But I want it to sound cool.
 *
 * @author YeetManLord
 * @zeta.usage INTERNAL
 */
public class ConsoleLogger implements IPluginLogger {

    private ZetaPlugin plugin;

    private ChatColor defaultColor;

    private boolean debug;

    public ConsoleLogger(ZetaPlugin plugin, ChatColor defaultColor) {

        this.plugin = plugin;
        this.defaultColor = defaultColor;
        debug = false;

    }
    
    public ConsoleLogger(ZetaPlugin plugin, org.bukkit.ChatColor defaultColor) {

        this.plugin = plugin;
        this.defaultColor = defaultColor.asBungee();
        debug = false;

    }

    public void setDebugging(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebugging() {
        return this.debug;
    }

    public ConsoleLogger(ZetaPlugin plugin) {

        this(plugin, ChatColor.WHITE);

    }

    public void info(Object... args) {

        Bukkit.getConsoleSender().sendMessage(defaultColor + "[" + plugin.getClass().getSimpleName() + "/INFO] " + ChatColor.translateAlternateColorCodes('&', convertToString(args)));

    }

    public void debug(Object... args) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage((defaultColor + "[" + plugin.getClass().getSimpleName() + "/DEBUG] " + ChatColor.translateAlternateColorCodes('&', convertToString(args))));
        }
    }

    public void warn(Object... args) {
        Bukkit.getConsoleSender().sendMessage((ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
    }

    public void warn(Throwable throwable, Object... args) {

        Bukkit.getConsoleSender().sendMessage((ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void error(Object... args) {

        Bukkit.getConsoleSender().sendMessage((ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));

    }

    public void fatal(Object... args) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args))));

    }

    public void error(Throwable throwable, Object... args) {

        Bukkit.getConsoleSender().sendMessage((ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void fatal(Throwable throwable, Object... args) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void setColor(ChatColor defaultColor) {

        this.defaultColor = defaultColor;

    }

    public ChatColor getColor() {
        return defaultColor;
    }

    public void setColor(org.bukkit.ChatColor color) {
        this.defaultColor = color.asBungee();
    }
}
