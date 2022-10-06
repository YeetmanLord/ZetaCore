package com.github.yeetmanlord.reflection_api.chat_components;

import java.lang.reflect.Constructor;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;

public class NMSChatComponentTextReflection extends NMSObjectReflection {

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("ChatComponentText");

	public NMSChatComponentTextReflection(String text) {

		super(init(text));

	}

	public NMSChatComponentTextReflection(Object nmsObject) {

		super(nmsObject);

	}

	private static Object init(String text) {

		try {
			Constructor<?> constr = staticClass.getConstructor(String.class);
			return constr.newInstance(text);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public Object getComponent() {

		return nmsObject;

	}

	public static NMSChatComponentTextReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSChatComponentTextReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSChatComponentTextReflection");

	}

}
