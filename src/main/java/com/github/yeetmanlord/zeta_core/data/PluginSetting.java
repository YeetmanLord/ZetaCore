package com.github.yeetmanlord.zeta_core.data;

import com.google.common.collect.ImmutableMap;

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
