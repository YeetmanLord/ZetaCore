package com.github.yeetmanlord.reflection_api.nbt;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;

public class NMSNBTTagCompoundReflection extends NMSObjectReflection {

    public NMSNBTTagCompoundReflection(Object nmsObject) {

        super(nmsObject);

    }

    public NMSNBTTagCompoundReflection() {

        super(init());

    }

    private static Object init() {

        try {
            return ReflectionApi.getNMSClass("NBTTagCompound").newInstance();
        } catch (SecurityException | InstantiationException | IllegalAccessException |
                 IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void setByte(String key, byte value) {

        try {
            this.invokeMethodForNmsObject("setByte", new Class<?>[]{String.class, byte.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setCompound(String key, NMSNBTTagCompoundReflection tag) {
        try {
            this.invokeMethodForNmsObject("set", new Class<?>[]{String.class, ReflectionApi.getNMSClass("NBTBase")}, new Object[]{key, tag.getNmsObject()});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setShort(String key, short value) {

        try {
            this.invokeMethodForNmsObject("setShort", new Class<?>[]{String.class, short.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setInt(String key, int value) {

        try {
            this.invokeMethodForNmsObject("setInt", new Class<?>[]{String.class, int.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setLong(String key, long value) {

        try {
            this.invokeMethodForNmsObject("setLong", new Class<?>[]{String.class, long.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setFloat(String key, float value) {

        try {
            this.invokeMethodForNmsObject("setFloat", new Class<?>[]{String.class, float.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setDouble(String key, double value) {

        try {
            this.invokeMethodForNmsObject("setDouble", new Class<?>[]{String.class, double.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setString(String key, String value) {

        try {
            this.invokeMethodForNmsObject("setString", new Class<?>[]{String.class, String.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setByteArray(String key, byte[] value) {

        try {
            this.invokeMethodForNmsObject("setByteArray", new Class<?>[]{String.class, byte[].class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setIntArray(String key, int[] value) {

        try {
            this.invokeMethodForNmsObject("setIntArray", new Class<?>[]{String.class, int[].class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setBoolean(String key, boolean value) {

        try {
            this.invokeMethodForNmsObject("setBoolean", new Class<?>[]{String.class, boolean.class}, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public byte getByte(String key) {
        try {
            return (byte) this.invokeMethodForNmsObject("getByte", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public short getShort(String key) {
        try {
            return (short) this.invokeMethodForNmsObject("getShort", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getInt(String key) {
        try {
            return (int) this.invokeMethodForNmsObject("getInt", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long getLong(String key) {
        try {
            return (long) this.invokeMethodForNmsObject("getLong", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getFloat(String key) {
        try {
            return (float) this.invokeMethodForNmsObject("getFloat", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getDouble(String key) {
        try {
            return (double) this.invokeMethodForNmsObject("getDouble", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getString(String key) {
        try {
            return (String) this.invokeMethodForNmsObject("getString", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getByteArray(String key) {
        try {
            return (byte[]) this.invokeMethodForNmsObject("getByteArray", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[] getIntArray(String key) {
        try {
            return (int[]) this.invokeMethodForNmsObject("getIntArray", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getBoolean(String key) {
        try {
            return (boolean) this.invokeMethodForNmsObject("getBoolean", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasKey(String key) {
        try {
            return (boolean) this.invokeMethodForNmsObject("hasKey", new Class<?>[]{String.class}, new Object[]{key});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NMSNBTTagCompoundReflection getCompound(String key) {
        try {
            return new NMSNBTTagCompoundReflection(this.invokeMethodForNmsObject("getCompound", new Class<?>[]{String.class}, new Object[]{key}));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass("NBTTagCompound");

    public static NMSNBTTagCompoundReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNmsObject())) {
            return new NMSNBTTagCompoundReflection(refl.getNmsObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNBTTagCompoundReflection");

    }

}
