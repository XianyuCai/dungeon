package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.battles.BattleStatistics;

public class InvincibleState extends PlayerState {
    public InvincibleState(Player player) {
        super(player, true, false);
    }

    @Override
    public void transitionBase() {
        getPlayer().changeState(new BaseState(getPlayer()));
    }

    @Override
    public void transitionInvisible() {
        getPlayer().changeState(new InvisibleState(getPlayer()));
    }

    @Override
    public void transitionInvincible() {
        // Already in invincible state, do nothing
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
                0, 0, 0, 1, 1, true, true));
    }
}
