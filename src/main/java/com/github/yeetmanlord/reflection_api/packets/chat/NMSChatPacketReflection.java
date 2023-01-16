package com.github.yeetmanlord.reflection_api.packets.chat;

import java.util.*;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSChatPacketReflection extends NMSPacketReflection {

    private static HashMap<Class<?>, Integer> classes = new HashMap<>();

    static {
        classes.put(ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "IChatBaseComponent"), 0);
    }

    /**
     * Create a chat packet from a chat component gotten using {@link NMSChatSerializerReflection#createChatComponentFromText(String)} or {@link NMSChatSerializerReflection#createChatComponentFromJSON(String)}.
     * This will be displayed in the specified chat position.
     *
     * @param chatComponent The chat component to send.
     * @param data          The chat position to display the chat in. The values in the replacement EnumChatPosition match their NMS counterparts.
     * @param sender        The sender of the chat packet. Use UUID(0, 0) for the server.
     * @apiNote As of 1.19, the sender parameter is no longer used. It is still required for backwards compatibility. Instead, this sends a system chat packet.
     */
    public NMSChatPacketReflection(Object chatComponent, EnumChatPosition data, UUID sender) {
        super(Mappings.CHAT_PACKET_CLASS_MAPPING, classes, getArgs(chatComponent, data, sender));
    }

    private static Object[] getArgs(Object chatComponent, EnumChatPosition data, UUID sender) {
        List<Object> args = new ArrayList<>();
        args.add(chatComponent);
        if (ReflectionApi.version.isNewer(ReflectionApi.v1_19_1)) {
            switch (data) {
                case CHAT:
                case SYSTEM_MESSAGE:
                    args.add(true);
                    break;
                case GAME_INFO:
                    args.add(false);
                    break;
            }
        } else if (ReflectionApi.version.isNewer(ReflectionApi.v1_19)) {
            args.add(data.ordinal());
        } else {
            args.add(data.get());
            if (ReflectionApi.version.isNewer(ReflectionApi.v1_16)) {
                args.add(sender);
            }
        }
        return args.toArray();
    }

    public NMSChatPacketReflection(Object chatComponent, EnumChatPosition data) {

        this(chatComponent, data, new UUID(0, 0));

    }

    public NMSChatPacketReflection(String text, EnumChatPosition data, UUID sender) {
        this(NMSChatSerializerReflection.createChatComponentFromText(text), data, sender);
    }

    public NMSChatPacketReflection(String text, EnumChatPosition data) {

        this(NMSChatSerializerReflection.createChatComponentFromText(text), data);

    }

    public NMSChatPacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static final Class<?> staticClass;

    static {
        try {
            staticClass = Mappings.CHAT_PACKET_CLASS_MAPPING.getNMSClassMapping();
        } catch (MappingsException e) {
            throw new RuntimeException(e);
        }
    }

    public static NMSChatPacketReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSChatPacketReflection(refl.getNMSObject());
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
            return ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "ChatMessageType").getEnumConstants()[this.ordinal()];
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
