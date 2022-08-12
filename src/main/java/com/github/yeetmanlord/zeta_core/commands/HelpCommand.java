package com.github.yeetmanlord.zeta_core.commands;

import org.bukkit.command.CommandSender;

public class HelpCommand 
{
	private String command;
	
	public HelpCommand(String cmd)
	{
		command = cmd;
	}
	
	public void sendHelpMessage(CommandSender sender, ISubCommand command)
	{
		sender.sendMessage(command.getHelpMessage(this.command));
	}
	
	
	
}
