package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import java.util.function.Function;

public abstract class ColumnWrapper<T> {

    private final Class<T> type;

    public ColumnWrapper(Class<T> type) {
        this.type = type;
    }

    public abstract String serialize(T object);

    public abstract T deserialize(String string);

    public Class<T> getType() {
        return type;
    }

}
