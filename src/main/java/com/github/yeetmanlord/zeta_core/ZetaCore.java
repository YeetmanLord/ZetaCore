package com.github.yeetmanlord.zeta_core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.packets.chat.NMSChatPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import com.github.yeetmanlord.zeta_core.api.uitl.PlayerUtil;
import com.github.yeetmanlord.zeta_core.data.DataBase;
import com.github.yeetmanlord.zeta_core.data.DataStorer;
import com.github.yeetmanlord.zeta_core.logging.Logger;
import com.github.yeetmanlord.zeta_core.sql.ISQLTableHandler;

/**
 * The core class of all Zeta series plugins. This registers all
 * {@link DataStorer DataStorers}, handles all SQL interactions, as well as
 * registers and connects all Zeta series plugins. This also contains helper
 * methods for Zeta plugins as well as APIs. Most of ZetaCore is strictly
 * internal use except for the api package and the sql package for when creating
 * custom objects that you want to add to a Zeta plugin's database
 * 
 * @apiNote <b>INTERNAL</b> means that this method/class/field etc. <b>SHOULD
 *          NOT BE USED</b> by other plugins while <b>Usable by other
 *          plugins</b> means that the method/class/field is encouraged to be
 *          used. If there is no such plugin usage specified, the
 *          method/class/field is not necessarily part of API usage but can
 *          still be used if necessary. TL;DR Usable by other plugins is
 *          intended to be used by APIs and are built to reflect that. All such
 *          methods, classes, and fields will come with javadocs to help explain
 *          what they are used for and in some cases providing examples
 * 
 * @author YeetManLord
 * @zeta.usage Zeta Core Class. Usable by other plugins
 *
 */
public class ZetaCore extends ZetaPlugin {

	public static Logger LOGGER;

	public static ZetaCore INSTANCE;

	private static final HashMap<String, ZetaPlugin> registeredPlugins = new HashMap<>();

	public DataBase dataBase;

	private static final HashMap<Player, PlayerUtil> playerUtils = new HashMap<>();

	private static final HashMap<ZetaPlugin, List<DataStorer>> dataHandlers = new HashMap<>();

	private static final HashMap<ZetaPlugin, List<ISQLTableHandler<?>>> databaseDataHandlers = new HashMap<>();

	@Override
	public void onEnable() {

		INSTANCE = this;
		LOGGER = new Logger(this, ChatColor.GREEN);
		this.registerDataStorers();

	}

	@Override
	public Logger getPluginLogger() {

		return LOGGER;

	}

	@Override
	public void onDisable() {

		dataBase.write();

		if (dataBase.initialized) {
			dataBase.client.disconnect();
		}

	}

	@Override
	public String getPluginName() {

		return "ZetaCommons";

	}

	public static void sendTitlePackets(Player bPlayer, String title, @Nullable String subtitle, @Nullable String actionBar) {

		NMSPlayerReflection player = new NMSPlayerReflection(bPlayer);
		NMSPlayerConnectionReflection connection = player.getPlayerConnection();
		NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection("TITLE", NMSChatSerializerReflection.createChatComponentFromRawText(title));
		NMSTitlePacketReflection subtitlePacket = null;
		NMSChatPacketReflection actionBarPacket = null;

		if (actionBar != null) {
			actionBarPacket = new NMSChatPacketReflection(NMSChatSerializerReflection.createChatComponentFromRawText(actionBar), (byte) 2);
		}

		if (subtitle != null) {
			subtitlePacket = new NMSTitlePacketReflection("SUBTITLE", NMSChatSerializerReflection.createChatComponentFromRawText(subtitle));
		}

		NMSTitlePacketReflection timesPacket = new NMSTitlePacketReflection(5, 400, 40);

		if (actionBarPacket != null) {
			connection.sendPacket(actionBarPacket);
		}

		connection.sendPacket(timesPacket);
		connection.sendPacket(titlePacket);

		if (subtitlePacket != null) {
			connection.sendPacket(subtitlePacket);
		}

	}

	public static void sendTitlePackets(Player bPlayer, String title, String subtitle, @Nullable String actionBar, int fadeIn, int stay, int fadeOut) {

		NMSPlayerReflection player = new NMSPlayerReflection(bPlayer);
		NMSPlayerConnectionReflection connection = player.getPlayerConnection();
		NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection("TITLE", NMSChatSerializerReflection.createChatComponentFromRawText(title));
		NMSTitlePacketReflection subtitlePacket = new NMSTitlePacketReflection("SUBTITLE", NMSChatSerializerReflection.createChatComponentFromRawText(subtitle));
		NMSChatPacketReflection actionBarPacket = null;

		if (actionBar != null) {
			actionBarPacket = new NMSChatPacketReflection(NMSChatSerializerReflection.createChatComponentFromRawText(actionBar), (byte) 2);
		}

		NMSTitlePacketReflection timesPacket = new NMSTitlePacketReflection(fadeIn, stay, fadeOut);

		if (actionBarPacket != null) {
			connection.sendPacket(actionBarPacket);
		}

		connection.sendPacket(timesPacket);
		connection.sendPacket(titlePacket);
		connection.sendPacket(subtitlePacket);

	}

