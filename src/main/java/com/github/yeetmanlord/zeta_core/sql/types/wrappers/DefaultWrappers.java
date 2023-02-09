package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class DefaultWrappers {

    public static final ColumnWrapper<ItemStack> ITEM_STACK = new ItemWrapper();

    public static final ColumnWrapper<Location> LOCATION = new LocationWrapper();

    public static final ColumnWrapper<JsonObject> JSON_OBJECT = new JsonObjectWrapper();

    public static final ColumnWrapper<JsonArray> JSON_ARRAY = new JsonArrayWrapper();

}
