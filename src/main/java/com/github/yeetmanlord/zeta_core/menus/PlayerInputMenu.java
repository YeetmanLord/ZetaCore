package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;

public class PlayerInputMenu extends AbstractGUIMenu implements IPlayerInventoryInputable {

    public AbstractGUIMenu parent;

    public PlayerInputMenu(AbstractGUIMenu parent) {

        super(parent.menuUtil, "&7Select an item", 9, parent);
        this.parent = parent;

    }

    @Override
    public boolean isInputing() {

        return true;

    }

    @Override
    public void playerInventoryInput(InventoryClickEvent e) {

        if (this.parent instanceof IPlayerInputMenuInputable) {
            ((IPlayerInputMenuInputable) parent).setInputValuesFromInputMenu(e);
            this.inv.setItem(4, new ItemStack(e.getCurrentItem().getType()));
            this.parent.open();
        }

    }

    @Override
    public void setItems() {

        makeBoarder();
        this.inv.setItem(7, makeItem(Material.BARRIER, "&cBack"));

    }

    @Override
    public void handleClick(InventoryClickEvent e) {

        if (e.getCurrentItem().getType() == Material.BARRIER) {
            close();
        }

    }

    @Override
    public void open() {

        menuUtil.setMenuToInputTo(null);
        Bukkit.getScheduler().scheduleSyncDelayedTask(ZetaCore.INSTANCE, () -> {
            owner.closeInventory();
            this.inv = Bukkit.createInventory(this, InventoryType.DISPENSER, ChatColor.translateAlternateColorCodes('&', this.getMenuName()));
            this.setItems();

            if (this.shouldFill) {
                this.makeFiller();
            }

            MenuSetItemsEvent event = CommonEventFactory.onMenuSetItems(this);
            this.owner.openInventory(inv);
            this.menuUtil.setGUIMenu(true);
            this.menuUtil.setMenuToInputTo(this);
        });
    }

    @Override
    public void makeBoarder() {

        for (int addon = 0; addon < 7; addon += 6) {

            for (int x = 0; x < 3; x++) {
                this.inv.setItem(x + addon, FILLER);
            }

        }

        this.inv.setItem(3, FILLER);
        this.inv.setItem(5, FILLER);

    }

}
