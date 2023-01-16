package com.github.yeetmanlord.reflection_api.packets.player;

import java.util.Arrays;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSPlayerInfoPacketReflection extends NMSPacketReflection {

    private static HashMap<Class<?>, Integer> specialClasses = new HashMap<>();

    static {

        try {
            specialClasses.put(Mappings.ENUM_PLAYER_ACTION_CLASS_MAPPING.getNMSClassMapping(), 0);
        } catch (MappingsException e) {
            e.printStackTrace();
        }

        specialClasses.put(ReflectionApi.getNMSClassArray(Mappings.SERVER_LEVEL_PACKAGE_MAPPING, "EntityPlayer"), 1);
    }

    /**
     * @param infoValue Allowed values for ADD_PLAYER, UPDATE_GAME_MODE,
     *                  UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER;
     * @param players   Should be {@link NMSPlayerReflection}
     */
    public NMSPlayerInfoPacketReflection(EnumPlayerInfoPacketAction infoValue, NMSPlayerReflection... players) {

        super(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutPlayerInfo", specialClasses, infoValue.EnumPlayerInfoAction(),
                ReflectionApi.castArrayToNMS(Mappings.SERVER_LEVEL_PACKAGE_MAPPING, "EntityPlayer",
                        Arrays.stream(players).map(NMSPlayerReflection::getNmsPlayer).toArray()));

    }

    public NMSPlayerInfoPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutPlayerInfo");

    public static NMSPlayerInfoPacketReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSPlayerInfoPacketReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSPlayerInfoPacketReflection");

    }

    public enum EnumPlayerInfoPacketAction {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;


        EnumPlayerInfoPacketAction() {
        }

        public Object EnumPlayerInfoAction() {
            try {
                return Mappings.ENUM_PLAYER_ACTION_CLASS_MAPPING.getNMSClassMapping().getEnumConstants()[ordinal()];
            } catch (MappingsException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
