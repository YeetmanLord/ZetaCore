package com.github.yeetmanlord.reflection_api.packets.entity;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.types.PackageMapping;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSEntityPacketReflection extends NMSPacketReflection {

    private static HashMap<Class<?>, Integer> classes = new HashMap<>();

    static {
        classes.put(NMSEntityReflection.staticClass, 0);
    }

    /**
     * A small extension to {@link NMSPacketReflection} that allows you to input any
     * entity, so you don't have to cast This only works for packets that have the
     * entity as the first parameter.
     *
     * @param mapping    The package mapping of the packet you want to use
     * @param packetName The name of the packet you want to use
     * @param entity    The entity you want to use
     * @param arg1       First positional argument of packet constructor
     * @param arg2       Second positional argument of packet constructor
     * @param arg3       Third positional argument of packet constructor
     * @implNote As far as I know, there are no packets that have more than 3 args.
     * I do it like this because I was having trouble with using Object... args. It just wasn't working
     * well with nested arrays.
     */
    public NMSEntityPacketReflection(PackageMapping mapping, String packetName, NMSEntityReflection entity, Object arg1, Object arg2, Object arg3) {

        super(mapping, packetName, classes, entity.getNmsEntity(), arg1, arg2, arg3);

    }

    /**
     * Creates a new entity packet using the specified entity, packet name, mapping, and arguments.
     *
     * @param mapping    The package mapping of the packet you want to use
     * @param packetName The name of the packet you want to use
     * @param entity     The entity you want to use
     * @param arg1       First positional argument of packet constructor
     * @param arg2       Second positional argument of packet constructor
     */
    public NMSEntityPacketReflection(PackageMapping mapping, String packetName, NMSEntityReflection entity, Object arg1, Object arg2) {

        super(mapping, packetName, classes, entity.getNmsEntity(), arg1, arg2);

    }

    /**
     * Creates a new entity packet using the specified entity, packet name, mapping, and arguments.
     *
     * @param mapping    The package mapping of the packet you want to use
     * @param packetName The name of the packet you want to use
     * @param entity     The entity you want to use
     * @param arg1       First positional argument of packet constructor
     */
    public NMSEntityPacketReflection(PackageMapping mapping, String packetName, NMSEntityReflection entity, Object arg1) {

        super(mapping, packetName, classes, entity.getNmsEntity(), arg1);

    }

    /**
     * Creates a new entity packet using the specified entity, packet name, and mapping.
     *
     * @param mapping    The package mapping of the packet you want to use
     * @param packetName The name of the packet you want to use
     * @param entity     The entity you want to use
     */
    public NMSEntityPacketReflection(PackageMapping mapping, String packetName, NMSEntityReflection entity) {

        super(mapping, packetName, classes, entity.getNmsEntity());

    }

    public NMSEntityPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static NMSEntityPacketReflection cast(NMSObjectReflection refl, String packetName) {

        Class<?> clazz = ReflectionApi.getNMSClass(packetName);

        if (clazz.isInstance(refl.getNMSObject())) {
            return new NMSEntityPacketReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityPacketReflection(" + packetName + ")");

    }

}
