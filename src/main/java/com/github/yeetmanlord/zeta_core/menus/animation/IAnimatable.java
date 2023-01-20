package com.github.yeetmanlord.zeta_core.menus.animation;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;

public interface IAnimatable {

    /**
     * @return The time before each animation frame is displayed in ticks
     */
    int getAnimationInterval();


    /**
     * @return Returns a list of animated items
     */
    List<AnimatedItem> getAnimatedItems();

    /**
     * Adds an animated item to the list of animated items
     * @param animatedItem The animated item to add
     */
    default void addAnimatedItem(AnimatedItem animatedItem) {
        getAnimatedItems().add(animatedItem);
    }

    Inventory getInventory();

    /**
     * Updates the inventory with the next frame of the animation
     */
    default void updateInventory() {
        if (isOpen()) {
            for (AnimatedItem animatedItem : getAnimatedItems()) {
                animatedItem.nextFrame();
            }
            Bukkit.getScheduler().runTaskLater(ZetaCore.getInstance(), this::updateInventory, getAnimationInterval());
        }
    }

    /**
     * @return Returns true if the inventory is open
     */
    boolean isOpen();

}
