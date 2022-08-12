package com.github.yeetmanlord.zeta_core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.uitl.IChatInputable;
import com.github.yeetmanlord.zeta_core.api.uitl.PlayerUtil;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

public class ChatEvent implements Listener {

	@EventHandler
	public void onChatInput(final AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		PlayerUtil util = ZetaCore.getPlayerMenuUtitlity(player);
		AbstractGUIMenu menu = util.getMenuToInputTo();

		if (util.isTakingChatInput() && menu != null && menu instanceof IChatInputable) {
			((IChatInputable) menu).processChatInput(menu.getInputType(), event);

			NMSPlayerReflection entityPlayer = new NMSPlayerReflection(player);
			NMSPlayerConnectionReflection connection = entityPlayer.getPlayerConnection();
			NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection("CLEAR", ReflectionApi.getNMSClass("IChatBaseComponent").cast(null));

			connection.sendPacket(titlePacket);

			util.getMenuToInputTo().open();
			util.setTakingChatInput(false);

			event.setCancelled(true);
		}
		else if (util.isTakingChatInput() && util.getInputObject() != null) {
			util.getInputObject().processChatInput(util.getInputObjectType(), event);

			NMSPlayerReflection entityPlayer = new NMSPlayerReflection(player);
			NMSPlayerConnectionReflection connection = entityPlayer.getPlayerConnection();
			NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection("CLEAR", ReflectionApi.getNMSClass("IChatBaseComponent").cast(null));

			connection.sendPacket(titlePacket);

			util.setTakingChatInput(false);

			event.setCancelled(true);
		}

	}

	

}
