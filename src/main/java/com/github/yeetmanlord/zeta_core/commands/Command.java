package com.github.yeetmanlord.zeta_core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;

public abstract class Command implements TabExecutor {

	protected ZetaPlugin main;

	protected HelpCommand help;

	protected List<ISubCommand> commands;

	public Command(HelpCommand help, ZetaPlugin main) {

		commands = new ArrayList<>();
		this.main = main;
		this.help = help;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		List<String> strings = new ArrayList<>();

		for (ISubCommand cmd : commands) {

			if (cmd.getIndex() == 0 && args.length == 1 && sender.hasPermission(cmd.getPermission())) {
				add(strings, cmd.getName(), args[0]);
			}

		}

		add(strings, "help", args[0]);

		return strings;

	}

	public static void add(List<String> list, String check, String currentArg) {

		if (StringUtil.startsWithIgnoreCase(check, currentArg)) {
			list.add(check);
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		if (this.main.initializedFinished()) {
			if (args.length > 0) {

				for (ISubCommand cmd : commands) {

					if (args[0].equalsIgnoreCase(cmd.getName()) && sender.hasPermission(cmd.getPermission())) {
						cmd.run(sender, args);
						return true;
					} else if (args[0].equalsIgnoreCase(cmd.getName()) && !sender.hasPermission(cmd.getPermission())) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have the permission to run the command " + this.getName() + " " + cmd.getName() + " (required permission: " + cmd.getPermission() + ")"));
						return true;
					}

				}

			}

			this.getHelp(sender);
		} else {
			sender.sendMessage(ChatColor.RED +  main.getPluginName() + " is still initializing");
		}
		return true;

	}

	public void getHelp(CommandSender sender) {

		sender.sendMessage(ChatColor.GOLD + "---------------");
		sender.sendMessage(ChatColor.RED + "Help page for " + getName());
		sender.sendMessage(ChatColor.RED + "Usage: " + this.getSyntax() + "\n" + getDesc() + "\n");

		for (ISubCommand cmd : commands) {

			if (sender.hasPermission(cmd.getPermission())) {
				this.help.sendHelpMessage(sender, cmd);
				sender.sendMessage(" ");
			}

		}

		sender.sendMessage(ChatColor.GOLD + "---------------");

	}

	protected abstract String getName();

	protected abstract String getDesc();

	protected abstract String getSyntax();

}
