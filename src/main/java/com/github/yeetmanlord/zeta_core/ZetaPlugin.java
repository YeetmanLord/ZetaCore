package com.github.yeetmanlord.zeta_core;

import com.github.yeetmanlord.zeta_core.data.DataStorer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yeetmanlord.zeta_core.data.DataBase;
import com.github.yeetmanlord.zeta_core.logging.Logger;

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

    private Logger logger;

    public ZetaPlugin() {
        super();
        this.logger = new Logger(this);
    }

    @Override
    public void onEnable() {

        super.onEnable();
        ZetaCore.registerPlugin(this);
        if (ZetaCore.INSTANCE.localSettings.disabled_plugins.contains(this.getName())) {
            this.setEnabled(false);
            return;
        }
        this.registerDataStorers();
        this.initDB();
        this.readData();

    }

    private void initDB() {
        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        if (ZetaCore.INSTANCE.dataBase.initialized) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {

                while (!ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                ZetaCore.getDatabaseDataHandlers(this).forEach(d -> d.initializeDB(ZetaCore.INSTANCE.dataBase.client.handler));

            });
        }
    }


    @Override
    public void onDisable() {

        super.onDisable();

        if (ZetaCore.INSTANCE.dataBase.initialized && ZetaCore.INSTANCE.dataBase.client != null && ZetaCore.INSTANCE.dataBase.client.isConnected()) {
            ZetaCore.INSTANCE.dataBase.client.writeData(this);
        }

        ZetaCore.getDataHandlers(this).forEach(DataStorer::write);

    }

    public abstract String getPluginName();

    public Logger getPluginLogger() {

        return this.logger;

    }

    protected abstract void registerDataStorers();

    protected void readData() {

        final DataBase db = ZetaCore.INSTANCE.dataBase;
        this.logger.info("Reading data for " + this.getPluginName());

        final LocalDateTime end = LocalDateTime.now().plus(3200, ChronoUnit.MILLIS);

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (db.initialized) {
                while (!ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    if (LocalDateTime.now().isAfter(end)) break;
                }
                if (ZetaCore.INSTANCE.isConnectedToDatabase()) {
                    db.client.readData(this);
                } else {
                    ZetaCore.getDataHandlers(this).forEach(DataStorer::read);
                }
                Bukkit.getScheduler().runTask(this, this::onDataReadFinish);
            } else {
                ZetaCore.getDataHandlers(this).forEach(DataStorer::read);
                onDataReadFinish();
            }
        });

    }

    protected void writeData() {
        DataBase db = ZetaCore.INSTANCE.dataBase;

        if (db.initialized && ZetaCore.INSTANCE.dataBase.client.isConnected()) {
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

}
