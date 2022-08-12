package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public abstract class SQLColumn<Type> {

	/**
	 * This column's settings
	 */
	protected ColumnSettings settings;

	private ISQLTable table;

	private String key;

	public SQLColumn(String key, ISQLTable table, ColumnSettings settings) {

		this.key = key;
		this.table = table;
		this.settings = settings;

	}

	public SQLColumn(String key, ISQLTable table) {

		this.key = key;
		this.table = table;
		this.settings = ColumnSettings.DEFAULT;

	}

	public String getKey() {

		return key;

	}

	public ISQLTable getTable() {

		return table;

	}

	public abstract ArrayList<SQLValue<Type>> load(SQLHandler handler);

	public abstract String initialize();

	public abstract <PrimaryKeyValue> SQLValue<Type> get(PrimaryKeyValue value);

	public String addSettings() {

		String settings = "";

		if (this.settings.isUnsigned()) {
			settings += "UNSIGNED ";
		}

		if (this.settings.isNonNull()) {
			settings += "NOT NULL ";
		}
		else {
			settings += "NULL ";
		}

		return settings;

	}

	public String createIndex() {

		String index = "";

		if (this.settings.hasIndex()) {

			if (this.settings.isUnique()) {
				index = "UNIQUE INDEX `" + this.key + "_UNIQUE` (`" + this.key + "` ASC) VISIBLE";
			}
			else {
				index = "INDEX `" + this.key + "_INDEX` (`" + this.key + "` ASC) INVISIBLE";
			}

		}

		return index;

	}

}
