package com.github.yeetmanlord.zeta_core;

import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.data.LocalData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yeetmanlord.zeta_core.logging.ConsoleLogger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Extension of {@link JavaPlugin} and is used to add some ease of use
 * functionality to all Zeta series plugins. As well as ensure standard and
 * default behaviors.
 *
 * @author YeetManLord
 */
public abstract class ZetaPlugin extends JavaPlugin {

    private ConsoleLogger logger;
    
    private LocalData.PluginSetting pluginSetting;

    public ZetaPlugin() {
        super();
        this.logger = new ConsoleLogger(this);
    }

    @Override
    public void onEnable() {

        super.onEnable();
        this.pluginSetting = ZetaCore.getInstance().getLocalSettings().getPluginSettings(this);
        this.logger.setDebugging(this.pluginSetting.isDebugLogging());
        ZetaCore.getInstance().registerPlugin(this);
        if (this.pluginSetting.isDisabled()) {
            ZetaCore.getInstance().getPluginLogger().debug(this.getPluginName() + " has been disabled in settings. Disabling...");
            this.setEnabled(false);
            return;
        }
        ZetaCore.getInstance().getPluginLogger().debug("Registering data handlers for " + this.getPluginName());
        this.registerDataStorers();
        ZetaCore.getInstance().getPluginLogger().debug("Initializing databases for " + this.getPluginName() +  " if necessary ");
        this.initDB();

    }
    
    public LocalData.PluginSetting getSettings() {
        if (this.pluginSetting == null) {
            return ZetaCore.getInstance().getLocalSettings().getPluginSettings(this);
        }
        return this.pluginSetting;
    }

    private void initDB() {
        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        if (ZetaCore.getInstance().getLocalSettings().isInitialized() && this.pluginSetting.isSyncDatabase()) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {

                while (!ZetaCore.getInstance().isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (ZetaCore.getInstance().isConnectedToDatabase()) {
                    ZetaCore.getInstance().getDatabaseDataHandlers(this).forEach(d -> d.initializeDB(ZetaCore.getInstance().getLocalSettings().getClient().handler));
                }

                this.readData();

            });
        } else {
            this.readData();
        }
    }


    @Override
    public void onDisable() {

        super.onDisable();

        if (ZetaCore.getInstance().isConnectedToDatabase() && this.pluginSetting.isSyncDatabase()) {
            ZetaCore.getInstance().getLocalSettings().getClient().writeData(this);
        }

        logger.debug("Writing data for " + this.getPluginName());
        ZetaCore.getInstance().getDataHandlers(this).forEach(DataStorer::write);

    }

    public abstract String getPluginName();

    public ConsoleLogger getPluginLogger() {

        return this.logger;

    }

    protected abstract void registerDataStorers();

    protected void readData() {

        final LocalData db = ZetaCore.getInstance().getLocalSettings();
        this.logger.info("Reading data for " + this.getPluginName());

        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (db.isInitialized() && this.pluginSetting.isSyncDatabase()) {
                while (!ZetaCore.getInstance().isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (ZetaCore.getInstance().isConnectedToDatabase()) {
                    logger.debug("[ASYNC] Connected to database and reading data");
                    db.getClient().readData(this);
                } else {
                    logger.debug("[ASYNC] Connection to database failed. Reading data from local files");
                    Bukkit.getScheduler().runTask(this, () -> ZetaCore.getInstance().getDataHandlers(this).forEach(DataStorer::read));
                }
            } else {
                logger.debug("[ASYNC] Reading data from local files");
                Bukkit.getScheduler().runTask(this, () -> ZetaCore.getInstance().getDataHandlers(this).forEach(DataStorer::read));
            }
            logger.debug("[ASYNC] Data read finished.");
            Bukkit.getScheduler().runTask(this, this::onDataReadFinish);
        });

    }

    protected void writeData() {

        if (ZetaCore.getInstance().isConnectedToDatabase() && this.pluginSetting.isSyncDatabase()) {
            ZetaCore.getInstance().getDataHandlers(this).forEach(DataStorer::write);
            ZetaCore.getInstance().getLocalSettings().getClient().writeData(this);
        } else {
            ZetaCore.getInstance().getDataHandlers(this).forEach(DataStorer::write);
        }
    }

    /**
     * Very important callback method that is called once asynchronous reading is completed. Use this to start any tasks relating to reading data from databases.
     * IF database connection fails files will be read from local files.
     */
    public abstract void onDataReadFinish();

    public abstract boolean initializedFinished();

    public abstract ItemStack getIcon();

}
