package com.github.yeetmanlord.reflection_api.packets.entity;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.util.EnumEquipmentSlot;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

public class NMSEntityEquipmentPacketReflection extends NMSPacketReflection {
    public NMSEntityEquipmentPacketReflection(int entityId, EnumEquipmentSlot slot, ItemStack stack) {
        super(init(entityId, slot, stack));
    }

    public static Object init(int entityId, EnumEquipmentSlot slot, ItemStack stack) {
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
             return new NMSPacketReflection(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityEquipment", entityId, slot.getLegacyEquipmentSlotNum(), new NMSItemStackReflection(stack).getNMSObject()).getNmsPacket();
        }
        else if (ReflectionApi.version.isOlder(ReflectionApi.v1_16)) {
            return new NMSPacketReflection(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityEquipment", entityId, slot.EnumItemSlot(), new NMSItemStackReflection(stack).getNMSObject()).getNmsPacket();
        } else {
            try {
                Constructor<?> pairConstructor =  Class.forName("com.mojang.datafixers.util.Pair").getConstructor(Object.class, Object.class);
                Object pair = pairConstructor.newInstance(slot.EnumItemSlot(), new NMSItemStackReflection(stack).getNMSObject());
                List<Object> list = Lists.newArrayList(pair);
                HashMap<Class<?>, Integer> classes = new HashMap<>();
                classes.put(int.class, 0);
                classes.put(List.class, 1);
                return new NMSPacketReflection(Mappings.PACKET_PLAY_PACKAGE_MAPPING, "PacketPlayOutEntityEquipment", classes, entityId, list).getNmsPacket();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
