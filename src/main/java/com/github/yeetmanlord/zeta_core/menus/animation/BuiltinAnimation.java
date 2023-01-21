package com.github.yeetmanlord.zeta_core.menus.animation;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuiltinAnimation {

    /**
     * Creates a flashing item with two different formats
     *
     * @param item    The item to add an animation to.
     * @param text    Text to flash
     * @param format1 First format
     * @param format2 Second format
     * @return The flashing item
     */
    public static AnimatedItem flashingItem(AnimatedItem item, String text, String format1, String format2) {
        item.addFrame(format1 + text);
        item.addFrame(format2 + text);
        return item;
    }

    /**
     * Creates a flashing item with the given text and formats
     *
     * @param item    The item to add an animation to.
     * @param text       Text to flash
     * @param formats    Formats to flash in order
     * @return The flashing item
     */
    public static AnimatedItem flashingItem(AnimatedItem item, String text, String... formats) {
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
     *
     * @param item    The item to add an animation to.
     * @param text            The text to scroll
     * @param size            The size of the "scroll" (how many characters the scroll format takes up)
     * @param defaultFormat   The default formatting code(s)
     * @param scrollingFormat The formatting code(s) for the scrolling text
     * @return The animated item
     */
    public static AnimatedItem scrollingFormatItem(AnimatedItem item, String text, int size, String defaultFormat, String scrollingFormat) {
        for (int index = 0; index < text.length(); index++) {
            String newStr = "";
            if (index + size > text.length()) {
                newStr = scrollingFormat + text.substring(0, index + size - text.length()) + "&r" + defaultFormat + text.substring(index + size - text.length(), index) + "&r" + scrollingFormat + text.substring(index, text.length());
            } else {
                newStr = defaultFormat + text.substring(0, index) + "&r" + scrollingFormat + text.substring(index, index + size) + "&r" + defaultFormat + text.substring(index + size);
            }
            item.addFrame(newStr);
        }
        return item;
    }

    /**
     * Creates an animated item where a item's name will scroll over a given text
     *
     * @param item    The item to add an animation to.
     * @param size       The amount of characters in the scroll
     * @param fullText   The full text to scroll over
     * @return The animated item with the scrolling text
     */
    public static AnimatedItem scrollingText(AnimatedItem item, int size, String fullText) {
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

    /**
     * Creates a complex animated item where the item's lore will scroll over a given list of text
     * @param item The item to add an animation to.
     * @param size The amount of lines in the scroll
     * @param beforeScroll The text to display before the scroll
     * @param afterScroll The text to display after the scroll
     * @param textToScroll The text to scroll over
     * @param displayEllipsis Whether to display ellipses (...) in the scrolling text
     * @return The animated item with the scrolling text
     */
    public static ComplexAnimatedItem scrollingLore(ComplexAnimatedItem item, int size, List<String> beforeScroll, List<String> afterScroll, List<String> textToScroll, boolean displayEllipsis) {
        int length = textToScroll.size();
        int max = length - size;
        for (int x = 0; x < max; x++) {
            List<String> fullLore = new ArrayList<>();
            fullLore.addAll(beforeScroll);
            if (x == 0 && x + size < length) {
                fullLore.addAll(textToScroll.subList(x, x + size));
                if (displayEllipsis) {
                    fullLore.add("&7...");
                }
            } else if (x > 0 && x + size < length) {
                if (displayEllipsis) {
                    fullLore.add("&7...");
                }
                fullLore.addAll(textToScroll.subList(x, x + size));
                if (displayEllipsis) {
                    fullLore.add("&7...");
                }
            } else if (x > 0 && x + size >= length) {
                if (displayEllipsis) {
                    fullLore.add("&7...");
                }
                fullLore.addAll(textToScroll.subList(x, length));
                fullLore.addAll(textToScroll.subList(0, x + size - length));
            } else {
                fullLore.addAll(textToScroll.subList(x, x + size));
            }
            fullLore.addAll(afterScroll);

            item.addLoreFrame(fullLore);
        }

        return item;
    }
}
