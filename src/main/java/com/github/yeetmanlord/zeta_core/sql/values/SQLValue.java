package com.github.yeetmanlord.zeta_core.sql.values;

import com.github.yeetmanlord.zeta_core.api.util.CommandUtil;

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
        if (this.value instanceof Number) {
            return ((Number) this.value).doubleValue();
        } else if (this.value instanceof String) {
            try {
                return Double.parseDouble((String) this.value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value is not a double!");
            }
        }
        throw new IllegalArgumentException("Value is not a double!");
    }

    public int getInt() {

        if (value instanceof Number) {
            try {
                return ((Number) value).intValue();
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Number cannot be cast to integer!");
            }
        } else if (value instanceof String) {

            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value is not an integer!");
            }

        }

        throw new IllegalArgumentException("Value is not an integer!");

    }

    public boolean getBoolean() {

        if (value instanceof Boolean) {
            return (boolean) (Object) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        } else if (value instanceof Number) {
            try {
                return ((Number) value).intValue() == 1;
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Value is not a boolean!");
            }
        }

        throw new IllegalArgumentException("Value is not a boolean!");

    }

    public String getString() {

        if (value instanceof String) {

            return (String) value;

        }

        if (value == null) {
            return "null";
        }
        return value.toString();
    }

    public float getFloat() {

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            try {
                return Float.parseFloat((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value is not a float!");
            }
        }
        throw new IllegalArgumentException("Value is not a float!");

    }

    public long getLong() {

        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value is not a long!");
            }
        }
        throw new IllegalArgumentException("Value is not a long!");

    }

    public String toString() {

        return "SQLValue:{" + key + ": " + value + "}";

    }

}
