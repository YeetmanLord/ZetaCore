package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import com.github.yeetmanlord.zeta_core.sql.impl.AbstractUntypedSQLDataStorer;
import com.github.yeetmanlord.zeta_core.sql.types.ColumnSettings;
import com.github.yeetmanlord.zeta_core.sql.types.SQLColumn;
import com.github.yeetmanlord.zeta_core.sql.types.SQLInteger;
import com.github.yeetmanlord.zeta_core.sql.types.wrappers.DefaultWrappers;
import com.github.yeetmanlord.zeta_core.sql.types.wrappers.WrappedColumn;
import com.github.yeetmanlord.zeta_core.sql.values.Row;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TestStorer extends AbstractUntypedSQLDataStorer<Integer> {
    public TestStorer(ZetaCore instanceIn) {
        super(instanceIn, "test", "test_table");
    }

    @Override
    public void setDefaults() {

    }

    @Override
    public void read() {

    }

    @Override
    public void write() {

    }

    @Override
    public String getPrimaryKey() {
        return "ID";
    }

    @Override
    public void readDB() {
        Row test = this.table.getRow(1);
        if (test == null || test.size() ==0) {
            return;
        }
        ItemStack item = (ItemStack) test.getValue("TEST_ITEM");
        Location loc = (Location) test.getValue("TEST_LOCATION");
        JsonObject json = (JsonObject) test.getValue("TEST_JSON");
        System.out.println(item);
        System.out.println(loc);
        System.out.println(json);
    }

    @Override
    public void writeDB() {
        Row test = new Row();
        test.put("ID", 1);
        test.put("TEST_ITEM", new ItemStack(Material.STONE, 2));
        test.put("TEST_LOCATION", new Location(Bukkit.getWorld("world"), 0, 0, 0));
        JsonObject json = new JsonObject();
        json.addProperty("test", "test");
        test.put("TEST_JSON", json);
        System.out.println(test);
        this.table.writeValue(test);
    }

    @Override
    public void initializeDB(SQLHandler handler) {
        System.out.println("INIT");
        super.initializeDB(handler);
    }

    @Override
    public List<SQLColumn<?>> getColumns(SQLHandler handler) {
        List<SQLColumn<?>> columns = new ArrayList<>();
        columns.add(new SQLInteger("ID", this.table, 10, ColumnSettings.PRIMARY_KEY));
        columns.add(new WrappedColumn<>("TEST_ITEM", this.table, ItemStack.class, DefaultWrappers.ITEM_STACK));
        columns.add(new WrappedColumn<>("TEST_LOCATION", this.table, Location.class, DefaultWrappers.LOCATION));
        columns.add(new WrappedColumn<>("TEST_JSON", this.table, JsonObject.class, DefaultWrappers.JSON_OBJECT));
        return columns;
    }
}
