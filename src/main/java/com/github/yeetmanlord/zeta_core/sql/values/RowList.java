package com.github.yeetmanlord.zeta_core.sql.values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Small utility class to hold a list of rows. Extends ArrayList and implements a few methods to easily get data
 * from the list using a key and a value.
 *
 * @author YeetManLord
 */
public class RowList extends ArrayList<Row> {

    public RowList() {
        super();
    }

    public RowList(Collection<Row> rows) {
        super(rows);
    }

    /**
     * Gets all rows where the value of the key is equal to the specified value.
     * @param key The key to search for.
     * @param value The value to search for.
     * @return A list of rows where the value of the key is equal to the specified value.
     */
    public RowList getRowWhere(String key, Object value) {
        RowList rows = new RowList();
        for (Row row : this) {
            if (row.getValue(key).equals(value)) {
                rows.add(row);
            }
        }
        return rows;
    }

    /**
     * Gets the first row where the value of the key is equal to the specified value.
     * @param key The key to search for.
     * @param value The value to search for.
     * @return The first row where the value of the key is equal to the specified value.
     */
    public Row getFirstRow(String key, Object value) {

        RowList rows = getRowWhere(key, value);
        if (rows.size() > 0) {
            return rows.get(0);
        }
        return null;

    }

    /**
     * Gets all values where the value of the key is equal to the specified value.
     * @param checkKey The key to search for.
     * @param value The value to search for.
     * @param colToGet The column to get the value from.
     * @return A list of values where the value of the key is equal to the specified value.
     */
    public List<SQLValue<?>> getValuesWhere(String checkKey, Object value, String colToGet) {
        List<SQLValue<?>> values = new ArrayList<>();

        for (Row row : this) {
            if (row.getValue(checkKey).equals(value)) {
                values.add(row.get(colToGet));
            }
        }

        return values;
    }

    /**
     * Gets the first value where the value of the key is equal to the specified value.
     * @param checkKey The key to search for.
     * @param value The value to search for.
     * @param colToGet The column to get the value from.
     * @return
     */
    public SQLValue<?> getFirstValue(String checkKey, Object value, String colToGet) {

        List<SQLValue<?>> values = getValuesWhere(checkKey, value, colToGet);
        if (values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    /**
     * Checks if the list contains a row where the value of the key is equal to the specified value.
     * @param key The key to search for.
     * @param value The value to search for.
     * @return True if the list contains a row where the value of the key is equal to the specified value.
     */
    public boolean contains(String key, Object value) {
        return getFirstRow(key, value) != null;
    }
}
