package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.ISQL;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import org.bukkit.Bukkit;

public abstract class AbstractSQLDataStorer<PrimaryKeyType> extends DataStorer implements ISQLTableHandler<PrimaryKeyType> {

	protected ISQLTable table;

	protected String tableName;

	@Override
	public ISQLTable getTable() {

		return table;

	}

	public AbstractSQLDataStorer(ZetaPlugin instanceIn, String name, String tableName) {

		super(instanceIn, name);
		this.tableName = instanceIn.getPluginName().toLowerCase() + "/" + tableName;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<PrimaryKeyType, ISQL<?>> getData() {

		List<Row> list = this.table.getRows();
		Map<PrimaryKeyType, ISQL<?>> iSQLValues = new HashMap<>();

		for (Row map : list) {
			SQLValue<?> primaryKey = map.get(this.getPrimaryKey());
			iSQLValues.put((PrimaryKeyType) primaryKey.getValue(), this.getHandler().load(map));
		}

		return iSQLValues;

	}

	@Override
	public void initializeDB(SQLHandler handler) {

		this.table = new SQLTable(tableName, handler);
		this.table.setPrimaryKey(this.getPrimaryKey());
		this.table.setColumns(getColumns(handler));
		this.table.initializeTable(handler);

		if (this.table.getRows().isEmpty()) {
			this.read();
			this.writeToDB();
		}

		this.readDB();

	}

	public abstract List<SQLColumn<?>> getColumns(SQLHandler handler);

	@Override
	public ISQL<?> get(PrimaryKeyType primaryKey) {

		return getData().get(primaryKey);

	}

	public List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck) {

		List<ISQL<?>> iSQLs = new ArrayList<>();

		List<Row> list = this.table.getHandler().getRowsWhere(this.table, valueToCheck.getKey(), valueToCheck.getValue());

		for (Row map : list) {
			iSQLs.add(this.getHandler().load(map));
		}

		return iSQLs;

	}

}
