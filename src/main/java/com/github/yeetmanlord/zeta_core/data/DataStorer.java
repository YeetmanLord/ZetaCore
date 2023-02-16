package com.github.yeetmanlord.zeta_core.data;

import java.io.File;
import java.io.IOException;

import com.github.yeetmanlord.zeta_core.IZetaPlugin;
import com.github.yeetmanlord.zeta_core.ZetaBungeePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;

/**
 * All {@link DataStorer DataStorers} for spigot plugins should extend this class.
 * This class is used to store data in a file. There are also extensions of this class
 * that store data in a database.
 *
 * @author YeetManLord
 */
public abstract class DataStorer implements IDataStorer {

    protected File file;

    protected FileConfiguration config;

    protected String fileName;

    protected ZetaPlugin instance;

    public DataStorer(ZetaPlugin instanceIn, String name) {

        this.instance = instanceIn;
        this.fileName = name;

        file = new File(instanceIn.getDataFolder(), name + ".yml");

    }

    public void setup() {

        file = new File(instance.getDataFolder(), fileName + ".yml");

        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
            }

        }

        config = YamlConfiguration.loadConfiguration(file);
        this.setDefaults();
        this.save();
        ZetaCore.getInstance().registerDataHandler(this);

    }

    public FileConfiguration get() {

        return config;

    }

    public void save() {

        try {
            config.save(file);
        } catch (IOException e) {
            instance.getPluginLogger().warn("Could not save config file!");
        }

    }

    public void reload() {

        config = YamlConfiguration.loadConfiguration(file);

    }

    public void setConfig(FileConfiguration config) {

        this.config = config;
        this.save();

    }

    public String getFileName() {

        return fileName;

    }

    @Override
    public String toString() {

        return "DataStorer{" + "filename: " + fileName + ", plugin: " + instance.getPluginName() + "}";

    }

    public ZetaPlugin getPlugin() {

        return this.instance;

    }

    public abstract void setDefaults();

    public abstract void read();

    public abstract void write();

}
