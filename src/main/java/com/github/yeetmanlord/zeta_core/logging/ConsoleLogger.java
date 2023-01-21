package com.github.yeetmanlord.zeta_core.logging;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;

/**
 * Custom {@link ZetaPlugin} logger. It supports colors and formatting unlike Log4J logger.
 * Technically a wrapper for Bukkit's logger. But I want it to sound cool.
 *
 * @author YeetManLord
 * @zeta.usage INTERNAL
 */
public class ConsoleLogger {

    private ZetaPlugin plugin;

    private ChatColor defaultColor;

    private boolean debug;

    public ConsoleLogger(ZetaPlugin plugin, ChatColor defaultColor) {

        this.plugin = plugin;
        this.defaultColor = defaultColor;
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

    public void log(LogType type, @Nullable Throwable error, Object... args) {

        switch (type) {
            case INFO:
                info(args);
                break;

            case DEBUG:
                debug(args);
                break;

            case WARN:
                if (error != null) {
                    warn(error, args);
                } else {
                    warn(args);
                }
                break;

            case ERROR:
                if (error != null) {
                    error(error, args);
                } else {
                    error(args);
                }
                break;

            case FATAL:
                if (error != null) {
                    fatal(error, args);
                } else {
                    fatal(args);
                }
                break;
        }

    }

    private String convertToString(Object... objects) {

        if (objects == null || objects.length == 0 || objects[0] == null) {
            return "null";
        }

        StringBuilder str = new StringBuilder();

        for (int x = 0; x < objects.length; x++) {
            Object obj = objects[x];


            if (x == objects.length - 1) {
                if (obj == null) {
                    str.append("null").append(defaultColor.toString());
                } else {
                    str.append(obj).append(" ").append(defaultColor.toString());
                }
            } else {
                if (obj == null) {
                    str.append("null").append(defaultColor.toString());
                } else {
                    str.append(obj).append(defaultColor.toString());
                }
            }

        }

        return str.toString();

    }

    private String[] getStackTrace(Throwable exc) {

        String exception = exc.toString();

        for (StackTraceElement elem : exc.getStackTrace()) {
            exception += "\n\tat" + elem.toString();
        }

        return exception.split("\n");

    }

    public enum LogType {
        INFO, DEBUG, ERROR, FATAL, WARN;
    }


    public void setColor(ChatColor defaultColor) {

        this.defaultColor = defaultColor;

    }

    public ChatColor getColor() {
        return defaultColor;
    }
}
