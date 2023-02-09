package com.github.yeetmanlord.zeta_core.sql.types.wrappers;

import com.github.yeetmanlord.zeta_core.api.util.ItemSerializer;
import org.bukkit.inventory.ItemStack;

public class ItemWrapper extends ColumnWrapper<ItemStack> {
    public ItemWrapper() {
        super(ItemStack.class);
    }

    @Override
    public String serialize(ItemStack object) {
        return ItemSerializer.serialize(object);
    }

    @Override
    public ItemStack deserialize(String string) {
        return ItemSerializer.deserialize(string);
    }
}
