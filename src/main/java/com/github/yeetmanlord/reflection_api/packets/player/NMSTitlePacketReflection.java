package com.github.yeetmanlord.reflection_api.packets.player;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatComponentTextReflection;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.types.ClassNameMapping;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;

public class NMSTitlePacketReflection extends NMSPacketReflection {

    private static HashMap<Class<?>, Integer> classes = new HashMap<>();

    static {
        classes.put(ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "IChatBaseComponent"), 1);
    }

    /**
     * For adding timings to titles
     *
     * @param fadeIn    Time in ticks it takes for the title to fade in.
     * @param timeShown Time in ticks before the title starts to fade out
     * @param fadeOut   Time in ticks it takes for the title to fade out and
     *                  disappear
     */
    public NMSTitlePacketReflection(int fadeIn, int timeShown, int fadeOut) {

        this(NMSEnumTitleAction.TIMES, null, fadeIn, timeShown, fadeOut);

    }

    /**
     * For creating an actual title packet without timings.
     *
     * @param enumTitleAction Based on EnumTitleAction allowed values are TITLE,
     *                        SUBTITLE, TIMES, CLEAR, and RESET
     * @param chatComponent   An NMS IChatBaseComponent gotten either by
     *                        {@link NMSChatSerializerReflection#createChatComponentFromJSON(String)},
     *                        {@link NMSChatSerializerReflection#createChatComponentFromText(String)},
     *                        or
     *                        {@link NMSChatComponentTextReflection#getComponent()}
     */
    public NMSTitlePacketReflection(NMSEnumTitleAction enumTitleAction, Object chatComponent) {

        this(enumTitleAction, chatComponent, -1, -1, -1);

    }

    /**
     * To create a title packet with timings or to update an existing packet before
     * sending it (like add timings)
     *
     * @param enumTitleAction Based on EnumTitleAction allowed values are TITLE,
     *                        SUBTITLE, TIMES, CLEAR, and RESET
     * @param chatComponent   An NMS IChatBaseComponent gotten either by
     *                        {@link NMSChatSerializerReflection#createChatComponentFromJSON(String)},
     *                        {@link NMSChatSerializerReflection#createChatComponentFromText(String)},
     *                        or
     *                        {@link NMSChatComponentTextReflection#getComponent()}
     * @param fadeIn          Time in ticks it takes for the title to fade in.
     * @param timeShow        Time in ticks before the title starts to fade out
     * @param fadeOut         Time in ticks it takes for the title to fade out and
     *                        disappear
     */
    public NMSTitlePacketReflection(NMSEnumTitleAction enumTitleAction, Object chatComponent, int fadeIn, int timeShow, int fadeOut) {
        super(init(enumTitleAction, chatComponent, fadeIn, timeShow, fadeOut));

    }

    private static Object init(NMSEnumTitleAction enumTitleAction, Object chatComponent, int fadeIn, int timeShow, int fadeOut) {
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
            try {
                Class<?> packetClass = ReflectionApi.getNMSClass("PacketPlayOutTitle");
                Object action = getTitleAction(enumTitleAction);
                Constructor<?> constructor = packetClass.getConstructor(action.getClass(), ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "IChatBaseComponent"), int.class, int.class, int.class);
                return constructor.newInstance(action, chatComponent, fadeIn, timeShow, fadeOut);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class<?> packetClass = enumTitleAction.getNMSClass();
                switch (enumTitleAction) {
                    case TITLE:
                    case SUBTITLE:
                        Constructor<?> constructor = packetClass.getConstructor(ReflectionApi.getNMSClass(Mappings.CHAT_PACKAGE_MAPPING, "IChatBaseComponent"));
                        return constructor.newInstance(chatComponent);

                    case TIMES:
                        Constructor<?> constructor2 = packetClass.getConstructor(int.class, int.class, int.class);
                        return constructor2.newInstance(fadeIn, timeShow, fadeOut);

                    case CLEAR:
                        Constructor<?> constructor3 = packetClass.getConstructor(boolean.class);
                        return constructor3.newInstance(false);

                    case RESET:
                        Constructor<?> constructor4 = packetClass.getConstructor(boolean.class);
                        return constructor4.newInstance(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Object getTitleAction(NMSEnumTitleAction enumField) {

        try {
            Class<?> clazz = Mappings.ENUM_TITLE_ACTION_CLASS_MAPPING.getNMSClassMapping();
            return clazz.getField(enumField.name()).get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static NMSTitlePacketReflection clear() {

        return new NMSTitlePacketReflection(NMSEnumTitleAction.CLEAR, NMSChatSerializerReflection.createChatComponentFromText("&6"));

    }

    public NMSTitlePacketReflection(Object nmsObject) {

        super(nmsObject);

    }

    public static NMSTitlePacketReflection cast(NMSObjectReflection refl) {

        if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
            Class<?> clazz = ReflectionApi.getNMSClass("PacketPlayOutTitle");
            if (clazz.isInstance(refl.getNMSObject())) {
                return new NMSTitlePacketReflection(refl.getNMSObject());
            }
        } else {
            try {
                Class<?>[] classes = new Class[]{Mappings.TITLE_TIMINGS_PACKET_CLASS_MAPPING.getNMSClassMapping(),
                        Mappings.TITLE_PACKET_CLASS_MAPPING.getNMSClassMapping(),
                        Mappings.TITLE_CLEAR_PACKET_CLASS_MAPPING.getNMSClassMapping(),
                        Mappings.SUBTITLE_PACKET_CLASS_MAPPING.getNMSClassMapping()};

                for (Class<?> clazz : classes) {
                    if (clazz.isInstance(refl.getNMSObject())) {
                        NMSTitlePacketReflection packet = new NMSTitlePacketReflection(refl.getNMSObject());
                    }
                }
            } catch (MappingsException e) {
                e.printStackTrace();
            }
        }

        throw new ClassCastException("Cannot cast " + refl + " to NMSTitlePacketReflection");

    }

    public enum NMSEnumTitleAction {
        TITLE(Mappings.TITLE_PACKET_CLASS_MAPPING),
        SUBTITLE(Mappings.SUBTITLE_PACKET_CLASS_MAPPING),
        TIMES(Mappings.TITLE_TIMINGS_PACKET_CLASS_MAPPING),
        CLEAR(Mappings.TITLE_CLEAR_PACKET_CLASS_MAPPING),
        RESET(Mappings.TITLE_CLEAR_PACKET_CLASS_MAPPING);

        ClassNameMapping mapping;

        NMSEnumTitleAction(ClassNameMapping mapping) {
            this.mapping = mapping;
        }

        Class<?> getNMSClass() {
            try {
                return mapping.getNMSClassMapping();
            } catch (MappingsException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
