package com.github.yeetmanlord.zeta_core;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.yeetmanlord.zeta_core.data.DataBase;
import com.github.yeetmanlord.zeta_core.logging.Logger;

/**
 * Extention of {@link JavaPlugin} and is used to add some ease of use
 * functionality to all Zeta series plugins. As well as ensure standard and
 * default behaviors.
 *
 * @author YeetManLord
 */
public abstract class ZetaPlugin extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {

        super.onEnable();
        logger = new Logger(this);
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

        if (ZetaCore.INSTANCE.dataBase.client != null) {

            ZetaCore.getDatabaseDataHandlers(this).forEach(d -> {
                d.initializeDB(ZetaCore.INSTANCE.dataBase.client.handler);
            });

        }

    }

    @Override
    public void onDisable() {

        super.onDisable();

        if (ZetaCore.INSTANCE.dataBase.initialized && ZetaCore.INSTANCE.dataBase.client != null && ZetaCore.INSTANCE.dataBase.client.isConnected()) {
            ZetaCore.INSTANCE.dataBase.client.writeData(this);
        } else {

            ZetaCore.getDataHandlers(this).forEach((handler) -> {

                handler.write();

            });

        }

    }

    public abstract String getPluginName();

    public Logger getPluginLogger() {

        return this.logger;

    }

    protected abstract void registerDataStorers();

    protected void readData() {

        DataBase db = ZetaCore.INSTANCE.dataBase;
        this.logger.info("Reading data for " + this.getPluginName());

        if (db.initialized && ZetaCore.INSTANCE.dataBase.client.isConnected()) {
            db.client.readData(this);
        } else {
            ZetaCore.getDataHandlers(this).forEach(d -> {
                d.read();
            });
        }

    }

    protected void writeData() {
        DataBase db = ZetaCore.INSTANCE.dataBase;

        if (db.initialized && ZetaCore.INSTANCE.dataBase.client.isConnected()) {
            db.client.writeData(this);
        } else {
            ZetaCore.getDataHandlers(this).forEach(d -> {
                d.write();
            });
        }
    }

}
