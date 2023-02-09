package com.github.yeetmanlord.zeta_core.sql.types;

import java.util.ArrayList;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * Represents a BOOL in SQL
 *
 * @author YeetManLord
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

        return SQLValue.create(this.getKey(), getTable().get(value, getKey()).getValue() == Integer.valueOf(1) || getTable().get(value, getKey()).getValue() == Boolean.valueOf(true));

    }

    @Override
    public ArrayList<SQLValue<Boolean>> load(SQLHandler handler) {

        if (handler != null) {
            ArrayList<SQLValue<Boolean>> returnable = new ArrayList<>();

            ArrayList<SQLValue<?>> sqlValues = handler.getColumnEntries(this.getTable().getName(), getKey());

            for (SQLValue<?> sqlValue : sqlValues) {
                returnable.add(new SQLValue<Boolean>(sqlValue.getKey(), (Boolean) sqlValue.getValue()));
            }

            return returnable;

        }

        return null;
    }

    @Override
    public Boolean load(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            return (Integer) value == 1;
        } else if (value instanceof String) {
            return Boolean.valueOf(value.toString());
        } else {
            throw new IllegalArgumentException("Cannot convert " + value.getClass().getName() + " to Boolean");
        }
    }

}
