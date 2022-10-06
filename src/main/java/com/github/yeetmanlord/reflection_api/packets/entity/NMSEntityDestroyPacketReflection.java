package com.github.yeetmanlord.reflection_api.packets.entity;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSEntityDestroyPacketReflection extends NMSPacketReflection {

	public NMSEntityDestroyPacketReflection(int... entityIds) {
		super("PacketPlayOutEntityDestroy", entityIds);
	}

	public NMSEntityDestroyPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("PacketPlayOutEntityDestroy");

	public static NMSEntityDestroyPacketReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSEntityDestroyPacketReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityDestroyPacketReflection");

	}

}
