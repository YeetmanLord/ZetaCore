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

        try {

            return Double.parseDouble(value.toString());

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException("Value is not a double!");

        }
    }

    public int getInt() {

        if (value instanceof Integer) {

            return (int) (Object) value;

        } else if (value instanceof Long) {

            if ((Long) value < Integer.MAX_VALUE) {
                return (int) (long) (Object) value;
            } else {
                throw new IllegalArgumentException("Value is too large to be an integer!");
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
        } else if (value instanceof Integer) {
            return (int) (Object) value == 1;
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

        try {

            return Float.parseFloat(value.toString());

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException("Value is not a float!");

        }


    }

    public long getLong() {

        try {

            return Long.parseLong((String) value);

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException("Value is not a long!");

        }


    }

    public String toString() {

        return "SQLValue:{" + key + ": " + value + "}";

    }

}
