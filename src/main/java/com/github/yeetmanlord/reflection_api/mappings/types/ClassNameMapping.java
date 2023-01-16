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

public class ClassNameMapping implements IMapping<String> {

    public Map<VersionRange, String> mappings;

    public String name;

    private PackageMapping pack;

    public ClassNameMapping(String name, PackageMapping packageMapping, Map<VersionRange, String> mappings) {

        this.mappings = mappings;
        this.name = name;
        Mappings.mappings.add(this);
        this.pack = packageMapping;

    }

    public void addMapping(VersionRange versionRange, String versionDependentNMSClassName) {

        this.mappings.put(versionRange, versionDependentNMSClassName);

    }

    public Class<?> getNMSClassMapping() throws MappingsException {
        Class<?> clazz;
        try{
             clazz = ReflectionApi.getNMSClass(pack, get());
        } catch (MappingsException exception) {
            throw (new MappingsException(this, "Failed to get class: No mapping for current version found"));
        }

        if (clazz == null) {
            throw (new MappingsException(this, "Failed to get class, class was not found"));
        } else {
            return clazz;
        }


    }

    @Override
    public String toString() {

        return "ClassNameMapping{name: " + name + ", packageMapping: `" + pack.getName() + "`}";

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
            Class<?> clazz = ReflectionApi.getNMSClass(pack, get());

            if (clazz == null) {
                return false;
            } else {
                return true;
            }

        } catch (MappingsException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Mapping " + name + " is out of range. Tentative Pass.");
            return true;
        }
    }

    public PackageMapping getPackageMapping() {
        return pack;
    }
}
