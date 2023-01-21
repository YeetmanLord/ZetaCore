package com.github.yeetmanlord.zeta_core.menus.animation;

import org.bukkit.Bukkit;
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
     * Add a new frame to the animation
     * @param lore Full list of lore for the frame
     */
    public void addLoreFrame(List<String> lore) {
        loreFrames.add(lore);
    }

    /**
     * Add a new frame to the animation using the last frame as a copy
     */
    public void addCopyOfLastFrame() {
        List<String> lastFrame = new ArrayList<>(loreFrames.get(loreFrames.size() - 1));
        loreFrames.add(lastFrame);
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

    /**
     * If you'd like to add one line in the lore, you can use this rather than rewriting the entire lore.
     * Will add to the last frame
     * @param text The text to add to the lore
     */
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

    /**
     * Get the current lore frame
     * @return The current lore frame
     */
    public int getLoreFrame() {
        return loreFrame;
    }

    /**
     * Get all the lore frames for this item.
     * @return A list of lists of strings, where each list of strings is a frame.
     */
    public List<List<String>> getLoreFrames() {
        return loreFrames;
    }

    @Override
    public void test() {
        super.test();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Lore Frames:");
        int f = 0;
        System.out.println(loreFrames.size());
        for (List<String> frame : loreFrames) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Frame " + f + ":");
            for (String line : frame) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            Bukkit.getConsoleSender().sendMessage("");
            ++f;
        }
    }
}
