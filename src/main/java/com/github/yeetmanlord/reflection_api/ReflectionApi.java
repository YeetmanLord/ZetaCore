package com.github.yeetmanlord.reflection_api;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.yeetmanlord.reflection_api.block.NMSBlockPositionReflection;
import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerInteractManagerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.inventory.NMSItemStackReflection;
import com.github.yeetmanlord.reflection_api.mappings.IMapping;
import com.github.yeetmanlord.reflection_api.nbt.NMSNBTTagCompoundReflection;
import com.github.yeetmanlord.reflection_api.packets.chat.NMSChatPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.entity.NMSEntityDestroyPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.entity.NMSEntityEquipmentPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.entity.NMSNamedEntitySpawnPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.entity.NMSPacketPlayOutRelEntityMove;
import com.github.yeetmanlord.reflection_api.packets.network.NMSNetworkManagerReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSPlayerInfoPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSScoreboardTeamPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import com.github.yeetmanlord.reflection_api.scoreboard.NMSScoreboardTeamReflection;
import com.github.yeetmanlord.reflection_api.util.EnumEquipmentSlot;
import com.github.yeetmanlord.reflection_api.util.ParticleUtility;
import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.github.yeetmanlord.reflection_api.world.NMSWorldServerReflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

	public static boolean runReflectionTests(Player executer) {
		try {
			// Chat packets
			NMSChatPacketReflection packet = new NMSChatPacketReflection(ChatColor.RED + "Hello World!", NMSChatPacketReflection.EnumChatPosition.CHAT);

			// Entity packets
			NMSEntityDestroyPacketReflection entityPacket = new NMSEntityDestroyPacketReflection(1, 2, 3);
			NMSEntityEquipmentPacketReflection equipmentPacketReflection = new NMSEntityEquipmentPacketReflection(1, EnumEquipmentSlot.HEAD, new ItemStack(Material.AIR));
			NMSNamedEntitySpawnPacketReflection spawnPacket = new NMSNamedEntitySpawnPacketReflection(new NMSPlayerReflection(executer));
			NMSPacketPlayOutRelEntityMove movePacket = new NMSPacketPlayOutRelEntityMove(1, 0L, 0L, 0L, (byte) 127, (byte) 0, false);

			// Player connection
			NMSPlayerConnectionReflection connection = new NMSPlayerConnectionReflection(new NMSPlayerReflection(executer));

			// Network manager
			NMSNetworkManagerReflection networkManager = new NMSNetworkManagerReflection(NMSNetworkManagerReflection.EnumNetworkDirection.SERVERBOUND);

			// Send packets
			connection.sendPacket(packet);

			// Player tests
			NMSPlayerReflection player = new NMSPlayerReflection(executer);
			player.setLocation(executer.getLocation().add(0, 1, 0));

			// Player packets
			NMSPlayerInfoPacketReflection infoPacket = new NMSPlayerInfoPacketReflection(NMSPlayerInfoPacketReflection.EnumPlayerInfoPacketAction.ADD_PLAYER, player);

			/// Teams
			NMSScoreboardTeamReflection team = new NMSScoreboardTeamReflection(Bukkit.getScoreboardManager().getMainScoreboard(), "test");
			NMSScoreboardTeamPacketReflection removePacket = new NMSScoreboardTeamPacketReflection(team, NMSScoreboardTeamPacketReflection.TeamPacketAction.REMOVE);
			NMSScoreboardTeamPacketReflection addPacket = new NMSScoreboardTeamPacketReflection(team, new ArrayList(Arrays.asList("123", "456")), NMSScoreboardTeamPacketReflection.TeamPacketAction.ADD_PLAYERS);

			/// Title
			NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection(NMSTitlePacketReflection.NMSEnumTitleAction.TITLE, NMSChatSerializerReflection.createChatComponentFromText(ChatColor.RED + "Hello World!"));
			NMSTitlePacketReflection subtitlePacket = new NMSTitlePacketReflection(NMSTitlePacketReflection.NMSEnumTitleAction.SUBTITLE, NMSChatSerializerReflection.createChatComponentFromText(ChatColor.RED + "Hello World!"));
			NMSTitlePacketReflection timesPacket = new NMSTitlePacketReflection(10, 20, 10);

			/// Actionbar
			NMSChatPacketReflection actionbarPacket = new NMSChatPacketReflection(ChatColor.RED + "Hello World!", NMSChatPacketReflection.EnumChatPosition.GAME_INFO);

			connection.sendPacket(actionbarPacket);

			/// NBT tests
			NMSItemStackReflection item = new NMSItemStackReflection(new ItemStack(Material.DIAMOND));
			NMSNBTTagCompoundReflection tag = new NMSNBTTagCompoundReflection();
			tag.setString("test", "test");
			item.setTag(tag);
			executer.getInventory().addItem(item.asBukkit());

			// Interact manager
			NMSPlayerInteractManagerReflection interactManager = new NMSPlayerInteractManagerReflection(player);

			// Block position
			NMSBlockPositionReflection blockPosition = new NMSBlockPositionReflection(executer.getLocation());

			// World & server
			NMSWorldServerReflection world = new NMSWorldServerReflection(executer.getWorld());
			NMSServerReflection server = new NMSServerReflection(Bukkit.getServer());

			// Particle
			ParticleUtility.spawnParticle(executer.getLocation(), ParticleUtility.ParticleTypes.CRIT_MAGIC, 1, 1, 1, 1, 1, 30);

			boolean testsPassed = true;
			// Material tests
			for (VersionMaterial material : VersionMaterial.stringMaterialMap.values()) {
				try {
					System.out.println(material.getFlatMaterial() + " " + material.getMaterial());
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + material.getFlatMaterial() + " " + e.getMessage());
				}
			}

			// Mappings tests
			for (IMapping mappings : Mappings.mappings) {
				boolean passed = mappings.testMapping();
				if (passed) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Mapping `" + mappings.getName() + "` tests passed");
				} else {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Mapping `" + mappings.getName() + "` tests failed");
					testsPassed = false;
				}
			}

			return testsPassed;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
