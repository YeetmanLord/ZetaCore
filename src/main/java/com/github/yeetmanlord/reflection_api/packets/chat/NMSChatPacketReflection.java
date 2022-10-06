package com.github.yeetmanlord.reflection_api.packets.chat;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSChatPacketReflection extends NMSPacketReflection {

	private static HashMap<Class<?>, Integer> classes = new HashMap<>();
	static {
		classes.put(ReflectionApi.getNMSClass("IChatBaseComponent"), 0);
	}

	public NMSChatPacketReflection(Object chatComponent, byte data) {

		super("PacketPlayOutChat", classes, chatComponent, data);

	}

	public NMSChatPacketReflection(Object nmsObject) {

		super(nmsObject);

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("PacketPlayOutChat");

	public static NMSChatPacketReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSChatPacketReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSChatPacketReflection");

	}

}
