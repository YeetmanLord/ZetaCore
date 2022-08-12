package com.github.yeetmanlord.zeta_core.menus;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface IPlayerInventoryInputable {

	public boolean isInputing();

	public void playerInventoryInput(InventoryClickEvent e);

}
