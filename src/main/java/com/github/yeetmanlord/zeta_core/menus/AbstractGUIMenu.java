package com.github.yeetmanlord.zeta_core.menus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.yeetmanlord.reflection_api.util.VersionMaterial;
import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.api.util.PluginUtilities;
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

import com.github.yeetmanlord.zeta_core.CommonEventFactory;
import com.github.yeetmanlord.zeta_core.api.api_event_hooks.menu.MenuSetItemsEvent;
import com.github.yeetmanlord.zeta_core.api.util.input.InputType;
import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

// TODO: Add ability to animate menu items and title
public abstract class AbstractGUIMenu implements InventoryHolder {

    protected Inventory inv;

    protected ItemStack FILLER;

    protected int slots;

    protected Player owner;

    protected PlayerUtil menuUtil;

    protected String title;

    protected boolean shouldFill;

    private InputType type;

    private AbstractGUIMenu parent;

    public AbstractGUIMenu(PlayerUtil helper, String title, int slots, boolean shouldFill, AbstractGUIMenu parent) {

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
        this.parent = parent;
    }

    public AbstractGUIMenu(PlayerUtil helper, String title, int slots, boolean shouldFill) {
        this(helper, title, slots, shouldFill, null);
    }

    public AbstractGUIMenu(PlayerUtil helper, String title, int slots, AbstractGUIMenu parent) {
        this(helper, title, slots, false, parent);
    }


    public AbstractGUIMenu(PlayerUtil helper, String title, int slots) {
        this(helper, title, slots, false, null);
    }

    public ItemStack[] getItems() {

        return this.inv.getContents();

    }

    public abstract void setItems();

    public void open() {

        menuUtil.setMenuToInputTo(null);
        menuUtil.setGUIMenu(false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(ZetaCore.getInstance(), () -> {
            owner.closeInventory();
            this.inv = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', this.getMenuName()));
            this.setItems();

            if (this.shouldFill) {
                this.makeFiller();
            }


            if (ZetaCore.getInstance().getLocalSettings().isDevFeatures()) {
                for (int i = 0; i < this.slots; ++i) {
                    ItemStack stack = this.inv.getItem(i);
                    if (stack != null && stack.getType() != Material.AIR) {
                        ItemMeta meta = stack.getItemMeta();
                        if (meta.hasDisplayName()) {
                            String name = meta.getDisplayName();
                            name += " §7(Slot" + i + ")";
                            meta.setDisplayName(name);
                        } else {
                            List<String> lore = PluginUtilities.getLore(meta);
                            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Slot: " + i));
                            meta.setLore(lore);
                        }
                    }
                }
            }

            CommonEventFactory.onMenuSetItems(this);

            this.owner.openInventory(inv);
            this.menuUtil.setGUIMenu(true);
            this.menuUtil.setMenuToInputTo(this);
        });


    }

    /**
     * Called whenever a player clicks on an item in the menu.
     * @see #handleClickAnywhere(InventoryClickEvent) for handling clicks anywhere in the inventory, including empty slots.
     * @param e The InventoryClickEvent
     */
    public abstract void handleClick(InventoryClickEvent e);

    /**
     * Called whenever a player clicks anywhere in the menu, including empty slots and filled slots.
     * @param e The InventoryClickEvent
     */
    public void handleClickAnywhere(InventoryClickEvent e) {

    }

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

    public ItemStack makeItem(Material material, String name) {

        return this.makeItem(material, name, new String[]{});

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

        PluginUtilities.sendTitlePackets(owner, title, subtitle, actionBar);

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

        return this.getClass().getName() + ": {\"Menu Title\": " + this.getMenuName() + ", \"Slot Number\": " + this.getSlots() + ", " + this.getPlayerUtil().toString() + "}";

    }

    protected void createCloser() {
        if (parent == null) {
            this.inv.setItem(slots - 5, this.makeItem(Material.BARRIER, "&cClose"));
        } else {
            this.inv.setItem(slots - 5, this.makeItem(Material.BARRIER, "&cBack"));
        }
    }

    protected void close() {
        if (parent == null) {
            this.syncClose();
        } else {
            parent.open();
        }
    }

    /**
     * Called whenever the menu is closed by the player either by clicking the close button or by closing the inventory
     * with escape.
     *
     * @return Returns true if the menu should skip closing logic and return to the parent menu. Returns false if the menu should proceed
     * with closing logic. <br>
     * @apiNote This method is called when a menu is closed by a plugin using player.closeInventory().
     * This could lead to Stack Overflows if not handled properly.
     * @Example public boolean onClose() { <ul>
     * if (this.getParent() != null) { <ul>
     * this.getParent().open(); <br>
     * return true;
     * </ul>
     * } <br>
     * return false;
     * </ul>
     * }
     */
    public boolean onClose() {
        return false;
    }

    public AbstractGUIMenu getParent() {
        return parent;
    }

    /**
     * This is a <b>VERY</b> important method. I discovered (through a lot of pain, trial, and error.) that when closing an inventory using
     * {@link Player#closeInventory() owner.closeInventory()} inside the {@link #handleClick(InventoryClickEvent) handleClick method}, the inventory is closed
     * <em>asynchronously</em> causing an error to be thrown. If you ever should close an inventory inside the {@link #handleClick(InventoryClickEvent) handleClick method},
     * you <b>must use this method</b> to avoid all possible errors.
     *
     * @apiNote Note 1: I'd just like to make a note that these asynchronous closing errors only happen when right-clicking in a slot greater than 45. WHY? I have no clue, NMS is weird sometimes.
     * My best guess would be that it tries to do stuff in the player inventory, since closing an inventory opens the player inventory. This is some deep NMS stuff but the player inventory has 45 slots.
     * I'm just assuming that's what is happening although I could be wrong. This is more of an explanation of the above error, but you should still use this method to close inventories despite being in a slot below 45. <br>
     * Note 2: The {@link #open() open method} already accounts for this error, so you shouldn't have to call this method before using the open method. Basically, you should try to replace the owner.closeInventory() method with this method.
     */
    public void syncClose() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ZetaCore.getInstance(), () -> {
            this.owner.closeInventory();
        });
    }

    public void setInput(InputType type, String title) {
        menuUtil.setGUIMenu(true);
        menuUtil.setMenuToInputTo(this);
        menuUtil.setTakingChatInput(true);
        this.setInputType(type);
        this.sendTitlePackets(title);
        this.syncClose();
    }

    public void setInput(InputType type, String title, String subTitle, String actionBar) {
        menuUtil.setGUIMenu(true);
        menuUtil.setMenuToInputTo(this);
        menuUtil.setTakingChatInput(true);
        this.setInputType(type);
        this.sendTitlePackets(title, subTitle, actionBar);
        this.syncClose();
    }
}
