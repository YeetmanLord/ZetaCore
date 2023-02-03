package com.github.yeetmanlord.zeta_core.logging;

import com.github.yeetmanlord.zeta_core.ZetaBungeePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

public class BungeeLogger implements IPluginLogger {

    private ZetaBungeePlugin plugin;

    private ChatColor defaultColor;

    private boolean debug;

    public BungeeLogger(ZetaBungeePlugin plugin, ChatColor defaultColor) {

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

    public BungeeLogger(ZetaBungeePlugin plugin) {

        this(plugin, ChatColor.WHITE);

    }

    public void info(Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage(defaultColor + "[" + plugin.getClass().getSimpleName() + "/INFO] " + ChatColor.translateAlternateColorCodes('&', convertToString(args)));

    }

    public void debug(Object... args) {
        if (debug) {
            ProxyServer.getInstance().getConsole().sendMessage((defaultColor + "[" + plugin.getClass().getSimpleName() + "/DEBUG] " + ChatColor.translateAlternateColorCodes('&', convertToString(args))));
        }
    }

    public void warn(Object... args) {
        ProxyServer.getInstance().getConsole().sendMessage((ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
    }

    public void warn(Throwable throwable, Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage((ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.GOLD + "[" + plugin.getClass().getSimpleName() + "/WARN] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void error(Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage((ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));

    }

    public void fatal(Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args))));

    }

    public void error(Throwable throwable, Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage((ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args)))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "/ERROR] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void fatal(Throwable throwable, Object... args) {

        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', convertToString(args))));
        String[] exc = getStackTrace(throwable);

        for (String string : exc) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.DARK_RED + "[" + plugin.getClass().getSimpleName() + "/FATAL] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)));
        }

    }

    public void setColor(ChatColor defaultColor) {

        this.defaultColor = defaultColor;

    }

    public ChatColor getColor() {
        return defaultColor;
    }

}
