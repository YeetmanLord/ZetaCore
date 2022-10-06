package com.github.yeetmanlord.reflection_api.mappings;

import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public interface IMapping<Type> {

	/**
	 * @return Returns the internal name of a mapping
	 */
	String getName();

	/**
	 * @return Returns a mapping types mappings with {@link VersionRange} as the key
	 *         to specify in which versions the mapping will be used and a
	 *         {@link String} for what the name of the field/method/class is
	 */
	Map<VersionRange, Type> getMappings();

	/**
	 * Adds mappings for a {@link VersionRange}. If you would like to add your own
	 * mappings you can do so by adding them in {@link JavaPlugin#onEnable()}.
	 * 
	 * @param range The key to specify in which versions the mapping will be used
	 * @param value What the name of the field/method/class is
	 */
	void addMapping(VersionRange range, Type value);

}
