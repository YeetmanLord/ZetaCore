package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.ISQL;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.ISQLObjectHandler;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * This is a type of {@link ISQLTableHandler} where there is no associated
 * {@link ISQLObjectHandler} and rather than handling java objects this
 * {@link DataStorer data storer} just stores general data in the database
 * 
 * @zeta.usage INTERNAL
 * 
 * @author YeetManLord
 *
 */
public abstract class AbstractUntypedSQLDataStorer<PrimaryKeyType> extends DataStorer implements ISQLTableHandler<PrimaryKeyType> {

	protected ISQLTable table;

	protected String tableName;

	public AbstractUntypedSQLDataStorer(ZetaPlugin instanceIn, String name, String tableName) {

		super(instanceIn, name);
		this.tableName = tableName;

	}

	@Override
	public ISQLTable getTable() {

		return this.table;

	}

	@Override
	public Map<PrimaryKeyType, ISQL<?>> getData() {

		return null;

	}

	@Override
	public ISQL<?> get(PrimaryKeyType primaryKey) {

		return null;

	}

	@Override
	public void initializeDB(SQLHandler handler) {

		this.table = new SQLTable(tableName, handler);
		this.table.setPrimaryKey(this.getPrimaryKey());
		this.table.setColumns(getColumns(handler));
		this.table.initializeTable(handler);

		if (handler.getEntrySize(tableName) <= 0) {
			this.writeDB();
		}

	}

	public abstract List<SQLColumn<?>> getColumns(SQLHandler handler);

	public ISQLObjectHandler<?> getHandler() {

		return null;

	}

	public List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck) {

		return null;

	}

}
