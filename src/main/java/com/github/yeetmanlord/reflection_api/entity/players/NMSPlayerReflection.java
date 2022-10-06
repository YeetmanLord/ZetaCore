package com.github.yeetmanlord.reflection_api.entity.players;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.NMSEntityReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.server.NMSServerReflection;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;

public class NMSPlayerReflection extends NMSEntityReflection {

	private Object nmsPlayer;

	private NMSPlayerConnectionReflection connection;

	public NMSPlayerReflection(NMSServerReflection server, NMSWorldServerReflection world, GameProfile profile, NMSPlayerInteractManagerReflection interactManager) throws Exception {

		super(staticClass.getConstructor(server.getNmsServer().getClass().getSuperclass(), world.getNmsWorldServer().getClass(), profile.getClass(), interactManager.getNmsManager().getClass()).newInstance(server.getNmsServer(), world.getNmsWorldServer(), profile, interactManager.getNmsManager()));
		nmsPlayer = this.nmsObject;
		connection = new NMSPlayerConnectionReflection(this);

	}

	public NMSPlayerReflection(Player player) {

		super(player);
		this.nmsPlayer = ReflectionApi.getHandle(player);
		connection = new NMSPlayerConnectionReflection(this);

	}

	public NMSPlayerReflection(Object nmsPlayer) {

		super(nmsPlayer);

		if (staticClass.isInstance(nmsPlayer)) {
			this.nmsPlayer = staticClass.cast(nmsPlayer);
			connection = new NMSPlayerConnectionReflection(this);
		}

	}

	/**
	 * Get a Bukkit {@link Player}
	 * 
	 * @return Returns the Bukkit {@link Player} associated with this reflection
	 */
	public Player getBukkitPlayer() {

		try {
			return (Player) invokeMethodForNmsObject("getBukkitEntity");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static boolean isInstance(NMSEntityReflection entity) {

		return staticClass.isInstance(entity.getNmsEntity());

	}

	/**
	 * @return The EntityPlayer associated with this reflection you would usually
	 *         use this for packets
	 */
	public Object getNmsPlayer() {

		return nmsPlayer;

	}

	/**
	 * This function gets a reflection of an EntityPlayer's player connection
	 * 
	 * @return The reflected player connection
	 */
	public NMSPlayerConnectionReflection getPlayerConnection() {

		return this.connection;

	}

	@Override
	public String toString() {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", nmsObject.getClass());
		values.put("entity", this.nmsObject);
		values.put("location", ImmutableMap.of("x", locX, "y", locY, "z", locZ));
		return "PlayerReflection" + values.toString();

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("EntityPlayer");

	public static NMSPlayerReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSPlayerReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerReflection");

	}

}
