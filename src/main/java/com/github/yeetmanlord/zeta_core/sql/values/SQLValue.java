package com.github.yeetmanlord.zeta_core.sql.values;

/**
 * Representation class for a value in an SQL table
 *
 * @param <Type> Value type, whether that be Integer, Boolean, Double, etc. Must
 *               be a valid SQL-encodeable data type.
 * @author YeetManLord
 */
public class SQLValue<Type> {

    private final String key;

    private Type value;

    public SQLValue(String key, Type value) {

        this.key = key;
        this.value = value;

    }

    public String getKey() {

        return key;

    }

    public Type getValue() {

        return value;

    }

    public void set(Type value) {

        this.value = value;

    }

    public static <T> SQLValue<T> create(String key, T value) {

        return new SQLValue<>(key, value);

    }

    public double getDouble() {

        if (value instanceof Double) {

            return (double) (Object) value;

        }

        throw new IllegalArgumentException("Value is not a double!");
    }

    public int getInt() {

        if (value instanceof Integer) {

            return (int) (Object) value;

        }

        throw new IllegalArgumentException("Value is not an integer!");

    }

    public boolean getBoolean() {

        if (value instanceof Boolean) {

            return (boolean) (Object) value;

        }

        throw new IllegalArgumentException("Value is not a boolean!");
    }

    public String getString() {

        if (value instanceof String) {

            return (String) value;

        }

        throw new IllegalArgumentException("Value is not a string!");
    }

    public float getFloat() {

    	if (value instanceof Float) {

    		return (float) (Object) value;

    	}

    	throw new IllegalArgumentException("Value is not an integer!");

    }

    public long getLong() {

    	if (value instanceof Long) {

    		return (long) (Object) value;

    	}

    	throw new IllegalArgumentException("Value is not a long!");

    }

    public String toString() {

    	return "SQLValue:{" + key + ": " + value + "}";

    }

}
