package com.github.yeetmanlord.zeta_core;

import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.data.LocalData;
import com.github.yeetmanlord.zeta_core.logging.BungeeLogger;
import com.github.yeetmanlord.zeta_core.logging.IPluginLogger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Extension of {@link Plugin} and is used to add some ease of use
 * functionality to all Zeta series bungee plugins. As well as ensure standard and
 * default behaviors.
 *
 * @author YeetManLord
 */
public abstract class ZetaBungeePlugin extends Plugin implements IZetaPlugin {

    private BungeeLogger logger;

    private LocalData.PluginSetting pluginSetting;

    public ZetaBungeePlugin() {
        super();
        this.logger = new BungeeLogger(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.pluginSetting = BungeeCore.getInstance().getLocalSettings().getPluginSettings(this);
        BungeeCore.getInstance().registerPlugin(this);
        BungeeCore.getInstance().getPluginLogger().debug("Registering data handlers for " + this.getPluginName());
        this.registerDataStorers();
        BungeeCore.getInstance().getPluginLogger().debug("Initializing databases for " + this.getPluginName() +  " if necessary ");
        this.initDB();
    }

    @Override
    public IPluginLogger getPluginLogger() {
        return logger;
    }

    @Override
    public void onDataReadFinish() {

    }

    @Override
    public boolean initializedFinished() {
        return false;
    }

    protected abstract void registerDataStorers();

    protected void readData() {

        final LocalData db = BungeeCore.getInstance().getLocalSettings();
        this.logger.info("Reading data for " + this.getPluginName());

        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        ProxyServer.getInstance().getScheduler().runAsync(this, () -> {
            if (db.isInitialized() && this.pluginSetting.isSyncDatabase()) {
                while (!BungeeCore.getInstance().isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (BungeeCore.getInstance().isConnectedToDatabase()) {
                    logger.debug("[ASYNC] Connected to database and reading data");
                    db.getClient().readData(this);
                } else {
                    logger.debug("[ASYNC] Connection to database failed. Reading data from local files");
                    ProxyServer.getInstance().getScheduler().schedule(this, () -> BungeeCore.getInstance().getDataHandlers(this).forEach(DataStorer::read), 0, TimeUnit.MILLISECONDS);
                }
            } else {
                logger.debug("[ASYNC] Reading data from local files");
                ProxyServer.getInstance().getScheduler().schedule(this, () -> BungeeCore.getInstance().getDataHandlers(this).forEach(DataStorer::read), 0, TimeUnit.MILLISECONDS);
            }
            logger.debug("[ASYNC] Data read finished.");
            ProxyServer.getInstance().getScheduler().schedule(this, this::onDataReadFinish, 0, TimeUnit.MILLISECONDS);
        });

    }

    protected void writeData() {

        if (BungeeCore.getInstance().isConnectedToDatabase()) {
            BungeeCore.getInstance().getDataHandlers(this).forEach(DataStorer::write);
            BungeeCore.getInstance().getLocalSettings().getClient().writeData(this);
        } else {
            BungeeCore.getInstance().getDataHandlers(this).forEach(DataStorer::write);
        }
    }

    private void initDB() {
        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        if (BungeeCore.getInstance().getLocalSettings().isInitialized() && this.pluginSetting.isSyncDatabase()) {
            ProxyServer.getInstance().getScheduler().runAsync(this, () -> {

                while (!BungeeCore.getInstance().isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (BungeeCore.getInstance().isConnectedToDatabase()) {
                    BungeeCore.getInstance().getDatabaseDataHandlers(this).forEach(d -> d.initializeDB(BungeeCore.getInstance().getLocalSettings().getClient().handler));
                }

                this.readData();

            });
        } else {
            this.readData();
        }
    }

    @Override
    public LocalData.PluginSetting getSettings() {
        return this.pluginSetting;
    }

    @Override
    public void scheduleAsyncTask(Runnable task) {
        ProxyServer.getInstance().getScheduler().runAsync(this, task);
    }

    @Override
    public void scheduleTask(Runnable task, long delay) {
        ProxyServer.getInstance().getScheduler().schedule(this, task, delay, TimeUnit.MILLISECONDS);
    }
}
