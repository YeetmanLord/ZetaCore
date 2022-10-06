package com.github.yeetmanlord.reflection_api.entity.players;

import java.lang.reflect.Constructor;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;

public class NMSPlayerInteractManagerReflection extends NMSObjectReflection {

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

		try {
			Constructor<?> managerConstructor = staticClass.getConstructor(worldServer.getNmsWorldServer().getClass().getSuperclass());
			return managerConstructor.newInstance(ReflectionApi.getNMSClass("World").cast(worldServer.getNmsWorldServer()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("PlayerInteractManager");

	public static NMSPlayerInteractManagerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSPlayerInteractManagerReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerInteractManagerReflection");

	}

}
