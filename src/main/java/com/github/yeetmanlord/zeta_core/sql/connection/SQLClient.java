package com.github.yeetmanlord.zeta_core.sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import org.bukkit.Bukkit;

/**
 * Actual client that connects to the sql database
 *
 * @author YeetManLord
 */
public class SQLClient {

    private int port;

    private String hostname;

    private String username;

    private String password;

    private String database;

    private Connection client;

    public SQLHandler handler;

    public SQLClient(String hostname, String username, String password, int port, String database) {

        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.database = database;

        this.connect();

    }

    public boolean isConnected() {

        return (client != null);

    }

    public void connect() {

        if (!isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.INSTANCE, () -> {
                try {
                    client = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false&characterEncoding=UTF-8", username, password);
                    handler = new SQLHandler(this);
                    ZetaCore.LOGGER.info("&aDatabase is connected!");
                } catch (SQLException e) {
                    ZetaCore.LOGGER.info("&cDatabase not connected");
                }
            });
        }

    }

    public void disconnect() {

        if (isConnected()) {

            try {
                client.close();
                this.client = null;
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

    }

    public Connection getClient() {

        return client;

    }

    public void readData(ZetaPlugin plugin) {

        ZetaCore.getDatabaseDataHandlers(plugin).forEach(ISQLTableHandler::readDB);

    }

    public void readData() {

        ZetaCore.getDatabaseDataHandlers().values().forEach(dList -> dList.forEach(ISQLTableHandler::readDB));

    }

    public void writeData() {

        ZetaCore.getDatabaseDataHandlers().values().forEach(dList -> dList.forEach(ISQLTableHandler::writeToDB));

    }

    public void writeData(ZetaPlugin plugin) {

        ZetaCore.getDatabaseDataHandlers(plugin).forEach(ISQLTableHandler::writeToDB);

    }

}
