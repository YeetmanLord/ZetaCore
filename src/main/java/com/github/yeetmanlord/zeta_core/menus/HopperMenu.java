package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;
import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;

public abstract class HopperMenu extends AbstractGUIMenu {

    public HopperMenu(PlayerUtil helper, String title, boolean shouldFill, AbstractGUIMenu parent) {

        super(helper, title, 5, shouldFill, parent);

    }

    public HopperMenu(PlayerUtil helper, String title, boolean shouldFill) {
        this(helper, title, shouldFill, null);
    }

    public HopperMenu(PlayerUtil helper, String title, AbstractGUIMenu parent) {
        this(helper, title, false, parent);
    }


    public HopperMenu(PlayerUtil helper, String title) {
        this(helper, title, false, null);
    }

    @Override
    protected void createCloser() {
        if (getParent() == null) {
            this.inv.setItem(slots - 2, this.makeItem(Material.BARRIER, "&cClose"));
        } else {
            this.inv.setItem(slots - 2, this.makeItem(Material.BARRIER, "&cBack"));
        }
    }

    @Override
    public void open() {

        menuUtil.setMenuToInputTo(null);
        menuUtil.setGUIMenu(false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(ZetaCore.getInstance(), () -> {
            owner.closeInventory();
            this.inv = Bukkit.createInventory(this, InventoryType.HOPPER, ChatColor.translateAlternateColorCodes('&', this.getMenuName()));
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
}
