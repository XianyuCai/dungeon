package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SunStone extends Entity implements InventoryItem {

    public SunStone(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (((Player) entity).pickUp(this)) {
                map.destroyEntity(this);
            }
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        // nothing
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        // nothing
    }

    public boolean canOpenDoor() {
        return true;
    }

    public boolean canUseAsTreasure() {
        return true;
    }

    public boolean canUseAsKey() {
        return true;
    }

    @Override
    public void onCollect(Player player) {
        player.getInventory().add(this);
    }
}
