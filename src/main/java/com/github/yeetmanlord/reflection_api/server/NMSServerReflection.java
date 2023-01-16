package com.github.yeetmanlord.reflection_api.server;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import org.bukkit.Server;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;

public class NMSServerReflection extends NMSObjectReflection {

	public NMSServerReflection(NMSWorldServerReflection world) {

		super(world.getNMSServer());

	}

	public NMSServerReflection(Object nmsObject) {

		super(nmsObject);

	}

	public NMSServerReflection(Server server) {

		super(server, "getServer");

	}

	public Object getNmsServer() {

		return nmsObject;

	}

	@Override
	public String toString() {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", nmsObject.getClass());
		values.put("server", nmsObject);
		return "ServerReflection" + values.toString();

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.SERVER_PACKAGE_MAPPING, "MinecraftServer");

	public static NMSServerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNMSObject())) {
			return new NMSServerReflection(refl.getNMSObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSServerReflection");

	}

}
