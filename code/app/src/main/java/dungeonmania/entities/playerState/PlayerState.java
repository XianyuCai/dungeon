package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.*;
import dungeonmania.battles.BattleStatistics;

public abstract class PlayerState {
    private Player player;
    private boolean isInvincible;
    private boolean isInvisible;

    PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.player = player;
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public Player getPlayer() {
        return player;
    }

    public void transitionToNextState(Potion potion) {
        if (potion instanceof InvincibilityPotion) {
            player.changeState(new InvincibleState(player));
        } else {
            player.changeState(new InvisibleState(player));
        }
    }

    public abstract void transitionBase();

    public abstract void transitionInvincible();

    public abstract void transitionInvisible();

    public abstract BattleStatistics applyBuff(BattleStatistics origin);
}

