package com.github.yeetmanlord.zeta_core.sql;

import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * This is a handler class for {@link ISQLTable}. It directly interacts with the
 * table that it is assigned to..
 * 
 * @author YeetManLord
 *
 * @param <PrimaryKeyType> This is kinda self-explanatory
 */
public interface ISQLTableHandler<PrimaryKeyType> {

	/**
	 * @return Table associated with this handler
	 */
	ISQLTable getTable();

	String getTableName();

	/**
	 * Sets this table handler's table
	 */
	void setTable(ISQLTable table);

	/**
	 * @return Table's primary key
	 */
	String getPrimaryKey();

	/**
	 * 
	 * @return Gets all SQL data from the table then translates it using this
	 *         class's {@link #getHandler() handler}
	 */
	Map<PrimaryKeyType, ISQL<?>> getData();

	/**
	 * Reads data from the table. By default, it is read asynchronously.
	 */
	void readDB();

	/**
	 * Used to write values to a batch statement buffer. All writes won't be executed until you commit using {@link ISQLTable#commit(boolean)}.
	 * Otherwise, use {@link #writeToDB()} to automatically commit (synchronously)
	 */
	void writeDB();

	default void writeToDB() {
		this.writeDB();
		this.getTable().commit();
	}

	/**
	 * @param primaryKey Primary key value
	 * @return The translated form of the SQL data in a certain row gotten from the
	 *         primary key value
	 */
	public ISQL<?> get(PrimaryKeyType primaryKey);

	/**
	 * 
	 * @param primaryKeyValue The value of the primary key in the row to grab
	 *                        the column value from
	 * @param columnName      The column to grab the value from
	 * @see SQLColumn#get(Object)
	 * @return Get datas in a column with a primary key and column name
	 */
	public default SQLValue<?> getDataInColumn(PrimaryKeyType primaryKeyValue, String columnName) {

		return getTable().getColumns().get(columnName).get(primaryKeyValue);

	}

	/**
	 * Used to initialize this handler's table and if necessary its data. This is
	 * called when the table is first created. This method is called before the
	 * {@link #readDB()} method. You should override this method if your handler has
	 * sub-tables that need to be initialized. Ensure that you initialize your data if one
	 * or more of the associated tables are empty. Otherwise, you can just initialize the
	 * table.
	 *
	 * @see com.github.yeetmanlord.zeta_core.sql.impl.AbstractSQLDataStorer#initializeDB(SQLHandler)
	 * @see com.github.yeetmanlord.zeta_core.sql.impl.AbstractUntypedSQLDataStorer#initializeDB(SQLHandler)
	 * @see com.github.yeetmanlord.zeta_core.sql.impl.AbstractSQLTableHandler#initializeDB(SQLHandler)
	 * @param handler The {@link SQLHandler} to use to initialize the table
	 */
	void initializeDB(SQLHandler handler);

	/**
	 * @return This class's {@link ISQLObjectHandler handler} for translating SQL
	 *         data into a Java Object
	 */
	ISQLObjectHandler<?> getHandler();

	/**
	 * @param valueToCheck The {@link SQLValue} to use to get the list
	 * @return Gets all the translated rows where the value in a specific column
	 *         match the given value. The key is gotten from the {@link SQLValue}
	 *         passed in
	 */
	List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck);

	List<SQLColumn<?>> getColumns(SQLHandler handler);

	/**
	 * <b>IMPORTANT METHOD</b> This method is used to determine if the table needs to
	 * have its data initialized. If this method returns true, then the this handler will
	 * <em>not</em> read from the database. Instead, it will initialize the data using local
	 * files. By default, this is handled by {@link ISQLTableHandler#initializeDB(SQLHandler)}.
	 * However, if you override this method, you must specify when to initialize the data.
	 * @return If the table needs to have its data initialized return true. Otherwise, return false
	 * (which will cause the table to read from the database)
	 */
	boolean doesRequireDataInit();

}
