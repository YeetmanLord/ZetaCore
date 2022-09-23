package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLVarChar extends SQLColumn<String> {

	private int textMaxLength;

	public SQLVarChar(String key, ISQLTable table, int textMaxLength) {

		super(key, table);
		this.textMaxLength = textMaxLength;

	}

	public SQLVarChar(String key, ISQLTable table, int textMaxLength, ColumnSettings settings) {

		super(key, table, settings);
		this.textMaxLength = textMaxLength;

	}

	@Override
	public ArrayList<SQLValue<String>> load(SQLHandler handler) {

		if (handler != null) {
			ArrayList<SQLValue<String>> returnable = new ArrayList<>();
			ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

			for (SQLValue<?> sqlValue : list) {
				returnable.add(new SQLValue<String>(sqlValue.getKey(), (String) sqlValue.getValue()));
			}

			return returnable;

		}

		return null;

	}

	@Override
	public String initialize() {

		return this.getKey() + " VARCHAR(" + String.valueOf(textMaxLength) + ")";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<String> get(PrimaryKeyValue value) {

		return new SQLValue<String>(this.getKey(), (String) getTable().get(value, getKey()).getValue());

	}

}
