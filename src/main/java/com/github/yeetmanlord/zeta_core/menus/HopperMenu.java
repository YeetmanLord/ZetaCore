package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.util.PluginUtilities;
import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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

            if (ZetaCore.getInstance().getLocalSettings().isDevFeatures()) {
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
                        this.inv.setItem(i, stack);
                    }
                }
            }

            CommonEventFactory.onMenuSetItems(this);

            this.owner.openInventory(inv);
            this.menuUtil.setGUIMenu(true);
            this.menuUtil.setMenuToInputTo(this);
        });
    }
}
