package com.github.yeetmanlord.reflection_api.chat_components;

import java.lang.reflect.Method;

import org.bukkit.ChatColor;

import com.github.yeetmanlord.reflection_api.mappings.Mappings;

public class NMSChatSerializerReflection 
{
	public static Object createChatComponentFromString(String string)
	{
		try
		{
			Class<?> clazz = Mappings.CHAT_SERIALIZER_CLASS_MAPPING.getNMSClassMapping();
			Method create = clazz.getMethod("a", String.class);
			return create.invoke(null, string);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This is easier to use as you just have to input text with color codes and it will automatically be translated into a usable form
	 * @return Returns an IChatBaseComponent
	 */
	public static Object createChatComponentFromRawText(String rawText)
	{
		return createChatComponentFromString("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', rawText) + "\"}");
	}
}
