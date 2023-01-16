package com.github.yeetmanlord.zeta_core.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public interface IMultiOptionSubCommand extends ISubCommand {

	List<ISubCommand> subCmds();

	String getParentCommand();

	List<String> tabComplete(CommandSender sender, String args[]);

	@Override
	default void run(CommandSender sender, String[] args) {

		if (args.length > 1) {

			for (ISubCommand cmd : subCmds()) {

				if (args[this.getIndex() + 1].equalsIgnoreCase(cmd.getName()) && sender.hasPermission(cmd.getPermission())) {
					cmd.run(sender, args);
				}
				else if (args[this.getIndex() + 1].equalsIgnoreCase(cmd.getName()) && !sender.hasPermission(cmd.getPermission())) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have the permission to run the command " + this.getParentCommand() + " " + this.getName() + " " + cmd.getName()));
				}

			}

		}
		else {
			this.getHelp(sender);
		}

	}

	default void getHelp(CommandSender sender) {

		sender.sendMessage(ChatColor.GOLD + "---------------");
		sender.sendMessage(ChatColor.RED + "Help page for " + getName());
		sender.sendMessage(ChatColor.RED + "Usage: " + this.getSyntax() + "\n" + getDesc());
		sender.sendMessage(" ");

		for (ISubCommand cmd : subCmds()) {

			if (sender.hasPermission(cmd.getPermission())) {
				sender.sendMessage(cmd.getHelpMessage(getParentCommand() + " " + this.getName()));
				sender.sendMessage(" ");
			}

		}

		sender.sendMessage(ChatColor.GOLD + "---------------");

	}

}
