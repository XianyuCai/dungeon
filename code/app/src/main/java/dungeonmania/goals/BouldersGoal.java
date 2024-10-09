package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.Switch;

class BouldersGoal implements GoalStrategy {
    @Override
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(Switch::isActivated);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":boulders";
    }
}
