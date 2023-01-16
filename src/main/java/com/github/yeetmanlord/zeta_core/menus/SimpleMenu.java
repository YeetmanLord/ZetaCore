package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SimpleMenu extends AbstractGUIMenu {

    public SimpleMenu(PlayerUtil menuUtil) {
        super(menuUtil, "&6Test Menu", 54); // Supports formatting codes
    }

    @Override
    public void setItems() {
        // Set items here
        this.inv.setItem(0, makeItem(Material.DIAMOND, "&bDiamond"));
        this.inv.setItem(1, makeItem(Material.GOLD_INGOT, "&6Gold"));
        this.inv.setItem(2, makeItem(Material.IRON_INGOT, "&fIron"));
        this.inv.setItem(3, makeItem(Material.COAL, "&8Coal"));
        this.inv.setItem(4, makeItem(Material.REDSTONE, "&cRedstone"));
        this.inv.setItem(5, makeItem(Material.BARRIER, "&cBarrier", "&7Click me to get a barrier")); // Supports lore and formatting codes
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        // Handle clicks here
        // If the player clicks on an item, this method will be called
        switch (e.getSlot()) {
            case 0:
                this.owner.getInventory().addItem(makeItem(Material.DIAMOND, "&bDiamond"));
                break;
            case 1:
                this.owner.getInventory().addItem(makeItem(Material.GOLD_INGOT, "&6Gold"));
                break;
            case 2:
                this.owner.getInventory().addItem(makeItem(Material.IRON_INGOT, "&fIron"));
                break;
            case 3:
                this.owner.getInventory().addItem(makeItem(Material.COAL, "&8Coal"));
                break;
            case 4:
                this.owner.getInventory().addItem(makeItem(Material.REDSTONE, "&cRedstone"));
                break;
            case 5:
                this.owner.getInventory().addItem(makeItem(Material.BARRIER, "&cBarrier"));
                break;
        }
    }

    @Override
    public void handleClickAnywhere(InventoryClickEvent e) {
        // However, if you want to handle clicks anywhere in the inventory, use this method
        super.handleClickAnywhere(e);
        if (e.getSlot() == 12) {
            this.owner.getInventory().addItem(makeItem(Material.DIAMOND, "&4Secret Diamond"));
        }
    }
}
