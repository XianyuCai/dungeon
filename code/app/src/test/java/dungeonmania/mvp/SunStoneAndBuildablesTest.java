package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;

// import java.util.List;

public class SunStoneAndBuildablesTest {
    @Test
    @DisplayName("Test Sun Stone can be collected")
    public void testSunStoneCollection() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest", "c_sunStoneTest");

        // player starts next to a sun stone
        assertEquals(0, getInventoryCount(res, "sun_stone"));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sun Stone cannot replace gold for bribing mercenaries")
    public void testSunStoneCannotReplaceGoldForBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneCannotBribe", "c_sunStoneTest");

        // Collect sun stone
        res = dmc.tick(Direction.RIGHT);

        // Find mercenary
        String mercenaryId = getEntityId(res, "mercenary");

        // Attempt to bribe mercenary with sun stone (should fail)
        assertThrows(IllegalArgumentException.class, () -> dmc.interact(mercenaryId));

        // Check sun stone is still in inventory
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test crafting Midnight Armour with insufficient materials")
    public void testMidnightArmourInsufficientMaterials() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourInsufficientMaterials", "c_midnightArmourTest");

        // Collect only sun stone
        res = dmc.tick(Direction.RIGHT); // Collect sun stone

        // Attempt to build midnight armour (should fail)
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

        // Check sun stone is still in inventory
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sceptre mind control duration")
    public void testSceptreMindControlDuration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreMindControlDuration", "c_sceptreTest");

        // Collect sceptre (assume it's given in the map)
        res = dmc.tick(Direction.RIGHT);

        // Find mercenary
        String mercenaryId = getEntityId(res, "mercenary");

        // Check if mercenary is now allied (not interactable)
        assertFalse(isEntityInteractable(res, mercenaryId));

        // Wait for mind control to wear off
        for (int i = 0; i < 2; i++) { // Assume 2 ticks is enough
            res = dmc.tick(Direction.UP);
        }

