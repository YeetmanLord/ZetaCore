package com.github.yeetmanlord.reflection_api.packets.network;

import java.lang.reflect.Constructor;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import io.netty.channel.Channel;

public class NMSNetworkManagerReflection extends NMSObjectReflection {

	/**
	 * @param value Allowed values are SERVERBOUND and CLIENTBOUND although case
	 *              doesn't matter
	 */
	public NMSNetworkManagerReflection(String value) {

		super(init(value));

	}

	public NMSNetworkManagerReflection(Object nmsObject) {

		super(nmsObject);

	}

	private static Object init(String value) {

		try {
			Class<?> enumProtocolDirection = ReflectionApi.getNMSClass("EnumProtocolDirection");
			Object direction = enumProtocolDirection.getField(value.toUpperCase()).get(null);
			Constructor<?> managerConstructor = staticClass.getConstructor(enumProtocolDirection);
			return managerConstructor.newInstance(direction);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public Object getNmsNetworkManager() {

		return nmsObject;

	}

	public Channel getChannel() {

		try {

			return Mappings.NETWORK_MANAGER_CHANNEL_MAPPING.getField(this);

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	private static Class<?> staticClass = ReflectionApi.getNMSClass("NetworkManager");

	public static NMSNetworkManagerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSNetworkManagerReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNetworkManagerReflection");

	}

}
