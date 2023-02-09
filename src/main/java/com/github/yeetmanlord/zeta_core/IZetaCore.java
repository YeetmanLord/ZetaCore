package com.github.yeetmanlord.zeta_core;

import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.data.IDataStorer;
import com.github.yeetmanlord.zeta_core.logging.IPluginLogger;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;

import java.util.HashMap;
import java.util.List;

public interface IZetaCore<T extends IZetaPlugin, U extends IDataStorer> {

    void registerPlugin(T plugin);

    default boolean pluginEnabled(String plugin) {
        return getPlugin(plugin) != null && getPlugin(plugin).isEnabled();
    }

    T getPlugin(String pluginName);

    List<T> getPlugins();

    void registerDataHandler(U storer);

    List<U> getDataHandlers(T plugin);

    List<ISQLTableHandler<?>> getDatabaseDataHandlers(T plugin);

    HashMap<T, List<ISQLTableHandler<?>>> getDatabaseDataHandlers();

    HashMap<T, List<U>> getDataHandlers();

    boolean isConnectedToDatabase();

    SQLClient getSQLClient();

    void scheduleTask(Runnable task, long delay);

    void scheduleAsyncTask(Runnable task);

    IPluginLogger getPluginLogger();

}
