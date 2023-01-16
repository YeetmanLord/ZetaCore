package com.github.yeetmanlord.reflection_api.mappings.types;

import java.util.Iterator;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;

public class ValueMapping<Type> implements IMapping<Type> {

	public Map<VersionRange, Type> mappings;

	public String name;

	public ValueMapping(String name, Map<VersionRange, Type> mappings) {

		this.mappings = mappings;
		this.name = name;
		Mappings.mappings.add(this);

	}

	@Override
	public String getName() {

		return name;

	}

	@Override
	public Map<VersionRange, Type> getMappings() {

		return this.mappings;

	}

	@Override
	public void addMapping(VersionRange range, Type value) {

		this.mappings.put(range, value);

	}

	public Type getValue() throws MappingsException {

		for (VersionRange range : mappings.keySet()) {

			if (range.isWithinRange(ReflectionApi.version)) {
				return mappings.get(range);
			}

		}
		
		throw new MappingsException(this, "Failed to get mapping value: No mapping for current version found");

	}

	@Override
	public String toString() {

		return "ValueMapping{name: " + name + "}";

	}

	@Override
	public boolean testMapping() {
		return true;
	}
}
