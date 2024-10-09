package dungeonmania.movement;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class HostileMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Enemy enemy, Game game) {
        GameMap map = game.getMap();
        Position playerPosition = game.getPlayer().getPosition();
        return map.dijkstraPathFind(enemy.getPosition(), playerPosition, enemy);
    }
}
