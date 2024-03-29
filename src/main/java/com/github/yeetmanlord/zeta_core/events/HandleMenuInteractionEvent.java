package com.github.yeetmanlord.zeta_core.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.PlayerInventory;

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.HandleMenuClickEvent;
import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import com.github.yeetmanlord.zeta_core.menus.IPlayerInventoryInputable;

public class HandleMenuInteractionEvent implements Listener {

	@EventHandler
	public void menuClicked(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player && event.getClickedInventory() != null) {
			Player player = (Player) event.getWhoClicked();
			InventoryHolder holder = event.getClickedInventory().getHolder();

			if (holder == null) {
				PlayerUtil util = ZetaCore.getInstance().getPlayerMenuUtility(player);
				if (util.isGUIMenu() && !util.isTakingChatInput()) {
					if (util.getMenuToInputTo() != null) {
						holder = util.getMenuToInputTo();
					}
				}
			}

			if (holder instanceof AbstractGUIMenu) {
				AbstractGUIMenu menu = (AbstractGUIMenu) holder;
				HandleMenuClickEvent handleEvent = CommonEventFactory.onMenuClicked(menu, event);

				if (!handleEvent.isCancelled()) {
					if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
						menu.handleClick(event);
					}
					menu.handleClickAnywhere(event);
				}

				event.setCancelled(true);
			}
			else if (event.getClickedInventory() instanceof PlayerInventory) {
				PlayerUtil util = ZetaCore.getInstance().getPlayerMenuUtility(player);

				if (util.isGUIMenu()) {
					event.setCancelled(true);

					if (util.getMenuToInputTo() instanceof IPlayerInventoryInputable) {

						IPlayerInventoryInputable inputable = (IPlayerInventoryInputable) util.getMenuToInputTo();

						if (inputable.isInputing()) {
							inputable.playerInventoryInput(event);
						}

					}

				}

			}

		}

	}

	@EventHandler
	public void onMenuClosed(InventoryCloseEvent event) {

		Player player = (Player) event.getPlayer();
		PlayerUtil util = ZetaCore.getInstance().getPlayerMenuUtility(player);
		if (util.getMenuToInputTo() != null && !util.isTakingChatInput()) {
			boolean result = util.getMenuToInputTo().onClose();
			if (result) {
				return;
			}
		}

		if (!util.isTakingChatInput()) {
			util.setMenuToInputTo(null);
			util.setGUIMenu(false);
		}

	}

}
