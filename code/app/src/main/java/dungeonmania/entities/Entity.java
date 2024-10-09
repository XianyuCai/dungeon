package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.UUID;

public abstract class Entity {
    public static final int FLOOR_LAYER = 0;
    public static final int ITEM_LAYER = 1;
    public static final int DOOR_LAYER = 2;
    public static final int CHARACTER_LAYER = 3;

    private Position position;
    private Position previousPosition;
    private Position previousDistinctPosition;
    private Direction facing;
    private String entityId;

    public Entity(Position position) {
        this.position = position;
        this.previousPosition = position;
        this.previousDistinctPosition = null;
        this.entityId = UUID.randomUUID().toString();
        this.facing = null;
    }

    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }

    public void setPosition(Position position) {
        this.previousPosition = this.position;
        this.position = position;
        if (!previousPosition.equals(this.position)) {
            this.previousDistinctPosition = this.previousPosition;
        }
    }

    public void translateBy(Direction direction) {
        setPosition(Position.translateBy(this.position, direction));
    }

    public void translateBy(Position offset) {
        setPosition(Position.translateBy(this.position, offset));
    }

    public void onOverlap(GameMap map, Entity entity) {
        // Default implementation
    }

    public void onMovedAway(GameMap map, Entity entity) {
        // Default implementation
    }

    public void onDestroy(GameMap gameMap) {
        // Default implementation
    }

    public Position getPosition() {
        return position;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getPreviousDistinctPosition() {
        return previousDistinctPosition;
    }

    public String getId() {
        return entityId;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public Direction getFacing() {
        return this.facing;
    }
}
