package com.github.yeetmanlord.reflection_api.util;

import com.github.yeetmanlord.reflection_api.ReflectionApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.InvocationTargetException;

public class InventoryUtilities {

    public static ItemStack getMainHandItem(Player player) {
        ItemStack stack = null;
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
            stack = player.getItemInHand();
        } else {
            try {
                stack = (ItemStack) PlayerInventory.class.getMethod("getItemInMainHand").invoke(player.getInventory());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return stack;
    }

    public static ItemStack getOffHandItem(Player player) {
        ItemStack stack = null;
        if (ReflectionApi.version.isOlder(ReflectionApi.v1_9)) {
            return null;
        } else {
            try {
                stack = (ItemStack) PlayerInventory.class.getMethod("getItemInOffHand").invoke(player.getInventory());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return stack;
    }

}
