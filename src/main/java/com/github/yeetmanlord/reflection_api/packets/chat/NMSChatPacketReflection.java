package com.github.yeetmanlord.reflection_api.packets.chat;

import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSChatPacketReflection extends NMSPacketReflection {

    private static HashMap<Class<?>, Integer> classes = new HashMap<>();

    static {
        classes.put(ReflectionApi.getNMSClass("IChatBaseComponent"), 0);
    }

    public NMSChatPacketReflection(Object chatComponent, EnumChatPosition data) {

        super("PacketPlayOutChat", classes, chatComponent, data.get());

    }

    public NMSChatPacketReflection(String text, EnumChatPosition data) {

        this(NMSChatSerializerReflection.createChatComponentFromText(text), data);

    }

    public NMSChatPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass("PacketPlayOutChat");

    public static NMSChatPacketReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNmsObject())) {
            return new NMSChatPacketReflection(refl.getNmsObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSChatPacketReflection");

    }

    public enum EnumChatPosition {
        CHAT((byte) 0),
        SYSTEM_MESSAGE((byte) 1),
        GAME_INFO((byte) 2);

        private byte id;

        EnumChatPosition(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }

        public Object getChatMessageType() {
            return ReflectionApi.getNMSClass("ChatMessageType").getEnumConstants()[this.ordinal()];
        }

        public Object get() {
            if (ReflectionApi.version.isOlder("1.12")) {
                return this.getId();
            } else {
                return this.getChatMessageType();
            }
        }
    }

}
