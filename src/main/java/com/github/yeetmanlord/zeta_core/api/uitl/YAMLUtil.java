package com.github.yeetmanlord.zeta_core.api.uitl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.json.simple.JSONObject;

import com.github.yeetmanlord.zeta_core.data.DataStorer;

/**
 * @author YeetManLord
 *
 * @zeta.usage INTERNAL This is only for storing and reading data from database
 */
public class YAMLUtil {

	public static JSONObject getJSONFromYAML(FileConfiguration config, @Nullable String path) {

		Map<String, Object> map = new HashMap<>();

		if (path != null) {

			for (String key : config.getConfigurationSection(path).getKeys(false)) {
				Object obj = config.get(path + "." + key);

				if (obj instanceof ConfigurationSection) {
					recurse(map, (ConfigurationSection) obj, key);
				}
				else {
					map.put(key, config.get(path + "." + key));
				}

			}

			return new JSONObject(map);
		}

		for (String key : config.getKeys(false)) {

			Object obj = config.get(key);

			if (obj instanceof ConfigurationSection) {
				recurse(map, (ConfigurationSection) obj, key);
			}
			else {
				map.put(key, config.get(key));
			}

		}

		return new JSONObject(map);

	}

	public static void writeYAMLFromJSON(DataStorer dataStorer, @Nullable String path, JSONObject json) {

		FileConfiguration config = dataStorer.get();

		if (path != null) {

			config.createSection(path, json);

		}
		else {

			for (Object key : json.keySet()) {

				if (key instanceof String) {
					Object obj = json.get(key);

					if (obj instanceof Map) {
						recurseWrite(json, config.createSection((String) key));
					}
					else {
						config.set((String) key, json.get(key));
					}

				}
				else {
					throw (new IllegalArgumentException("The given JSONObject contains non-string keys making it incompatible with YAML"));
				}

			}

		}

		dataStorer.save();

	}

	private static void recurse(Map<String, Object> map, ConfigurationSection section, String key1) {

		Map<String, Object> newMap = new HashMap<>();

		for (String key : section.getKeys(false)) {
			Object obj = section.get(key);

			if (obj instanceof ConfigurationSection) {
				recurse(newMap, (ConfigurationSection) obj, key);
			}
			else {
				newMap.put(key, section.get(key));
			}

		}

		map.put(key1, newMap);

	}

	private static void recurseWrite(Map<String, Object> map, ConfigurationSection section) {

		for (String key : map.keySet()) {
			Object obj = map.get(key);

			if (obj instanceof Map) {
				recurseWrite((Map<String, Object>) obj, section.createSection(key));
			}
			else {
				section.set(key, obj);
			}

		}

	}

}
