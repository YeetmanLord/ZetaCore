package com.github.yeetmanlord.zeta_core.sql;

import java.util.HashMap;
import java.util.List;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import com.github.yeetmanlord.zeta_core.sql.values.RowList;

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

    /**
     * Should be called asyncly
     *
     * @param handler SQLHandler object to process sql requests
     */
    default void initializeTable(SQLHandler handler, boolean async) {

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

            handler.createTable(getName(), params, async);
        }

    }

    /**
     * By default, this runs asynchronously
     *
     * @param handler SQLHandler object to process sql requests
     */
    default void initializeTable(SQLHandler handler) {
        initializeTable(handler, false);
    }

    /**
     * @param <PrimaryKeyType> Inherited from primaryKeyValue
     * @param primaryKeyValue  The value of the primary key
     * @param columnToGet      Key of the column to get value from.
     * @return The value of a column with a specific primary key value
     * @implNote This should preferably be done asyncly using {@link java.util.concurrent.CompletableFuture CompletableFutures}.
     * The reedDB method is already called asyncly so futures aren't necessary.
     */
    <PrimaryKeyType> SQLValue<?> get(PrimaryKeyType primaryKeyValue, String columnToGet);

    /**
     * @param <PrimaryKeyType> Inherited from primaryKeyValue
     * @param primaryKeyValue  The value of the primary key
     * @return A {@link Row row map} for the specified primary key value
     * @implNote This should preferably be done asyncly using {@link java.util.concurrent.CompletableFuture CompletableFutures}.
     * The reedDB method is already called asyncly so futures aren't necessary.
     */
    <PrimaryKeyType> Row getRow(PrimaryKeyType primaryKeyValue);

    void setPrimaryKey(String key);

    RowList getRows();

    void setColumns(List<SQLColumn<?>> column);

    /**
     * Writes a row to an SQL table
     *
     * @param row {@link Row} of data to write
     * @implNote This is very important for server performance. When writing to a database
     * <i>outside of</i> the {@link ZetaPlugin#onDisable() onDisable} method will slow down the server
     * if your database is not already hosted locally. This also applies to the onDisable method but data <i>must</i>
     * be written immediately before the plugin is shut off so that someone stopping the server doesn't lose data.
     */
    void writeValue(Row row);

    /**
     * Writes a row to an SQL table,
     *
     * @param args Array of data to write (in order)
     * @implNote This is very important for server performance. When writing to a database
     * <i>outside of</i> the {@link ZetaPlugin#onDisable() onDisable} method will slow down the server
     * if your database is not already hosted locally. This also applies to the onDisable method but data <i>must</i>
     * be written immediately before the plugin is shut off so that someone stopping the server doesn't lose data.
     */
    void writeValue(Object... args);

    SQLHandler getHandler();

    List<SQLValue<?>> getColumn(String column);

    /**
     * Removes a row with specified primary key value.
     *
     * @param primaryKeyValue Value of the primary key
     * @implNote By default, this runs asynchronously.
     */
    void removeRow(Object primaryKeyValue);

    /**
     * Removes a row with specified primary key value.
     *
     * @param primaryKeyValue Value of the primary key
     * @param async           Whether to run on async thread or main thread. True indicates running on an async thread
     */
    void removeRow(Object primaryKeyValue, boolean async);

    /**
     * Removes a row where the specified value exists in the specified column
     *
     * @param column Column to check
     * @param value  Value to check
     * @implNote By default, this runs asynchronously
     */
    void removeRowWhere(String column, Object value);

    /**
     * Removes a row where the specified value exists in the specified column
     *
     * @param column Column to check
     * @param value  Value to check
     * @param async  Whether to run on async thread or main thread. True indicates running on an async thread
     */
    void removeRowWhere(String column, Object value, boolean async);

    /**
     * Drops this table <b>Warning: all data will be lost</b>
     *
     * @implNote By default, this runs asynchronously
     */
    void drop();

    /**
     * Drops this table <b>Warning: all data will be lost</b>
     *
     * @param async Whether to run on async thread or main thread. True indicates running on an async thread
     */
    void drop(boolean async);

    /**
     * Updates a given column with new data using a primary key
     *
     * @param column          Column to update
     * @param value           Value to update (new value)
     * @param primaryKeyValue Primary key to grab row by and then update
     * @implNote By default, this runs asynchronously
     */
    void update(String column, Object value, Object primaryKeyValue);

    /**
     * Updates a given column with new data using a primary key
     *
     * @param column          Column to update
     * @param value           Value to update (new value)
     * @param primaryKeyValue Primary key to grab row by and then update
     * @param async           Whether to run on async thread or main thread. True indicates running on an async thread
     */
    void update(String column, Object value, Object primaryKeyValue, boolean async);

    /**
     * @see ISQLTable#update(String, Object, Object, boolean)
     */
    default void update(SQLValue<?> value, SQLValue<?> primaryKeyValue, boolean async) {
        update(value.getKey(), value.getValue(), primaryKeyValue.getValue(), async);
    }

    /**
     * Updates a given column with new data where the specified whereColumn has the given.
     *
     * @param column      Column to update
     * @param value       Value to update (new value)
     * @param whereColumn Column to check and update by
     * @param whereValue  Value of that column
     * @implNote By default, this runs asynchronously
     */
    void update(String column, Object value, String whereColumn, Object whereValue);

    /**
     * @see #update(String, Object, String, Object)
     */
    default void update(SQLValue<?> value, SQLValue<?> whereValue) {
        update(value.getKey(), value.getValue(), whereValue.getKey(), whereValue.getValue());
    }

    /**
     * Updates a given column with new data where the specified whereColumn has the given.
     *
     * @param column      Column to update
     * @param value       Value to update (new value)
     * @param whereColumn Column to check and update by
     * @param whereValue  Value of that column
     * @param async       Whether to run on async thread or main thread. True indicates running on an async thread
     */
    void update(String column, Object value, String whereColumn, Object whereValue, boolean async);

    /**
     * Since {@link #writeValue(Row)} and {@link #writeValue(Object...)} use an SQL batch statement to execute writing (in order to save performance)
     * you must use this to commit the changes to the database. Not committing will lead to data being lost.
     *
     * @param async Whether to run on async thread or main thread. True indicates running on an async thread
     * @implNote When using {@link ISQLTableHandler SQL table handlers} you can also use {@link ISQLTableHandler#writeToDB()} to commit automatically.
     * @see com.github.yeetmanlord.zeta_core.sql.impl.SQLTable#writeValue(Row) (Row) for implementation of batching.
     */
    void commit(boolean async);

    /**
     * Since {@link #writeValue(Row)} and {@link #writeValue(Object...)} use an SQL batch statement to execute writing (in order to save performance)
     * you must use this to commit the changes to the database. Not committing will lead to data being lost.
     *
     * @implNote When using {@link ISQLTableHandler SQL table handlers} you can also use {@link ISQLTableHandler#writeToDB()} to commit automatically.
     * @see com.github.yeetmanlord.zeta_core.sql.impl.SQLTable#writeValue(Row) SQLTable.writeValue for implementation of batching.
     */
    void commit();

    /**
     * Sets this table's handler. Used for one-time syncs to database.
     * @param handler Handler to use
     */
    void setHandler(SQLHandler handler);

    /**
     * Checks if the table is empty
     * @param handler Handler to use
     * @return True if empty, false if not
     */
    boolean isEmpty(SQLHandler handler);

    void updateBatch(String column, Object value, String whereColumn, Object whereValue);

    void updateBatch(String column, Object value, Object primaryKey);

    void commitUpdate(boolean async);

}
