package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLInteger extends SQLColumn<Integer> {

	private int numDigits;

	public SQLInteger(String key, ISQLTable table, int numDigits) {

		super(key, table);
		this.numDigits = numDigits;

	}

	public SQLInteger(String key, ISQLTable table, int numDigits, ColumnSettings settings) {

		super(key, table, settings);
		this.numDigits = numDigits;

	}

	@Override
	public ArrayList<SQLValue<Integer>> load(SQLHandler handler) {

		if (handler != null) {
			ArrayList<SQLValue<Integer>> returnable = new ArrayList<>();
			ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

			for (SQLValue<?> sqlValue : list) {
				returnable.add(new SQLValue<Integer>(sqlValue.getKey(), (Integer) sqlValue.getValue()));
			}

			return returnable;

		}

		return null;

	}

	@Override
	public String initialize() {

		return this.getKey() + " INT(" + String.valueOf(numDigits) + ")";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<Integer> get(PrimaryKeyValue value) {

		return new SQLValue<Integer>(this.getKey(), (Integer) getTable().get(value, getKey()).getValue());

	}

	@Override
	public Integer load(Object value) {
		if (value instanceof SQLValue<?>) {
			value = ((SQLValue<?>)value).getValue();
		}
		if (value instanceof Number) {
			return ((Number)value).intValue();
		} else if (value instanceof String) {
			return Integer.valueOf((String)value);
		} else {
			throw new IllegalArgumentException("Cannot convert " + value.getClass().getName() + " to Integer");
		}
	}
}
