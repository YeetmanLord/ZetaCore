package com.github.yeetmanlord.zeta_core;

import java.util.*;
import java.util.function.BiFunction;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.zeta_core.data.LocalData;
import com.github.yeetmanlord.zeta_core.events.ChatEvent;
import com.github.yeetmanlord.zeta_core.events.HandleMenuInteractionEvent;
import com.github.yeetmanlord.zeta_core.events.LeftClickEvent;
import com.github.yeetmanlord.zeta_core.logging.ConsoleLogger;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import com.github.yeetmanlord.zeta_core.menus.animation.AnimatedItem;
import com.github.yeetmanlord.zeta_core.menus.animation.BuiltinAnimation;
import com.github.yeetmanlord.zeta_core.menus.animation.ComplexAnimatedItem;
import com.github.yeetmanlord.zeta_core.menus.animation.IAnimatable;
import com.github.yeetmanlord.zeta_core.menus.config.LocalSettingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;

/**
 * The core class of all Zeta series plugins. This registers all
 * {@link DataStorer DataStorers}, handles all SQL interactions, as well as
 * registers and connects all Zeta series plugins. This also contains helper
 * methods for Zeta plugins as well as APIs. Most of ZetaCore is strictly
 * internal use except for the api package and the sql package for when creating
 * custom objects that you want to add to a Zeta plugin's database
 *
 * @author YeetManLord
 * @apiNote <b>INTERNAL</b> means that this method/class/field etc. <b>SHOULD
 * NOT BE USED</b> by other plugins while <b>Usable by other
 * plugins</b> means that the method/class/field is encouraged to be
 * used. If there is no such plugin usage specified, the
 * method/class/field is not necessarily part of API usage but can
 * still be used if necessary. TL;DR Usable by other plugins is
 * intended to be used by APIs and are built to reflect that. All such
 * methods, classes, and fields will come with javadocs to help explain
 * what they are used for and in some cases providing examples
 * @zeta.usage Zeta Core Class. Usable by other plugins
 * @implNote I would suggest using async tasks for whenever reading and writing to a database. By default, when you are loading a plugin everything will be read asynchronously. When disabling everything will be saved synchronously.
 */
public class ZetaCore extends ZetaPlugin {

    private ConsoleLogger logger;

    private static ZetaCore instance;

    private final HashMap<String, ZetaPlugin> registeredPlugins = new HashMap<>();

    private LocalData localSettings;

    private final HashMap<Player, PlayerUtil> playerUtils = new HashMap<>();

    private final HashMap<ZetaPlugin, List<DataStorer>> dataHandlers = new HashMap<>();

    private final HashMap<ZetaPlugin, List<ISQLTableHandler<?>>> databaseDataHandlers = new HashMap<>();

    /**
     * Super config constructor must have a player utility as first slot and an AbstractGUIMenu parent
     */
    private final HashMap<ZetaPlugin, BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu>> superConfigs = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        logger = new ConsoleLogger(this, ChatColor.GREEN);
        this.registerDataStorers();
        logger.setDebugging(localSettings.get().getBoolean("should_debug"));
        logger.debug("Registering data handlers");
        logger.debug("Initializing ReflectionAPI");
        ReflectionApi.init(this);
        logger.info("ZetaCore framework is initializing");
        logger.debug("Reading from local files");
        localSettings.read();

        getServer().getPluginManager().registerEvents(new HandleMenuInteractionEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new LeftClickEvent(), this);

