package com.github.yeetmanlord.reflection_api.packets.entity;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSNamedEntitySpawnPacketReflection extends NMSPacketReflection {

	private static HashMap<Class<?>, Integer> classObjectMap = new HashMap<>();
	static {
		classObjectMap.put(ReflectionApi.getNMSClass("EntityHuman"), 0);
	}

	public NMSNamedEntitySpawnPacketReflection(NMSPlayerReflection entity) {

		super("PacketPlayOutNamedEntitySpawn", classObjectMap, entity.getNmsPlayer());

	}

	public NMSNamedEntitySpawnPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("PacketPlayOutNamedEntitySpawn");

	public static NMSNamedEntitySpawnPacketReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSNamedEntitySpawnPacketReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNamedEntitySpawnPacketReflection");

	}

}
