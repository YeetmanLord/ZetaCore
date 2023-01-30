package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.ISQL;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * Same as {@link AbstractSQLDataStorer} except this does not hava a physical
 * file and only manages an SQL table. This is what you would use to create a related sub table.
 *
 * @param <PrimaryKeyType> The type of the primary key. Usually either String or
 *                         int
 * @author YeetManLord
 */
public abstract class AbstractSQLTableHandler<PrimaryKeyType> implements ISQLTableHandler<PrimaryKeyType> {

    protected ISQLTable table;

    protected String tableName;

    public AbstractSQLTableHandler(ZetaPlugin pl, String tableName) {

        this.tableName = pl.getPluginName().toLowerCase() + "/" + tableName;

    }

    @Override
    public ISQLTable getTable() {

        return table;

    }

    @Override
    public void setTable(ISQLTable table) {
        this.table = table;
    }

    @Override
    public String getTableName() {
        return tableName;
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

    }

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

    @Override
    public String toString() {
        return "AbstractSQLTableHandler{" +
                "table=" + table +
                ", tableName='" + tableName + '\'' +
                '}';
    }

    @Override
    public boolean doesRequireDataInit() {
        return true;
    }
}