	public static String titleCase(String initial) {

		StringBuilder builder = new StringBuilder();
		initial = initial.replaceAll("_", " ");

		for (String s : initial.split(" ")) {

			for (int x = 0; x < s.length(); x++) {
				char c = s.charAt(x);

				if (x == 0) {
					builder.append(String.valueOf(c).toUpperCase());
				}
				else {
					builder.append(c);
				}

			}

		}

		return builder.toString();

	}

	/**
	 * @param p Player
	 * @return Returns a {@link PlayerUtil} of a certain player. If one doesn't
	 *         exist it creates a new one and returns that.
	 * 
	 * @zeta.usage SPECIAL. This is INTERNAL <i>but</i> this method still can be
	 *             used if you are interacting with this plugins method. I still do
	 *             not suggest messing with those menus though.
	 */
	public static PlayerUtil getPlayerMenuUtitlity(Player p) {

		PlayerUtil util;

		if (playerUtils.containsKey(p)) {
			return playerUtils.get(p);
		}

		util = new PlayerUtil(p);
		playerUtils.put(p, util);
		return util;

	}

	public static String[] fromListString(List<String> list) {

		String[] array = new String[list.size()];

		for (int x = 0; x < list.size(); x++) {
			array[x] = list.get(x);
		}

		return array;

	}

	public static <T> T[] fromGenericList(T[] arrayA, List<T> list) {

		T[] array = Arrays.copyOf(arrayA, list.size());

		for (int x = 0; x < list.size(); x++) {
			array[x] = list.get(x);
		}

		return array;

	}

	public static int[] fromListInt(List<Integer> list) {

		int[] array = new int[list.size()];

		for (int x = 0; x < list.size(); x++) {
			array[x] = list.get(x);
		}

		return array;

	}

	public static List<String> getLore(ItemMeta meta) {

		List<String> empty = new ArrayList<>();

		if (meta == null) {
			return empty;
		}

		if (meta.getLore() != null) {
			return meta.getLore();
		}

		return empty;

	}

	public static void registerPlugin(ZetaPlugin plugin) {

		registeredPlugins.put(plugin.getName(), plugin);

	}

	public static boolean pluginEnabled(String plugin) {

		return getPlugin(plugin) != null;

	}

	public static ZetaPlugin getPlugin(String pluginName) {

		return registeredPlugins.get(pluginName);

	}

	public static Logger getLogger(String pluginName) {

		return getPlugin(pluginName).getPluginLogger();

	}

	public static void registerDataHandler(DataStorer storer) {

		ZetaPlugin plugin = storer.getPlugin();

		if (storer instanceof ISQLTableHandler && ZetaCore.INSTANCE.dataBase.initialized && ZetaCore.INSTANCE.dataBase.client != null) {

			if (!databaseDataHandlers.containsKey(plugin)) {
				databaseDataHandlers.put(plugin, new ArrayList<>());
			}

			List<ISQLTableHandler<?>> dbHandlers = databaseDataHandlers.get(plugin);
			dbHandlers.add((ISQLTableHandler<?>) storer);
		}
		else {

			if (!dataHandlers.containsKey(plugin)) {
				dataHandlers.put(plugin, new ArrayList<>());
			}

			List<DataStorer> handlers = dataHandlers.get(plugin);

			handlers.add(storer);
		}

	}

	public static List<DataStorer> getDataHandlers(ZetaPlugin plugin) {

		return dataHandlers.get(plugin);

	}

	public static List<ISQLTableHandler<?>> getDatabaseDataHandlers(ZetaPlugin plugin) {

		return databaseDataHandlers.get(plugin);

	}

	public static HashMap<ZetaPlugin, List<ISQLTableHandler<?>>> getDatabaseDataHandlers() {

		return databaseDataHandlers;

	}

	public static HashMap<ZetaPlugin, List<DataStorer>> getDataHandlers() {

		return dataHandlers;

	}

	@Override
	protected void registerDataStorers() {

		dataBase = new DataBase(this);
		dataBase.setup();

	}

	public void readAll() {

		if (dataBase.initialized && dataBase.client != null) {

			databaseDataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.readDB();
				});

			});

			dataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.read();
				});

			});

		}
		else {

			dataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.read();
				});

			});

		}

	}

	public void saveAll() {

		if (dataBase.initialized && dataBase.client != null) {

			databaseDataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.writeDB();
				});

			});

			dataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.write();
				});

			});

		}
		else {

			dataHandlers.values().forEach(list -> {

				list.forEach(handler -> {
					handler.write();
				});

			});

		}

	}

}
