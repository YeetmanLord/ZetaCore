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

import javax.annotation.Nonnull;

public class ArgumentMethodMapping<ReturnType, ReflectionType extends NMSObjectReflection> implements IMapping<String> {

    public Class<ReflectionType> reflectionType;

    public Map<VersionRange, String> mappings;

    public String name;

    private Class<?>[] argTypes;

    public ArgumentMethodMapping(String name, Class<ReflectionType> type, Map<VersionRange, String> mappings, Class<?>... argTypes) {

        this.reflectionType = type;
        this.mappings = mappings;
        this.argTypes = argTypes;
        this.name = name;
        Mappings.mappings.add(this);

    }

    /**
     * Sets arguement types when the arg is an nms class (which can change)
     *
     * @param type The arguments type (class)
     */
    public void setArgType(Class<?>... type) {

        argTypes = type;

    }

    public void addMapping(VersionRange versionRange, String versionDependentMethodName) {

        this.mappings.put(versionRange, versionDependentMethodName);

    }

    /**
     * @param reflection The reflection object to use to run the method
     * @param args       The arguments of the method
     * @return Returns an instance of this mappings ReturnType if the method has a
     * return. If the reflection object is not an instance of
     * {@link #reflectionType} it will return null.
     * @throws MappingsException A {@link MappingsException} is thrown when this
     *                           mapping cannot find a matching mapping for the
     *                           current version
     */
    public ReturnType runMethod(ReflectionType reflection, Object... args) throws MappingsException {

        try {
            String methodName = get();

            try {
                return (ReturnType) reflection.invokeMethodForNmsObject(methodName, argTypes, args);
            } catch (NoSuchMethodException e) {
                throw (new MappingsException(this, "Method " + methodName + " does not exist in " + reflectionType.getSimpleName()));
            }
        } catch (MappingsException e) {
            throw (new MappingsException(this, "Failed to run method: No mapping for current version found"));
        }

    }

    @Override
    public String toString() {

        return "ArguementMethodMapping{name: " + name + "}";

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
            String methodName = get();
            Class<?> nmsClass;
            try {
                nmsClass = (Class<?>) reflectionType.getField("staticClass").get(null);
            } catch (NoSuchFieldException | ClassCastException | IllegalAccessException e) {
                throw new NMSObjectReflection.ImplementationException(reflectionType, "The reflection type " + reflectionType.getName() + " does not have a staticClass field");
            }
            try {
                nmsClass.getMethod(methodName, argTypes);
            } catch (NoSuchMethodException e) {
                nmsClass.getDeclaredMethod(methodName, argTypes);
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
}
