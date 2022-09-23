package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLText extends SQLColumn<String> {

	private int textMaxLength;

	public SQLText(String key, ISQLTable table, int textMaxLength) {

		super(key, table);
		this.textMaxLength = textMaxLength;

	}

	public SQLText(String key, ISQLTable table, int textMaxLength, ColumnSettings settings) {

		super(key, table, settings);
		this.textMaxLength = textMaxLength;

		if (settings.hasIndex() && textMaxLength > 768) {
			ZetaCore.LOGGER.error("SQL ERROR: KEY LENGTH FOR SQL LENGTH IS TOO BIG TO BE INDEXED!!!");
		}

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

		return this.getKey() + " TEXT(" + String.valueOf(textMaxLength) + ")";

	}

	@Override
	public <PrimaryKeyValue> SQLValue<String> get(PrimaryKeyValue value) {

		return new SQLValue<String>(this.getKey(), (String) getTable().get(value, getKey()).getValue());

	}

	@Override
	public String createIndex() {

		String index = "";

		if (this.settings.hasIndex()) {

			if (this.settings.isUnique()) {
				index = "UNIQUE INDEX `" + this.getKey() + "_UNIQUE` (`" + this.getKey() + "(" + String.valueOf(this.textMaxLength) + ")` ASC) VISIBLE";
			}
			else {
				index = "INDEX `" + this.getKey() + "_INDEX` (`" + this.getKey() + "` (" + String.valueOf(this.textMaxLength) + ") ASC) INVISIBLE";
			}

		}

		return index;

	}

}
