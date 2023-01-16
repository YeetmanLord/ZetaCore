package com.github.yeetmanlord.reflection_api.packets.in;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

/**
 * This class acts more as a parser rather than a wrapper. You can't create your own EntityUsePacket since they are serverbound.
 * You can only parse them. Thus, this class.
 * This packet class parses the EntityUsePacket and its action and hand.
 *
 * @author YeetManLord
 */
public class NMSEntityUsePacketReflection extends NMSPacketReflection {

    private EnumAction action;
    private EnumActionHand hand;

    public NMSEntityUsePacketReflection(Object nmsPacket) {
        super(nmsPacket);
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
                action = EnumAction.getFromEnumEntityUseAction((Enum<?>) Mappings.PACKET_ENTITY_USE_GET_ACTION_MAPPING.runMethod(this));

                if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
                    hand = EnumActionHand.MAIN_HAND;
                } else {
                    hand = EnumActionHand.getFromEnumHand((Enum<?>) Mappings.PACKET_ENTITY_USE_GET_HAND_MAPPING.runMethod(this));
                }
            } else {
                NMSObjectReflection actionObject = new NMSObjectReflection(this.getFieldFromNmsObject("b"));
                action = EnumAction.getFromEnumEntityUseAction((Enum<?>) actionObject.invokeMethodForNmsObject("a"));

                if (action == EnumAction.ATTACK) {
                    hand = EnumActionHand.MAIN_HAND;
                } else {
                    hand = EnumActionHand.getFromEnumHand((Enum<?>) actionObject.getFieldFromNmsObject("a"));
                }
            }
        } catch (MappingsException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public EnumAction getAction() {
        return action;
    }

    public EnumActionHand getHand() {
        return hand;
    }

    public enum EnumAction {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        public static EnumAction getFromEnumEntityUseAction(Enum<?> enumEntityUseAction) {
            return values()[enumEntityUseAction.ordinal()];
        }
    }

    public enum EnumActionHand {
        MAIN_HAND,
        OFF_HAND;

        public static EnumActionHand getFromEnumHand(Enum<?> enumHand) {
            if (enumHand == null) {
                return MAIN_HAND;
            }
            return values()[enumHand.ordinal()];
        }
    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayInUseEntity");
}
