package com.github.yeetmanlord.reflection_api.packets.network;

import java.lang.reflect.Constructor;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import io.netty.channel.Channel;

public class NMSNetworkManagerReflection extends NMSObjectReflection {

	/**
	 * @param value The network direction to use. The values in the replacement EnumNetworkDirection match their counterparts
	 *                 in the original EnumProtocolDirection.
	 */
	public NMSNetworkManagerReflection(EnumNetworkDirection value) {

		super(init(value));

	}

	public NMSNetworkManagerReflection(Object nmsObject) {

		super(nmsObject);

	}

	private static Object init(EnumNetworkDirection value) {

		try {
			Class<?> enumProtocolDirection = ReflectionApi.getNMSClass(Mappings.PROTOCOL_PACKAGE_MAPPING, "EnumProtocolDirection");
			Object direction = enumProtocolDirection.getEnumConstants()[value.ordinal()];
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


	public static Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.NETWORK_PACKAGE_MAPPING, "NetworkManager");

	public static NMSNetworkManagerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNMSObject())) {
			return new NMSNetworkManagerReflection(refl.getNMSObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNetworkManagerReflection");

	}

	public enum EnumNetworkDirection {
		SERVERBOUND, CLIENTBOUND;


	}

}
