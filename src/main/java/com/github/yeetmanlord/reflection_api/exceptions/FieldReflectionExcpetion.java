package com.github.yeetmanlord.reflection_api.exceptions;

import com.github.yeetmanlord.reflection_api.util.ReflectedField;

public class FieldReflectionExcpetion extends Exception {

	private static final long serialVersionUID = 3186035366649954124L;

	public FieldReflectionExcpetion() {

		super();

	}

	public FieldReflectionExcpetion(ReflectedField<?> refl) {

		super(refl.getFieldName());

	}

	public FieldReflectionExcpetion(String s) {

		super(s);

	}

	public FieldReflectionExcpetion(ReflectedField<?> refl, String s) {

		super(s + ": " + refl.getFieldName());

	}

}
