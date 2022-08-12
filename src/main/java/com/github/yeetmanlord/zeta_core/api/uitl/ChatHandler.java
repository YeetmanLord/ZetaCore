package com.github.yeetmanlord.zeta_core.api.uitl;

import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * 
 * Small handler class for chat inputs for commands
 * 
 * @author YeetManLord
 *
 */
public abstract class ChatHandler {

	public abstract void handleChat(AsyncPlayerChatEvent event);

}
