package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements GoalStrategy {
    private int requiredEnemies;

    public EnemyGoal(int requiredEnemies) {
        this.requiredEnemies = requiredEnemies;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getDefeatedEnemiesCount() >= requiredEnemies && game.allSpawnersDestroyed();
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }
}
