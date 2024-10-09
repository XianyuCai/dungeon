package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.entities.enemies.Hydra;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BossesTest {
    @Test
    @DisplayName("Test Assassin movement")
    public void assassinMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_movement", "c_assassinTest_movement");

        assertEquals(1, getAssassins(res).size());
        Position initialPos = getAssassins(res).get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        Position newPos = getAssassins(res).get(0).getPosition();
        assertNotEquals(initialPos, newPos);
    }

    @Test
    @DisplayName("Test Assassin bribe success")
    public void assassinBribeSuccess() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribe", "c_assassinTest_bribe");

        String assassinId = getAssassins(res).get(0).getId();

        // Collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Attempt bribe
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @DisplayName("Test Assassin bribe failure")
    public void assassinBribeFail() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeFail", "c_assassinTest_bribeFail");

        String assassinId = getAssassins(res).get(0).getId();

        // Collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Attempt bribe
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Assassin should still be hostile (not allied)
        assertTrue(res.getBattles().size() > 0);
    }

    @Test
    @DisplayName("Test Hydra movement")
    public void hydraMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_movement", "c_hydraTest_movement");

        assertEquals(1, getHydras(res).size());
        Position initialPos = getHydras(res).get(0).getPosition();

        res = dmc.tick(Direction.RIGHT);
        Position newPos = getHydras(res).get(0).getPosition();
        assertNotEquals(initialPos, newPos);
    }

    @Test
    @DisplayName("Test Hydra health increase")
    public void hydraHealthIncrease() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_healthIncrease", "c_hydraTest_healthIncrease");

        // Move player next to Hydra
        res = dmc.tick(Direction.RIGHT);

        // Initial battle
        res = dmc.tick(Direction.RIGHT);
        assertTrue(res.getBattles().size() > 0);

        // Check if Hydra's health increased
        BattleResponse battle = res.getBattles().get(0);
        double initialHealth = Hydra.DEFAULT_HEALTH;

        double finalHealth = battle.getInitialEnemyHealth();
        assertTrue(finalHealth > initialHealth);
    }

    private List<EntityResponse> getAssassins(DungeonResponse res) {
        List<EntityResponse> assassins = TestUtils.getEntities(res, "assassin");
        return assassins;
    }

    private List<EntityResponse> getHydras(DungeonResponse res) {
        return TestUtils.getEntities(res, "hydra");
    }
}
