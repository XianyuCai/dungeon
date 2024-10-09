package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.util.Position;

import java.util.Random;

public class Assassin extends Mercenary {
    public static final double DEFAULT_HEALTH = 10.0;
    public static final double DEFAULT_ATTACK = 10.0;
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 0;
    public static final double DEFAULT_BRIBE_FAIL_RATE = 0.3;
    public static final double DEFAULT_ALLY_ATTACK = 0;
    public static final double DEFAULT_ALLY_DEFENCE = 0;

    private double bribeFailRate;
    private Random random = new Random();

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
                    double bribeFailRate, double allyAttack, double allyDefence) {
        super(position, health, attack, bribeAmount, bribeRadius, allyAttack, allyDefence);
        this.bribeFailRate = bribeFailRate;
    }

    @Override
    public void interact(Player player, Game game) {
        if (random.nextDouble() >= bribeFailRate) {
            super.interact(player, game);
        } else {
            for (int i = 0; i < getBribeAmount(); i++) {
                player.use(Treasure.class);
            }
        }
    }

    @Override
    public void move(Game game) {
        if (game.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            // Don't move when player is invisible
            return;
        }
        super.move(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !isAllied() && player.countEntityOfType(Treasure.class) >= getBribeAmount();
    }

    public double getBribeFailRate() {
        return bribeFailRate;
    }
}
