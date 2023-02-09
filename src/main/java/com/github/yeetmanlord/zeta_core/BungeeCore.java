package com.github.yeetmanlord.zeta_core;

import java.util.*;
import java.util.function.BiFunction;

import com.github.yeetmanlord.zeta_core.data.BungeeDataStorer;
import com.github.yeetmanlord.zeta_core.data.BungeeLocalData;
import com.github.yeetmanlord.zeta_core.logging.BungeeLogger;
import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;
import com.github.yeetmanlord.zeta_core.menus.config.LocalSettingsMenu;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;
import org.bukkit.entity.Player;

import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;

import net.md_5.bungee.api.ChatColor;

/**
 * The core class of all Zeta series plugins. This registers all
 * {@link BungeeDataStorer DataStorers}, handles all SQL interactions, as well as
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
public class BungeeCore extends ZetaBungeePlugin implements IZetaCore<ZetaBungeePlugin, BungeeDataStorer> {

    private BungeeLogger logger;

    private static BungeeCore instance;

    private final HashMap<String, ZetaBungeePlugin> registeredPlugins = new HashMap<>();

    private BungeeLocalData localSettings;

    private final HashMap<Player, PlayerUtil> playerUtils = new HashMap<>();

    private final HashMap<ZetaBungeePlugin, List<BungeeDataStorer>> dataHandlers = new HashMap<>();

    private final HashMap<ZetaBungeePlugin, List<ISQLTableHandler<?>>> databaseDataHandlers = new HashMap<>();

    /**
     * Super config constructor must have a player utility as first slot and an AbstractGUIMenu parent
     */
    private final HashMap<ZetaBungeePlugin, BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu>> superConfigs = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        this.enabled = true;
        logger = new BungeeLogger(this, ChatColor.GREEN);
        this.registerDataStorers();
        logger.setDebugging(localSettings.get().getBoolean("should_debug"));
        logger.debug("Registering data handlers");
        logger.debug("Initializing ReflectionAPI");
        logger.info("ZetaCore framework is initializing");
        logger.debug("Reading from local files");
        localSettings.read();

    }

    @Override
    public BungeeLogger getPluginLogger() {

        return logger;

    }

    @Override
    public void onDisable() {

        this.enabled = false;
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

    public void registerPlugin(ZetaBungeePlugin plugin) {

        registeredPlugins.put(plugin.getPluginName(), plugin);

    }

    public ZetaBungeePlugin getPlugin(String pluginName) {

        return registeredPlugins.get(pluginName);

    }

    public List<ZetaBungeePlugin> getPlugins() {
        return new ArrayList<>(registeredPlugins.values());
    }

    public void registerDataHandler(BungeeDataStorer storer) {

        ZetaBungeePlugin plugin = storer.getPlugin();
        logger.debug("Registering data handler for file, " + storer.getFileName() + ", for " + plugin.getPluginName());

        if (storer instanceof ISQLTableHandler && localSettings.isInitialized()) {

            if (!databaseDataHandlers.containsKey(plugin)) {
                databaseDataHandlers.put(plugin, new ArrayList<>());
            }

            List<ISQLTableHandler<?>> dbHandlers = databaseDataHandlers.get(plugin);
            dbHandlers.add((ISQLTableHandler<?>) storer);
        }

        if (!dataHandlers.containsKey(plugin)) {
            dataHandlers.put(plugin, new ArrayList<>());
        }

        List<BungeeDataStorer> handlers = dataHandlers.get(plugin);

        handlers.add(storer);


    }

    public List<BungeeDataStorer> getDataHandlers(ZetaBungeePlugin plugin) {

        return dataHandlers.getOrDefault(plugin, new ArrayList<>());

    }

    public List<ISQLTableHandler<?>> getDatabaseDataHandlers(ZetaBungeePlugin plugin) {

        return databaseDataHandlers.getOrDefault(plugin, new ArrayList<>());

    }

    public HashMap<ZetaBungeePlugin, List<ISQLTableHandler<?>>> getDatabaseDataHandlers() {

        return databaseDataHandlers;

    }

    public HashMap<ZetaBungeePlugin, List<BungeeDataStorer>> getDataHandlers() {

        return dataHandlers;

    }

    @Override
    protected void registerDataStorers() {

        localSettings = new BungeeLocalData(this);
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

    public void readAll() {

        if (this.isConnectedToDatabase()) {

            databaseDataHandlers.values().forEach(list -> {

                list.forEach(ISQLTableHandler::readDB);

            });

            dataHandlers.values().forEach(list -> {

                list.forEach(BungeeDataStorer::read);

            });

        } else {

            dataHandlers.values().forEach(list -> {

                list.forEach(BungeeDataStorer::read);

            });

        }

    }

    public void saveAll() {

        if (this.isConnectedToDatabase()) {

            databaseDataHandlers.values().forEach(list -> {

                list.forEach(ISQLTableHandler::writeToDB);

            });

            dataHandlers.values().forEach(list -> {

                list.forEach(BungeeDataStorer::write);

            });

        } else {

            dataHandlers.values().forEach(list -> {

                list.forEach(BungeeDataStorer::write);

            });

        }

    }

    public boolean isConnectedToDatabase() {
        return localSettings.isInitialized() && localSettings.getClient() != null && localSettings.getClient().isConnected() && !localSettings.isFirstInit();
    }

    public void registerSuperConfig(ZetaBungeePlugin plugin, BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu> superConfigFactory) {
        superConfigs.put(plugin, superConfigFactory);
    }

    public Optional<AbstractGUIMenu> getSuperConfig(ZetaBungeePlugin plugin, PlayerUtil utility, LocalSettingsMenu.PluginEditMenu menu) {
        if (superConfigs.containsKey(plugin)) {
            BiFunction<PlayerUtil, AbstractGUIMenu, AbstractGUIMenu> constructorFunction = superConfigs.get(plugin);
            return Optional.of(constructorFunction.apply(utility, menu));
        }
        return Optional.empty();
    }

    public boolean hasSuperConfig(ZetaBungeePlugin plugin) {
        return superConfigs.containsKey(plugin);
    }

    public static BungeeCore getInstance() {
        return instance;
    }

    public BungeeLocalData getLocalSettings() {
        return localSettings;
    }

    @Override
    public SQLClient getSQLClient() {
        return localSettings.getClient();
    }
}
