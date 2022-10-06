package com.github.yeetmanlord.reflection_api.mappings.types;

import java.util.Iterator;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;

public class FieldMapping<FieldType> implements IMapping<String> {

	public Class<? extends NMSObjectReflection> reflectionType;

	public Map<VersionRange, String> mappings;

	public String name;

	public FieldMapping(String name, Class<? extends NMSObjectReflection> type, Map<VersionRange, String> mappings) {

		this.reflectionType = type;
		this.mappings = mappings;
		this.name = name;

	}

	public void addMapping(VersionRange versionRange, String versionDependentFieldName) {

		this.mappings.put(versionRange, versionDependentFieldName);

	}

	/**
	 * 
	 * @param reflection The reflection object to use to get the field
	 * @return Returns an instance of this mappings FieldType If the reflection
	 *         object is not an instance of {@link #reflectionType} it will return
	 *         null.
	 * @throws MappingsException A {@link MappingsException} is thrown when this
	 *                           mapping cannot find a matching mapping for the
	 *                           current version
	 */
	public FieldType getField(NMSObjectReflection reflection) throws MappingsException {

		if (reflectionType.isInstance(reflection)) {

			for (VersionRange range : mappings.keySet()) {

				if (range.isWithinRange(ReflectionApi.version)) {

					try {
						return (FieldType) reflection.getFieldFromNmsObject(mappings.get(range));
					}
					catch (NoSuchFieldException e) {
					}

				}

			}

		}

		throw (new MappingsException(this, "Failed to get field"));

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

		return "FieldMapping{name: " + name + ", mappings: " + maps + "}";

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
