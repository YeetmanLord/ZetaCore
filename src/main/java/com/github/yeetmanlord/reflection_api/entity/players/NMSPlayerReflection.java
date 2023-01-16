package com.github.yeetmanlord.reflection_api.entity.players;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.mappings.Mappings;
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

    /**
     * Creates a new entity player. This constructor works for 1.8.8 - 1.16.5
     *
     * @param server          The server to create the player on.
     * @param world           The world to create the player in.
     * @param profile         The profile of the player.
     * @param interactManager The player's interaction manager.
     * @throws Exception Thrown when a reflection exception is thrown.
     * @deprecated This constructor only works for 1.16.5 and below. Use {@link NMSPlayerReflection#NMSPlayerReflection(NMSServerReflection, NMSWorldServerReflection, GameProfile)} instead.
     */
    public NMSPlayerReflection(NMSServerReflection server, NMSWorldServerReflection world, GameProfile profile, NMSPlayerInteractManagerReflection interactManager) throws Exception {

        super(staticClass.getConstructor(server.getNmsServer().getClass().getSuperclass(), world.getNmsWorldServer().getClass(), profile.getClass(), interactManager.getNmsManager().getClass()).newInstance(server.getNmsServer(), world.getNmsWorldServer(), profile, interactManager.getNmsManager()));
        nmsPlayer = this.nmsObject;
        connection = new NMSPlayerConnectionReflection(this);

    }

    /**
     * Creates a new entity player. This constructor works from 1.8 - 1.19+. This constructor is recommended over
     * {@link NMSPlayerReflection#NMSPlayerReflection(NMSServerReflection, NMSWorldServerReflection, GameProfile, NMSPlayerInteractManagerReflection)}.
     *
     * @param server  The server to create the player on.
     * @param world   The world to create the player in.
     * @param profile The profile of the player.
     * @implNote As of 1.19 the constructor for EntityPlayer had an added parameter for the player's public profile key.
     * When using this constructor on 1.19+, the player's public profile key will be set to null.
     */
    public NMSPlayerReflection(NMSServerReflection server, NMSWorldServerReflection world, GameProfile profile) {
        super(newPlayer(server, world, profile));
        nmsPlayer = this.nmsObject;
        connection = new NMSPlayerConnectionReflection(this);
    }

    private static Object newPlayer(NMSServerReflection server, NMSWorldServerReflection world, GameProfile profile) {
        try {
            if (ReflectionApi.version.isNewer(ReflectionApi.v1_19)) {
                Constructor<?> constructor = staticClass.getConstructor(NMSServerReflection.staticClass, NMSWorldServerReflection.staticClass, GameProfile.class, ReflectionApi.getNMSClass(Mappings.WORLD_PLAYER_PACKAGE_MAPPING,"ProfilePublicKey"));
                return constructor.newInstance(server.getNmsServer(), world.getNmsWorldServer(), profile, null);
            }
            if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
                Constructor<?> constructor = staticClass.getConstructor(NMSServerReflection.staticClass, NMSWorldServerReflection.staticClass, GameProfile.class);
                return constructor.newInstance(server.getNmsServer(), world.getNmsWorldServer(), profile);
            } else {
                Constructor<?> constructor = staticClass.getConstructor(NMSServerReflection.staticClass, NMSWorldServerReflection.staticClass, GameProfile.class, NMSPlayerInteractManagerReflection.staticClass);
                NMSPlayerInteractManagerReflection manager = new NMSPlayerInteractManagerReflection(world);
                return constructor.newInstance(server.getNmsServer(), world.getNmsWorldServer(), profile, manager.getNmsManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static boolean isInstance(NMSEntityReflection entity) {

        return staticClass.isInstance(entity.getNmsEntity());

    }

    /**
     * @return The EntityPlayer associated with this reflection you would usually
     * use this for packets
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
        values.put("location", ImmutableMap.of("x", this.getLocation().getX(), "y", this.getLocation().getY(), "z", this.getLocation().getZ()));
        return "PlayerReflection" + values;

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.SERVER_LEVEL_PACKAGE_MAPPING, "EntityPlayer");

    public static NMSPlayerReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSPlayerReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerReflection");

    }

}
