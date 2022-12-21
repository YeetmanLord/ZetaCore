package com.github.yeetmanlord.reflection_api.packets.entity;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.packets.NMSPacketReflection;
import com.github.yeetmanlord.reflection_api.util.EnumEquipmentSlot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NMSEntityEquipmentPacketReflection extends NMSPacketReflection {
    public NMSEntityEquipmentPacketReflection(int entityId, EnumEquipmentSlot slot, ItemStack stack) {
        super(init(entityId, slot, stack));
    }

    public static Object init(int entityId, EnumEquipmentSlot slot, ItemStack stack) {
        if (ReflectionApi.version.isOlder("1.9")) {
             return new NMSPacketReflection("PacketPlayOutEntityEquipment", entityId, slot.getLegacyEquipmentSlotNum(), new NMSItemStackReflection(stack).getNmsObject()).getNmsPacket();
        }
        else {
            return new NMSPacketReflection("PacketPlayOutEntityEquipment", entityId, slot.EnumItemSlot(), new NMSItemStackReflection(stack).getNmsObject()).getNmsPacket();
        }

    }
}
