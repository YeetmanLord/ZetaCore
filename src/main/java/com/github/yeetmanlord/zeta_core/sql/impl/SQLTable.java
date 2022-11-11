package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.ColumnSettings;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

public class SQLTable implements ISQLTable {

    private String primaryKey;

    private String tableName;

    private LinkedHashMap<String, SQLColumn<?>> columns;

    private SQLHandler handler;

    public SQLTable(String primaryKey, String tableName, SQLHandler handler) {

        columns = new LinkedHashMap<>();
        this.primaryKey = primaryKey;
        this.tableName = tableName;
        this.handler = handler;

    }

    public SQLTable(String tableName, SQLHandler handler) {

        columns = new LinkedHashMap<>();
        this.tableName = tableName;
        this.handler = handler;

    }

    @Override
    public HashMap<String, SQLColumn<?>> getColumns() {

        return this.columns;

    }

    @Override
    public String getPrimary() {

        return primaryKey;

    }

    @Override
    public String getName() {

        return tableName;

    }

    public <PrimaryKeyType> SQLValue<?> get(PrimaryKeyType primaryKeyValue, String columnToGet) {

        return handler.query(getPrimary(), primaryKeyValue, getName(), columnToGet);

    }

    @Override
    public <PrimaryKeyType> Row getRow(PrimaryKeyType primaryKeyValue) {

        return handler.getRow(this, primaryKeyValue);

    }

    public void setPrimaryKey(String key) {

        this.primaryKey = key;

    }

    @Override
    public List<Row> getRows() {

        List<Row> rows = new ArrayList<>();

        List<SQLValue<?>> primaryKeys = this.handler.getColumnEntries(tableName, primaryKey);

        for (SQLValue<?> value : primaryKeys) {
            rows.add(this.getRow(value.getValue()));
        }

        return rows;

    }

    @Override
    public void setColumns(List<SQLColumn<?>> columns) {

        for (SQLColumn<?> sqlColumn : columns) {
            if (sqlColumn.getKey().equalsIgnoreCase(this.primaryKey) && !sqlColumn.getSettings().isNonNull()) {
                ColumnSettings settings = sqlColumn.getSettings();
                if (!settings.isUnique()) {
                    settings.setNonNull(true);
                    sqlColumn.applySettings(settings);
                }
            }
            this.columns.put(sqlColumn.getKey(), sqlColumn);
        }

    }

    public void writeValue(Object... args) {
        writeValue(false, args);
    }

    public void writeValue(boolean async, Object... args) {

        String params = "";

        int x = 0;

        for (String key : columns.keySet()) {
            SQLColumn<?> col = columns.get(key);

            if (x == columns.size() - 1) {
                params += col.getKey();
            } else {
                params += col.getKey() + ", ";
            }

            x++;
        }

        params.trim();
        this.handler.replaceInto(tableName, params, async, args);

    }

    public void writeValue(Row row, boolean async) {
        String params = "";
        Object[] values = new Object[row.size()];

        int x = 0;

        for (String key : row.keySet()) {

            SQLValue<?> value = row.get(key);
            values[x] = value.getValue();

            if (x == row.size() - 1) {
                params += value.getKey();
            } else {
                params += value.getKey() + ", ";
            }

            x++;

        }

        params.trim();
        this.handler.replaceInto(tableName, params, async, values);
    }

    /**
     * By default, synchronous
     *
     * @param row {@link Row} of data to write
     */
    public void writeValue(Row row) {

        this.writeValue(row, false);

    }

    @Override
    public SQLHandler getHandler() {

        return handler;

    }

    public List<SQLValue<?>> getColumn(String column) {

        return this.handler.getColumnEntries(this.tableName, column);

    }

    /**
     * By default, asynchronous
     *
     * @param primaryKeyValue Value of the primary key
     */
    public void removeRow(Object primaryKeyValue) {
        this.removeRow(primaryKeyValue, true);
    }

    public void removeRow(Object primaryKeyValue, boolean async) {
        this.handler.removeRow(this.tableName, this.primaryKey, primaryKeyValue, async);
    }

    public void removeRowWhere(String column, Object value) {
        this.removeRowWhere(column, value, true);
    }

    public void removeRowWhere(String column, Object value, boolean async) {
        this.handler.removeRow(this.tableName, column, value, async);
    }

    public void drop() {
        this.drop(true);
    }

    public void drop(boolean async) {
        this.handler.dropTable(this.tableName, async);
    }

    public void update(String column, SQLValue<?> value, SQLValue<?> primaryKeyValue) {
        update(column, value, primaryKeyValue, true);
    }

    public void update(String column, SQLValue<?> value, SQLValue<?> primaryKeyValue, boolean async) {
        this.handler.updateWhere(this.tableName, column, value, this.primaryKey, primaryKeyValue, async);
    }

    public void update(String column, SQLValue<?> value, String whereColumn, SQLValue<?> whereValue) {
        update(column, value, whereColumn, whereValue, true);
    }

    public void update(String column, SQLValue<?> value, String whereColumn, SQLValue<?> whereValue, boolean async) {
        this.handler.updateWhere(this.tableName, column, value, whereColumn, whereValue, async);
    }

    @Override
    public ArrayList<Row> getAllData() {
        return this.handler.getAllData(this);
    }

    @Override
    public String toString() {
        return "SQLTable{primaryKey: " + primaryKey + ", tableName: " + tableName + ", columns: " + columns + "}";
    }
}
