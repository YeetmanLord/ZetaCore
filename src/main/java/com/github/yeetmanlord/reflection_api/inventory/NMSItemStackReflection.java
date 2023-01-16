package com.github.yeetmanlord.reflection_api.inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NMSItemStackReflection extends NMSObjectReflection {

    public NMSItemStackReflection(Object nmsObject) {

        super(nmsObject);

    }

    public NMSItemStackReflection(ItemStack stack) {

        super(init(stack));

    }

    public NMSItemStackReflection(Material mat, int amount) {

        this(new ItemStack(mat, amount));

    }

    public NMSItemStackReflection(Material mat, int amount, short damage) {

        this(new ItemStack(mat, amount, damage));

    }

    public NMSItemStackReflection(Material mat) {

        this(new ItemStack(mat));

    }

    /**
     * @param mat    Material of the itemstack
     * @param amount Amount in the stack
     * @param damage Damage or metadata value
     * @param data   Data value
     * @deprecated Uses deprecated ItemStack constructor
     */
    @Deprecated
    public NMSItemStackReflection(Material mat, int amount, short damage, Byte data) {

        this(new ItemStack(mat, amount, damage, data));

    }

    private static Object init(ItemStack stack) {

        try {
            if (stack == null) {
                throw new IllegalArgumentException("ItemStack cannot be null");
            }
            if (stack.getType() != Material.AIR) {
                Method asNMSCopy = ReflectionApi.getCraftBukkitClass("CraftItemStack", "inventory").getMethod("asNMSCopy", ItemStack.class);
                return asNMSCopy.invoke(null, stack);
            } else {
                Constructor<?> constr;
                if (ReflectionApi.version.isOlder(ReflectionApi.v1_13)) {
                    constr = staticClass.getConstructor(ReflectionApi.getNMSClass(Mappings.BLOCK_PACKAGE_MAPPING, "Block"));
                } else {
                    constr = staticClass.getConstructor(ReflectionApi.getNMSClass(Mappings.WORLD_LEVEL_PACKAGE_MAPPING, "IMaterial"));
                }
                String airField = "AIR";
                if (ReflectionApi.version.isNewer(ReflectionApi.v1_17)) {
                    airField = "a";
                }
                Object airBlock = ReflectionApi.getNMSClass(Mappings.BLOCK_PACKAGE_MAPPING, "Blocks").getField(airField).get(null);
                return constr.newInstance(airBlock);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchFieldException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static final Class<?> staticClass = ReflectionApi.getNMSClass(Mappings.ITEM_PACKAGE_MAPPING, "ItemStack");

    public static NMSItemStackReflection cast(NMSObjectReflection refl) {

        if (staticClass.isInstance(refl.getNMSObject())) {
            return new NMSItemStackReflection(refl.getNMSObject());
        }

        throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSItemStackReflection");

    }

    public NMSNBTTagCompoundReflection getTag() {
        try {
            if (this.nmsObject == null) {
                throw new IllegalStateException("NMS ItemStack is null and cannot be used");
            }
            Object o = Mappings.ITEM_STACK_GET_NBT_TAG_MAPPING.runMethod(this);
            if (o == null) {
                return new NMSNBTTagCompoundReflection();
            }
            return new NMSNBTTagCompoundReflection(o);
        } catch (MappingsException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public ItemStack asBukkit() {
        Class<?> craftItemStack = ReflectionApi.getCraftBukkitClass("CraftItemStack", "inventory");
        try {
            Method asCraftMirror = craftItemStack.getMethod("asCraftMirror", staticClass);
            return (ItemStack) asCraftMirror.invoke(null, this.getNMSObject());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTag(NMSNBTTagCompoundReflection tag) {
        try {
            Mappings.ITEM_STACK_SET_NBT_TAG_MAPPING.runMethod(this, tag.getNMSObject());
        } catch (MappingsException exc) {
            exc.printStackTrace();
        }
    }

    public static Object asNMS(ItemStack stack) {
        return new NMSItemStackReflection(stack).nmsObject;
    }

}
