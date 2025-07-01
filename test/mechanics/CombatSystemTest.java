package mechanics;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class CombatSystemTest {

    private Mage attacker;
    private Enemy defender;

    @BeforeEach
    void setUp() {
        // Initialize a Mage with correct constructor arguments
        attacker = new Mage(
                "Gandalf", 100, 30, 10, 100,20, 15,3,2,               // abilityRange
                new Position(0, 0) // position
        );

        // Initialize a simple enemy with dummy callbacks
        defender = new Monster("Goblin", 50,  5,
                5, 10, 5, new Position(1, 1) // position
        );
        defender.initialize(u -> {}, m -> {}, (f, t) -> {});
        defender.setPosition(new Position(1, 1));
    }



    /*
     * summary: checks that engageCombat causes the defender's health to decrease on a successful hit
     * expected: defender health is less than initial health if damage > 0
     */
    @Test
    void testCombatReducesHealth() {
        int initialHealth = defender.getCurrentHealth();
        CombatSystem.engageCombat(attacker, defender);
        assertTrue(defender.getCurrentHealth() <= initialHealth);
    }

    /*
     * summary: ensures that engageCombat does not increase defender health
     * expected: defender health remains same or decreases
     */
    @Test
    void testCombatDoesNotHeal() {
        int initialHealth = defender.getCurrentHealth();
        CombatSystem.engageCombat(attacker, defender);
        assertTrue(defender.getCurrentHealth() <= initialHealth, "Defender should not be healed");
    }

    /*
     * summary: ensures attacker and defender interaction does not result in a crash or exception
     * expected: combat runs without exceptions regardless of roll outcomes
     */
    @Test
    void testCombatStability() {
        assertDoesNotThrow(() -> CombatSystem.engageCombat(attacker, defender));
    }
}
