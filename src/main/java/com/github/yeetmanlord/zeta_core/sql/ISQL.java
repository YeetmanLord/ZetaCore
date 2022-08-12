package com.github.yeetmanlord.zeta_core.sql;

/**
 * ISQLs are SQL encodable objects. These objects are encoded by the ORM. The
 * data to encode them is gotten from its {@link ISQLObjectHandler handler}.
 * 
 * @author YeetManLord
 *
 * @param <SuperType> The SuperType is the class that implements ISQL. For
 *                    example: class SuperClass implements ISQL<SuperClass>
 */
public interface ISQL<SuperType extends ISQL<?>> {

	ISQLObjectHandler<SuperType> getHandler();

}
