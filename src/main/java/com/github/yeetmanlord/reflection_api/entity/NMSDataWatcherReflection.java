package com.github.yeetmanlord.reflection_api.entity;

import java.lang.reflect.InvocationTargetException;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import org.bukkit.Bukkit;

public class NMSDataWatcherReflection extends NMSObjectReflection {

    public NMSDataWatcherReflection(NMSEntityReflection entity) {

        super(init(entity));

    }

    public NMSDataWatcherReflection(Object nmsObject) {

        super(nmsObject);

    }

    private static Object init(NMSEntityReflection entity) {

        try {
            return Mappings.ENTITY_GET_DATA_WATCHER_MAPPING.runMethod(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Object getNmsDataWatcher() {

        return nmsObject;

    }

    /**
     * WARNING DOES NOT ACCEPT VALUES NOT ALREADY EXISTING IN THE DATA WATCHER THIS
     * IS STRICTLY FOR SETTING VALUES!!!! As of 1.14+ you get an error you cannot set values after
	 * the entity has been initialized. So this method takes this into account and will not call
	 * the register method after 1.14.
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
            if (ReflectionApi.version.isNewer(ReflectionApi.v1_9)) {
                Object obj = ReflectionApi.getNMSClass(Mappings.DATAWATCHER_PACKAGE_MAPPING, "DataWatcherRegistry").getField(registryType).get(null);
                Object obj1 = ReflectionApi.getNMSClass(Mappings.DATAWATCHER_PACKAGE_MAPPING, "DataWatcherSerializer").getMethod("a", int.class).invoke(obj, index);
                Mappings.ENTITY_DATA_WATCHER_SET_ENTRY_MAPPING.runMethod(this, obj1, data);
            } else {
                nmsObject.getClass().getMethod("watch", int.class, Object.class).invoke(nmsObject, index, data);
            }

        } catch (InvocationTargetException exc) {
            if (exc.getCause() instanceof IllegalStateException) {
                Bukkit.getConsoleSender().sendMessage("§c[ReflectionAPI] §7DataWatcher attempted to register entry after entity was spawned. This is not supported in 1.14+.");
            } else if (exc.getCause() instanceof IllegalArgumentException) {
				Bukkit.getConsoleSender().sendMessage("§c[ReflectionAPI] §7DataWatcher attempted to register entry but something went wrong. Please check server logs and report this to the plugin developer. Or if appropriate, submit a bug report to the ReflectionAPI github.");
				exc.printStackTrace();
			} else {
				exc.printStackTrace();
			}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.DATAWATCHER_PACKAGE_MAPPING, "DataWatcher");

    public static NMSDataWatcherReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSDataWatcherReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSDataWatcherReflection");

    }

}
