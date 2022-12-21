package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;

public class LocalData extends DataStorer {

    public boolean shouldDebug;

    public HashMap<String, PluginSetting> pluginSettings;

    public SQLClient client;

    public String ipAddress;

    public String username;

    public String password;

    public boolean initialized;

    public int port;

    public String databaseName;

    public LocalData(ZetaCore instance) {

        super(instance, "local_data");
        pluginSettings = new HashMap<>();

    }

    public PluginSetting getPluginSettings(ZetaPlugin plugin) {
        if (!pluginSettings.containsKey(plugin.getPluginName())) {
            pluginSettings.put(plugin.getPluginName(), new PluginSetting(false, true, false));
        }
        return pluginSettings.get(plugin.getPluginName());
    }

    @Override
    public void write() {
        this.config.set("should_debug", this.shouldDebug);
        for (ZetaPlugin plugin : ZetaCore.getPlugins()) {
            PluginSetting settings = plugin.getSettings();
            String path = "plugins." + plugin.getPluginName() + ".";
            this.config.set(path + "disabled", settings.disabled);
            this.config.set(path + "sync_database", settings.syncDatabase);
            this.config.set(path + "debug", settings.debugLogging);
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
        this.shouldDebug = this.config.getBoolean("should_debug");
        ConfigurationSection plugins = this.config.getConfigurationSection("plugins");
        if (plugins != null) {
            for (String key : plugins.getKeys(false)) {
                String path = "plugins." + key + ".";
                boolean disabled = config.getBoolean(path + "disabled");
                boolean syncDatabase = config.getBoolean(path + "sync_database");
                boolean debug = config.getBoolean(path + "debug");
                this.pluginSettings.put(key, new PluginSetting(disabled, syncDatabase, debug));
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

    public class PluginSetting {
        private boolean disabled;
        private boolean syncDatabase;
        private boolean debugLogging;

        public PluginSetting(boolean disabled, boolean syncDatabase, boolean debugLogging) {
            this.disabled = disabled;
            this.syncDatabase = syncDatabase;
            this.debugLogging = debugLogging;
        }

        public boolean isDebugLogging() {
            return debugLogging;
        }

        public void setDebugLogging(boolean debugLogging) {
            this.debugLogging = debugLogging;
        }

        public boolean isSyncDatabase() {
            return syncDatabase;
        }

        public void setSyncDatabase(boolean syncDatabase) {
            this.syncDatabase = syncDatabase;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        @Override
        public String toString() {
            return "PluginSettings" + ImmutableMap.of("Disabled", disabled, "SyncDatabase", syncDatabase, "DebugLogging", debugLogging);
        }
    }
}
