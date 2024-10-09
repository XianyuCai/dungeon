package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.util.Position;
import dungeonmania.movement.*;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack, new HostileMovementStrategy());
    }

    @Override
    public void move(Game game) {
        if (game.getMap().getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            setMovementStrategy(new InvincibilityMovementStrategy());
        } else if (game.getMap().getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            setMovementStrategy(new InvisibilityMovementStrategy());
        } else {
            setMovementStrategy(new HostileMovementStrategy());
        }
        super.move(game);
    }
}
