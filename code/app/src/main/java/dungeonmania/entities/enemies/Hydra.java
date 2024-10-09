package dungeonmania.entities.enemies;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

import java.util.Random;

public class Hydra extends ZombieToast {
    private double healthIncreaseRate;
    private double healthIncreaseAmount;
    private Random randGen = new Random();

    public static final double DEFAULT_HEALTH = 10.0;
    public static final double DEFAULT_ATTACK = 10.0;
    public static final double DEFAULT_HEALTH_INCREASE_RATE = 0.5;
    public static final double DEFAULT_HEALTH_INCREASE_AMOUNT = 1;

    public Hydra(Position position, double health, double attack,
                 double healthIncreaseRate, double healthIncreaseAmount) {
        super(position, health, attack);
        this.healthIncreaseRate = healthIncreaseRate;
        this.healthIncreaseAmount = healthIncreaseAmount;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        BattleStatistics stats = super.getBattleStatistics();
        if (randGen.nextDouble() < healthIncreaseRate) {
            stats.setHealth(stats.getHealth() + healthIncreaseAmount);
        }
        return stats;
    }

    public double getHealthIncreaseRate() {
        return healthIncreaseRate;
    }

    public double getHealthIncreaseAmount() {
        return healthIncreaseAmount;
    }
}
