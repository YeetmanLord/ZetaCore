package com.github.yeetmanlord.reflection_api.packets.entity;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSNamedEntitySpawnPacketReflection extends NMSPacketReflection {

	private static HashMap<Class<?>, Integer> classObjectMap = new HashMap<>();
	static {
		classObjectMap.put(ReflectionApi.getNMSClass(Mappings.WORLD_PLAYER_PACKAGE_MAPPING, "EntityHuman"), 0);
	}

	public NMSNamedEntitySpawnPacketReflection(NMSPlayerReflection entity) {

		super(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutNamedEntitySpawn", classObjectMap, entity.getNmsPlayer());

	}

	public NMSNamedEntitySpawnPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutNamedEntitySpawn");

	public static NMSNamedEntitySpawnPacketReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNMSObject())) {
			return new NMSNamedEntitySpawnPacketReflection(refl.getNMSObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNamedEntitySpawnPacketReflection");

	}

}
