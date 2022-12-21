package com.github.yeetmanlord.reflection_api.packets.in;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSEntityUsePacketReflection extends NMSPacketReflection {

    private EnumAction action;
    private EnumActionHand hand;

    public NMSEntityUsePacketReflection(Object nmsPacket) {
        super(nmsPacket);
        try {
            action = EnumAction.getFromEnumEntityUseAction(this.invokeMethodForNmsObject("a"));

            if (ReflectionApi.version.isOlder("1.9")) {
                hand = EnumActionHand.MAIN_HAND;
            } else {
                hand = EnumActionHand.getFromEnumHand(this.invokeMethodForNmsObject("b"));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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

        public static EnumAction getFromEnumEntityUseAction(Object enumEntityUseAction) {
            return valueOf(enumEntityUseAction.toString());
        }
    }

    public enum EnumActionHand {
        MAIN_HAND,
        OFF_HAND;

        public static EnumActionHand getFromEnumHand(Object enumHand) {
            if (enumHand == null) {
                return MAIN_HAND;
            }
            return valueOf(enumHand.toString());
        }
    }
}
