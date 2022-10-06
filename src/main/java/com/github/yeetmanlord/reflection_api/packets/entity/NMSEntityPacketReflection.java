package com.github.yeetmanlord.reflection_api.packets.entity;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSEntityPacketReflection extends NMSPacketReflection {

	private static HashMap<Class<?>, Integer> classes = new HashMap<>();
	static {
		classes.put(ReflectionApi.getNMSClass("Entity"), 0);
	}

	/**
	 * A small extension to {@link NMSPacketReflection} that allows you to input any
	 * entity so you don't have to cast This only works for packets that have the
	 * entity as the first parameter.
	 * 
	 * @param packetName The name of the packet you want to use
	 * @param entity
	 */
	public NMSEntityPacketReflection(String packetName, NMSEntityReflection entity, Object arg1, Object arg2, Object arg3) {

		super(packetName, classes, entity.getNmsEntity(), arg1, arg2, arg3);

	}

	public NMSEntityPacketReflection(String packetName, NMSEntityReflection entity, Object arg1, Object arg2) {

		super(packetName, classes, entity.getNmsEntity(), arg1, arg2);

	}

	public NMSEntityPacketReflection(String packetName, NMSEntityReflection entity, Object arg1) {

		super(packetName, classes, entity.getNmsEntity(), arg1);

	}

	/**
	 * Does the same thing as
	 * {@link NMSEntityPacketReflection#NMSEntityPacketReflection(String, NMSEntityReflection, Object...)}
	 * but doesn't take any extra arguements
	 * 
	 * @param packetName
	 * @param entity
	 */
	public NMSEntityPacketReflection(String packetName, NMSEntityReflection entity) {

		super(packetName, classes, entity.getNmsEntity());

	}

	public NMSEntityPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static NMSEntityPacketReflection cast(NMSObjectReflection refl, String packetName) {

		Class<?> clazz = ReflectionApi.getNMSClass(packetName);

		if (clazz.isInstance(refl.getNmsObject())) {
			return new NMSEntityPacketReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityPacketReflection(" + packetName + ")");

	}

}
