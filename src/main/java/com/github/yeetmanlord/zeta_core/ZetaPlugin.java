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
        this.pluginSetting = ZetaCore.INSTANCE.localSettings.getPluginSettings(this);
        this.logger.setDebugging(this.pluginSetting.isDebugLogging());
        ZetaCore.registerPlugin(this);
        if (this.pluginSetting.isDisabled()) {
            ZetaCore.LOGGER.debug(this.getPluginName() + " has been disabled in settings. Disabling...");
            this.setEnabled(false);
            return;
        }
        ZetaCore.LOGGER.debug("Registering data handlers for " + this.getPluginName());
        this.registerDataStorers();
        ZetaCore.LOGGER.debug("Initializing databases for " + this.getPluginName() +  " if necessary ");
        this.initDB();

    }
    
    public LocalData.PluginSetting getSettings() {
        if (this.pluginSetting == null) {
            return ZetaCore.INSTANCE.localSettings.getPluginSettings(this);
        }
        return this.pluginSetting;
    }

    private void initDB() {
        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        if (ZetaCore.INSTANCE.localSettings.initialized && this.pluginSetting.isSyncDatabase()) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {

                while (!ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    ZetaCore.getDatabaseDataHandlers(this).forEach(d -> d.initializeDB(ZetaCore.INSTANCE.localSettings.client.handler));
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

        if (ZetaCore.INSTANCE.localSettings.initialized && ZetaCore.INSTANCE.localSettings.client != null && ZetaCore.INSTANCE.localSettings.client.isConnected() && this.pluginSetting.isSyncDatabase()) {
            ZetaCore.INSTANCE.localSettings.client.writeData(this);
        }

        logger.debug("Writing data for " + this.getPluginName());
        ZetaCore.getDataHandlers(this).forEach(DataStorer::write);

    }

    public abstract String getPluginName();

    public ConsoleLogger getPluginLogger() {

        return this.logger;

    }

    protected abstract void registerDataStorers();

    protected void readData() {

        final LocalData db = ZetaCore.INSTANCE.localSettings;
        this.logger.info("Reading data for " + this.getPluginName());

        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (db.initialized && this.pluginSetting.isSyncDatabase()) {
                while (!ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    logger.debug("[ASYNC] Connected to database and reading data");
                    db.client.readData(this);
                } else {
                    logger.debug("[ASYNC] Connection to database failed. Reading data from local files");
                    Bukkit.getScheduler().runTask(this, () -> ZetaCore.getDataHandlers(this).forEach(DataStorer::read));
                }
            } else {
                logger.debug("[ASYNC] Reading data from local files");
                Bukkit.getScheduler().runTask(this, () -> ZetaCore.getDataHandlers(this).forEach(DataStorer::read));
            }
            logger.debug("[ASYNC] Data read finished.");
            Bukkit.getScheduler().runTask(this, this::onDataReadFinish);
        });

    }

    protected void writeData() {
        LocalData db = ZetaCore.INSTANCE.localSettings;

        if (db.initialized && ZetaCore.INSTANCE.localSettings.client.isConnected() && this.pluginSetting.isSyncDatabase()) {
            ZetaCore.getDataHandlers(this).forEach(DataStorer::write);
            db.client.writeData(this);
        } else {
            ZetaCore.getDataHandlers(this).forEach(DataStorer::write);
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
