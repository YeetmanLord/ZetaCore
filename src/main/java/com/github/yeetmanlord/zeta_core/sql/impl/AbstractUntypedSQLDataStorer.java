package com.github.yeetmanlord.zeta_core.sql.impl;

import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.ISQL;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;
import com.github.yeetmanlord.zeta_core.sql.ISQLObjectHandler;
import com.github.yeetmanlord.zeta_core.sql.ISQLTable;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;
import org.bukkit.Bukkit;

/**
 * This is a type of {@link ISQLTableHandler} where there is no associated
 * {@link ISQLObjectHandler} and rather than handling java objects this
 * {@link DataStorer data storer} just stores general data in the database
 *
 * @author YeetManLord
 * @zeta.usage INTERNAL
 */
public abstract class AbstractUntypedSQLDataStorer<PrimaryKeyType> extends DataStorer implements ISQLTableHandler<PrimaryKeyType> {

    protected ISQLTable table;

    protected String tableName;
    protected boolean dataInitialized = false;

    public AbstractUntypedSQLDataStorer(ZetaPlugin instanceIn, String name, String tableName) {

        super(instanceIn, name);
        this.tableName = instanceIn.getPluginName().toLowerCase() + "/" + tableName;

    }

    @Override
    public ISQLTable getTable() {

        return this.table;

    }

    @Override
    public void setTable(ISQLTable table) {
        this.table = table;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public Map<PrimaryKeyType, ISQL<?>> getData() {

        return null;

    }

    @Override
    public ISQL<?> get(PrimaryKeyType primaryKey) {

        return null;

    }

    @Override
    public void initializeDB(SQLHandler handler) {

        this.table = new SQLTable(tableName, handler);
        this.table.setPrimaryKey(this.getPrimaryKey());
        this.table.setColumns(getColumns(handler));
        this.table.initializeTable(handler);

        if (this.table.isEmpty(handler)) {
            Bukkit.getScheduler().runTask(this.instance, () -> {
                this.dataInitialized = true;
                this.read();
                Bukkit.getScheduler().runTaskAsynchronously(this.instance, this::writeToDB);
            });
        }

    }

    public ISQLObjectHandler<?> getHandler() {

        return null;

    }

    public List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck) {

        return null;

    }

    @Override
    public String toString() {
        return "AbstractUntypedSQLDataStorer{" +
                "table=" + table +
                ", tableName='" + tableName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", plugin=" + instance.getPluginName() +
                '}';
    }

    @Override
    public boolean doesRequireDataInit() {
        return !this.dataInitialized;
    }
}
