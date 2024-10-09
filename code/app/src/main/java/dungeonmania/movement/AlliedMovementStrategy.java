package dungeonmania.movement;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class AlliedMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Enemy enemy, Game game) {
        Player player = game.getPlayer();
        GameMap map = game.getMap();
        Position nextPos;

        if (enemy.isAdjacentToPlayer()) {
            nextPos = player.getPreviousDistinctPosition();
        } else {
            nextPos = map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
            if (Position.isAdjacent(player.getPosition(), nextPos)) {
                enemy.setAdjacentToPlayer(true);
            }
        }

        return nextPos;
    }
}
