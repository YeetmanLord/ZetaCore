package com.github.yeetmanlord.zeta_core.menus.config;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.uitl.IChatInputable;
import com.github.yeetmanlord.zeta_core.api.uitl.InputType;
import com.github.yeetmanlord.zeta_core.api.uitl.PlayerUtil;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LocalSettingsMenu extends AbstractGUIMenu implements IChatInputable {

    public LocalSettingsMenu(PlayerUtil helper) {
        super(helper, "Local Settings (Not synced)", 9);
    }

    @Override
    public void setItems() {
        this.inv.setItem(3, makeItem(Material.REDSTONE, "&cDebug Mode", "&7" + ZetaCore.getBooleanColor(ZetaCore.INSTANCE.localSettings.should_debug)));
        this.inv.setItem(5, makeItem(Material.REDSTONE_BLOCK, "&cDisable Plugins", "&7Left Click to disable zeta plugins", "&7Right Click to enable zeta plugins"));
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 3:
                ZetaCore.INSTANCE.localSettings.should_debug = !ZetaCore.INSTANCE.localSettings.should_debug;
                this.owner.sendMessage(ChatColor.GREEN + "Debug mode set to " + ZetaCore.INSTANCE.localSettings.should_debug);
                this.open();
                break;
            case 5:
                if (e.isLeftClick()) {
                    this.menuUtil.setMenuToInputTo(this);
                    this.menuUtil.setTakingChatInput(true);
                    this.sendTitlePackets(ChatColor.GOLD + "Enter the plugin to disable");
                    this.setInputType(InputType.STRING);
                    this.owner.closeInventory();
                } else if (e.isRightClick()) {
                    this.menuUtil.setMenuToInputTo(this);
                    this.menuUtil.setTakingChatInput(true);
                    this.sendTitlePackets(ChatColor.GOLD + "Enter the plugin to enable");
                    this.setInputType(InputType.STRING1);
                    this.owner.closeInventory();
                }
        }
    }

    @Override
    public void processChatInput(InputType type, AsyncPlayerChatEvent event) {
        switch (type) {
            case STRING:
                ZetaCore.INSTANCE.localSettings.disabled_plugins.add(event.getMessage());
                this.owner.sendMessage(ChatColor.RED + "Disabled plugin " + event.getMessage());
            case STRING1:
                ZetaCore.INSTANCE.localSettings.disabled_plugins.remove(event.getMessage());
                this.owner.sendMessage(ChatColor.GREEN + "Enabled plugin " + event.getMessage());
        }
    }
}
