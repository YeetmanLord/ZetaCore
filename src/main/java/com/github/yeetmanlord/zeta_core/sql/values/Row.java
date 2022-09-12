package com.github.yeetmanlord.zeta_core.sql.values;

import java.util.HashMap;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;

/**
 * 
 * This type extends {@link HashMap} and is here
 * just to condense that into an easier form just for ease of use. {@link Row}
 * also contains {@link #createRow(Map)} just to convert normal maps into rows
 * more quickly. I am not sure if you would use this in your extention APIs, but
 * I just use it when dealing with more raw rows in {@link SQLHandler} as well
 * as {@link ISQLTableHandler#writeDB() writing to tables}
 * 
 * @author YeetManLord
 */
public class Row extends HashMap<String, SQLValue<?>> {

	private static final long serialVersionUID = -2488093439597381250L;

	/**
	 * Converts a {@link Map} into a Row. Ease of use function
	 * 
	 * @param map Generic map of String key, and {@link SQLValue} value
	 * @return A map converted to a {@link Row row}
	 */
	public static Row createRow(Map<String, SQLValue<?>> map) {

		Row row = new Row();

		for (String key : map.keySet()) {
			row.put(key, map.get(key));
		}

		return row;

	}

	public <T> void put(String key, T value) {

		this.put(key, SQLValue.create(key, value));

	}

	public Object getValue(String key) {

		return this.get(key).getValue();

	}

	@Override
	public Row clone() {

		return Row.createRow(this);

	}

	public double getDouble(String key) {

		return this.get(key).getDouble();

	}

	public int getInt(String key) {

		return this.get(key).getInt();

	}

	public String getString(String key) {

		return this.get(key).getString();

	}

	public boolean getBoolean(String key) {

		return this.get(key).getBoolean();

	}

	public float getFloat(String key) {

		return this.get(key).getFloat();

	}

}
