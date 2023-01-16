package com.github.yeetmanlord.reflection_api.exceptions;

import com.github.yeetmanlord.reflection_api.mappings.IMapping;

public class MappingsException extends Exception {

	private static final long serialVersionUID = 1134534421L;

	public MappingsException(IMapping<?> mapping) {

		this(mapping, "This means your plugin uses an unsupported version or mappings have not been added for your version");

	}

	public MappingsException(IMapping<?> mapping, String extraInfo) {

		super("Failed to load " + mapping.getName() + "\nFull Data: " + mapping.toString() + "\n" + extraInfo);

	}
	public MappingsException(IMapping<?> mapping, String extraInfo, Throwable cause) {

		super("Failed to load " + mapping.getName() + "\nFull Data: " + mapping.toString() + "\n" + extraInfo, cause);

	}

}
