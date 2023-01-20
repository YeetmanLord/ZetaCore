package com.github.yeetmanlord.zeta_core.menus.animation;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Supports lore animations for items in a menu.
 */
public class ComplexAnimatedItem extends AnimatedItem{

    private List<List<String>> loreFrames;

    private int loreFrame;



    public ComplexAnimatedItem(int slot, ItemStack item, IAnimatable animatable) {
        super(slot, item, animatable);
        this.loreFrames = new ArrayList<>();
    }

    /**
     * Add a new frame to the animation
     * @param lore Full list of lore for the frame
     */
    public void addLoreFrame(String... lore) {
        loreFrames.add(Arrays.asList(lore));
    }

    /**
     * Add a new frame to the animation using the last frame as a copy
     */
    public void addCopyOfLastFrame() {
        loreFrames.add(loreFrames.get(loreFrames.size() - 1));
    }

    /**
     * If you'd like to change one line in the lore, you can use this rather than rewriting the entire lore
     * @param index The index of the frame you'd like to change
     * @param line The line of lore
     * @param text The text to change the line to
     */
    public void changeLine(int index, int line, String text) {
        loreFrames.get(index).set(line, text);
    }

    /**
     * If you'd like to add one line in the lore, you can use this rather than rewriting the entire lore
     * @param index The index of the frame you'd like to change
     * @param text The text to add to the lore
     */
    public void addLine(int index, String text) {
        loreFrames.get(index).add(text);
    }

    /**
     * If you'd like to change one line in the lore, you can use this rather than rewriting the entire lore
     * @param line The line of lore
     * @param text The text to change the line to
     */
    public void changeLineLast(int line, String text) {
        loreFrames.get(loreFrames.size() - 1).set(line, text);
    }

    public void addLineLast(String text) {
        loreFrames.get(loreFrames.size() - 1).add(text);
    }

    @Override
    public void nextFrame() {
        super.nextFrame();
        if (this.loreFrames.size() == 0) {
            return;
        }
        loreFrame++;
        if (loreFrame >= loreFrames.size()) {
            loreFrame = 0;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> colored = new ArrayList<>();
        for (String s : loreFrames.get(loreFrame)) {
            colored.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(colored);
        item.setItemMeta(meta);
        this.animatable.getInventory().setItem(slot, item);
    }

    public int getLoreFrame() {
        return loreFrame;
    }

    public List<List<String>> getLoreFrames() {
        return loreFrames;
    }
}
