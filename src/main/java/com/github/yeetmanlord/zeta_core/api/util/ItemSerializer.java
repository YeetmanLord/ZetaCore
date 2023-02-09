package com.github.yeetmanlord.zeta_core.api.util;

import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTBase;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ItemSerializer {

    /**
     * ItemStack serializer. This is used to convert an ItemStack into a JSON string, and then base64 encode it.
     *
     * @param item The ItemStack to serialize.
     * @return The base64 encoded JSON string.
     * @apiNote This stores certain data about the item but not all. This will store the item's display name, lore, enchantments
     * custom model data and the item's type. Regarding custom NBT data, this will only store data that is a non-list native type. This will not store
     * sub-nbt data. For example, if you have a custom nbt tag called "test" that has a sub-nbt tag called "test2" that has a value of "test3",
     * the "test" nbt tag will not be stored. Attribute Modifiers are not stored.
     */
    public static String serialize(ItemStack item) {
        NMSItemStackReflection nmsItem = new NMSItemStackReflection(item);
        NMSNBTTagCompoundReflection nbt = nmsItem.getTag();
        List<String> invalidKeys = new ArrayList<>();
        invalidKeys.add("Enchantments");
        invalidKeys.add("ench");
        invalidKeys.add("AttributeModifiers");
        invalidKeys.add("display");
        invalidKeys.add("CustomModelData");
        JsonObject json = new JsonObject();
        json.addProperty("type", item.getType().name());
        json.addProperty("amount", item.getAmount());
        json.addProperty("durability", item.getDurability());
        JsonObject enchantments = new JsonObject();
        item.getEnchantments().forEach((enchantment, level) -> {
            enchantments.addProperty(enchantment.getName(), level);
        });
        json.add("enchantments", enchantments);
        if (nbt.hasKey("CustomModelData")) {
            json.addProperty("customModelData", nbt.getInt("CustomModelData"));
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            json.addProperty("displayName", meta.getDisplayName().replaceAll("§", "&"));
        }
        if (meta.hasLore()) {
            JsonArray lore = new JsonArray();
            meta.getLore().forEach(line -> {
                lore.add(new JsonPrimitive(line.replaceAll("§", "&")));
            });
            json.add("lore", lore);
        }

        JsonObject nbtData = new JsonObject();
        for (String key : nbt.getKeys()) {
            if (invalidKeys.contains(key)) {
                continue;
            }
            NMSNBTBase base = nbt.get(key);
            JsonObject nbtSubData = new JsonObject();
            switch (base.getType()) {
                case TAG_BYTE:
                    nbtSubData.addProperty("type", "byte");
                    nbtSubData.addProperty("value", nbt.getByte(key));
                    break;
                case TAG_SHORT:
                    nbtSubData.addProperty("type", "short");
                    nbtSubData.addProperty("value", nbt.getShort(key));
                    break;
                case TAG_INT:
                    nbtSubData.addProperty("type", "int");
                    nbtSubData.addProperty("value", nbt.getInt(key));
                    break;
                case TAG_LONG:
                    nbtSubData.addProperty("type", "long");
                    nbtSubData.addProperty("value", nbt.getLong(key));
                    break;
                case TAG_FLOAT:
                    nbtSubData.addProperty("type", "float");
                    nbtSubData.addProperty("value", nbt.getFloat(key));
                    break;
                case TAG_DOUBLE:
                    nbtSubData.addProperty("type", "double");
                    nbtSubData.addProperty("value", nbt.getDouble(key));
                    break;
                case TAG_STRING:
                    nbtSubData.addProperty("type", "string");
                    nbtSubData.addProperty("value", nbt.getString(key));
                    break;
                default:
                    continue;
            }
            nbtData.add(key, nbtSubData);
        }
        json.add("nbtData", nbtData);
        return Base64.getEncoder().encodeToString(json.toString().getBytes());
    }

    public static ItemStack deserialize(String encoded) {
        String json = new String(Base64.getDecoder().decode(encoded)); // Decode the base64 string.
        JsonObject object = new JsonParser().parse(json).getAsJsonObject(); // Parse the JSON string.
        ItemStack item = new ItemStack(VersionMaterial.getFromString(object.get("type").getAsString()), object.get("amount").getAsInt(), object.get("durability").getAsShort());
        ItemMeta meta = item.getItemMeta();
        if (object.has("displayName")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', object.get("displayName").getAsString()));
        }
        if (object.has("lore")) {
            List<String> lore = new ArrayList<>();
            (object.get("lore").getAsJsonArray()).forEach(line -> {
                lore.add(ChatColor.translateAlternateColorCodes('&', line.getAsString()));
            });
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        NMSItemStackReflection nmsItem = new NMSItemStackReflection(item);
        NMSNBTTagCompoundReflection nbt = nmsItem.getTag();
        JsonObject enchantments = object.get("enchantments").getAsJsonObject();
        enchantments.entrySet().forEach((entry) -> {
            String ench = entry.getKey();
            int lvl = entry.getValue().getAsInt();
            Enchantment enchObj = Enchantment.getByName(ench);
            if (enchObj != null) {
                item.addUnsafeEnchantment(enchObj, lvl);
            }
        });

        if (object.has("customModelData")) {
            nbt.setInt("CustomModelData", object.get("customModelData").getAsInt());
        }
        JsonObject nbtData = object.get("nbtData").getAsJsonObject();
        nbtData.entrySet().forEach((entry) -> {
            String nbtKey = entry.getKey();
            JsonObject nbtValue = entry.getValue().getAsJsonObject();
            String type = nbtValue.get("type").getAsString();
            switch (type) {
                case "byte":
                    nbt.setByte(nbtKey, nbtValue.get("value").getAsByte());
                    break;
                case "short":
                    nbt.setShort(nbtKey, nbtValue.get("value").getAsShort());
                    break;
                case "int":
                    nbt.setInt(nbtKey, nbtValue.get("value").getAsInt());
                    break;
                case "long":
                    nbt.setLong(nbtKey, nbtValue.get("value").getAsLong());
                    break;
                case "float":
                    nbt.setFloat(nbtKey, nbtValue.get("value").getAsFloat());
                    break;
                case "double":
                    nbt.setDouble(nbtKey, nbtValue.get("value").getAsDouble());
                    break;
                case "string":
                    nbt.setString(nbtKey, nbtValue.get("value").getAsString());
                    break;
            }
        });
        nmsItem.setTag(nbt);
        return nmsItem.asBukkit();

    }

}
