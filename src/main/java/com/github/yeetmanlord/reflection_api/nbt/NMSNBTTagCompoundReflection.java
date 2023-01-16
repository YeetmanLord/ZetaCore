package com.github.yeetmanlord.reflection_api.nbt;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;

import java.sql.Ref;
import java.util.Set;

public class NMSNBTTagCompoundReflection extends NMSObjectReflection {

    public NMSNBTTagCompoundReflection(Object nmsObject) {

        super(nmsObject);

    }

    public NMSNBTTagCompoundReflection() {

        super(init());

    }

    private static Object init() {

        try {
            return ReflectionApi.getNMSClass(Mappings.NBT_PACKAGE_MAPPING, "NBTTagCompound").newInstance();
        } catch (SecurityException | InstantiationException | IllegalAccessException |
                 IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void setByte(String key, byte value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setByte", new Class<?>[]{String.class, byte.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, byte.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setCompound(String key, NMSNBTTagCompoundReflection tag) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("set", new Class<?>[]{String.class, ReflectionApi.getNMSClass(Mappings.NBT_PACKAGE_MAPPING, "NBTBase")}, new Object[]{key, tag.getNMSObject()});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, ReflectionApi.getNMSClass(Mappings.NBT_PACKAGE_MAPPING, "NBTBase")}, new Object[]{key, tag.getNMSObject()});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setShort(String key, short value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setShort", new Class<?>[]{String.class, short.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, short.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setInt(String key, int value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setInt", new Class<?>[]{String.class, int.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, int.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setLong(String key, long value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setLong", new Class<?>[]{String.class, long.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, long.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setFloat(String key, float value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setFloat", new Class<?>[]{String.class, float.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, float.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setDouble(String key, double value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setDouble", new Class<?>[]{String.class, double.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, double.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setString(String key, String value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setString", new Class<?>[]{String.class, String.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, String.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setByteArray(String key, byte[] value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setByteArray", new Class<?>[]{String.class, byte[].class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, byte[].class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setIntArray(String key, int[] value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setIntArray", new Class<?>[]{String.class, int[].class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, int[].class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setBoolean(String key, boolean value) {

        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                this.invokeMethodForNmsObject("setBoolean", new Class<?>[]{String.class, boolean.class}, new Object[]{key, value});
            } else {
                this.invokeMethodForNmsObject("a", new Class<?>[]{String.class, boolean.class}, new Object[]{key, value});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public byte getByte(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (byte) this.invokeMethodForNmsObject("getByte", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (byte) this.invokeMethodForNmsObject("f", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public short getShort(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (short) this.invokeMethodForNmsObject("getShort", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (short) this.invokeMethodForNmsObject("g", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getInt(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (int) this.invokeMethodForNmsObject("getInt", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (int) this.invokeMethodForNmsObject("h", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long getLong(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (long) this.invokeMethodForNmsObject("getLong", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (long) this.invokeMethodForNmsObject("i", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getFloat(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (float) this.invokeMethodForNmsObject("getFloat", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (float) this.invokeMethodForNmsObject("j", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getDouble(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (double) this.invokeMethodForNmsObject("getDouble", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (double) this.invokeMethodForNmsObject("k", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getString(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (String) this.invokeMethodForNmsObject("getString", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (String) this.invokeMethodForNmsObject("l", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getByteArray(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (byte[]) this.invokeMethodForNmsObject("getByteArray", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (byte[]) this.invokeMethodForNmsObject("m", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[] getIntArray(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (int[]) this.invokeMethodForNmsObject("getIntArray", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (int[]) this.invokeMethodForNmsObject("n", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getBoolean(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (boolean) this.invokeMethodForNmsObject("getBoolean", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (boolean) this.invokeMethodForNmsObject("q", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasKey(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (boolean) this.invokeMethodForNmsObject("hasKey", new Class<?>[]{String.class}, new Object[]{key});
            } else {
                return (boolean) this.invokeMethodForNmsObject("e", new Class<?>[]{String.class}, new Object[]{key});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NMSNBTTagCompoundReflection getCompound(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return new NMSNBTTagCompoundReflection(this.invokeMethodForNmsObject("getCompound", new Class<?>[]{String.class}, new Object[]{key}));
            } else {
                return new NMSNBTTagCompoundReflection(this.invokeMethodForNmsObject("p", new Class<?>[]{String.class}, new Object[]{key}));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.NBT_PACKAGE_MAPPING, "NBTTagCompound");

    public static NMSNBTTagCompoundReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSNBTTagCompoundReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSNBTTagCompoundReflection");

    }

    public Set<String> getKeys() {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return (Set<String>) this.invokeMethodForNmsObject("getKeys");
            } else {
                return (Set<String>) this.invokeMethodForNmsObject("d");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NMSNBTBase get(String key) {
        try {
            if (ReflectionApi.version.isOlder(ReflectionApi.v1_18)) {
                return new NMSNBTBase(this.invokeMethodForNmsObject("get", new Class<?>[]{String.class}, new Object[]{key}));
            } else {
                return new NMSNBTBase(this.invokeMethodForNmsObject("c", new Class<?>[]{String.class}, new Object[]{key}));
            }
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

}
