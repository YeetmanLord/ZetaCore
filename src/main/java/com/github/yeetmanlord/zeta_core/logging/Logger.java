package com.github.yeetmanlord.zeta_core.logging;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;

/**
 * Custom {@link ZetaPlugin} logger
 *
 * @author YeetManLord
 * @zeta.usage INTERNAL
 */
public class Logger {

    private ZetaPlugin plugin;

    private ChatColor defaultColor;

    private boolean debug;

    public Logger(ZetaPlugin plugin, ChatColor defaultColor) {

        this.plugin = plugin;
        this.defaultColor = defaultColor;
        debug = false;

    }

    public void setDebugging(boolean debug) {
        this.debug = debug;
    }

    public Logger(ZetaPlugin plugin) {

        this(plugin, ChatColor.WHITE);

    }

    public void info(Object... args) {

        Bukkit.getConsoleSender().sendMessage(defaultColor + "[" + plugin.getClass().getName() + "/INFO] " + ChatColor.translateAlternateColorCodes('&', convertToString(args)));

    }

    public void debug(Object... args) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage((defaultColor + "[" + plugin.getClass().getName() + "/DEBUG] " + ChatColor.translateAlternateColorCodes('&', convertToString(args))));
        }
    }

    public void error(Object... args) {

        Bukkit.getConsoleSender().sendMessage((ChatColor.RED + "[" + plugin.getClass().getName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));

    }

    public void fatal(Object... args) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args))));

    }

    public void error(Throwable throwable, Object... args) {

        Bukkit.getConsoleSender().sendMessage((ChatColor.RED + "[" + plugin.getClass().getName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + plugin.getClass().getName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void fatal(Throwable throwable, Object... args) {

        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void log(LogType type, @Nullable Throwable error, Object... args) {

        if (type == LogType.INFO) {
            info(args);
        } else if (type == LogType.DEBUG) {
            debug(args);
        } else if (type == LogType.ERROR) {

            if (error != null) {
                error(error, args);
            } else {
                error(args);
            }

        } else {

            if (error != null) {
                fatal(error, args);
            } else {
                fatal(args);
            }

        }

    }

    private String convertToString(Object... objects) {

        if (objects == null || objects.length == 0 || objects[0] == null) {
            return "null";
        }

        String str = "";

        for (int x = 0; x < objects.length; x++) {
            Object obj = objects[x];

            if (x == objects.length - 1) {
                str += obj.toString() + " ";
            } else {
                str += obj.toString();
            }

        }

        return str;

    }

    private String[] getStackTrace(Throwable exc) {

        String exception = exc.toString();

        for (StackTraceElement elem : exc.getStackTrace()) {
            exception += "\n\tat" + elem.toString();
        }

        return exception.split("\n");

    }

    public enum LogType {
        INFO, DEBUG, ERROR, FATAL;
    }


    public void setColor(ChatColor defaultColor) {

        this.defaultColor = defaultColor;

    }

}
