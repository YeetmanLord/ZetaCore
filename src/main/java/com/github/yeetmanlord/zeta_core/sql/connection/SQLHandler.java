package com.github.yeetmanlord.zeta_core.sql.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * @author YeetManLord
 * @zeta.usage INTERNAL
 * <p>
 * Handles all SQL interactions in this plugin.
 */
public class SQLHandler {

    private SQLClient client;

    public SQLHandler(SQLClient client) {

        this.client = client;

    }

    public void createTable(String name, String parameters) {

        if (this.client.isConnected()) {
            executeStatement("CREATE TABLE IF NOT EXISTS `" + name + "` (" + parameters + ");");
        }

    }

    public void replaceInto(String tableName, String tableParams, @Nonnull Object... values) {

        if (this.client.isConnected()) {
            String value = "";

            for (int x = 0; x < values.length; x++) {
                Object obj = values[x];

                if (obj == null) {
                    obj = "null";
                }

                if (x < values.length - 1) {

                    if (obj instanceof String) {
                        value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\", ";
                    } else {
                        value += obj.toString().replaceAll("\"", "\\\\\\\"") + ", ";
                    }

                } else {

                    if (obj instanceof String) {
                        value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\"";
                    } else {
                        value += obj.toString().replaceAll("\"", "\\\\\\\"");
                    }

                }

            }

            value = value.trim();
            executeStatement("REPLACE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + value + ");");

        }

    }

    public void dropTable(String tableName) {

        if (this.client.isConnected()) {
            executeStatement("DROP TABLE `" + tableName + "`;");
        }

    }

    public <PrimaryKeyValue> void removeRow(String tableName, String checkColumn, String value) {

        if (this.client.isConnected()) {
            executeStatement("DELETE FROM `" + tableName + "` WHERE " + checkColumn + " = " + value + ";");
        }

    }

    public void insertIntoIgnore(String tableName, String tableParams, @Nonnull Object... values) {

        if (this.client.isConnected()) {
            String value = "";

            for (int x = 0; x < values.length; x++) {
                Object obj = values[x];

                if (obj == null) {
                    obj = "null";
                }

                if (x < values.length - 1) {

                    if (obj instanceof String) {
                        value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\", ";
                    } else {
                        value += obj.toString().replaceAll("\"", "\\\\\\\"") + ", ";
                    }

                } else {

                    if (obj instanceof String) {
                        value += "\"" + obj.toString().replaceAll("\"", "\\\\\\\"") + "\"";
                    } else {
                        value += obj.toString().replaceAll("\"", "\\\\\\\"");
                    }

                }

            }

            value = value.trim();

            executeStatement("INSERT IGNORE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + value + ");");
        }

    }

    public void executeStatement(String sqlCode) {

        if (this.client.isConnected()) {

            try {

                if (client != null) {
                    PreparedStatement statement = client.getClient().prepareStatement(sqlCode);
                    statement.executeUpdate();
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

    }

    public Connection getClient() {

        return client.getClient();

    }

    public SQLValue<?> query(String mainColumnName, Object mainColumnValue, String table, String queryColumn) {

        Object defaultValue = null;

        if (this.client.isConnected()) {

            try {
                PreparedStatement ps = this.client.getClient().prepareStatement("SELECT " + queryColumn + " FROM `" + table + "` WHERE " + mainColumnName + "=?");
                ps.setObject(1, mainColumnValue);
                ResultSet queryResult = ps.executeQuery();

                if (queryResult.next()) {
                    defaultValue = queryResult.getObject(queryColumn);
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return SQLValue.create(queryColumn, defaultValue);

    }

    public int getEntrySize(String table) {

        int count = 0;

        if (this.client.isConnected()) {

            try {
                PreparedStatement statement = client.getClient().prepareStatement("SELECT count(*) FROM `" + table + "`");
                ResultSet queryResult = statement.executeQuery();

                if (queryResult.next()) {
                    count = queryResult.getInt(1);
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return count;

    }

    public ArrayList<SQLValue<?>> getColumnEntries(String table, String columnName) {

        ArrayList<SQLValue<?>> list = new ArrayList<>();

        if (this.client.isConnected()) {

            try {
                PreparedStatement statement = client.getClient().prepareStatement("SELECT " + columnName + " FROM `" + table + "`");
                ResultSet queryResult = statement.executeQuery();
                if (queryResult.next()) {
                    Object obj = queryResult.getObject(columnName);
                    list.add(SQLValue.create(columnName, obj));
                }

                while (queryResult.next()) {
                    list.add(SQLValue.create(columnName, queryResult.getObject(columnName)));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return list;

    }

    public <PrimaryColumnValue> Row getRow(ISQLTable table, PrimaryColumnValue prim) {

        HashMap<String, SQLValue<?>> row = new HashMap<>();

        if (this.client.isConnected()) {

            for (SQLColumn<?> val : table.getColumns().values()) {
                row.put(val.getKey(), val.get(prim));
            }

        }
        return Row.createRow(row);

    }

    public <RowValue> List<Row> getRowsWhere(String primaryKey, ISQLTable table, String column, RowValue whereEquals) {

        List<Row> list = new ArrayList<>();

        if (this.client.isConnected()) {

            try {
                PreparedStatement statement = client.getClient().prepareStatement("SELECT " + primaryKey + " FROM `" + table.getName() + "` WHERE " + column + "=\"" + whereEquals.toString() + "\"");
                ResultSet queryResult = statement.executeQuery();

                if (queryResult.next()) {
                    list.add(getRow(table, queryResult.getObject(primaryKey)));
                }

                while (queryResult.next()) {
                    list.add(getRow(table, queryResult.getObject(primaryKey)));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return list;

    }

}