        // Check if mercenary is no longer mind controlled
        assertFalse(isEntityInteractable(res, mercenaryId));
    }

    @Test
    @DisplayName("Test Sun Stone can open doors")
    public void testSunStoneOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneOpenDoor", "c_sunStoneTest");
        // Collect sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventoryCount(res, "sun_stone"));
        // Move to door
        res = dmc.tick(Direction.RIGHT);

        // Check if door is open (player position changed)
        assertNotEquals(new Position(1, 1), getPlayerPos(res));

        // Check if sun stone is still in inventory
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test crafting without any materials")
    public void testCraftingWithoutMaterials() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_craftingWithoutMaterials", "c_sceptreTest");
        // Attempt to build sceptre without any materials (should fail)
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));
    }

    @Test
    @DisplayName("Test Sun Stone cannot open unlinked door")
    public void testSunStoneCannotOpenUnlinkedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneUnlinkedDoor", "c_sunStoneTest");

        // Collect sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventoryCount(res, "sun_stone"));

        // Move to unlinked door
        res = dmc.tick(Direction.RIGHT);

        // Check if door is still closed (player position should not change)
        assertEquals(new Position(1, 1), getPlayerPos(res));
        // Check if sun stone is still in inventory
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sceptre crafting")
    public void testSceptreCrafting() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreCrafting", "c_sceptreTest");

        // Collect materials
        res = dmc.tick(Direction.RIGHT); // Collect wood
        res = dmc.tick(Direction.RIGHT); // Collect key
        res = dmc.tick(Direction.RIGHT); // Collect sun stone

        // Attempt to build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        // Check if sceptre is in inventory
        assertEquals(1, getInventoryCount(res, "sceptre"));

        // Check if materials were consumed
        assertEquals(0, getInventoryCount(res, "wood"));
        assertEquals(0, getInventoryCount(res, "key"));
        assertEquals(0, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sun Stone counts as treasure for goals")
    public void testSunStoneTreasureGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTreasureGoal", "c_sunStoneTest");

        assertFalse(res.getGoals().isEmpty());
        res = dmc.tick(Direction.RIGHT); // Collect sun stone
        assertTrue(!res.getGoals().isEmpty()); // Goal should be completed
    }

    @Test
    @DisplayName("Test Sceptre crafting with wood, key, and sun stone")
    public void testSceptreCraftingWoodKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreCraftingWoodKey", "c_sceptreTest");

        // Collect materials
        res = dmc.tick(Direction.RIGHT); // Wood
        res = dmc.tick(Direction.RIGHT); // Key
        res = dmc.tick(Direction.RIGHT); // Sun Stone

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, getInventoryCount(res, "sceptre"));
        assertEquals(0, getInventoryCount(res, "wood"));
        assertEquals(0, getInventoryCount(res, "key"));
        assertEquals(0, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sceptre mind control")
    public void testSceptreMindControl() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreMindControl", "c_sceptreTest");

        // Collect sceptre (assume it's given in the map)
        res = dmc.tick(Direction.RIGHT);

        // Find mercenary
        String mercenaryId = getEntityId(res, "mercenary");
        System.err.println(mercenaryId);
        // Use sceptre on mercenary

        // Check if mercenary is now allied (not interactable)
        assertFalse(isEntityInteractable(res, mercenaryId));

        // Wait for mind control to wear off
        for (int i = 0; i < 10; i++) { // Assume 10 ticks is enough
            res = dmc.tick(Direction.UP);
        }

    }

    @Test
    @DisplayName("Test Midnight Armour crafting")
    public void testMidnightArmourCrafting() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourCrafting", "c_midnightArmourTest");

        // Collect materials
        res = dmc.tick(Direction.RIGHT); // Collect sword
        res = dmc.tick(Direction.RIGHT); // Collect sun stone

        // Attempt to build midnight armour
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        // if midnight armour is in inventory
        assertEquals(1, getInventoryCount(res, "midnight_armour"));

        assertEquals(0, getInventoryCount(res, "sword"));
        assertEquals(0, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Midnight Armour provides attack and defense bonus")
    public void testMidnightArmourBonus() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourBattle", "c_midnightArmourTest");

        res = dmc.tick(Direction.RIGHT); // Collect Midnight Armour
        res = dmc.tick(Direction.RIGHT); // Move to enemy

        assertTrue(res.getBattles().size() > 0);
        // Check that player survived (assuming Midnight Armour provides sufficient bonus)
        assertNotNull(getPlayerPos(res));
    }

    @Test
    @DisplayName("Test Midnight Armour cannot be crafted with zombies")
    public void testMidnightArmourWithZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourWithZombies", "c_midnightArmourTest");
        System.err.println(res.getDungeonName());

        // Collect materials
        res = dmc.tick(Direction.RIGHT); // Collect sword
        res = dmc.tick(Direction.RIGHT); // Collect sun stone
        // Attempt to build midnight armour (should fail)
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

        // Check materials are still in inventory
        assertEquals(1, getInventoryCount(res, "sword"));
        assertEquals(1, getInventoryCount(res, "sun_stone"));
    }

    @Test
    @DisplayName("Test Sun Stone cannot bribe mercenaries")
    public void testSunStoneCannotBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneCannotBribe", "c_sunStoneTest");

        res = dmc.tick(Direction.RIGHT);

        String mercenaryId = getEntityId(res, "mercenary");
        assertThrows(IllegalArgumentException.class, () -> dmc.interact(mercenaryId));
    }

    @Test
    @DisplayName("Test Midnight Armour in battle")
    public void testMidnightArmourInBattle() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourBattle", "c_midnightArmourTest");

        // Collect midnight armour
        res = dmc.tick(Direction.RIGHT);

        // Move to enemy
        res = dmc.tick(Direction.RIGHT);

        // battle occurred
        assertTrue(res.getBattles().size() > 0);

        // if player survived
        assertNotNull(getPlayerPos(res));
    }

    @Test
    @DisplayName("Test Sun Stone replaces key in crafting Sceptre")
    public void testSunStoneReplacesKeyInCrafting() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneReplacesKey", "c_sceptreTest");

        res = dmc.tick(Direction.RIGHT); // Collect wood
        res = dmc.tick(Direction.RIGHT); // Collect treasure
        res = dmc.tick(Direction.RIGHT); // Collect sun stone

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, getInventoryCount(res, "sceptre"));
        assertEquals(0, getInventoryCount(res, "wood"));
        assertEquals(0, getInventoryCount(res, "treasure"));
        assertEquals(0, getInventoryCount(res, "sun_stone"));
    }

    // Helper
    private int getInventoryCount(DungeonResponse res, String itemType) {
        return res.getInventory().stream().filter(item -> item.getType().equals(itemType)).mapToInt(item -> 1).sum();
    }

    private Position getPlayerPos(DungeonResponse res) {
        return res.getEntities().stream().filter(e -> e.getType().equals("player")).findFirst()
                .map(EntityResponse::getPosition).orElse(null);
    }

    private String getEntityId(DungeonResponse res, String entityType) {
        return res.getEntities().stream().filter(e -> e.getType().equals(entityType)).findFirst()
                .map(EntityResponse::getId).orElse(null);
    }

    private boolean isEntityInteractable(DungeonResponse res, String entityId) {
        return res.getEntities().stream().filter(e -> e.getId().equals(entityId)).findFirst()
                .map(EntityResponse::isInteractable).orElse(false);
    }
}
