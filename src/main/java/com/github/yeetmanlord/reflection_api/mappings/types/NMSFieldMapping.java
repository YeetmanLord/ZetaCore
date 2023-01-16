package com.github.yeetmanlord.reflection_api.mappings.types;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;

public class NMSFieldMapping<Type> implements IMapping<String> {

    private String name;

    private Map<VersionRange, String> mappings;

    private Class<?> nmsClass;

    public NMSFieldMapping(String name, Class<?> nmsClass, Map<VersionRange, String> mappings) {
        this.name = name;
        this.mappings = mappings;
        this.nmsClass = nmsClass;
        Mappings.mappings.add(this);
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
    public void addMapping(VersionRange range, String nmsField) {
        this.mappings.put(range, nmsField);
    }

    @Override
    public boolean testMapping() {

        try {
            String nmsField = get();

            try {
                nmsClass.getField(nmsField);
                return true;
            } catch (Exception e) {
                try {
                    nmsClass.getDeclaredField(nmsField);
                    return true;
                } catch (Exception e1) {
                    return false;
                }
            }
        } catch (MappingsException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Mapping " + name + " is out of range. Tentative Pass.");
            return true;
        }
    }

    /**
     * @param nmsObject The nms object to use to get the field
     * @return Returns an instance of this mappings FieldType If the reflection
     * object is not an instance of {@link #reflectionType} it will return
     * null.
     * @throws MappingsException A {@link MappingsException} is thrown when this
     *                           mapping cannot find a matching mapping for the
     *                           current version
     */
    public Type getField(@Nullable Object nmsObject) throws MappingsException {

        if (nmsClass.isInstance(nmsObject) || nmsObject == null) {

            for (VersionRange range : mappings.keySet()) {

                if (range.isWithinRange(ReflectionApi.version)) {

                    try {
                        Field f = nmsClass.getField(mappings.get(range));
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                            Type value = (Type) f.get(nmsObject);
                            f.setAccessible(false);
                            return value;
                        }
                        return (Type) f.get(nmsObject);

                    } catch (Exception e) {
                        try {
                            Field f = nmsClass.getDeclaredField(mappings.get(range));
                            if (!f.isAccessible()) {
                                f.setAccessible(true);
                                Type value = (Type) f.get(nmsObject);
                                f.setAccessible(false);
                                return value;
                            }
                            return (Type) f.get(nmsObject);
                        } catch (Exception e1) {
                            throw (new MappingsException(this, "Failed to get field", e1));
                        }
                    }

                }


            }
        } else {
            throw (new MappingsException(this, "The nms object is not an instance of " + nmsClass.getName()));
        }

        throw (new MappingsException(this, "Failed to get field: No mapping for current version found"));

    }

    @Override
    public String toString() {
        return "NMSFieldMapping{name: " + name + ", nmsClass: " + nmsClass.getName() + "}";
    }
}
