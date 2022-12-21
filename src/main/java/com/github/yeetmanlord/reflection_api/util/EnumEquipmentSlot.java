package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;

import java.lang.reflect.Field;

public enum EnumEquipmentSlot {
    MAINHAND(SlotFunction.HAND, 0, 0, 0, "mainhand", "MAINHAND"),
    OFFHAND(SlotFunction.HAND, 1, 5, 0, "offhand", "OFFHAND"),
    FEET(SlotFunction.ARMOR, 0, 1, 1, "feet", "FEET"),
    LEGS(SlotFunction.ARMOR, 1, 2, 2, "legs", "LEGS"),
    CHEST(SlotFunction.ARMOR, 2, 3, 3, "chest", "CHEST"),
    HEAD(SlotFunction.ARMOR, 3, 4, 4, "head", "HEAD");


    private final SlotFunction function;
    private final int slotType;
    private final int equipmentSlotNum;
    private final int legacyEquipmentSlotNum;
    private final String name;
    private final String enumItemSlotCounterpart;

    EnumEquipmentSlot(SlotFunction function, int slotType, int equipmentSlotNum, int legacyEquipmentSlotNum, String name, String enumItemSlotCounterpart) {
        this.function = function;
        this.slotType = slotType;
        this.equipmentSlotNum = equipmentSlotNum;
        this.legacyEquipmentSlotNum = legacyEquipmentSlotNum;
        this.name = name;
        this.enumItemSlotCounterpart = enumItemSlotCounterpart;
    }

    public Object EnumItemSlot() {
        Class<?> ClassEnumItemSlot = ReflectionApi.getNMSClass("EnumItemSlot");
        try {
            assert ClassEnumItemSlot != null;
            Field f = ClassEnumItemSlot.getField(this.enumItemSlotCounterpart);
            return f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getEquipmentSlotNum() {
        return equipmentSlotNum;
    }

    public int getLegacyEquipmentSlotNum() {
        return legacyEquipmentSlotNum;
    }

    public int getSlotType() {
        return slotType;
    }

    public SlotFunction getFunction() {
        return function;
    }

    public String getEnumItemSlotCounterpart() {
        return enumItemSlotCounterpart;
    }

    public String getName() {
        return name;
    }

    public static EnumEquipmentSlot getByName(String name) {
        for (EnumEquipmentSlot value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid slot '" + name + "'");
    }

    public enum SlotFunction {
        HAND, ARMOR;
    }
}
