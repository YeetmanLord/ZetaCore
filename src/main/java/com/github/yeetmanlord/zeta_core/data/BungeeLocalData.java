package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.IZetaPlugin;
import com.github.yeetmanlord.zeta_core.ZetaBungeePlugin;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class BungeeLocalData extends BungeeDataStorer {

    private boolean devFeatures;

    private HashMap<String, com.github.yeetmanlord.zeta_core.data.PluginSetting> pluginSettings;

    private SQLClient client;

    private String ipAddress;

    private String username;

    private String password;

    private boolean initialized;

    private boolean firstInit;

    private int port;

    private String databaseName;

    public BungeeLocalData(ZetaBungeePlugin instance) {

        super(instance, "local_data");
        pluginSettings = new HashMap<>();
        firstInit = false;

    }

    public com.github.yeetmanlord.zeta_core.data.PluginSetting getPluginSettings(IZetaPlugin plugin) {
        if (!pluginSettings.containsKey(plugin.getPluginName())) {
            pluginSettings.put(plugin.getPluginName(), new PluginSetting(false, true, false));
        }
        return pluginSettings.get(plugin.getPluginName());
    }

    @Override
    public void write() {
        this.config.set("should_debug", this.devFeatures);
        for (ZetaPlugin plugin : ZetaCore.getInstance().getPlugins()) {
            com.github.yeetmanlord.zeta_core.data.PluginSetting settings = plugin.getSettings();
            String path = "plugins." + plugin.getPluginName() + ".";
            this.config.set(path + "disabled", settings.isDisabled());
            this.config.set(path + "sync_database", settings.isSyncDatabase());
            this.config.set(path + "debug", settings.isDebugLogging());
        }

        config.set("ip", ipAddress);
        config.set("initialized", initialized);
        config.set("username", username);
        config.set("password", password);
        config.set("port", port);
        config.set("databaseName", databaseName);

        this.save();
    }

    @Override
    public void read() {
        this.devFeatures = this.config.getBoolean("should_debug");
        ConfigurationSection plugins = this.config.getConfigurationSection("plugins");
        if (plugins != null) {
            for (String key : plugins.getKeys(false)) {
                String path = "plugins." + key + ".";
                boolean disabled = config.getBoolean(path + "disabled");
                boolean syncDatabase = config.getBoolean(path + "sync_database");
                boolean debug = config.getBoolean(path + "debug");
                this.pluginSettings.put(key, new com.github.yeetmanlord.zeta_core.data.PluginSetting(disabled, syncDatabase, debug));
            }
        }
        this.initialized = config.getBoolean("initialized");
        this.username = config.getString("username");
        this.ipAddress = config.getString("ip");
        this.password = config.getString("password");
        this.port = config.getInt("port");
        this.databaseName = config.getString("databaseName");

        if (initialized) {
            instance.getPluginLogger().debug("Initializing database");
            this.client = new SQLClient(ipAddress, username, password, port, databaseName);
        }
    }

    @Override
    public void setDefaults() {
        if (!this.config.contains("should_debug")) {
            config.set("should_debug", false);
        }

        if (!this.config.contains("ip")) {
            config.set("ip", "localhost");
        }

        if (!this.config.contains("initialized")) {
            config.set("initialized", false);
        }

        if (!this.config.contains("username")) {
            config.set("username", "root");
        }

        if (!this.config.contains("password")) {
            config.set("password", "");
        }

        if (!this.config.contains("port")) {
            config.set("port", 3306);
        }

        if (!this.config.contains("databaseName")) {
            config.set("databaseName", "zeta_data");
        }
    }

    public boolean isDevFeatures() {
        return devFeatures;
    }

    public void setDevFeatures(boolean devFeatures) {
        this.devFeatures = devFeatures;
    }

    public HashMap<String, com.github.yeetmanlord.zeta_core.data.PluginSetting> getPluginSettings() {
        return pluginSettings;
    }

    public void setPluginSettings(HashMap<String, com.github.yeetmanlord.zeta_core.data.PluginSetting> pluginSettings) {
        this.pluginSettings = pluginSettings;
    }

    public SQLClient getClient() {
        return client;
    }

    public void setClient(SQLClient client) {
        this.client = client;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isFirstInit() {
        return firstInit;
    }

    public void setFirstInit(boolean firstInit) {
        this.firstInit = firstInit;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

}