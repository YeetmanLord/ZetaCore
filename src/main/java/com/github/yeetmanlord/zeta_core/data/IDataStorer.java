package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.IZetaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public interface IDataStorer {

    void setup();

    FileConfiguration get();

    void save();

    void reload();

    void setConfig(FileConfiguration config);

    String getFileName();

    IZetaPlugin getPlugin();

    void setDefaults();

    void read();

    void write();
}
