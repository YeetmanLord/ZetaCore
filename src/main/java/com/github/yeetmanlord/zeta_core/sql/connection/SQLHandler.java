package com.github.yeetmanlord.zeta_core.sql.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * 
 * @zeta.usage INTERNAL
 * 
 *             Handles all SQL interactions in this plugin.
 * 
 * @author YeetManLord
 *
 */
public class SQLHandler {

	private Connection sqlConnection;

	private ZetaCore instance;

	public SQLHandler(ZetaCore main) {

		if (main.dataBase.client != null && main.dataBase.client.isConnected()) {
			this.sqlConnection = main.dataBase.client.getClient();
		}
		else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
				retryConnection();
			}, 1L);
		}

	}

	private void retryConnection() {

		if (instance.dataBase.client != null && instance.dataBase.client.isConnected()) {
			this.sqlConnection = instance.dataBase.client.getClient();
		}

		if (this.sqlConnection == null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
				retryConnection();
			}, 1L);
		}

	}

	public void createTable(String name, String parameters) {

		if (this.instance.dataBase.client.isConnected()) {
			executeStatement("CREATE TABLE IF NOT EXISTS " + name + " (" + parameters + ");");
		}

	}

	public void replaceInto(String tableName, String tableParams, @Nonnull Object... values) {

		if (this.instance.dataBase.client.isConnected()) {
			String value = "";

			for (int x = 0; x < values.length; x++) {
				Object obj = values[x];

				if (obj == null) {
					obj = "null";
				}

				if (x < values.length - 1) {

					if (obj instanceof String) {
						value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\", ";
					}
					else {
						value += obj.toString().replaceAll("\"", "\\\\\\\"") + ", ";
					}

				}
				else {

					if (obj instanceof String) {
						value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\"";
					}
					else {
						value += obj.toString().replaceAll("\"", "\\\\\\\"");
					}

				}

			}

			value = value.trim();
			executeStatement("REPLACE INTO " + tableName + " (" + tableParams + ") VALUES (" + value + ");");

		}

	}

	public void insertIntoIgnore(String tableName, String tableParams, @Nonnull Object... values) {

		if (this.instance.dataBase.client.isConnected()) {
			String value = "";

			for (int x = 0; x < values.length; x++) {
				Object obj = values[x];

				if (obj == null) {
					obj = "null";
				}

				if (x < values.length - 1) {

					if (obj instanceof String) {
						value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\", ";
					}
					else {
						value += obj.toString().replaceAll("\"", "\\\\\\\"") + ", ";
					}

				}
				else {

					if (obj instanceof String) {
						value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\"";
					}
					else {
						value += obj.toString().replaceAll("\"", "\\\\\\\"");
					}

				}

			}

			value = value.trim();

			executeStatement("INSERT IGNORE INTO " + tableName + " (" + tableParams + ") VALUES (" + value + ");");
		}

	}

	public void executeStatement(String sqlCode) {

		if (this.instance.dataBase.client.isConnected()) {

			try {

				if (sqlConnection != null) {
					PreparedStatement statement = sqlConnection.prepareStatement(sqlCode);
					statement.executeUpdate();
				}

			}
			catch (SQLException exc) {
				exc.printStackTrace();
			}

		}

	}

	public Connection getSqlConnection() {

		return sqlConnection;

	}

	public SQLValue<?> query(String mainColumnName, Object mainColumnValue, String table, String queryColumn) {

		Object defaultValue = null;

		if (this.instance.dataBase.client.isConnected()) {

			try {
				PreparedStatement ps = this.sqlConnection.prepareStatement("SELECT " + queryColumn + " FROM " + table + " WHERE " + mainColumnName + "=?");
				ps.setObject(1, mainColumnValue);
				ResultSet queryResult = ps.executeQuery();

				if (queryResult.next()) {
					defaultValue = queryResult.getObject(queryColumn);
				}

			}
			catch (SQLException exc) {
				exc.printStackTrace();
			}

		}

		return SQLValue.create(queryColumn, defaultValue);

	}

	public int getEntrySize(String table) {

		int count = 0;

		if (this.instance.dataBase.client.isConnected()) {

			try {
				PreparedStatement statement = sqlConnection.prepareStatement("SELECT count(*) FROM " + table);
				ResultSet queryResult = statement.executeQuery();

				if (queryResult.next()) {
					count = queryResult.getInt(1);
				}

			}
			catch (SQLException exc) {
				exc.printStackTrace();
			}

		}

		return count;

	}

	public ArrayList<SQLValue<?>> getColumnEntries(String table, String columnName) {

		ArrayList<SQLValue<?>> list = new ArrayList<>();

		if (this.instance.dataBase.client.isConnected()) {

			try {
				PreparedStatement statement = sqlConnection.prepareStatement("SELECT " + columnName + " FROM " + table);
				ResultSet queryResult = statement.executeQuery();

				if (queryResult.next()) {
					list.add(SQLValue.create(columnName, queryResult.getObject(columnName)));
				}

				while (queryResult.next()) {
					list.add(SQLValue.create(columnName, queryResult.getObject(columnName)));
				}

			}
			catch (SQLException exc) {
				exc.printStackTrace();
			}

		}

		return list;

	}

	public <PrimaryColumnValue> Row getRow(ISQLTable table, PrimaryColumnValue prim) {

		HashMap<String, SQLValue<?>> row = new HashMap<>();

		if (this.instance.dataBase.client.isConnected()) {

			for (SQLColumn<?> val : table.getColumns().values()) {
				row.put(val.getKey(), val.get(prim));
			}

		}

		return Row .createRow(row);

	}

	public <RowValue> List<Row> getRowsWhere(String primaryKey, ISQLTable table, String column, RowValue whereEquals) {

		List<Row> list = new ArrayList<>();
		
		if (this.instance.dataBase.client.isConnected()) {

			try {
				PreparedStatement statement = sqlConnection.prepareStatement("SELECT " + primaryKey + " FROM " + table.getName() + " WHERE " + column + "=\"" + whereEquals.toString() + "\"");
				ResultSet queryResult = statement.executeQuery();

				if (queryResult.next()) {
					list.add(getRow(table, queryResult.getObject(primaryKey)));
				}

				while (queryResult.next()) {
					list.add(getRow(table, queryResult.getObject(primaryKey)));
				}

			}
			catch (SQLException exc) {
				exc.printStackTrace();
			}

		}
		
		return list;

	}

}
