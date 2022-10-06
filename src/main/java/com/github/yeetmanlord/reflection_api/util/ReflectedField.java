package com.github.yeetmanlord.reflection_api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.exceptions.FieldReflectionExcpetion;

public class ReflectedField<Type> {

	private final NMSObjectReflection holder;

	private final String fieldName;

	private final boolean isFinal;

	private final boolean isStatic;

	private final boolean isPrivate;

	private final boolean nmsObject;

	public ReflectedField(Field fieldReflection, boolean isPrivate, boolean nmsObject, NMSObjectReflection holder) {

		this.nmsObject = nmsObject;
		this.holder = holder;
		this.isPrivate = isPrivate;
		isFinal = Modifier.isFinal(fieldReflection.getModifiers());
		isStatic = Modifier.isStatic(fieldReflection.getModifiers());
		fieldName = fieldReflection.getName();

	}

	public ReflectedField(String fieldName, boolean isPrivate, boolean nmsObject, NMSObjectReflection holder) throws FieldReflectionExcpetion {

		this.nmsObject = nmsObject;
		this.holder = holder;
		this.isPrivate = isPrivate;
		Field fieldReflection = null;
		this.fieldName = fieldName;

		try {
			fieldReflection = holder.getField(fieldName);
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		if (fieldReflection == null) {
			throw new FieldReflectionExcpetion("The specified field name, " + fieldName + " is not a field in class " + holder.getNmsObject().getClass());
		}

		isFinal = Modifier.isFinal(fieldReflection.getModifiers());
		isStatic = Modifier.isStatic(fieldReflection.getModifiers());

	}

	public void set(Type value) throws FieldReflectionExcpetion {

		if (isFinal) {
			throw new FieldReflectionExcpetion(this, "You cannot modify the final field");
		}

		try {
			Field field = holder.getField(fieldName);

			if (isPrivate) {
				field.setAccessible(true);
			}

			if (isStatic) {

				if (nmsObject) {
					if (value == null) {
						field.set(null, null);
					}
					else {
						field.set(null, ((NMSObjectReflection) value).getNmsObject());
					}
				}
				else {
					field.set(null, value);
				}

			}
			else {

				if (nmsObject) {
					if (value == null) {
						field.set(holder.getNmsObject(), null);
					}
					else {
						field.set(holder.getNmsObject(), ((NMSObjectReflection) value).getNmsObject());
					}
				}
				else {
					field.set(holder.getNmsObject(), value);
				}

			}

			if (isPrivate) {
				field.setAccessible(isFinal);
			}

		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public Type get() throws FieldReflectionExcpetion {

		try {
			Field field = holder.getField(fieldName);

			if (isPrivate) {
				field.setAccessible(true);
			}
			Type value;
			if (isStatic) {
				value = (Type) field.get(null);
			}
			else {
				value = (Type) field.get(holder.getNmsObject());
			}

			if (isPrivate) {
				field.setAccessible(isFinal);
			}

			return value;

		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		throw new FieldReflectionExcpetion(this, "Could not get field");

	}

	public String getFieldName() {

		return fieldName;

	}

	public boolean isFinal() {

		return isFinal;

	}

	public boolean isStatic() {

		return isStatic;

	}

	public boolean isPrivate() {

		return isPrivate;

	}

	public boolean isNmsObject() {

		return nmsObject;

	}

}
