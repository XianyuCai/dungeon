package dungeonmania.movement;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;
import dungeonmania.map.GameMap;
import dungeonmania.entities.Player;

public class DefaultMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Enemy enemy, Game game) {
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        return map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
    }
}
