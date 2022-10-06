package com.github.yeetmanlord.reflection_api;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yeetmanlord.reflection_api.exceptions.IllegalVersionException;
import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.Mappings;
import com.github.yeetmanlord.reflection_api.mappings.VersionRange;
import com.github.yeetmanlord.reflection_api.server.NMSServerReflection;

public class ReflectionApi {

	public static final VersionRange SUPPORTED_VERSIONS = new VersionRange("1.8", "1.19");

	private static Version getVersion() {

		try {
			NMSServerReflection object = new NMSServerReflection(Bukkit.getServer());
			return new Version((String) object.invokeMethodForNmsObject("getVersion"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Version version;

	public static void init(JavaPlugin plugin) {

		version = getVersion();

		try {

			if (version == null) {
				throw (new IllegalVersionException("Cannot find the version for this server. Likely means this plugin is broken!!"));
			}

		}
		catch (IllegalVersionException versionException) {
			versionException.printStackTrace();
			Bukkit.getConsoleSender().sendMessage((ChatColor.DARK_RED + "FATAL ERROR: Cannot find the version for this server. Likely means this plugin is broken!!"));
			Bukkit.getPluginManager().disablePlugin(plugin);
		}

		try {
			Mappings.loadMappings();
		}
		catch (MappingsException exc) {

			exc.printStackTrace();
			Bukkit.getConsoleSender().sendMessage((ChatColor.DARK_RED + "ERROR: " + ChatColor.RED + "Could not properly load mappings. Because of this I do not recommend using this plugin on your current version because it could easily lead to errors."));

		}
		if (!SUPPORTED_VERSIONS.isWithinRange(version)) {
			Bukkit.getConsoleSender().sendMessage((ChatColor.YELLOW + "[WARNING] The server version you are using is not supported! This means that this plugin may not work properly."));
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[ReflectionAPI] Running on server version &2" + version));

	}

	public static Class<?> getNMSClass(String className) {

		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String name = "net.minecraft.server." + version + className;
		Class<?> nmsClass;

		try {
			nmsClass = Class.forName(name);
			return nmsClass;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Class<?> getCraftBukkitClass(String className, String subpackage) {

		String pack = Bukkit.getServer().getClass().getPackage().getName();

		if (!subpackage.isEmpty() && subpackage.charAt(subpackage.length() - 1) != '.') {
			subpackage += '.';
		}

		String name = pack + "." + subpackage + className;
		Class<?> bukkitClass;

		try {
			bukkitClass = Class.forName(name);
			return bukkitClass;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Class<?> getNMSInnerClass(String innerClassName, String parenetClassName) {

		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String name = "net.minecraft.server." + version + parenetClassName + "$" + innerClassName;
		Class<?> nmsClass;

		try {
			nmsClass = Class.forName(name);
			return nmsClass;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Gets the class of an array of a given NMS class
	 * 
	 * @param className The NMS class name
	 * @return The array class version of an NMS class
	 */
	public static Class<?> getNMSClassArray(String className) {

		Class<?> type = getNMSClass(className);
		return Array.newInstance(type, 1).getClass();

	}

	public static Class<?> getNMSInnerClassArray(String innerClassName, String parenetClassName) {

		Class<?> type = getNMSInnerClass(innerClassName, parenetClassName);
		return Array.newInstance(type, 1).getClass();

	}

	public static Object getHandle(Object bukkitObject) {

		try {
			Method getHandle = bukkitObject.getClass().getMethod("getHandle");
			return getHandle.invoke(bukkitObject);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Casts a given array to the an nms class by the given name
	 * 
	 * @param nmsClassName The name of the NMS class to cast to
	 * @param arrayToCast  The array you are casting
	 * @return Returns an object array casted to the given nmsClassName
	 * @throws IllegalArgumentException when any value in the arrayToCast are not
	 *                                  castable to the given class
	 */
	public static Object[] castArrayToNMS(String nmsClassName, Object[] arrayToCast) throws IllegalArgumentException {

		Class<?> type = getNMSClass(nmsClassName);
		Class<?> arrayType = getNMSClassArray(nmsClassName);
		Object[] castedArray = Arrays.copyOf((Object[]) arrayType.cast(Array.newInstance(type, arrayToCast.length)), arrayToCast.length);

		for (int x = 0; x < arrayToCast.length; x++) {

			if (type.isInstance(arrayToCast[x])) {
				castedArray[x] = type.cast(arrayToCast[x]);
			}
			else {
				System.err.println(arrayToCast[x]);
				System.err.println(nmsClassName);
				throw (new IllegalArgumentException("The provided array contains objects that are not instances of the given class"));
			}

		}

		return castedArray;

	}

}
