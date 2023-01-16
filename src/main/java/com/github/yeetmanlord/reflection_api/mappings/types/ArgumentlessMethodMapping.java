package com.github.yeetmanlord.reflection_api.mappings.types;

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

public class ArgumentlessMethodMapping<ReturnType, ReflectionType extends NMSObjectReflection> implements IMapping<String> {

    public Class<ReflectionType> reflectionType;

    public Map<VersionRange, String> mappings;

    public String name;

    public ArgumentlessMethodMapping(String name, Class<ReflectionType> type, Map<VersionRange, String> mappings) {

        this.reflectionType = type;
        this.mappings = mappings;
        this.name = name;
        Mappings.mappings.add(this);

    }

    public void addMapping(VersionRange versionRange, String versionDependentMethodName) {

        this.mappings.put(versionRange, versionDependentMethodName);

    }

    public boolean testMapping() {
        try {
            String methodName = get();
            Class<?> nmsClass;
            try {
                nmsClass = (Class<?>) reflectionType.getField("staticClass").get(null);
            } catch (NoSuchFieldException | ClassCastException | IllegalAccessException e) {
                throw new NMSObjectReflection.ImplementationException(reflectionType, "The reflection type " + reflectionType.getName() + " does not have a staticClass field");
            }
            try {
                nmsClass.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                nmsClass.getDeclaredMethod(methodName);
            }
            return true;
        } catch (MappingsException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Mapping " + name + " is out of range. Tentative Pass.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param reflection The reflection object to use to run the method
     * @return Returns an instance of this mappings ReturnType if the method has a
     * return. If the reflection object is not an instance of
     * {@link #reflectionType} it will return null.
     * @throws MappingsException A {@link MappingsException} is thrown when this
     *                           mapping cannot find a matching mapping for the
     *                           current version
     */
    public ReturnType runMethod(ReflectionType reflection) throws MappingsException {

        for (VersionRange range : mappings.keySet()) {

            if (range.isWithinRange(ReflectionApi.version)) {

                try {
                    return (ReturnType) reflection.invokeMethodForNmsObject(mappings.get(range));
                } catch (NoSuchMethodException e) {
                    throw (new MappingsException(this, "Failed to run method", e));
                }

            }

        }

        throw (new MappingsException(this, "Failed to run method: No mapping for current version found"));

    }

    @Override
    public String toString() {

        return "ArguementlessMethodMapping{name: " + name + "}";

    }

    @Override
    public String getName() {

        return name;

    }

    @Override
    public Map<VersionRange, String> getMappings() {

        return mappings;

    }

}
