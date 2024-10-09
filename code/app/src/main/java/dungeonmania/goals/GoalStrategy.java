package dungeonmania.goals;

import dungeonmania.Game;

interface GoalStrategy {
    boolean achieved(Game game);
    String toString(Game game);
}
