package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.ColumnSettings;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

import java.util.ArrayList;

public class WrappedColumn<T> extends SQLColumn<T> {

    private final ColumnWrapper<T> wrapper;

    private final Class<T> type;

    public WrappedColumn(String key, ISQLTable table, Class<T> type, ColumnWrapper<T> wrapper, ColumnSettings settings) {
        super(key, table, settings);
        this.wrapper = wrapper;
        this.type = type;
    }

    public WrappedColumn(String key, ISQLTable table, Class<T> type, ColumnWrapper<T> wrapper) {
        super(key, table);
        this.wrapper = wrapper;
        this.type = type;
    }

    @Override
    public ArrayList<SQLValue<T>> load(SQLHandler handler) {

        if (handler != null) {
            ArrayList<SQLValue<T>> returnable = new ArrayList<>();
            ArrayList<SQLValue<?>> list = handler.getColumnEntries(this.getTable().getName(), getKey());

            for (SQLValue<?> sqlValue : list) {
                returnable.add(new SQLValue<T>(sqlValue.getKey(), wrapper.deserialize(sqlValue.getString())));
            }

            return returnable;

        }

        return null;

    }

    @Override
    public String initialize() {
        return this.getKey() + " TEXT(65535)";
    }

    @Override
    public String createIndex() {

        String index = "";

        if (this.settings.hasIndex()) {

            if (this.settings.isUnique()) {
                index = "UNIQUE INDEX `" + this.getKey() + "_UNIQUE` (`" + this.getKey() + "(65535)` ASC) VISIBLE";
            }
            else {
                index = "INDEX `" + this.getKey() + "_INDEX` (`" + this.getKey() + "` (65535) ASC) INVISIBLE";
            }

        }

        return index;

    }

    @Override
    public <PrimaryKeyValue> SQLValue<T> get(PrimaryKeyValue primaryKeyValue) {
        return new SQLValue<>(this.getKey(), wrapper.deserialize(getTable().get(primaryKeyValue, getKey()).getString()));
    }

    @Override
    public T load(Object value) {

        if (type.isAssignableFrom(value.getClass())) {
            return type.cast(value);
        } else if (value instanceof String) {
            return wrapper.deserialize((String) value);
        } else {
            throw new IllegalArgumentException("Cannot load value of type " + value.getClass().getName() + " into column of type " + type.getName());
        }

    }

    @Override
    public String write(Object value) {
        if (type.isAssignableFrom(value.getClass())) {
            return wrapper.serialize(type.cast(value));
        } else if (value instanceof String) {
            return (String) value;
        } else {
            throw new IllegalArgumentException("Cannot write value of type " + value.getClass().getName() + " into column of type " + type.getName());
        }
    }
}
