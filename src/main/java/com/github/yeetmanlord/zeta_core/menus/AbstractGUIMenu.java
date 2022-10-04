package com.github.yeetmanlord.zeta_core.menus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.yeetmanlord.reflection_api.chat_components.NMSChatSerializerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.NMSPlayerReflection;
import com.github.yeetmanlord.reflection_api.entity.players.player_connection.NMSPlayerConnectionReflection;
import com.github.yeetmanlord.reflection_api.packets.chat.NMSChatPacketReflection;
import com.github.yeetmanlord.reflection_api.packets.player.NMSTitlePacketReflection;
import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;
import com.github.yeetmanlord.zeta_core.api.uitl.InputType;
import com.github.yeetmanlord.zeta_core.api.uitl.PlayerUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public abstract class AbstractGUIMenu implements InventoryHolder {

	protected Inventory inv;

	protected ItemStack FILLER;

	protected int slots;

	protected Player owner;

	protected PlayerUtil menuUtil;

	protected String title;

	protected boolean shouldFill;

	private InputType type;

	public AbstractGUIMenu(PlayerUtil helper, String title, int slots, boolean shouldFill) {

		this.setInputType(InputType.NONE);
		this.shouldFill = shouldFill;
		this.title = title;
		menuUtil = helper;
		this.owner = helper.getOwner();
		this.slots = slots;
		FILLER = VersionMaterial.GRAY_STAINED_GLASS_PANE.getItem();
		ItemMeta meta = FILLER.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6"));
		FILLER.setItemMeta(meta);

	}

	public AbstractGUIMenu(PlayerUtil helper, String title, int slots) {
		this(helper, title, slots, false);
	}

	public ItemStack[] getItems() {

		return this.inv.getContents();

	}

	public abstract void setItems();

	public void open() {

		owner.closeInventory();
		this.inv = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', this.getMenuName()));
		this.setItems();

		if (this.shouldFill) {
			this.makeFiller();
		}

		MenuSetItemsEvent event = CommonEventFactory.onMenuSetItems(this);

		this.owner.openInventory(inv);
		this.menuUtil.setGUIMenu(true);
		this.menuUtil.setMenuToInputTo(this);

	}

	public abstract void handleClick(InventoryClickEvent e);

	public String getMenuName() {

		return this.title;

	}

	public ItemStack makeItem(Material material, String name, @Nullable String... loreArray) {

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

	public ItemStack makeSkullWithCustomTexture(String name, @Nullable String[] loreArray, String textureURL) {

		ItemStack stack = VersionMaterial.PLAYER_HEAD.getItem();
		SkullMeta meta = (SkullMeta) stack.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", textureURL));

		try {
			Field headProfile = meta.getClass().getDeclaredField("profile");
			headProfile.setAccessible(true);
			headProfile.set(meta, profile);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
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

	public ItemStack makeItem(Material material, String name) {

		return this.makeItem(material, name, new String[] {});

	}

	public ItemStack makeItemFromExisting(ItemStack base, String name, @Nullable String... loreArray) {

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

	public void makeFiller() {

		for (int i = 0; i < this.getSlots(); i++) {
			ItemStack stack = this.inv.getItem(i);

			if (stack == null) {
				this.inv.setItem(i, this.FILLER);
			}

		}

	}

	public void makeBoarder() {

		for (int x = 0; x < this.inv.getSize() / 9; x++) {

			if (this.inv.getItem(x * 9) == null) {
				this.inv.setItem(x * 9, FILLER);
			}

			if (this.inv.getItem(x * 9 + 8) == null) {
				this.inv.setItem(x * 9 + 8, FILLER);
			}

		}

		for (int x = 0; x < 9; x++) {

			if (this.inv.getItem(x) == null) {
				this.inv.setItem(x, FILLER);
			}

		}

	}

	public int getSlots() {

		return slots;

	}

	public boolean shouldFill() {

		return this.shouldFill;

	}

	public PlayerUtil getPlayerUtil() {

		return this.menuUtil;

	}

	@Override
	public Inventory getInventory() {

		return this.inv;

	}

	public InputType getInputType() {

		return type;

	}

	public void setInputType(InputType type) {

		this.type = type;

	}

	public void sendTitlePackets(String title, String subtitle, @Nullable String actionBar) {

		NMSPlayerReflection player = new NMSPlayerReflection(owner);
		NMSPlayerConnectionReflection connection = player.getPlayerConnection();
		NMSTitlePacketReflection titlePacket = new NMSTitlePacketReflection("TITLE", NMSChatSerializerReflection.createChatComponentFromRawText(title));
		NMSTitlePacketReflection subtitlePacket = new NMSTitlePacketReflection("SUBTITLE", NMSChatSerializerReflection.createChatComponentFromRawText(subtitle));
		NMSChatPacketReflection actionBarPacket = null;

		if (actionBar != null) {
			actionBarPacket = new NMSChatPacketReflection(NMSChatSerializerReflection.createChatComponentFromRawText(actionBar), (byte) 2);
		}

		NMSTitlePacketReflection timesPacket = new NMSTitlePacketReflection(5, 400, 40);

		if (actionBarPacket != null) {
			connection.sendPacket(actionBarPacket);
		}

		connection.sendPacket(timesPacket);
		connection.sendPacket(titlePacket);
		connection.sendPacket(subtitlePacket);

	}

	public void sendTitlePackets(String title) {

		sendTitlePackets(title, "&cLeft click to cancel", null);

	}

	public void refresh() {

		this.inv.clear();
		this.setItems();

		MenuSetItemsEvent event = CommonEventFactory.onMenuSetItems(this);

	}

	@Override
	public String toString() {

		return this.getClass().getName() + ": {\"Menu Title\": " + this.getMenuName() + "\"Slot Number\": " + this.getSlots() + this.getPlayerUtil().toString() + "}";

	}

	protected void createCloser() {

		this.inv.setItem(slots - 5, this.makeItem(Material.BARRIER, "&cClose"));

	}

}
