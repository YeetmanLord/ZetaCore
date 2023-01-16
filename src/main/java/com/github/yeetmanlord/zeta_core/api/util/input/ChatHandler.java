package com.github.yeetmanlord.zeta_core.api.util.input;

import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * 
 * Small handler class for chat inputs for commands
 * 
 * @author YeetManLord
 *
 */
public abstract class ChatHandler {

	/**
	 * Callback for when a player sends a chat message. Event implementation is on a per-plugin basis
	 * @param event The event that was fired
	 */
	public abstract void handleChat(AsyncPlayerChatEvent event);

}
