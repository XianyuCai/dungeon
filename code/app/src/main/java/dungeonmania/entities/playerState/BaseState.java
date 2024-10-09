package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.battles.BattleStatistics;

public class BaseState extends PlayerState {
    public BaseState(Player player) {
        super(player, false, false);
    }

    @Override
    public void transitionBase() {
        // Do nothing
    }

    @Override
    public void transitionInvincible() {
        Player player = getPlayer();
        player.changeState(new InvincibleState(player));
    }

    @Override
    public void transitionInvisible() {
        Player player = getPlayer();
        player.changeState(new InvisibleState(player));
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        // Base state does not apply any buffs
        return origin;
    }
}
