package com.github.yeetmanlord.zeta_core.events;

import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.uitl.PlayerUtil;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LeftClickEvent implements Listener {

    @EventHandler
    public void onLeftClick(final PlayerInteractEvent event) {

        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            PlayerUtil util = ZetaCore.getPlayerMenuUtility(player);

            if (util.isTakingChatInput()) {
                util.setTakingChatInput(false);

                if (util.getMenuToInputTo() != null) {
                    AbstractGUIMenu menu = util.getMenuToInputTo();
                    menu.open();

                    NMSTitlePacketReflection title = NMSTitlePacketReflection.clear();

                    new NMSPlayerConnectionReflection(player).sendPacket(title);
                }
                else if (util.getInputObject() != null) {
                    util.setInputObject(null);
                    util.setInputObjectType(null);

                    NMSTitlePacketReflection title = NMSTitlePacketReflection.clear();

                    new NMSPlayerConnectionReflection(player).sendPacket(title);
                }

            }

        }

    }

}