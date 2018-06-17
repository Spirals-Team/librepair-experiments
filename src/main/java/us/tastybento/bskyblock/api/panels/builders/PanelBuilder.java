package us.tastybento.bskyblock.api.panels.builders;

import java.util.TreeMap;

import us.tastybento.bskyblock.api.panels.Panel;
import us.tastybento.bskyblock.api.panels.PanelItem;
import us.tastybento.bskyblock.api.panels.PanelListener;
import us.tastybento.bskyblock.api.user.User;

public class PanelBuilder {
    private String name;
    private TreeMap<Integer, PanelItem> items = new TreeMap<>();
    private int size;
    private User user;
    private PanelListener listener;

    public PanelBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Add item to the panel in the last slot.
     * @param item - Panel item
     * @return PanelBuilder
     */
    public PanelBuilder item(PanelItem item) {
        return item(nextSlot(), item);
    }

    /**
     * Add item into a specific slot. If it is already occupied, it will be replaced.
     * @param slot - slot
     * @param item - Panel item
     * @return PanelBuilder
     */
    public PanelBuilder item(int slot, PanelItem item) {
        items.put(slot, item);
        return this;
    }

    /**
     * Forces panel to be a specific number of slots.
     * @param size - size to be
     * @return PanelBuilder - PanelBuilder
     */
    public PanelBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * Sets the user who will get this panel. This will open it immediately when it is built
     * @param user - the User
     * @return PanelBuilder
     */
    public PanelBuilder user(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets which PanelListener will listen for clicks
     * @param listener - listener for this panel
     * @return PanelBuilder
     */
    public PanelBuilder listener(PanelListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Get the next free slot number
     * @return next slot number, or -1 in case none has been found.
     */
    public int nextSlot() {
        for (int i = 0 ; i < (size == 0 ? 54 : size) ; i++) {
            if (!slotOccupied(i)) return i;
        }
        return -1;
    }

    /**
     * Checks if a slot is occupied in the panel or not
     * @param slot to check
     * @return true or false
     */
    public boolean slotOccupied(int slot) {
        return items.containsKey(slot);
    }

    /**
     * Build the panel
     * @return Panel
     */
    public Panel build() {
        return new Panel(name, items, Math.max(size, items.isEmpty() ? size : items.lastKey()), user, listener);
    }
}
