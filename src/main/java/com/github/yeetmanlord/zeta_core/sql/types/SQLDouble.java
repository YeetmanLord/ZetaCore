package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLDouble extends SQLColumn<Double> {

	private int digits;

	private int afterDecimal;

	public SQLDouble(String key, ISQLTable table, int digits, int afterDecimal) {

		super(key, table);
		this.digits = digits;
		this.afterDecimal = afterDecimal;

	}

	public SQLDouble(String key, ISQLTable table, int digits, int afterDecimal, ColumnSettings settings) {

		super(key, table, settings);
		this.digits = digits;
		this.afterDecimal = afterDecimal;

	}

	@Override
	public ArrayList<SQLValue<Double>> load(SQLHandler handler) {

		if (handler != null) {
			ArrayList<SQLValue<Double>> returnable = new ArrayList<>();
			ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

			for (SQLValue<?> sqlValue : list) {
				returnable.add(new SQLValue<Double>(sqlValue.getKey(), (Double) sqlValue.getValue()));
			}

			return returnable;

		}

		return null;

	}

	@Override
	public String initialize() {

		return this.getKey() + " DOUBLE(" + String.valueOf(digits) + ", " + String.valueOf(afterDecimal) + ")";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<Double> get(PrimaryKeyValue value) {

		return new SQLValue<Double>(this.getKey(), (Double) getTable().get(value, getKey()).getValue());

	}

}
