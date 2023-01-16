package com.github.yeetmanlord.reflection_api.entity.players.player_connection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.network.NMSNetworkManagerReflection;
import com.github.yeetmanlord.reflection_api.server.NMSServerReflection;

public class NMSPlayerConnectionReflection extends NMSObjectReflection {

	private Object nmsPlayerConnection;

	/**
	 * {@link NMSPlayerConnectionReflection#NMSPlayerConnectionReflection(NMSPlayerReflection)}
	 * is for getting a player connection for a normal player While this is for
	 * creating a new connection for fake players
	 */
	public NMSPlayerConnectionReflection(NMSServerReflection server, NMSNetworkManagerReflection netManager, NMSPlayerReflection player) {

		super(instance(server, netManager, player));
		nmsPlayerConnection = nmsObject;

	}

	public NMSPlayerConnectionReflection(Object nmsObject) {

		super(nmsObject);

	}

	/**
	 * Gets a player connection for a bukkit player
	 * 
	 * @param player A Bukkit {@link Player}
	 */
	public NMSPlayerConnectionReflection(Player player) {

		super(instance(player));
		nmsPlayerConnection = nmsObject;

	}

	/**
	 * Gets a player connection for an EntityPlayer reflection
	 * 
	 * @param player An actual player not a fake player/npc
	 */
	public NMSPlayerConnectionReflection(NMSPlayerReflection player) {

		super(getConnection(player));
		nmsPlayerConnection = nmsObject;

	}

	public Object getNmsPlayerConnection() {

		return nmsPlayerConnection;

	}

	public Object nmsNetworkManager() {

		try {
			return Mappings.PLAYER_CONNECTION_NETWORK_MANAGER_MAPPING.getField(this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public void sendPacket(NMSPacketReflection packet) {

		try {
			Mappings.PLAYER_CONNECTION_SEND_PACKET_MAPPING.runMethod(this, packet.getNmsPacket());
		}
		catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[ReflectionAPI/ERROR] Failed to send packet! Packet Type: " + packet.getNmsPacket().getClass().getSimpleName());
			Bukkit.getConsoleSender().sendMessage("§c[ReflectionAPI/ERROR] Packet Reflection: " + packet);
			Bukkit.getConsoleSender().sendMessage("§c[ReflectionAPI/ERROR] NMS Packet: " + packet.getNmsPacket().toString());
			e.printStackTrace();
		}

	}

	private static Object instance(NMSServerReflection server, NMSNetworkManagerReflection netManager, NMSPlayerReflection player) {

		try {
			Constructor<?> playerConnectionConstructor = staticClass.getConstructor(server.getNmsServer().getClass().getSuperclass(), netManager.getNmsNetworkManager().getClass(), player.getNmsPlayer().getClass());
			return playerConnectionConstructor.newInstance(server.getNmsServer(), netManager.getNmsNetworkManager(), player.getNmsPlayer());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private static Object instance(Player player) {

		try {
			NMSPlayerReflection playerReflection = new NMSPlayerReflection(player);
			return playerReflection.getPlayerConnection().nmsPlayerConnection;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Object getConnection(NMSPlayerReflection player) {

		try {
			return Mappings.ENTITY_PLAYER_CONNECTION_MAPPING.getField(player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public NMSNetworkManagerReflection getNetworkManager() {

		return new NMSNetworkManagerReflection(this.nmsNetworkManager());

	}

	@Override
	public String toString() {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", nmsObject.getClass());
		values.put("object", nmsObject);
		return "PlayerConnectionReflection" + values.toString();

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.SERVER_NETWORK_PACKAGE_MAPPING, "PlayerConnection");

	public static NMSPlayerConnectionReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNMSObject())) {
			return new NMSPlayerConnectionReflection(refl.getNMSObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerConnectionReflection");

	}

}
