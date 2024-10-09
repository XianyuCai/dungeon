package dungeonmania.entities.inventory;

import dungeonmania.entities.Player;

/**
 * A marker interface for InventoryItem
 */
public interface InventoryItem {
    void onCollect(Player player);
}
