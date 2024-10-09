package dungeonmania.goals;

import dungeonmania.Game;

public class Goal {
    private GoalStrategy strategy;

    public Goal(GoalStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean achieved(Game game) {
        return strategy.achieved(game);
    }

    public String toString(Game game) {
        return strategy.toString(game);
    }
}
