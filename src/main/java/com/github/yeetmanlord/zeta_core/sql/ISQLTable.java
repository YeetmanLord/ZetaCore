package com.github.yeetmanlord.zeta_core.sql;

import java.util.HashMap;
import java.util.List;

import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * Represents and interacts with a specific SQL table
 *
 * @author YeetManLord
 */
public interface ISQLTable {

    /**
     * @return A list of {@link SQLColumn SQLColumns} that will be used to create
     * the table and add data into that table
     */
    HashMap<String, SQLColumn<?>> getColumns();

    /**
     * @return The name of this table in the database
     */
    String getName();

    /**
     * @return Name of this table's primary key
     */
    String getPrimary();

    public default void initializeTable(SQLHandler handler) {

        if (handler != null) {
            String params = "";

            String indexes = "";

            for (SQLColumn<?> col : getColumns().values()) {
                params += (col.initialize() + " " + col.addSettings()).trim() + ", ";
                String i = col.createIndex();

                if (!i.isEmpty()) {
                    indexes += col.createIndex() + ", ";
                }

            }

            params += "PRIMARY KEY (" + getPrimary() + ")";

            if (!indexes.isEmpty()) {
                params += ", " + indexes;
            }

            if (params.charAt(params.length() - 2) == ',') {
                params = params.substring(0, params.length() - 2);
            }

            handler.createTable(getName(), params);
        }

    }

    /**
     * @param <PrimaryKeyType> Inherited from primaryKeyValue
     * @param primaryKeyValue  The value of the primary key
     * @param columnToGet      Key of the column to get value from.
     * @return The value of a column with a specific primary key value
     */
    <PrimaryKeyType> SQLValue<?> get(PrimaryKeyType primaryKeyValue, String columnToGet);

    /**
     * @param <PrimaryKeyType> Inherited from primaryKeyValue
     * @param primaryKeyValue  The value of the primary key
     * @return A {@link Row row map} for the specified primary key value
     */
    <PrimaryKeyType> Row getRow(PrimaryKeyType primaryKeyValue);

    void setPrimaryKey(String key);

    List<Row> getRows();

    void setColumns(List<SQLColumn<?>> column);

    void writeValue(Row row);

    void writeValue(Object... args);

    SQLHandler getHandler();

    List<SQLValue<?>> getColumn(String column);

    <PrimaryKeyType> void removeRow(String primaryKeyValue);

    <PrimaryKeyType> void removeRow(String column, String value);

    void drop();

}
