package com.github.yeetmanlord.zeta_core.api.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.yeetmanlord.zeta_core.data.DataStorer;

/**
 * @author YeetManLord
 * @zeta.usage INTERNAL This is only for storing and reading data from database
 */
public class YAMLUtil {

    /**
     * Recursively reads a {@link ConfigurationSection} or entire into a map
     * @param config The {@link FileConfiguration} to read from
     * @param path   The config path to read from
     * @return A map containing the data at the specified path
     */
    public static Map<String, Object> getMapFromYAML(FileConfiguration config, @Nullable String path) {

        Map<String, Object> map = new HashMap<>();

        if (path != null) {

            for (String key : config.getConfigurationSection(path).getKeys(false)) {
                Object obj = config.get(path + "." + key);

                if (obj instanceof ConfigurationSection) {
                    recurse(map, (ConfigurationSection) obj, key);
                } else {
                    map.put(key, config.get(path + "." + key));
                }

            }

            return map;
        }

        for (String key : config.getKeys(false)) {

            Object obj = config.get(key);

            if (obj instanceof ConfigurationSection) {
                recurse(map, (ConfigurationSection) obj, key);
            } else {
                map.put(key, config.get(key));
            }

        }

        return map;

    }

    /**
     * Quickly write a map to a config
     * @param dataStorer The config to write to
     * @param path       The section to write to
     * @param json       The map data to write
     * @param shouldSave Whether to save the config after writing
     */
    public static void writeYAMLFromMap(DataStorer dataStorer, @Nullable String path, Map<String, Object> json, boolean shouldSave) {

        FileConfiguration config = dataStorer.get();

        if (path != null) {

            config.createSection(path, json);

        } else {

            for (String key : json.keySet()) {

                Object obj = json.get(key);

                if (obj instanceof Map) {
                    config.createSection(key, (Map<?, ?>) obj);
                } else {
                    config.set(key, json.get(key));
                }

            }

        }

        if (shouldSave)
            dataStorer.save();

    }

    public static void writeYAMLFromMap(DataStorer dataStorer, @Nullable String path, Map<String, Object> json) {
        writeYAMLFromMap(dataStorer, path, json, true);
    }

    private static void recurse(Map<String, Object> map, ConfigurationSection section, String key1) {

        Map<String, Object> newMap = new HashMap<>();

        for (String key : section.getKeys(false)) {
            Object obj = section.get(key);

            if (obj instanceof ConfigurationSection) {
                recurse(newMap, (ConfigurationSection) obj, key);
            } else {
                newMap.put(key, section.get(key));
            }

        }

        map.put(key1, newMap);

    }

}
