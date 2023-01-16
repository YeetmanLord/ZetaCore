package com.github.yeetmanlord.reflection_api.packets.entity;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.types.PackageMapping;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NMSEntityDestroyPacketReflection extends NMSPacketReflection {

    /**
     * Creates a new NMSEntityDestroyPacketReflection
     * This packet is used to destroy entities on the client
     *
     * @param entityIds List of entity IDs to destroy
     * @deprecated Constructor doesn't work for 1.17+.
     */
    public NMSEntityDestroyPacketReflection(int... entityIds) {
        super(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityDestroy", entityIds);
    }

    /**
     * Creates a new NMSEntityDestroyPacketReflection
     * This packet is used to destroy entities on the client
     *
     * @param entityId ID of entity to destroy
     */
    public NMSEntityDestroyPacketReflection(int entityId) {
        super(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityDestroy", init(entityId));
    }

    private static Object init(int entityId) {
        if (ReflectionApi.version.isNewer(ReflectionApi.v1_17) && ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
            return entityId;
        } else {
            return new int[]{entityId};
        }
    }

    public NMSEntityDestroyPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityDestroy");

    public static NMSEntityDestroyPacketReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSEntityDestroyPacketReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityDestroyPacketReflection");

    }

}
