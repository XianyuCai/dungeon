package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Player;


public class MidnightArmour extends Buildable {
    private double extraAttack;
    private double extraDefence;

    public MidnightArmour(double extraAttack, double extraDefence) {
        super(null, 0);
        this.extraAttack = extraAttack;
        this.extraDefence = extraDefence;
    }

    @Override
    public void use(Game game) {
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0, extraAttack, extraDefence, 1, 1
        ));
    }

    @Override
    public int getDurability() {
        //can use forever
        return Integer.MAX_VALUE;
    }

    @Override
    public void onCollect(Player player) {

    }
}
