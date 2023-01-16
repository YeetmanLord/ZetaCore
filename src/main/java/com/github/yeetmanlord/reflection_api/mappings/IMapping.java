package com.github.yeetmanlord.reflection_api.mappings;

import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import org.bukkit.plugin.java.JavaPlugin;

public interface IMapping<Type> {

    /**
     * @return Returns the internal name of a mapping
     */
    String getName();

    /**
     * @return Returns a mapping types mappings with {@link VersionRange} as the key
     * to specify in which versions the mapping will be used and a
     * {@link String} for what the name of the field/method/class is
     */
    Map<VersionRange, Type> getMappings();

    /**
     * Adds mappings for a {@link VersionRange}. If you would like to add your own
     * mappings you can do so by adding them in {@link JavaPlugin#onEnable()}.
     * Or in a method called inside it.
     *
     * @param range The key to specify in which versions the mapping will be used
     * @param value What the name of the field/method/class is
     */
    void addMapping(VersionRange range, Type value);

    /**
     * Adds mappings for all entries in a list. If you would like to add your own
     * mappings you can do so by adding them in {@link JavaPlugin#onEnable()}.
     * Or in a method called inside it.
     *
     * @param range The key to specify in which versions the mapping will be used
     * @param value What the name of the field/method/class is
     */
    default void addAllMappings(Map<VersionRange, Type> mappings) {
        mappings.forEach(this::addMapping);
    }

    boolean testMapping();

    default Type get() throws MappingsException {
        Map<VersionRange, Type> mappings = getMappings();
        for (VersionRange range : mappings.keySet()) {
            if (range.isWithinRange(ReflectionApi.version)) {
                return mappings.get(range);
            }
        }
        throw new MappingsException(this, "No mapping found for " + getName() + " in version " + ReflectionApi.version);
    }

}
