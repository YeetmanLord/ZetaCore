package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLFloat extends SQLColumn<Float> {

	private int digits;

	public SQLFloat(String key, ISQLTable table, int digits) {

		super(key, table);
		this.digits = digits;

	}

	public SQLFloat(String key, ISQLTable table, int digits, ColumnSettings settings) {

		super(key, table, settings);
		this.digits = digits;

	}

	@Override
	public ArrayList<SQLValue<Float>> load(SQLHandler handler) {

		if (handler != null) {
			ArrayList<SQLValue<Float>> returnable = new ArrayList<>();
			ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

			for (SQLValue<?> sqlValue : list) {
				returnable.add(new SQLValue<Float>(sqlValue.getKey(), (Float) sqlValue.getValue()));
			}

			return returnable;

		}

		return null;

	}

	@Override
	public String initialize() {

		return this.getKey() + " FLOAT(" + String.valueOf(digits) + ")";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<Float> get(PrimaryKeyValue value) {

		return new SQLValue<Float>(getTable().getName(), (Float) getTable().get(value, getKey()).getValue());

	}

}
