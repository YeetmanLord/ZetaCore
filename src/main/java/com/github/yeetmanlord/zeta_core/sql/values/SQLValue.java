package com.github.yeetmanlord.zeta_core.sql.values;

/**
 * Representation class for a value in an SQL table
 * @author YeetManLord
 *
 * @param <Type> Value type, whether that be Integer, Boolean, Double, etc. Must
 *               be a valid SQL-encodable data type.
 */
public class SQLValue<Type> {

	private String key;

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

		return new SQLValue<T>(key, value);

	}

}
