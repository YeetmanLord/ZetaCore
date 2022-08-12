package com.github.yeetmanlord.zeta_core.sql;

import java.util.List;
import java.util.Map;

import com.github.yeetmanlord.zeta_core.sql.values.SQLValue;

/**
 * This is an {@link ISQLTableHandler} that isn't associated with a specific
 * table but instead manages and controls multiple tables. This is a holder for
 * other tables.
 * 
 * @author YeetManLord
 *
 */
public interface ISQLTableHolder extends ISQLTableHandler<Void> {

	@Override
	default String getPrimaryKey() {

		return "";

	}

	default ISQLTable getTable() {

		return null;

	}

	default Map<Void, ISQL<?>> getData() {

		return null;

	}

	default ISQL<?> get(Void primaryKey) {

		return null;

	}

	public default SQLValue<?> getDataInColumn(Void primaryKeyValue, String columnName) {

		return null;

	}

	default ISQLObjectHandler<?> getHandler() {

		return null;

	}

	default List<ISQL<?>> getISQLsWithValue(SQLValue<?> valueToCheck) {

		return null;

	}

}
