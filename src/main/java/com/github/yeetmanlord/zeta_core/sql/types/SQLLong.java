package com.github.yeetmanlord.zeta_core.sql.types;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

import java.util.ArrayList;

/**
 * @apiNote <b>TECHNICALLY</b> in SQL this is actually called a BigInt, but it is equivalent to a Java Long so that's what it's called here. If you have a problem with that, fight me.
 */
public class SQLLong extends SQLColumn<Long> {


    public SQLLong(String key, ISQLTable table) {

        super(key, table);

    }

    public SQLLong(String key, ISQLTable table,ColumnSettings settings) {

        super(key, table, settings);

    }

    @Override
    public ArrayList<SQLValue<Long>> load(SQLHandler handler) {

        if (handler != null) {
            ArrayList<SQLValue<Long>> returnable = new ArrayList<>();
            ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

            for (SQLValue<?> sqlValue : list) {
                returnable.add(new SQLValue<Long>(sqlValue.getKey(), (Long) sqlValue.getValue()));
            }

            return returnable;

        }

        return null;

    }

    @Override
    public String initialize() {

        return this.getKey() + " BIGINT()";

    }

    @Override
    public <PrimaryKeyValue> SQLValue<Long> get(PrimaryKeyValue value) {

        return new SQLValue<Long>(this.getKey(), (Long) getTable().get(value, getKey()).getValue());

    }

    @Override
    public Long load(Object value) {
        if (value instanceof Number) {
            return ((Number)value).longValue();
        } else if (value instanceof String) {
            return Long.valueOf((String)value);
        } else {
            throw new IllegalArgumentException("Cannot convert " + value.getClass().getName() + " to Long");
        }
    }
}
