package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;

public class Sceptre extends Buildable {
    private int duration;

    public Sceptre(int duration) {
        super(null, 0);
        this.duration = duration;
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    public int getDuration() {
        return duration;
    }


    @Override
    public void onCollect(Player player) {

    }

    public void controlEnemy(Game game, Enemy enemy) {

    }
}

