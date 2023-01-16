package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;

import java.lang.reflect.Field;

public enum EnumEquipmentSlot {
    MAINHAND(SlotFunction.HAND, 0, 0, 0, "mainhand", "MAINHAND"),
    OFFHAND(SlotFunction.HAND, 1, 5, 0, "offhand", "OFFHAND"),
    FEET(SlotFunction.ARMOR, 0, 1, 1, "feet", "FEET"),
    LEGS(SlotFunction.ARMOR, 1, 2, 2, "legs", "LEGS"),
    CHEST(SlotFunction.ARMOR, 2, 3, 3, "chest", "CHEST"),
    HEAD(SlotFunction.ARMOR, 3, 4, 4, "head", "HEAD");


    private final SlotFunction function;
    private final int index;
    private final int filterFlag;
    private final int legacyEquipmentSlotNum;
    private final String name;
    private final String enumItemSlotCounterpart;

    EnumEquipmentSlot(SlotFunction function, int slotType, int equipmentSlotNum, int legacyEquipmentSlotNum, String name, String enumItemSlotCounterpart) {
        this.function = function;
        this.index = slotType;
        this.filterFlag = equipmentSlotNum;
        this.legacyEquipmentSlotNum = legacyEquipmentSlotNum;
        this.name = name;
        this.enumItemSlotCounterpart = enumItemSlotCounterpart;
    }

    public Object EnumItemSlot() {
        Class<?> ClassEnumItemSlot = ReflectionApi.getNMSClass(Mappings.ENTITY_PACKAGE_MAPPING, "EnumItemSlot");
        try {
            assert ClassEnumItemSlot != null;
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_17)) {
                Field f = ClassEnumItemSlot.getField(this.enumItemSlotCounterpart);
                return f.get(null);
            } else {
                return ClassEnumItemSlot.getEnumConstants()[ordinal()];
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getFilterFlag() {
        return filterFlag;
    }

    public int getLegacyEquipmentSlotNum() {
        return legacyEquipmentSlotNum;
    }

    public int getIndex() {
        return index;
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
