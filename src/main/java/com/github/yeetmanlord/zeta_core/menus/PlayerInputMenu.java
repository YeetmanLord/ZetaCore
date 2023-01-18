package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.util.PluginUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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

        if (this.parent instanceof IPlayerInputMenuInputable && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
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
        menuUtil.setGUIMenu(false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(ZetaCore.getInstance(), () -> {
            owner.closeInventory();
            this.inv = Bukkit.createInventory(this, InventoryType.DISPENSER, ChatColor.translateAlternateColorCodes('&', this.getMenuName()));
            this.setItems();

            if (this.shouldFill) {
                this.makeFiller();
            }

            if (ZetaCore.getInstance().getLocalSettings().isShouldDebug()) {
                for (int i = 0; i < this.slots; ++i) {
                    ItemStack stack = this.inv.getItem(i);
                    if (stack != null && stack.getType() != Material.AIR) {
                        ItemMeta meta = stack.getItemMeta();
                        if (meta.hasDisplayName()) {
                            String name = meta.getDisplayName();
                            name += " §7(Slot" + i + ")";
                            meta.setDisplayName(name);
                        } else {
                            List<String> lore = PluginUtilities.getLore(meta);
                            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Slot: " + i));
                            meta.setLore(lore);
                        }
                    }
                }
            }

            CommonEventFactory.onMenuSetItems(this);

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
