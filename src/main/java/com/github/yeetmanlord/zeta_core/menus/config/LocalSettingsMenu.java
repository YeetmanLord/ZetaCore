package com.github.yeetmanlord.zeta_core.menus.config;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.api.util.PluginUtilities;
import com.github.yeetmanlord.zeta_core.api.util.input.IChatInputable;
import com.github.yeetmanlord.zeta_core.api.util.input.InputType;
import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHolder;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;
import com.github.yeetmanlord.zeta_core.sql.impl.SQLTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class LocalSettingsMenu extends AbstractGUIMenu {

    public LocalSettingsMenu(PlayerUtil helper) {
        super(helper, "Zeta settings.", 27);
    }

    @Override
    public void setItems() {
        this.inv.setItem(11, makeItem(Material.REDSTONE_BLOCK, "&cDebug Logging", "", "&aDetermine whether ZetaCore should add in debug logs to console.", "&aThis can help when trying to find problems or reporting bugs", "&6" + PluginUtilities.getBooleanColor(ZetaCore.getInstance().localSettings.shouldDebug)));
        this.inv.setItem(13, makeItem(Material.REDSTONE, "&6Configure Plugins", "", "&aConfigure different aspects of Zeta series plugins.", "&aincluding disabling them, enabling debug logging, or", "&adetermining if they should sync to the database.", "&6If the option is available, you can also configure", "&6other aspects of that plugin."));
        this.inv.setItem(15, makeItem(Material.ENDER_CHEST, "&6Configure MySQL", "", "&aConfigure database connection settings and", "&adisable, enable, or sync data with a MySQL database."));
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 11:
                ZetaCore.getInstance().localSettings.shouldDebug = !ZetaCore.getInstance().localSettings.shouldDebug;
                this.refresh();
                break;

            case 13:
                new PluginManagementMenu(this).open();
                break;

            case 15:
                new SQLConfigurationMenu(this).open();
                break;
        }
    }


    public static class SQLConfigurationMenu extends AbstractGUIMenu implements IChatInputable {

        public SQLConfigurationMenu(LocalSettingsMenu parent) {

            super(parent.menuUtil, "&aConfigure MySQL", 54, parent);

        }

        @Override
        public void setItems() {

            createCloser();

            this.inv.setItem(19, this.makeSkullWithCustomTexture("&aSet hostname", new String[]{"", "&6Set the IP address of the MySQL database", "&aDefault: localhost", "&aCurrent: " + ZetaCore.getInstance().localSettings.ipAddress}, "eyJ0ZXh0dXJlcyIgOiB7IlNLSU4iIDogeyJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODEzYzlkOWJlNWRhNjFmMDJjYzBiZjI3MTcwNWU4NTZlYmUwMjc5Mjc0MGZlZmI0MTE5OWFmYmQyNzExYTQ5YSJ9fX0="));
            this.inv.setItem(21, this.makeItem(VersionMaterial.OAK_SIGN.getMaterial(), "&aSet username", "", "&6Set the username to login to the database", "&aDefault: root", "&aCurrent: " + ZetaCore.getInstance().localSettings.username));
            this.inv.setItem(23, this.makeItem(Material.OBSIDIAN, "&aSet login password", "", "&cType in NO to set the password to empty"));
            this.inv.setItem(25, this.makeItem(Material.CHEST, "&aSet database name", "", "&aCurrent: " + ZetaCore.getInstance().localSettings.databaseName));

            this.inv.setItem(31, this.makeItem(VersionMaterial.COMMAND_BLOCK.getMaterial(), "", "&aSet port", "&6Set the database's connection port", "&aDefault: 3306", "&aCurrrent: " + ZetaCore.getInstance().localSettings.port));

            if (ZetaCore.getInstance().localSettings.initialized) {
                this.inv.setItem(50, this.makeItemFromExisting(VersionMaterial.RED_WOOL.getItem(), "&cDeactivate Database", "&cWill stop syncing with database"));
            } else {
                this.inv.setItem(51, this.makeItemFromExisting(VersionMaterial.GREEN_WOOL.getItem(), "&aOne Time Sync", "", "&aOnce you click this, the server will attempt to", "&aConnect to the database. All data in this server will be updated", "&aThen this server will disconnect from the database.", "&cImportant: &aPlugins marked to not sync won't sync doing this."));
                this.inv.setItem(50, this.makeItemFromExisting(VersionMaterial.LIME_WOOL.getItem(), "&aInitialize Database", "", "&aOnce you click this, the server will attempt to", "&aConnect to the database.", "&aIf no data exists in the database for this plugin, it", "&aWill write all the current settings. Otherwise it will sync data"));
            }

        }

        @Override
        public void handleClick(InventoryClickEvent e) {

            ItemStack stack = e.getCurrentItem();
            Material mat = e.getCurrentItem().getType();

            switch (mat) {

                case OBSIDIAN:
                    this.setInputType(InputType.STRING3);
                    this.menuUtil.setGUIMenu(true);
                    this.menuUtil.setMenuToInputTo(this);
                    this.menuUtil.setTakingChatInput(true);
                    owner.closeInventory();
                    this.sendTitlePackets("&6Input the database user's password");
                    break;

                case CHEST:
                    this.setInputType(InputType.STRING2);
                    this.menuUtil.setGUIMenu(true);
                    this.menuUtil.setMenuToInputTo(this);
                    this.menuUtil.setTakingChatInput(true);
                    owner.closeInventory();
                    this.sendTitlePackets("&6Input the database name");
                    break;

                case BARRIER:
                    this.close();
                    break;

                default:
                    if (mat.name().contains("WOOL") && e.getSlot() != 51) {
                        this.close();
                        if (ZetaCore.getInstance().localSettings.initialized) {
                            ZetaCore.getInstance().localSettings.initialized = false;
                            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                                ZetaCore.getInstance().localSettings.client.disconnect();

                                if (!ZetaCore.getInstance().localSettings.client.isConnected()) {
                                    owner.sendMessage(ChatColor.GREEN + "DISCONNECTION SUCCESSFUL!");
                                } else {
                                    owner.sendMessage(ChatColor.DARK_RED + "AN ERROR STOPPED THE CLIENT FROM DISCONNECTING! CHECK CONSOLE LOGS FOR MORE INFORMATION!");
                                }

                                ZetaCore.getInstance().localSettings.client = null;
                            });
                        } else {
                            ZetaCore.getInstance().localSettings.initialized = true;

                            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                                ZetaCore.getInstance().localSettings.client = new SQLClient(ZetaCore.getInstance().localSettings.ipAddress, ZetaCore.getInstance().localSettings.username, ZetaCore.getInstance().localSettings.password, ZetaCore.getInstance().localSettings.port, ZetaCore.getInstance().localSettings.databaseName);

                                LocalDateTime timeout = LocalDateTime.now().plus(3, ChronoUnit.SECONDS);
                                while (!ZetaCore.getInstance().localSettings.client.isConnected()) {
                                    if (LocalDateTime.now().isAfter(timeout)) break;
                                }
                                if (ZetaCore.getInstance().localSettings.client.isConnected()) {
                                    owner.sendMessage(ChatColor.GREEN + "CONNECTION SUCCESSFUL!");
                                    owner.sendMessage(ChatColor.GREEN + "Restart or reload the server to ensure correct functionality.");
                                } else {
                                    owner.sendMessage(ChatColor.RED + "CONNECTION FAILED! Check that the credentials you entered are correct!");
                                }
                            });
                        }
                        // TODO: FIX SQL NOT SYNCING PROPERLY. IT SEEMS THAT THE CLIENT IS NULL OR ISN'T CONNECTED FOR SOME REASON.
                    } else if (e.getSlot() == 51) {
                        this.close();
                        Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                            ZetaCore.getInstance().localSettings.initialized = true;
                            ZetaCore.getInstance().localSettings.client = new SQLClient(ZetaCore.getInstance().localSettings.ipAddress, ZetaCore.getInstance().localSettings.username, ZetaCore.getInstance().localSettings.password, ZetaCore.getInstance().localSettings.port, ZetaCore.getInstance().localSettings.databaseName);

                            LocalDateTime timeout = LocalDateTime.now().plus(3, ChronoUnit.SECONDS);
                            while (!ZetaCore.getInstance().localSettings.client.isConnected()) {
                                if (LocalDateTime.now().isAfter(timeout)) break;
                            }
                            if (ZetaCore.getInstance().localSettings.client.isConnected()) {
                                owner.sendMessage(ChatColor.GREEN + "CONNECTION SUCCESSFUL! Beginning data sync...");
                                for (ZetaPlugin plugin : ZetaCore.getInstance().getDataHandlers().keySet()) {
                                    List<DataStorer> dataStorerList = ZetaCore.getInstance().getDataHandlers(plugin);
                                    if (plugin.getSettings().isSyncDatabase()) {
                                        for (DataStorer storer : dataStorerList) {
                                            if (storer instanceof ISQLTableHandler && !(storer instanceof ISQLTableHolder)) {
                                                ISQLTableHandler<?> tableHandler = (ISQLTableHandler<?>) storer;
                                                tableHandler.setTable(new SQLTable(tableHandler.getTableName(), ZetaCore.getInstance().localSettings.client.handler));
                                                tableHandler.getTable().setPrimaryKey(tableHandler.getPrimaryKey());
                                                tableHandler.getTable().setColumns(tableHandler.getColumns(ZetaCore.getInstance().localSettings.client.handler));
                                                ((ISQLTableHandler<?>) storer).readDB();
                                            } else if (storer instanceof ISQLTableHolder) {
                                                ((ISQLTableHolder) storer).syncDB(ZetaCore.getInstance().localSettings.client.handler);
                                            }
                                        }
                                    }
                                }
                                owner.sendMessage(ChatColor.GREEN + "Sync complete! It is recommended that you restart your server now to ensure. This will allow all changes to save and properly reload.");
                                ZetaCore.getInstance().localSettings.client.disconnect();
                                ZetaCore.getInstance().localSettings.client = null;
                            } else {
                                owner.sendMessage(ChatColor.RED + "CONNECTION FAILED! Check that the credentials you entered are correct!");
                            }
                            ZetaCore.getInstance().localSettings.initialized = false;
                        });
                    } else if (mat == VersionMaterial.COMMAND_BLOCK.getMaterial()) {
                        this.setInputType(InputType.NUMBER);
                        this.menuUtil.setGUIMenu(true);
                        this.menuUtil.setMenuToInputTo(this);
                        this.menuUtil.setTakingChatInput(true);
                        owner.closeInventory();
                        this.sendTitlePackets("&6Input the database IP port", "", "&6Default: 3306");
                    } else if (mat == VersionMaterial.OAK_SIGN.getMaterial()) {
                        this.setInputType(InputType.STRING1);
                        this.menuUtil.setGUIMenu(true);
                        this.menuUtil.setMenuToInputTo(this);
                        this.menuUtil.setTakingChatInput(true);
                        owner.closeInventory();
                        this.sendTitlePackets("&6Input the username to login to the database with", "", "&6Default: root");
                    } else if (mat == VersionMaterial.PLAYER_HEAD.getMaterial()) {
                        this.setInputType(InputType.STRING);
                        this.menuUtil.setGUIMenu(true);
                        this.menuUtil.setMenuToInputTo(this);
                        this.menuUtil.setTakingChatInput(true);
                        owner.closeInventory();
                        this.sendTitlePackets("&6Input the database IP", "", "&6Default: localhost");
                    }

                    break;
            }

        }

        @Override
        public void processChatInput(InputType type, AsyncPlayerChatEvent event) {

            switch (type) {

                case NUMBER:
                    try {
                        int port = Integer.valueOf(event.getMessage());

                        if (port > 0 && port < 65536) {
                            ZetaCore.getInstance().localSettings.port = port;
                        } else {
                            this.owner.sendMessage(ChatColor.RED + "Invalid port! Ports range from 1-65535");
                        }

                    } catch (NumberFormatException exc) {
                        this.owner.sendMessage(ChatColor.RED + "That is not a real number");
                    }
                    break;

                case STRING:
                    ZetaCore.getInstance().localSettings.ipAddress = event.getMessage();
                    owner.sendMessage(ChatColor.GREEN + "Setting hostname to " + event.getMessage());
                    break;

                case STRING1:
                    ZetaCore.getInstance().localSettings.username = event.getMessage();
                    owner.sendMessage(ChatColor.GREEN + "Setting username to " + event.getMessage());
                    break;

                case STRING2:
                    ZetaCore.getInstance().localSettings.databaseName = event.getMessage();
                    owner.sendMessage(ChatColor.GREEN + "Setting database name to " + event.getMessage());
                    break;

                case STRING3:
                    if (event.getMessage().equals("NO")) {
                        owner.sendMessage(ChatColor.GREEN + "Clearing password");
                        ZetaCore.getInstance().localSettings.password = "";
                    } else {
                        ZetaCore.getInstance().localSettings.password = event.getMessage();
                        owner.sendMessage(ChatColor.GREEN + "Setting password to " + event.getMessage());
                    }
                    break;

                default:
                    break;
            }

        }

    }

    public static class PluginManagementMenu extends AbstractGUIMenu {

        private HashMap<Integer, ZetaPlugin> slotToPlugin;

        public PluginManagementMenu(AbstractGUIMenu parent) {
            super(parent.getPlayerUtil(), "Manage Plugins", 54, parent);
            slotToPlugin = new HashMap<>();
        }

        @Override
        public void setItems() {
            int index = 0;
            for (ZetaPlugin plugin : ZetaCore.getInstance().getPlugins()) {
                inv.setItem(index, plugin.getIcon());
                slotToPlugin.put(index, plugin);
                index++;
            }
            createCloser();
        }

        @Override
        public void handleClick(InventoryClickEvent e) {
            if (e.getSlot() == 49) {
                this.close();
            } else if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                new PluginEditMenu(slotToPlugin.get(e.getSlot()), this).open();
            }
        }

    }

    public static class PluginEditMenu extends AbstractGUIMenu {

        private ZetaPlugin plugin;

        public PluginEditMenu(ZetaPlugin plugin, PluginManagementMenu parent) {
            super(parent.getPlayerUtil(), "Manage " + plugin.getPluginLogger().getColor() + plugin.getPluginName(), 36, parent);
            this.plugin = plugin;
        }

        @Override
        public void setItems() {
            if (!ZetaCore.getInstance().hasSuperConfig(this.plugin)) {
                this.inv.setItem(11, makeItem(Material.REDSTONE_BLOCK, "&cDebug Logging", "", "&aDetermine whether this plugin should add in debug logs to console.", "&aThis can help when trying to find problems or reporting bugs", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isDebugLogging())));
                this.inv.setItem(13, makeItem(Material.BARRIER, "&6Disable", "", "&aDisables or enables this plugin.", "&aAfterwards you must restart the server to make the changes take effect.", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isDisabled())));
                this.inv.setItem(15, makeItem(Material.ENDER_CHEST, "&6Sync with database", "", "&aDetermines whether this plugin should sync with the database.", "&aThis overrides all over forced syncs including one-time syncs", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isSyncDatabase())));
            }
            else {
                this.inv.setItem(10, makeItem(Material.REDSTONE_BLOCK, "&cDebug Logging", "", "&aDetermine whether this plugin should add in debug logs to console.", "&aThis can help when trying to find problems or reporting bugs", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isDebugLogging())));
                this.inv.setItem(12, makeItem(Material.BARRIER, "&6Disable", "", "&aDisables or enables this plugin.", "&aAfterwards you must restart the server to make the changes take effect.", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isDisabled())));
                this.inv.setItem(14, makeItem(Material.ENDER_CHEST, "&6Sync with database", "", "&aDetermines whether this plugin should sync with the database.", "&aThis overrides all over forced syncs including one-time syncs", "&6" + PluginUtilities.getBooleanColor(this.plugin.getSettings().isSyncDatabase())));
                this.inv.setItem(16, makeItem(VersionMaterial.REDSTONE_TORCH.getMaterial(), "&6Configure Settings", "", "&aOpens this plugin's super config menu, so you can modify it's settings."));
            }
            createCloser();
        }

        @Override
        public void handleClick(InventoryClickEvent e) {
            switch (e.getSlot()) {
                case 10:
                case 11:
                    this.plugin.getSettings().setDebugLogging(!this.plugin.getSettings().isDebugLogging());
                    this.refresh();
                    break;

                case 12:
                case 13:
                    this.plugin.getSettings().setDisabled(!this.plugin.getSettings().isDisabled());
                    this.refresh();
                    break;

                case 14:
                case 15:
                    this.plugin.getSettings().setSyncDatabase(!this.plugin.getSettings().isSyncDatabase());
                    this.refresh();
                    break;

                case 16:
                    Optional<AbstractGUIMenu> menuOptional = ZetaCore.getInstance().getSuperConfig(this.plugin, this.menuUtil, this);
                    if (menuOptional.isPresent()) {
                        AbstractGUIMenu menu = menuOptional.get();
                        menu.open();
                    }
                    break;

                case 31:
                    this.close();
                    break;
            }
        }
    }

}
