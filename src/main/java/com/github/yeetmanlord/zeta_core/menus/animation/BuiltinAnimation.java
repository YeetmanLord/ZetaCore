package com.github.yeetmanlord.zeta_core.menus.animation;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuiltinAnimation {

    /**
     * Creates a flashing item with two different formats
     * @param slot Slot of the item
     * @param stack Item to flash
     * @param animatable The animatable object
     * @param text Text to flash
     * @param format1 First format
     * @param format2 Second format
     * @return The flashing item
     */
    public static AnimatedItem flashingItem(int slot, ItemStack stack, IAnimatable animatable, String text, String format1, String format2) {
        AnimatedItem item = new AnimatedItem(slot, stack, animatable);
        item.addFrame(format1 + text);
        item.addFrame(format2 + text);
        return item;
    }

    /**
     * Creates a flashing item with the given text and formats
     * @param slot Slot of the item
     * @param stack Item to flash
     * @param animatable The animatable object
     * @param text Text to flash
     * @param formats Formats to flash in order
     * @return The flashing item
     */
    public static AnimatedItem flashingItem(int slot, ItemStack stack, IAnimatable animatable, String text, String... formats) {
        AnimatedItem item = new AnimatedItem(slot, stack, animatable);
        for (String format : formats) {
            item.addFrame(format + text);
        }
        return item;
    }

    /**
     * Creates an animated item where a format "scrolls" across the item's name
     * The amount of text set to that format is the size of the "scroll." If you used
     * "&c" as the format, and "Hello" as the text, with "&f" as the default. The animation would be
     * like this. "&cH&fello", "&fH&ce&fllo", etc.
     * @param slot The slot of the item
     * @param stack The item
     * @param animatable The animatable object
     * @param text The text to scroll
     * @param size The size of the "scroll" (how many characters the scroll format takes up)
     * @param defaultFormat The default formatting code(s)
     * @param scrollingFormat The formatting code(s) for the scrolling text
     * @return The animated item
     */
    public static AnimatedItem scrollingFormatItem(int slot, ItemStack stack, IAnimatable animatable, String text, int size, String defaultFormat, String scrollingFormat) {
        AnimatedItem item = new AnimatedItem(slot, stack, animatable);
        for (int index = 0; index < text.length(); index++) {
            String newStr = "";
            if (index + size > text.length()) {
                newStr = scrollingFormat + text.substring(0, index + size - text.length()) + "&r" + defaultFormat + text.substring(index + size - text.length(), index) + "&r" + scrollingFormat + text.substring(index, text.length());
            }
            else {
                newStr = defaultFormat + text.substring(0, index) + "&r" + scrollingFormat + text.substring(index, index + size) + "&r" + defaultFormat + text.substring(index + size);
            }
            item.addFrame(newStr);
        }
        return item;
    }

    /**
     * Creates an animated item where a item's name will scroll over a given text
     * @param slot The slot of the item
     * @param stack The item
     * @param animatable The animatable object
     * @param size The amount of characters in the scroll
     * @param fullText The full text to scroll over
     * @return The animated item with the scrolling text
     */
    public static AnimatedItem scrollingText(int slot, ItemStack stack, IAnimatable animatable, int size, String fullText) {
        AnimatedItem item = new AnimatedItem(slot, stack, animatable);
        int length = fullText.length();
        int max = length - size;
        for (int x = 0; x < max; x++) {
            item.addFrame(fullText.substring(x, x + size));
        }
        for (int x = max; x < length; x++) {
            item.addFrame(fullText.substring(x, length) + " " + fullText.substring(0, x - max));
        }
        return item;
    }

    public static ComplexAnimatedItem scrollingLore(int slot, ItemStack stack, IAnimatable animatable, int size, List<String> beforeScroll, List<String> afterScroll, List<String> textToScroll) {
        ComplexAnimatedItem item = new ComplexAnimatedItem(slot, stack, animatable);
        int length = textToScroll.size();
        int max = length - size;
        for (int x = 0; x < max; x++) {
            List<String> fullLore = new ArrayList<>();
            fullLore.addAll(beforeScroll);
            if (x == 0 && x + size < length) {
                fullLore.addAll(textToScroll.subList(x, x + size));
                fullLore.add("&7...");
            }
            else if (x > 0 && x + size < length) {
                fullLore.add("&7...");
                fullLore.addAll(textToScroll.subList(x, x + size));
                fullLore.add("&7...");
            }
            else if (x > 0 && x + size >= length) {
                fullLore.add("&7...");
                fullLore.addAll(textToScroll.subList(x, length));
                fullLore.addAll(textToScroll.subList(0, x + size - length));
            }
            else {
                fullLore.addAll(textToScroll.subList(x, x + size));
            }
            fullLore.addAll(afterScroll);

            item.addLoreFrame(fullLore);
        }

        return item;
    }
}
