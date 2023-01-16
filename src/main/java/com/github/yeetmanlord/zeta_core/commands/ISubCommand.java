package com.github.yeetmanlord.zeta_core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface ISubCommand {

    int getIndex();

    String getName();

    String getDesc();

    String getSyntax();

    String getPermission();

    void run(CommandSender sender, String[] args);

    default List<String> getTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<String>();
    }

    default String getHelpMessage(String cmd) {

        return ChatColor.RED + "Help for " + cmd + " " + getName() + "\n" + getSyntax() + "\n" + getDesc();

    }

}
