package com.github.yeetmanlord.zeta_core.sql;

import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.sql.values.Row;

/**
 * Handler that loads and encodes SQL encodable objects. This is the backbone of
 * the ORM.
 * 
 * @author YeetManLord
 *
 * @param <SuperType> The SuperType is the class that implements ISQL. For
 *                    example: class SuperClass implements ISQL<SuperClass>
 */
public interface ISQLObjectHandler<SuperType extends ISQL<?>> {

	/**
	 * Encodes an {@link ISQL} into a {@link Row row} so it can be stored in a SQL
	 * table.
	 * 
	 * @param iSQL Object to encode
	 * @return A {@link Row row map} for the object
	 */
	Row translateDataToSQL(SuperType iSQL);

	/**
	 * The equivalent of {@link DataStorer#read() DataStorer.read()}. Will take a
	 * row from a SQL table, reads its data, then returns an instance of this
	 * class's SuperType.
	 * 
	 * @param data
	 * @return
	 */
	SuperType load(Row data);

}
