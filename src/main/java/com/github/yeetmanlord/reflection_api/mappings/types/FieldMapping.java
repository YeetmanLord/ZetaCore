package com.github.yeetmanlord.reflection_api.mappings.types;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class FieldMapping<FieldType, ReflectionType extends NMSObjectReflection> implements IMapping<String> {

    public Class<ReflectionType> reflectionType;

    public Map<VersionRange, String> mappings;

    public String name;

    public FieldMapping(String name, Class<ReflectionType> type, Map<VersionRange, String> mappings) {

        this.reflectionType = type;
        this.mappings = mappings;
        this.name = name;
        Mappings.mappings.add(this);

    }

    public void addMapping(VersionRange versionRange, String versionDependentFieldName) {

        this.mappings.put(versionRange, versionDependentFieldName);

    }

    /**
     * @param reflection The reflection object to use to get the field
     * @return Returns an instance of these mappings FieldType If the reflection
     * object is not an instance of {@link #reflectionType} it will return
     * null.
     * @throws MappingsException A {@link MappingsException} is thrown when this
     *                           mapping cannot find a matching mapping for the
     *                           current version
     */
    public FieldType getField(ReflectionType reflection) throws MappingsException {

        for (VersionRange range : mappings.keySet()) {

            if (range.isWithinRange(ReflectionApi.version)) {

                try {
                    return (FieldType) reflection.getFieldFromNmsObject(mappings.get(range));
                } catch (NoSuchFieldException e) {
                    throw (new MappingsException(this, "Failed to get field", e));
                }

            }

        }

        throw (new MappingsException(this, "Failed to get field: No mapping for current version found"));

    }

    @Override
    public String toString() {

        return "FieldMapping{name: " + name + "}";

    }

    @Override
    public String getName() {

        return name;

    }

    @Override
    public Map<VersionRange, String> getMappings() {

        return mappings;

    }

    @Override
    public boolean testMapping() {
        try {
            String fieldName = get();
            Class<?> nmsClass;
            try {
                nmsClass = (Class<?>) reflectionType.getField("staticClass").get(null);
            } catch (NoSuchFieldException | ClassCastException | IllegalAccessException e) {
                throw new NMSObjectReflection.ImplementationException(reflectionType, "The reflection type " + reflectionType.getName() + " does not have a staticClass field");
            }
            try {
                nmsClass.getField(fieldName);
            } catch (NoSuchFieldException e) {
                nmsClass.getDeclaredField(fieldName);
            }

            return true;

        } catch (MappingsException exc) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Mapping " + name + " is out of range. Tentative Pass.");
            return true;
        } catch (NoSuchFieldException | NMSObjectReflection.ImplementationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
