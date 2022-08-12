package com.github.yeetmanlord.zeta_core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public interface ISubCommand {

	public int getIndex();

	public String getName();

	public String getDesc();

	public String getSyntax();

	public String getPermission();

	public void run(CommandSender sender, String[] args);

	public default String getHelpMessage(String cmd) {

		return ChatColor.RED + "Help for " + cmd + " " + getName() + "\n" + getSyntax() + "\n" + getDesc();

	}

}
