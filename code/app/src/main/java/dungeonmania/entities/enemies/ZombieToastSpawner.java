package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.Wall;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import java.util.List;

public class ZombieToastSpawner extends Entity implements Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        if (!areAdjacentCellsWalls(game.getMap())) {
            game.getEntityFactory().spawnZombie(game, this);
        }
    }

    private boolean areAdjacentCellsWalls(GameMap map) {
        Position[] adjacentPositions = {
            Position.translateBy(getPosition(), Direction.UP),
            Position.translateBy(getPosition(), Direction.DOWN),
            Position.translateBy(getPosition(), Direction.LEFT),
            Position.translateBy(getPosition(), Direction.RIGHT)
        };

        for (Position pos : adjacentPositions) {
            List<Entity> entities = map.getEntities(pos);
            boolean hasWall = false;
            for (Entity entity : entities) {
                if (entity instanceof Wall) {
                    hasWall = true;
                    break;
                }
            }
            if (!hasWall) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }

    @Override
    public void interact(Player player, Game game) {
        player.getInventory().getWeapon().use(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        // No-op for overlap; interaction handled by player
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }
}
