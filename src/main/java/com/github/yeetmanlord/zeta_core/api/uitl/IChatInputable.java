package com.github.yeetmanlord.zeta_core.api.uitl;

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

	public void processChatInput(InputType type, AsyncPlayerChatEvent event);

}
