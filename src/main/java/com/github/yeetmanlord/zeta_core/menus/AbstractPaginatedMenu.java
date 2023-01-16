package com.github.yeetmanlord.zeta_core.menus;

import com.github.yeetmanlord.zeta_core.api.util.input.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPaginatedMenu<T> extends AbstractGUIMenu {

    protected int page = 0;

    protected List<T> items;

    protected Map<Integer, Integer> slotToIndex;

    private int itemsPerPage;

    public AbstractPaginatedMenu(PlayerUtil helper, String title, int slots, int itemsPerPage, boolean shouldFill, AbstractGUIMenu parent) {
        super(helper, title, slots <= 9 ? 18 : slots, shouldFill, parent);
        this.itemsPerPage = itemsPerPage;
        slotToIndex = new HashMap<>();
    }

    public AbstractPaginatedMenu(PlayerUtil helper, String title, int slots, int itemsPerPage, boolean shouldFill) {
        this(helper, title, slots, itemsPerPage, shouldFill, null);
    }

    public AbstractPaginatedMenu(PlayerUtil helper, String title, int slots, int itemsPerPage) {
        this(helper, title, slots, itemsPerPage, false, null);
    }

    public AbstractPaginatedMenu(PlayerUtil helper, String title, int slots, int itemsPerPage, AbstractGUIMenu parent) {
        this(helper, title, slots, itemsPerPage, false, parent);
    }


    @Override
    public void setItems() {
        renderPage();
        createCloser();
        if (page + 1 < this.getMaxPages()) {
            this.inv.setItem(this.getSlots() - 1, this.makeItem(Material.ARROW, "&aNext Page"));
        }
        if (page > 0) {
            this.inv.setItem(this.getSlots() - 9, this.makeItem(Material.ARROW, "&aPrevious Page"));
        }
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType() == Material.ARROW) {
            if (e.getSlot() == this.getSlots() - 1) {
                if (page + 1 < getMaxPages()) {
                    page++;
                    setItems();
                }
            } else if (e.getSlot() == this.getSlots() - 9) {
                if (page - 1 >= 0) {
                    page--;
                    setItems();
                }
            }
        }
        if (e.getSlot() == this.getSlots() - 5) {
            this.close();
        }
    }

    public int getMaxPages() {
        return (int) Math.ceil((double) (items.size()) / itemsPerPage);
    }

    public abstract void renderPage();

    public void nextPage() {
        if (page + 1 < getMaxPages()) {
            page++;
            setItems();
        }
    }

    public void previousPage() {
        if (page - 1 > 0) {
            page--;
            setItems();
        }
    }

    public void setPage(int page) {
        if (page >= 0 && page < getMaxPages()) {
            this.page = page;
            setItems();
        }
    }

    public int getPage() {
        return page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public List<T> getAllItems() {
        return items;
    }

    public List<T> getItemsOnPage() {
        return items.subList(page * itemsPerPage, Math.min((page + 1) * itemsPerPage, items.size()));
    }
}
