package com.github.yeetmanlord.reflection_api.mappings.types;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;

import java.util.Iterator;
import java.util.Map;

public class PackageMapping implements IMapping<String> {

    private Map<VersionRange, String> mappings;

    private String name;

    public PackageMapping(String name, Map<VersionRange, String> mappings) {

        this.mappings = mappings;
        this.name = name;
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
    public void addMapping(VersionRange range, String nmsSubpackage) {
        mappings.put(range, nmsSubpackage);
    }

    public String getNMSSubPackage() throws MappingsException {
        for (VersionRange range : mappings.keySet()) {
            if (range.isWithinRange(ReflectionApi.version)) {
                String pack = mappings.get(range);
                if (pack.isEmpty()) {
                    return "";
                }
                return mappings.get(range) + ".";
            }
        }
        throw new MappingsException(this, "Failed to get subpackage: No mapping for current version found");
    }

    @Override
    public boolean testMapping() {
        String basePackage = ReflectionApi.getBasePackage();
        try {
            String packageName = get();

            if (packageName.isEmpty()) {
                return true;
            }
            Package pack = Package.getPackage(basePackage + "." +  packageName);

            if (pack == null) {
            } else {
                return true;
            }
        } catch (MappingsException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String toString() {

        return "PackageMapping{name: " + name + "}";

    }
}
