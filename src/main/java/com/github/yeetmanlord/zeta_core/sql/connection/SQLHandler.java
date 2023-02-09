package com.github.yeetmanlord.zeta_core.sql.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import com.github.yeetmanlord.zeta_core.sql.values.RowList;
import org.bukkit.Bukkit;

/**
 * @author YeetManLord
 * @zeta.usage INTERNAL
 * <p>
 * Handles all SQL interactions in this plugin.
 */
public class SQLHandler {

    private final SQLClient client;

    public SQLHandler(SQLClient client) {

        this.client = client;

    }

    public void createTable(String name, String parameters, boolean async) {

        if (this.client.isConnected()) {
            executeStatement("CREATE TABLE IF NOT EXISTS `" + name + "` (" + parameters + ");", async);
        }

    }

    public void replaceInto(final String tableName, final String tableParams, boolean async, @Nonnull final Object... values) {

        if (this.client.isConnected()) {
            String value = "";

            for (int x = 0; x < values.length; x++) {
                if (x == values.length - 1) {
                    value += "?";
                } else {
                    value += "?, ";
                }
            }

            final String valueFinal = value.trim();
            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                    try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("REPLACE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + valueFinal + ");")) {
                        for (int x = 0; x < values.length; x++) {
                            statement.setObject(x + 1, values[x]);
                        }
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("REPLACE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + value + ");")) {
                for (int x = 0; x < values.length; x++) {
                    statement.setObject(x + 1, values[x]);
                }
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public SQLBatchStatement addReplaceInto(SQLBatchStatement existing, String tableName, String params, Object... values) {

        String value = "";

        for (int x = 0; x < values.length; x++) {
            if (x == values.length - 1) {
                value += "?";
            } else {
                value += "?, ";
            }
        }

        String statement = "REPLACE INTO `" + tableName + "` (" + params + ") VALUES (" + value + ");";

        SQLBatchStatement batch = (existing == null) ? new SQLBatchStatement(statement) : existing;

        batch.addBatch(values);
        return batch;

    }

    public void dropTable(String tableName, boolean async) {

        if (this.client.isConnected()) {
            executeStatement("DROP TABLE `" + tableName + "`;", async);
        }

    }

    public void removeRow(String tableName, String checkColumn, Object value, boolean async) {
        if (this.client.isConnected()) {
            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                    try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("DELETE FROM `" + tableName + "` WHERE " + checkColumn + "=?;")) {
                        statement.setObject(1, value);
                        statement.executeUpdate();
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                });
            } else {
                try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("DELETE FROM `" + tableName + "` WHERE " + checkColumn + "=?;")) {
                    statement.setObject(1, value);
                    statement.executeUpdate();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    public void insertIntoIgnore(String tableName, String tableParams, boolean async, @Nonnull Object... values) {

        if (this.client.isConnected()) {
            String value = "";

            for (int x = 0; x < values.length; x++) {
                if (x == values.length - 1) {
                    value += "?";
                } else {
                    value += "?, ";
                }
            }

            final String valueFinal = value.trim();


            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                    try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("INSERT IGNORE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + valueFinal + ");")) {
                        for (int x = 0; x < values.length; x++) {
                            statement.setObject(x + 1, values[x]);
                        }
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("INSERT IGNORE INTO `" + tableName + "` (" + tableParams + ") VALUES (" + valueFinal + ");")) {
                for (int x = 0; x < values.length; x++) {
                    statement.setObject(x + 1, values[x]);
                }
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void executeStatement(final String sqlCode, boolean async) {
        if (async) {
            asyncExecuteStatement(sqlCode);
            return;
        }
        executeStatement(sqlCode);
    }

    public void executeStatement(final String sqlCode) {

        if (this.client.isConnected()) {

            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement(sqlCode)) {
                statement.executeUpdate();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }
    }

    public void asyncExecuteStatement(final String sqlCode) {
        if (this.client.isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> executeStatement(sqlCode));
        }
    }

    public SQLClient getClient() {

        return client;

    }

    public SQLValue<?> queryFirst(String mainColumnName, Object mainColumnValue, String table, String queryColumn) {

        Object value = null;

        if (this.client.isConnected()) {

            try (Connection conn = client.getSource().getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT " + queryColumn + " FROM `" + table + "` WHERE " + mainColumnName + "=?")) {
                ps.setObject(1, mainColumnValue);
                ResultSet queryResult = ps.executeQuery();

                if (queryResult.next()) {
                    value = queryResult.getObject(queryColumn);
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return SQLValue.create(queryColumn, value);

    }

    public List<SQLValue<?>> query(String mainColumnName, Object mainColumnValue, String table, String queryColumn) {
        List<SQLValue<?>> results = new ArrayList<>();

        if (this.client.isConnected()) {

            try (Connection conn = client.getSource().getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT " + queryColumn + " FROM `" + table + "` WHERE " + mainColumnName + "=?")) {
                ps.setObject(1, mainColumnValue);
                ResultSet queryResult = ps.executeQuery();

                while (queryResult.next()) {
                    results.add(SQLValue.create(queryColumn, queryResult.getObject(queryColumn)));
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return results;
    }

    public int getEntrySize(String table) {

        int count = 0;

        if (this.client.isConnected()) {

            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM `" + table + "`")) {
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

            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT " + columnName + " FROM `" + table + "`")) {
                ResultSet queryResult = statement.executeQuery();
                while (queryResult.next()) {
                    list.add(SQLValue.create(columnName, queryResult.getObject(columnName)));
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return list;

    }

    public Row getRow(ISQLTable table, Object prim) {

        Row row = new Row();
        if (this.client.isConnected()) {
            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT * FROM `" + table.getName() + "` WHERE " + table.getPrimary() + "=?;")) {
                statement.setObject(1, prim);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    for (SQLColumn<?> col : table.getColumns().values()) {
                        row.put(col.getKey(), result.getObject(col.getKey()));
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return row;

    }

    public RowList getRowsWhere(ISQLTable table, String column, Object whereEquals) {

        RowList list = new RowList();

        if (this.client.isConnected()) {

            try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT * FROM `" + table.getName() + "` WHERE " + column + "=?;")) {
                statement.setObject(1, whereEquals);
                ResultSet queryResult = statement.executeQuery();

                while (queryResult.next()) {
                    Row row = new Row();
                    for (String key : table.getColumns().keySet()) {
                        row.put(key, queryResult.getObject(key));
                    }
                    list.add(row);
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }

        return list;

    }

    public void updateWhere(String table, SQLValue<?> value, SQLValue<?> whereValue, boolean async) {
        if (this.client.isConnected()) {
            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> {
                    try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("UPDATE `" + table + "` SET " + value.getKey() + "=?" + " WHERE " + whereValue.getKey() + "=?;")) {
                        statement.setObject(1, value.getValue());
                        statement.setObject(2, whereValue.getValue());
                        statement.executeUpdate();
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                });
            }
        }
    }

    public RowList getAllData(ISQLTable table) {
        RowList rows = new RowList();
        if (client != null) {
            if (this.client.isConnected()) {
                try (Connection conn = client.getSource().getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT * FROM `" + table.getName() + "`;")) {
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        Row row = new Row();
                        for (String key : table.getColumns().keySet()) {
                            row.put(key, set.getObject(key));
                        }
                        rows.add(row);
                    }
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }

        return rows;
    }

    public SQLBatchStatement addUpdate(SQLBatchStatement existing, String tableName, SQLValue<Object> value, SQLValue<Object> whereValue) {

        String statement = "UPDATE `" + tableName + "` SET " + value.getKey() + "=?" + " WHERE " + whereValue.getKey() + "=?;";

        SQLBatchStatement batch = (existing == null) ? new SQLBatchStatement(statement) : existing;

        batch.addBatch(value.getValue(), whereValue.getValue());
        return batch;
    }
}
