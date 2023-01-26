package com.github.yeetmanlord.zeta_core.api.util;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class ItemUtils {
    public static ItemStack makeItem(Material material, String name, @Nullable String... loreArray) {

        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (loreArray != null) {
            ArrayList<String> lore = new ArrayList<>();

            for (String s : loreArray) {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(lore);

        }

        stack.setItemMeta(meta);
        return stack;

    }

    public static ItemStack makeSkullWithCustomTexture(String name, @Nullable String[] loreArray, String textureURL) {

        ItemStack stack = VersionMaterial.PLAYER_HEAD.getItem();
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (textureURL != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", textureURL));

            try {
                Field headProfile = meta.getClass().getDeclaredField("profile");
                headProfile.setAccessible(true);
                headProfile.set(meta, profile);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (loreArray != null) {
            ArrayList<String> lore = new ArrayList<>();

            for (String s : loreArray) {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(lore);
        }

        stack.setItemMeta(meta);

        return stack;

    }

    public static  ItemStack makeItem(Material material, String name) {

        return makeItem(material, name, new String[]{});

    }

    public static ItemStack makeItemFromExisting(ItemStack base, String name, @Nullable String... loreArray) {

        ItemStack stack = base.clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (loreArray != null) {
            ArrayList<String> lore = new ArrayList<>();

            for (String s : loreArray) {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(lore);
        }

        stack.setItemMeta(meta);

        return stack;

    }
}
