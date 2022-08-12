package com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

/**
 * 
 * Used to override menu clicks to add custom functionality. This will inject
 * just before execution of
 * {@link AbstractGUIMenu#handleClick(org.bukkit.event.inventory.InventoryClickEvent)}
 * so once you have executed your code cancel the event to prevent the normal
 * code to run. If you did not execute your code or want the normal code inside
 * {@link AbstractGUIMenu#handleClick(org.bukkit.event.inventory.InventoryClickEvent)}
 * to run don't cancel the event.
 * 
 * @author YeetManLord
 *
 */
public class HandleMenuClickEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private boolean cancelled;

	@Override
	public HandlerList getHandlers() {

		return HANDLERS;

	}

	public static HandlerList getHandlerList() {

		return HANDLERS;

	}

	private final AbstractGUIMenu menu;

	public final InventoryClickEvent clickEvent;

	public HandleMenuClickEvent(AbstractGUIMenu menu, InventoryClickEvent event) {

		this.menu = menu;
		this.clickEvent = event;
		cancelled = false;

	}

	public AbstractGUIMenu getMenu() {

		return menu;

	}

	@Override
	public boolean isCancelled() {

		return cancelled;

	}

	@Override
	public void setCancelled(boolean cancelled) {

		this.cancelled = cancelled;

	}

}
