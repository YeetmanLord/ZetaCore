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
 * @param <PrimaryKeyType> This is kinda self explanatory
 */
public interface ISQLTableHandler<PrimaryKeyType> {

	ISQLTable getTable();

	String getPrimaryKey();

	/**
	 * 
	 * @return Gets all SQL data from the table then translates it using this
	 *         class's {@link #getHandler() handler}
	 */
	Map<PrimaryKeyType, ISQL<?>> getData();

	void readDB();

	void writeDB();

	/**
	 * @param primaryKey Primary key value
	 * @return The translated form of the SQL data in a certain row gotten from the
	 *         primary key value
	 */
	public ISQL<?> get(PrimaryKeyType primaryKey);

	/**
	 * @see SQLColumn#get(Object)
	 */
	public default SQLValue<?> getDataInColumn(PrimaryKeyType primaryKeyValue, String columnName) {

		return getTable().getColumns().get(columnName).get(primaryKeyValue);

	}

	void initializeDB(SQLHandler handler);

	/**
	 * @return This class's {@link ISQLObjectHandler handler} for translating SQL
	 *         data into a Java Object
	 */
	ISQLObjectHandler<?> getHandler();

	/**
	 * @param valueToCheck
	 * @return Gets all the translated rows where the value in a specific column
	 *         match the given value. The key is gotten from the {@link SQLValue}
	 *         passed in
	 */
	List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck);

}
