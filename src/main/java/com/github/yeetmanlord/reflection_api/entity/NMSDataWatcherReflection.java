package com.github.yeetmanlord.reflection_api.entity;

import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;

public class NMSDataWatcherReflection extends NMSObjectReflection {

	public NMSDataWatcherReflection(NMSEntityReflection entity) {

		super(init(entity));

	}

	public NMSDataWatcherReflection(Object nmsObject) {

		super(nmsObject);

	}

	private static Object init(NMSEntityReflection entity) {

		try {
			return entity.getNmsEntity().getClass().getMethod("getDataWatcher").invoke(entity.getNmsEntity());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public Object getNmsDataWatcher() {

		return nmsObject;

	}

	/**
	 * WARNING DOES NOT ACCEPT VALUES NOT ALREADY EXISTING IN THE DATA WATCHER THIS
	 * IS STRICTLY FOR SETTING VALUES!!!!
	 * 
	 * @param <T>          Specifies the data type
	 * @param index        Which metadata value to change
	 * @param data         The new data value
	 * @param registryType the name of the variable that corresponds to the data
	 *                     type you are looking for (Check DataWatcherRegistry for
	 *                     more)
	 */
	public <T> void watch(int index, T data, String registryType) {

		try {

			if (ReflectionApi.version.isNewer("1.9")) {
				Object obj = ReflectionApi.getNMSClass("DataWatcherRegistry").getField(registryType).get(null);
				Object obj1 = ReflectionApi.getNMSClass("DataWatcherSerializer").getMethod("a", int.class).invoke(obj, index);
				((Map<Integer, Object>) this.getFieldFromNmsObject(ReflectionApi.version.isOlder("1.10") ? "c" : "d")).remove(index);
				staticClass.getMethod("register", ReflectionApi.getNMSClass("DataWatcherObject"), Object.class).invoke(this.nmsObject, obj1, data);
				staticClass.getMethod("set", ReflectionApi.getNMSClass("DataWatcherObject"), Object.class).invoke(this.nmsObject, obj1, data);
			}
			else {
				nmsObject.getClass().getMethod("watch", int.class, Object.class).invoke(nmsObject, index, data);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("DataWatcher");

	public static NMSDataWatcherReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSDataWatcherReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSDataWatcherReflection");

	}

}
