package com.github.yeetmanlord.zeta_core.menus.animation;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimatedItem {

    private List<String> frames;

    protected int slot;

    protected ItemStack item;

    private int frame;

    protected IAnimatable animatable;

    public AnimatedItem(int slot, ItemStack item, IAnimatable animatable) {
        this.frames = new ArrayList<>();
        this.slot = slot;
        this.item = item;
        this.frame = 0;
        this.animatable = animatable;
    }

    public AnimatedItem(int slot, ItemStack item, IAnimatable animatable, List<String> frames) {
        this.frames = frames;
        this.slot = slot;
        this.item = item;
        this.frame = 0;
        this.animatable = animatable;
    }

    public AnimatedItem(int slot, ItemStack item, IAnimatable animatable, String... frames) {
        this.frames = new ArrayList<>(Arrays.asList(frames));
        this.slot = slot;
        this.item = item;
        this.frame = 0;
        this.animatable = animatable;
    }

    /**
     * @return Returns all frames in this animation
     */
    public List<String> getFrames() {
        return frames;
    }

    /**
     * Sets the frames for this animation
     * @param frames The frames to set
     */
    public void setFrames(List<String> frames) {
        this.frames = frames;
    }

    /**
     * @return Gets this item's slot in the inventory
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @return Gets the item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @return Gets the current frame
     */
    public int getFrame() {
        return frame;
    }

    /**
     * Sets the current frame
     * @param frame The frame to set
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * @param frame The frame to add
     */
    public void addFrame(String frame) {
        this.frames.add(frame);
    }

    /**
     * @param frames the frames to add
     */
    public void addFrames(String... frames) {
        for (String frame : frames) {
            this.addFrame(frame);
        }
    }

    /**
     * Advances to the next frame and updates the item
     */
    public void nextFrame() {
        if (this.frames.size() == 0) {
            this.animatable.getInventory().setItem(slot, item);
            return;
        }
        if (frame == frames.size() - 1) {
            frame = 0;
        } else {
            frame++;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', frames.get(frame)));
        item.setItemMeta(meta);
        this.animatable.getInventory().setItem(slot, item);
    }

}
