package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.ZetaCore;

import java.util.List;

public class LocalData extends DataStorer {

    public boolean should_debug;

    public List<String> disabled_plugins;

    public LocalData(ZetaCore instance) {

        super(instance, "local_data");

    }

    @Override
    public void write() {
        this.config.set("should_debug", this.should_debug);
        this.config.set("disabled_plugins", this.disabled_plugins);
    }

    @Override
    public void read() {
        this.should_debug = this.config.getBoolean("should_debug");
        this.disabled_plugins = this.config.getStringList("disabled_plugins");
    }

    @Override
    public void setDefaults() {

    }
}
