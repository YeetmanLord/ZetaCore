package com.github.yeetmanlord.reflection_api.nbt;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;

public class NMSNBTBase extends NMSObjectReflection {

    public NMSNBTBase(Object nmsObject) {
        super(nmsObject);
    }

    public byte getTypeID() {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_19)){
                return (byte) this.invokeMethodForNmsObject("getTypeId");
            }
            return (byte) this.invokeMethodForNmsObject("a");
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public NBTType getType() {
        return NBTType.fromID(getTypeID());
    }

    public enum NBTType {
        TAG_BYTE(1),
        TAG_SHORT(2),
        TAG_INT(3),
        TAG_LONG(4),
        TAG_FLOAT(5),
        TAG_DOUBLE(6),
        TAG_BYTE_ARRAY(7),
        TAG_STRING(8),
        TAG_LIST(9),
        TAG_COMPOUND(10),
        TAG_INT_ARRAY(11),
        TAG_LONG_ARRAY(12);

        private final int id;

        NBTType(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }

        public static NBTType fromID(int id) {
            for (NBTType type : NBTType.values()) {
                if (type.getID() == id) {
                    return type;
                }
            }
            return null;
        }
    }

}
