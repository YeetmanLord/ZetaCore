package com.github.yeetmanlord.zeta_core;

import com.github.yeetmanlord.zeta_core.data.PluginSetting;
import com.github.yeetmanlord.zeta_core.logging.IPluginLogger;

import java.io.File;

/**
 * Represents a ZetaPlugin. Either a Bukkit or BungeeCord plugin.
 */
public interface IZetaPlugin {

    File getDataFolder();

    void onEnable();

    void onDisable();

    String getPluginName();

    IPluginLogger getPluginLogger();
    /**
     * Very important callback method that is called once asynchronous reading is completed. Use this to start any tasks relating to reading data from databases.
     * IF database connection fails files will be read from local files.
     */
    void onDataReadFinish();

    boolean initializedFinished();

    PluginSetting getSettings();

    void scheduleTask(Runnable task, long delay);

    void scheduleAsyncTask(Runnable task);

    boolean isEnabled();


}
