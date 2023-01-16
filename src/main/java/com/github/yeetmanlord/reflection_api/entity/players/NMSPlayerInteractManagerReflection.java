package com.github.yeetmanlord.reflection_api.entity.players;

import java.lang.reflect.Constructor;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;

public class NMSPlayerInteractManagerReflection extends NMSObjectReflection {

	/**
	 * As of 1.17, this is no longer a part of nms code. This means that this class cannot be used.
	 */
	public NMSPlayerInteractManagerReflection(NMSWorldServerReflection worldServer) {

		super(instance(worldServer));

	}

	public NMSPlayerInteractManagerReflection(Object nmsObject) {

		super(nmsObject);

	}

	public Object getNmsManager() {

		return nmsObject;

	}

	public static Object instance(NMSWorldServerReflection worldServer) {
		if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
			throw new UnsupportedOperationException("As of 1.17, this class, PlayerInteractManager, is no longer a part of nms code. This means that this class cannot be used.");
		}

		try {
			Class worldClass = ReflectionApi.getNMSClass("WorldServer");
			if (ReflectionApi.version.isOlder(ReflectionApi.v1_14)) {
				worldClass = ReflectionApi.getNMSClass("World");
			}
			Constructor<?> managerConstructor = staticClass.getConstructor(worldClass);
			assert worldClass != null;
			return managerConstructor.newInstance(worldClass.cast(worldServer.getNmsWorldServer()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Class<?> staticClass;

	static {
		if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
			staticClass = ReflectionApi.getNMSClass("PlayerInteractManager");
		}
	}

	public static NMSPlayerInteractManagerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNMSObject())) {
			return new NMSPlayerInteractManagerReflection(refl.getNMSObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerInteractManagerReflection");

	}

}
