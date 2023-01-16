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

    private String getter, setter;

    public ReflectedField(Field fieldReflection, boolean isPrivate, boolean nmsObject, NMSObjectReflection holder) {

        this.nmsObject = nmsObject;
        this.holder = holder;
        this.isPrivate = isPrivate;
        isFinal = Modifier.isFinal(fieldReflection.getModifiers());
        isStatic = Modifier.isStatic(fieldReflection.getModifiers());
        fieldName = fieldReflection.getName();

    }

    public ReflectedField(Field fieldReflection, boolean isPrivate, boolean nmsObject, NMSObjectReflection holder, String getterName, String setterName) {

        this(fieldReflection, isPrivate, false, holder);
        this.getter = getterName;
        this.setter = setterName;

    }

    public ReflectedField(String fieldName, boolean isPrivate, boolean nmsObject, NMSObjectReflection holder) throws FieldReflectionExcpetion {

        this.nmsObject = nmsObject;
        this.holder = holder;
        this.isPrivate = isPrivate;
        Field fieldReflection = null;
        this.fieldName = fieldName;

        try {
            fieldReflection = holder.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (fieldReflection == null) {
            throw new FieldReflectionExcpetion("The specified field name, " + fieldName + ", is not a field in class " + holder.getNMSObject().getClass());
        }

        isFinal = Modifier.isFinal(fieldReflection.getModifiers());
        isStatic = Modifier.isStatic(fieldReflection.getModifiers());

    }

    public void set(Type value) throws FieldReflectionExcpetion {

        if (isFinal) {
            throw new FieldReflectionExcpetion(this, "You cannot modify the final field");
        }

        if (setter != null && !setter.isEmpty()) {
            try {
                holder.invokeMethodForNmsObject(setter, new Class[]{value.getClass()}, new Object[]{value});
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    } else {
                        field.set(null, ((NMSObjectReflection) value).getNMSObject());
                    }
                } else {
                    field.set(null, value);
                }

            } else {

                if (nmsObject) {
                    if (value == null) {
                        field.set(holder.getNMSObject(), null);
                    } else {
                        field.set(holder.getNMSObject(), ((NMSObjectReflection) value).getNMSObject());
                    }
                } else {
                    field.set(holder.getNMSObject(), value);
                }

            }

            if (isPrivate) {
                field.setAccessible(isFinal);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    public Type get() throws FieldReflectionExcpetion {

        if (getter != null && !getter.isEmpty()) {
            try {
                return (Type) holder.invokeMethodForNmsObject(getter);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Field field = holder.getField(fieldName);

                if (isPrivate) {
                    field.setAccessible(true);
                }
                Type value;
                if (isStatic) {
                    value = (Type) field.get(null);
                } else {
                    value = (Type) field.get(holder.getNMSObject());
                }

                if (isPrivate) {
                    field.setAccessible(isFinal);
                }

                return value;

            } catch (Exception exc) {
                exc.printStackTrace();
            }
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
