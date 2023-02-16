package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.ISQL;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import org.bukkit.Bukkit;

/**
 * This is an implementation of {@link ISQLTableHandler}. This is used for {@link DataStorer data storers} that store data in one table in a database.
 * It can also have a related table that stores related data. For example, say you have a file called player-info.yml. In it, you have a list of UUIDs and their balance.
 * Within that file, you may also have a few additional config options. You could specify something like a max balance, and a default balance. These are not related to the player's balance
 * so would be stored in a related table. You could also just separate that into a different file if you so choose.
 * <p>
 * Additionally, this class has a connected {@link ISQL} object. This is used to quickly load and save data to the database.
 * If you just want to store data in a table, you can use {@link AbstractUntypedSQLDataStorer}
 *
 * @param <PrimaryKeyType> The type of the primary key of the table.
 * @author YeetManLord
 */
public abstract class AbstractSQLDataStorer<PrimaryKeyType> extends DataStorer implements ISQLTableHandler<PrimaryKeyType> {

    protected ISQLTable table;

    protected String tableName;

    protected boolean dataInitialized = false;

    public AbstractSQLDataStorer(ZetaPlugin instanceIn, String name, String tableName) {

        super(instanceIn, name);
        this.tableName = instanceIn.getPluginName().toLowerCase() + "/" + tableName;

    }

    @Override
    public ISQLTable getTable() {

        return table;

    }

    @Override
    public void setTable(ISQLTable table) {
        this.table = table;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<PrimaryKeyType, ISQL<?>> getData() {

        List<Row> list = this.table.getRows();
        Map<PrimaryKeyType, ISQL<?>> iSQLValues = new HashMap<>();

        for (Row map : list) {
            SQLValue<?> primaryKey = map.get(this.getPrimaryKey());
            iSQLValues.put((PrimaryKeyType) primaryKey.getValue(), this.getHandler().load(map));
        }

        return iSQLValues;

    }

    @Override
    public void initializeDB(SQLHandler handler) {

        this.table = new SQLTable(tableName, handler);
        this.table.setPrimaryKey(this.getPrimaryKey());
        this.table.setColumns(getColumns(handler));
        this.table.initializeTable(handler);

        if (this.table.isEmpty(handler)) {
            this.instance.scheduleTask(() -> {
                this.dataInitialized = true;
                this.read();
                this.instance.scheduleAsyncTask(this::writeToDB);
            }, 0L);
        }

    }

    @Override
    public ISQL<?> get(PrimaryKeyType primaryKey) {

        return getData().get(primaryKey);

    }

    public List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck) {

        List<ISQL<?>> iSQLs = new ArrayList<>();

        List<Row> list = this.table.getHandler().getRowsWhere(this.table, valueToCheck.getKey(), valueToCheck.getValue());

        for (Row map : list) {
            iSQLs.add(this.getHandler().load(map));
        }

        return iSQLs;

    }

    @Override
    public String toString() {
        return "AbstractSQLDataStorer{" +
                "table=" + table +
                ", tableName='" + tableName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", plugin=" + instance.getPluginName() +
                '}';
    }

    public boolean doesRequireDataInit() {
        return dataInitialized;
    }
}
