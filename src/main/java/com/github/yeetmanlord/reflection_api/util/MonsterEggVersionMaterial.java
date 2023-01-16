package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.Version;
import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.sql.Ref;
import java.util.Objects;

public class MonsterEggVersionMaterial extends VersionMaterial {
    public MonsterEggVersionMaterial() {
        super("MONSTER_EGG", "GHAST_SPAWN_EGG", (byte) 56);
    }

    public ItemStack getItem(EntityType type) {
        ItemStack stack = this.getItem();
        EnumNMSEntityToBukkit nmsEntityToBukkit = EnumNMSEntityToBukkit.getEnumNMSEntityToBukkit(type);
        if (ReflectionApi.version.isNewer(ReflectionApi.v1_13)) {
            stack.setType(VersionMaterial.getFromString(nmsEntityToBukkit.getSpawnEggMaterial()));
        }
        NMSItemStackReflection nmsStack = new NMSItemStackReflection(stack);
        NMSNBTTagCompoundReflection nbt = nmsStack.getTag();
        NMSNBTTagCompoundReflection entityTag = new NMSNBTTagCompoundReflection();
        if (type == EntityType.IRON_GOLEM) {
            type = EntityType.GHAST;
        }
        entityTag.setString("id", Objects.requireNonNull(EnumNMSEntityToBukkit.getEnumNMSEntityToBukkit(type)).getNmsEntityType());
        nbt.setCompound("EntityTag", entityTag);
        nmsStack.setTag(nbt);
        return nmsStack.asBukkit();
    }

    public Material getMaterial(EntityType type) {
        if (ReflectionApi.version.isNewer(ReflectionApi.v1_13)) {
            EnumNMSEntityToBukkit nmsEntityToBukkit = EnumNMSEntityToBukkit.getEnumNMSEntityToBukkit(type);
            return VersionMaterial.getFromString(nmsEntityToBukkit.getSpawnEggMaterial());
        }
        else return VersionMaterial.getFromString("MONSTER_EGG");
    }
}
