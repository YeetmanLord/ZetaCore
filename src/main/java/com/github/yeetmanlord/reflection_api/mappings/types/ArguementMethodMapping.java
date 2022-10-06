package com.github.yeetmanlord.reflection_api.mappings.types;

import java.util.Iterator;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;

public class ArguementMethodMapping<ReturnType> implements IMapping<String> {

	public Class<? extends NMSObjectReflection> reflectionType;

	public Map<VersionRange, String> mappings;

	public String name;

	private Class<?>[] argTypes;

	public ArguementMethodMapping(String name, Class<? extends NMSObjectReflection> type, Map<VersionRange, String> mappings, Class<?>... argTypes) {

		this.reflectionType = type;
		this.mappings = mappings;
		this.argTypes = argTypes;
		this.name = name;

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
	 * 
	 * @param reflection The reflection object to use to run the method
	 * @param args       The arguments of the method
	 * @return Returns an instance of this mappings ReturnType if the method has a
	 *         return. If the reflection object is not an instance of
	 *         {@link #reflectionType} it will return null.
	 * @throws MappingsException A {@link MappingsException} is thrown when this
	 *                           mapping cannot find a matching mapping for the
	 *                           current version
	 */
	public ReturnType runMethod(NMSObjectReflection reflection, Object... args) throws MappingsException {

		if (reflectionType.isInstance(reflection)) {

			for (VersionRange range : mappings.keySet()) {

				if (range.isWithinRange(ReflectionApi.version)) {

					try {
						return (ReturnType) reflection.invokeMethodForNmsObject(mappings.get(range), argTypes, args);
					}
					catch (NoSuchMethodException e) {
					}

				}

			}

		}

		throw (new MappingsException(this, "Failed to run method"));

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

		return "ArguementMethodMapping{name: " + name + ", mappings: " + maps + "}";

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
