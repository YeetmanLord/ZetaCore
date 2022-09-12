package com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

/**
 * 
 * This event is used to override how menus set items. This can be used to
 * customize built-in menus as well as add custom functionality to menus and
 * items.
 * 
 * @zeta.example <code> public void event (MenuSetItemsEvent event) {<br>
 *          <br>
 *          <ul>
 *          if (event.getMenu() instanceof MenuYouWantToModify) { <br>
 *          <br>
 *          <ul>
 *          event.setItem(12, new ItemStack(Material.DIAMOND)); <br>
 *          <br>
 *          </ul>
 *          } <br>
 *          </ul>
 *          } </code>
 * 
 * @author YeetManLord
 *
 */
public class MenuSetItemsEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {

		return HANDLERS;

	}

	public static HandlerList getHandlerList() {

		return HANDLERS;

	}

	private final AbstractGUIMenu menu;

	public MenuSetItemsEvent(AbstractGUIMenu menu) {

		this.menu = menu;

	}

	public void setItem(int slot, ItemStack stack) {

		menu.getInventory().setItem(slot, stack);

	}

	public ItemStack getStack(int slot) {

		return menu.getInventory().getItem(slot);

	}

	public Inventory menuInv() {

		return menu.getInventory();

	}

	public AbstractGUIMenu getMenu() {

		return menu;

	}

}
