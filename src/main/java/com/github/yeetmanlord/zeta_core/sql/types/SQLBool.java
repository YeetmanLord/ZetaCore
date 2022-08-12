package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * Represents a BOOL in SQL
 * 
 * @author YeetManLord
 *
 */
public class SQLBool extends SQLColumn<Boolean> {

	public SQLBool(String key, ISQLTable table) {

		super(key, table);

	}

	public SQLBool(String key, ISQLTable table, ColumnSettings settings) {

		super(key, table, settings);

	}

	@Override
	public String initialize() {

		return this.getKey() + " BOOL";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<Boolean> get(PrimaryKeyValue value) {

		return new SQLValue<Boolean>(getTable().getName(), (Boolean) getTable().get(value, getKey()).getValue());

	}

	@Override
	public ArrayList<SQLValue<Boolean>> load(SQLHandler handler) {

		if (handler != null) {
			ArrayList<SQLValue<Boolean>> returnable = new ArrayList<>();
			ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

			for (SQLValue<?> sqlValue : list) {
				returnable.add(new SQLValue<Boolean>(sqlValue.getKey(), (Boolean) sqlValue.getValue()));
			}

			return returnable;

		}

		return null;

	}

}
