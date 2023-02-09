package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLBatchStatement;
import com.github.yeetmanlord.zeta_core.sql.types.ColumnSettings;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import com.github.yeetmanlord.zeta_core.sql.values.RowList;

public class SQLTable implements ISQLTable {

    private String primaryKey;

    private String tableName;

    private LinkedHashMap<String, SQLColumn<?>> columns;

    private SQLHandler handler;

    private SQLBatchStatement batch;

    private SQLBatchStatement updateBatch;

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

        SQLValue<?> queryResult = handler.queryFirst(getPrimary(), primaryKeyValue, getName(), columnToGet);

        if (queryResult != null) {
            SQLColumn<?> col = this.columns.get(columnToGet);
            if (col != null) {
                return new SQLValue<>(columnToGet, col.load(queryResult.getValue()));
            }
        }

        return null;

    }

    @Override
    public <PrimaryKeyType> Row getRow(PrimaryKeyType primaryKeyValue) {

        Row raw = handler.getRow(this, primaryKeyValue);
        Row wrapped = new Row();

        for (String key : raw.keySet()) {
            SQLColumn<?> col = this.columns.get(key);
            if (col != null) {
                wrapped.put(key, col.load(raw.get(key).getValue()));
            }
        }

        return wrapped;

    }

    public void setPrimaryKey(String key) {

        this.primaryKey = key;

    }

    @Override
    public RowList getRows() {

        RowList raw = this.handler.getAllData(this);
        RowList wrapped = new RowList();

        for (Row row : raw) {
            Row newRow = new Row();
            for (String key : row.keySet()) {
                SQLColumn<?> col = this.columns.get(key);
                if (col != null) {
                    newRow.put(key, col.load(row.get(key).getValue()));
                }
            }
            wrapped.add(newRow);
        }

        return wrapped;

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

        String params = "";
        Object[] translatedArgs = new Object[args.length];

        int x = 0;

        for (String key : columns.keySet()) {
            SQLColumn<?> col = columns.get(key);

            translatedArgs[x] = col.write(args[x]);
            if (x == columns.size() - 1) {
                params += col.getKey();
            } else {
                params += col.getKey() + ", ";
            }

            x++;
        }

        params = params.trim();
        this.batch = this.handler.addReplaceInto(this.batch, tableName, params, translatedArgs);

    }

    public void writeValue(Row row) {
        String params = "";
        Object[] values = new Object[row.size()];

        int x = 0;

        for (String key : row.keySet()) {

            SQLValue<?> value = row.get(key);
            SQLColumn<?> col = columns.get(key);
            values[x] = col.write(value.getValue());

            if (x == row.size() - 1) {
                params += value.getKey();
            } else {
                params += value.getKey() + ", ";
            }

            x++;

        }

        params = params.trim();
        this.batch = this.handler.addReplaceInto(this.batch, tableName, params, values);
    }

    public void commit(boolean async) {
        if (this.batch == null) return;
        this.batch.execute(this.handler, async);
    }

    public void commit() {
        this.commit(false);
    }

    @Override
    public SQLHandler getHandler() {

        return handler;

    }

    public List<SQLValue<?>> getColumn(String column) {

        List<SQLValue<?>> values = this.handler.getColumnEntries(this.tableName, column);
        List<SQLValue<?>> translated = new ArrayList<>();

        SQLColumn<?> col = this.columns.get(column);
        for (SQLValue<?> value : values) {
            translated.add(new SQLValue<>(value.getKey(), col.load(value.getValue())));
        }

        return translated;

    }

    public void removeRow(Object primaryKeyValue) {
        this.removeRow(primaryKeyValue, true);
    }

    public void removeRow(Object primaryKeyValue, boolean async) {
        SQLColumn<?> col = this.columns.get(this.primaryKey);
        this.handler.removeRow(this.tableName, this.primaryKey, col.write(primaryKeyValue), async);
    }

    public void removeRowWhere(String column, Object value) {
        this.removeRowWhere(column, value, true);
    }

    public void removeRowWhere(String column, Object value, boolean async) {
        SQLColumn<?> col = this.columns.get(column);
        this.handler.removeRow(this.tableName, column, col.write(value), async);
    }

    public void drop() {
        this.drop(true);
    }

    public void drop(boolean async) {
        this.handler.dropTable(this.tableName, async);
    }

    public void update(String column, Object value, Object primaryKeyValue) {
        update(column, value, primaryKeyValue, true);
    }

    public void update(String column, Object value, Object primaryKeyValue, boolean async) {
        SQLColumn<?> col = this.columns.get(column);
        SQLColumn<?> whereCol = this.columns.get(this.primaryKey);
        this.handler.updateWhere(this.tableName, new SQLValue<>(column, col.write(value)), SQLValue.create(this.primaryKey, whereCol.write(primaryKeyValue)), async);
    }

    public void update(String column, Object value, String whereColumn, Object whereValue) {
        update(column, value, whereColumn, whereValue, true);
    }

    public void update(String column, Object value, String whereColumn, Object whereValue, boolean async) {
        SQLColumn<?> col = this.columns.get(column);
        SQLColumn<?> whereCol = this.columns.get(whereColumn);
        this.handler.updateWhere(this.tableName, SQLValue.create(column, col.write(value)),
                SQLValue.create(whereColumn, whereCol.write(whereValue)), async);
    }

    @Override
    public String toString() {
        return "SQLTable{primaryKey: " + primaryKey + ", tableName: " + tableName + ", columns: " + columns + "}";
    }

    public void setHandler(SQLHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean isEmpty(SQLHandler handler) {
        return handler.getEntrySize(this.getName()) <= 0;
    }

    @Override
    public void updateBatch(String column, Object value, String whereColumn, Object whereValue) {
        SQLColumn<?> col = this.columns.get(column);
        SQLColumn<?> whereCol = this.columns.get(whereColumn);
        this.updateBatch = this.handler.addUpdate(this.updateBatch, this.tableName,
                SQLValue.create(column, col.write(value)), SQLValue.create(whereColumn, whereCol.write(whereValue)));
    }

    @Override
    public void updateBatch(String column, Object value, Object primaryKey) {
        SQLColumn<?> col = this.columns.get(column);
        SQLColumn<?> whereCol = this.columns.get(this.primaryKey);
        this.updateBatch = this.handler.addUpdate(this.updateBatch, this.tableName,
                SQLValue.create(column, col.write(value)), SQLValue.create(this.primaryKey, whereCol.write(primaryKey)));
    }

    @Override
    public void commitUpdate(boolean async) {
        if (this.updateBatch == null) return;
        this.updateBatch.execute(this.handler, async);
    }
}
