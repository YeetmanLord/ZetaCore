package com.github.yeetmanlord.zeta_core.sql.connection;

import java.sql.SQLException;
import java.util.Properties;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.bukkit.Bukkit;

import javax.sql.DataSource;

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

    private DataSource dataSource;

    public SQLHandler handler;

    private boolean validated = false;

    public SQLClient(String hostname, String username, String password, int port, String database) {

        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.database = database;

        this.connect();

    }

    public boolean isConnected() {

        return (this.dataSource != null && validated);

    }

    public void connect() {

        if (!isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.INSTANCE, () -> {
                Properties props = new Properties();
                props.setProperty("dataSourceClassName", "org.mariadb.jdbc.MariaDbDataSource");
                props.setProperty("dataSource.url", String.format("jdbc:%s://%s:%s/%s", "mariadb", hostname, port, database));
                props.setProperty("dataSource.user", username);
                props.setProperty("dataSource.password", password);

                HikariConfig hConfig = new HikariConfig(props);

                hConfig.setMaximumPoolSize(10);

                try {
                    this.dataSource = new HikariDataSource(hConfig);
                    handler = new SQLHandler(this);

                    validated = this.dataSource.getConnection().isValid(3);
                    ZetaCore.LOGGER.info("&aDatabase is connected!");
                } catch (RuntimeException e) {
                    ZetaCore.LOGGER.error("Pool Initialization Failed! In most cases this means that the database service isn't running or the database connection information is wrong");
                    ZetaCore.LOGGER.error("To enable a full stacktrace please enable debug mode.");
                    if (ZetaCore.LOGGER.isDebugging()) {
                        ZetaCore.LOGGER.debug("&fFull Stack Trace:");
                        e.printStackTrace();
                    }
                }
                catch (Exception e) {
                    ZetaCore.LOGGER.error(e, "Could not connect to database. Caused by the following exception:");
                    if (ZetaCore.LOGGER.isDebugging()) {
                        ZetaCore.LOGGER.debug("&fFull Stack Trace:");
                        e.printStackTrace();
                    }
                    validated = false;
                }
            });
        }

    }

    public void disconnect() {

        if (isConnected()) {

            ((HikariDataSource)this.dataSource).close();
            this.dataSource = null;

        }

    }

    public DataSource getSource() {

        return dataSource;

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

    @Override
    public String toString() {
        return "SQLClient" + ImmutableMap.of("connected", isConnected(), "username", username, "database", database, "hostname", hostname, "valid", validated);
    }
}
