package com.github.yeetmanlord.reflection_api.packets.entity;

import org.bukkit.Bukkit;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

import net.md_5.bungee.api.ChatColor;

public class NMSPacketPlayOutRelEntityMove extends NMSPacketReflection {

	public NMSPacketPlayOutRelEntityMove(int entityId, long x, long y, long z, byte pitch, byte yaw, boolean onGround) {

		super(Mappings.PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING, getVersionDependentArgs(entityId, x, y, z, pitch, yaw, onGround));

	}

	private static Object[] getVersionDependentArgs(int entityId, long x, long y, long z, byte pitch, byte yaw, boolean onGround) {

		Object[] args = new Object[7];
		args[0] = entityId;

		if (ReflectionApi.version.isOlder("1.9")) {
			byte bX = x > Byte.MAX_VALUE ? Byte.MAX_VALUE : x < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) x;
			byte bY = y > Byte.MAX_VALUE ? Byte.MAX_VALUE : y < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) y;
			byte bZ = z > Byte.MAX_VALUE ? Byte.MAX_VALUE : z < Byte.MIN_VALUE ? Byte.MIN_VALUE : (byte) z;

			args[1] = bX;
			args[2] = bY;
			args[3] = bZ;
		}
		else {
			args[1] = x;
			args[2] = y;
			args[3] = z;
		}

		args[4] = pitch;
		args[5] = yaw;
		args[6] = onGround;

		return args;

	}

	public NMSPacketPlayOutRelEntityMove(Object nmsObject) {

		super(nmsObject);

	}

	private static Class<?> staticClass;

	static {

		try {
			staticClass = Mappings.PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING.getNMSClassMapping();
		}
		catch (MappingsException exc) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "ERROR! Could not load mapping PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING");
			exc.printStackTrace();
		}

	}

	public static NMSPacketPlayOutRelEntityMove cast(NMSObjectReflection refl) throws MappingsException {

		if (staticClass != null) {

			if (staticClass.isInstance(refl.getNmsObject())) {
				return new NMSPacketPlayOutRelEntityMove(refl.getNmsObject());
			}

			throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPacketPlayOutRelEntityMove");
		}

		throw new MappingsException(Mappings.PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING, "PACKET_PLAY_OUT_REL_ENTITY_MOVE_LOOK_CLASS_MAPPING was not loaded properly meaning that this interaction has failed!");

	}

}
