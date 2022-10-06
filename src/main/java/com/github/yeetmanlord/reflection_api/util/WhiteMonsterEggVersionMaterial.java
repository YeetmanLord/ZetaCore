package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class WhiteMonsterEggVersionMaterial extends VersionMaterial {
    public WhiteMonsterEggVersionMaterial() {
        super("MONSTER_EGG", "GHAST_SPAWN_EGG", (byte) 56);
    }

    public ItemStack getItem(EntityType type) {
        ItemStack stack = this.getItem();
        NMSItemStackReflection nmsStack = new NMSItemStackReflection(stack);
        NMSNBTTagCompoundReflection nbt = nmsStack.getTag();
        NMSNBTTagCompoundReflection entityTag = new NMSNBTTagCompoundReflection();
        entityTag.setString("id", EnumNMSEntityToBukkit.getEnumNMSEntityToBukkit(type).getNmsEntityType());
        nbt.setCompound("EntityTag", entityTag);
        nmsStack.setTag(nbt);
        return nmsStack.asBukkit();
    }
}
