package com.github.yeetmanlord.zeta_core.api.util.input;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

/**
 * 
 * Specifies that a command or {@link AbstractGUIMenu GUI Menu} should receive
 * chat input
 * 
 * @author YeetManLord
 *
 */
public interface IChatInputable {

	/**
	 * Callback for when a player sends a chat message while a command or {@link AbstractGUIMenu} is waiting for input
	 * @param event The event that was fired
	 */
	void processChatInput(InputType type, AsyncPlayerChatEvent event);

}
