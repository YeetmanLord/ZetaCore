package com.github.yeetmanlord.zeta_core;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.HandleMenuClickEvent;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

public class CommonEventFactory {

	public static HandleMenuClickEvent onMenuClicked(AbstractGUIMenu menu, InventoryClickEvent clickEvent) {

		HandleMenuClickEvent event = new HandleMenuClickEvent(menu, clickEvent);
		Bukkit.getPluginManager().callEvent(event);
		return event;

	}

	public static MenuSetItemsEvent onMenuSetItems(AbstractGUIMenu menu) {

		MenuSetItemsEvent event = new MenuSetItemsEvent(menu);
		Bukkit.getPluginManager().callEvent(event);
		return event;

	}

}
