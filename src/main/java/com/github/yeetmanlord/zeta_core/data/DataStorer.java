package com.github.yeetmanlord.zeta_core.data;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.api.uitl.YAMLUtil;

/**
 * All {@link DataStorer}s are for INTERNAL USE ONLY! Refrain from using any
 * methods of {@linkplain DataStorer}s because it could likely lead to data
 * lossage, failure to save or load, etc.
 * 
 * @author YeetManLord
 *
 * @zeta.usage INTERNAL
 */
public abstract class DataStorer {

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
			}
			catch (IOException e) {
			}

		}

		config = YamlConfiguration.loadConfiguration(file);
		this.setDefaults();
		this.save();
		ZetaCore.registerDataHandler(this);

	}

	public FileConfiguration get() {

		return config;

	}

	public void save() {

		try {
			config.save(file);
		}
		catch (IOException e) {
			instance.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Bedwars Zeta] &4WARNING: &eCould not save config file!"));
		}

	}

	public void reload() {

		config = YamlConfiguration.loadConfiguration(file);

	}

	public void setConfig(FileConfiguration config) {

		this.config = config;
		this.save();

	}

	public abstract void write();

	public abstract void read();

	public abstract void setDefaults();

	public String getFileName() {

		return fileName;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {

		return "DataStorer{" + "filename: " + fileName + ", plugin: " + instance.getPluginName() + "}";

	}

	public ZetaPlugin getPlugin() {

		return this.instance;

	}

}