        getCommand("zeta_settings").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission("zeta.admin")) {
                if (sender instanceof Player) {
                    new LocalSettingsMenu(getPlayerMenuUtility((Player) sender)).open();
                }
                else {
                    sender.sendMessage(ChatColor.RED + "This command can only be run by players!");
                }
            }
            return true;
        });

        getCommand("test").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission("zeta.admin")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage("Running reflection tests");
                    boolean result = ReflectionApi.runReflectionTests(player);
                    if (result) {
                        player.sendMessage(ChatColor.GREEN + "Reflection tests passed!");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Reflection tests failed!");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "This command can only be run by players!");
                }
            }
            return true;
        });

    }

    @Override
    public ConsoleLogger getPluginLogger() {

        return logger;

    }

    @Override
    public void onDisable() {

        logger.info("ZetaCore framework is disabling");
        localSettings.write();

        if (this.isConnectedToDatabase()) {
            logger.debug("Disconnecting database client");
            localSettings.getClient().disconnect();
        }

    }

    @Override
    public String getPluginName() {

        return "ZetaCore";

    }

    /**
     * @param p Player
     * @return Returns a {@link PlayerUtil} of a certain player. If one doesn't
     * exist it creates a new one and returns that.
     * @zeta.usage SPECIAL. This is INTERNAL <i>but</i> this method still can be
     * used if you are interacting with this plugins method. I still do
     * not suggest messing with those menus though.
     */
    public PlayerUtil getPlayerMenuUtility(Player p) {

        PlayerUtil util;

        if (playerUtils.containsKey(p)) {
            return playerUtils.get(p);
        }

        util = new PlayerUtil(p);
        playerUtils.put(p, util);
        return util;

    }

    public void registerPlugin(ZetaPlugin plugin) {

        registeredPlugins.put(plugin.getName(), plugin);

    }

    public boolean pluginEnabled(String plugin) {

        return getPlugin(plugin) != null && getPlugin(plugin).isEnabled();

    }

    public ZetaPlugin getPlugin(String pluginName) {

        return registeredPlugins.get(pluginName);

    }

    public List<ZetaPlugin> getPlugins() {
        return new ArrayList<>(registeredPlugins.values());
    }

    public void registerDataHandler(DataStorer storer) {

        ZetaPlugin plugin = storer.getPlugin();
        logger.debug("Registering data handler for file, " + storer.getFileName() + ", for " + plugin.getName());

        if (storer instanceof ISQLTableHandler && ZetaCore.getInstance().localSettings.isInitialized()) {

            if (!databaseDataHandlers.containsKey(plugin)) {
                databaseDataHandlers.put(plugin, new ArrayList<>());
            }

            List<ISQLTableHandler<?>> dbHandlers = databaseDataHandlers.get(plugin);
            dbHandlers.add((ISQLTableHandler<?>) storer);
        }

        if (!dataHandlers.containsKey(plugin)) {
            dataHandlers.put(plugin, new ArrayList<>());
        }

        List<DataStorer> handlers = dataHandlers.get(plugin);

        handlers.add(storer);


    }

    public List<DataStorer> getDataHandlers(ZetaPlugin plugin) {

        return dataHandlers.get(plugin);

    }

    public List<ISQLTableHandler<?>> getDatabaseDataHandlers(ZetaPlugin plugin) {

        return databaseDataHandlers.get(plugin);

    }

    public HashMap<ZetaPlugin, List<ISQLTableHandler<?>>> getDatabaseDataHandlers() {

        return databaseDataHandlers;

    }

    public HashMap<ZetaPlugin, List<DataStorer>> getDataHandlers() {

        return dataHandlers;

    }

    @Override
    protected void registerDataStorers() {

        localSettings = new LocalData(this);
        localSettings.setup();
        logger.debug("Database and local settings initialized");

    }

    @Override
    public void onDataReadFinish() {

    }

    @Override
    public boolean initializedFinished() {
        return true;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.AIR);
    }

    public void readAll() {

        if (this.isConnectedToDatabase()) {

            databaseDataHandlers.values().forEach(list -> {

                list.forEach(ISQLTableHandler::readDB);

            });

            dataHandlers.values().forEach(list -> {

                list.forEach(DataStorer::read);

            });

        } else {

            dataHandlers.values().forEach(list -> {

                list.forEach(DataStorer::read);

            });

        }

    }

    public void saveAll() {

        if (this.isConnectedToDatabase()) {

            databaseDataHandlers.values().forEach(list -> {

                list.forEach(ISQLTableHandler::writeToDB);

            });

            dataHandlers.values().forEach(list -> {

                list.forEach(DataStorer::write);

            });

        } else {

            dataHandlers.values().forEach(list -> {

                list.forEach(DataStorer::write);

            });

        }

    }

    public boolean isConnectedToDatabase() {
        return localSettings.isInitialized() && localSettings.getClient() != null && localSettings.getClient().isConnected() && !localSettings.isFirstInit();
    }

    public void registerSuperConfig(ZetaPlugin plugin, BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu> superConfigFactory) {
        superConfigs.put(plugin, superConfigFactory);
    }

    public Optional<AbstractGUIMenu> getSuperConfig(ZetaPlugin plugin, PlayerUtil utility, LocalSettingsMenu.PluginEditMenu menu) {
        if (superConfigs.containsKey(plugin)) {
            BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu> constructorFunction = superConfigs.get(plugin);
            return Optional.of(constructorFunction.apply(utility, menu));
        }
        return Optional.empty();
    }

    public boolean hasSuperConfig(ZetaPlugin plugin) {
        return superConfigs.containsKey(plugin);
    }

    public static ZetaCore getInstance() {
        return instance;
    }

    public LocalData getLocalSettings() {
        return localSettings;
    }

    public void setLocalSettings(LocalData localSettings) {
        this.localSettings = localSettings;
    }
}
