package com.github.yeetmanlord.reflection_api.mappings.types;

import java.util.Iterator;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;

public class ClassNameMapping implements IMapping<String> {

	public Map<VersionRange, String> mappings;

	public String name;

	public ClassNameMapping(String name, Map<VersionRange, String> mappings) {

		this.mappings = mappings;
		this.name = name;
		Mappings.mappings.add(this);

	}

	public void addMapping(VersionRange versionRange, String versionDependentNMSClassName) {

		this.mappings.put(versionRange, versionDependentNMSClassName);

	}

	public Class<?> getNMSClassMapping() throws MappingsException {

		for (VersionRange range : mappings.keySet()) {

			if (range.isWithinRange(ReflectionApi.version)) {

				Class<?> clazz = ReflectionApi.getNMSClass(mappings.get(range));

				if (clazz == null) {
					continue;
				} else {
					return clazz;
				}

			}

		}

		throw (new MappingsException(this, "Failed to get class"));

	}

	@Override
	public String toString() {

		String maps = "Mappings[";
		Iterator<VersionRange> iter = mappings.keySet().iterator();

		while (iter.hasNext()) {
			VersionRange range = iter.next();

			if (iter.hasNext()) {
				maps += "(versionRange: " + range.toString() + ", mappingValue: " + mappings.get(range) + "), ";
			}
			else {
				maps += "(versionRange: " + range.toString() + ", mappingValue: " + mappings.get(range) + ")]";
			}

		}

		for (int x = 0; x < mappings.keySet().size(); x++) {
		}

		return "ClassNameMapping{name: " + name + ", mappings: " + maps + "}";

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
			getNMSClassMapping();
			return true;
		} catch (MappingsException e) {
			return false;
		}
	}
}
