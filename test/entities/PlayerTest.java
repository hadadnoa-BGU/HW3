package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Adjust imports as needed — HeroicUnit, Mage, etc.

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        Position pos = new Position(0, 0); // Replace with actual position if needed
        player = new Mage("Gandalf", 100, 10, 5, 50, 10, 20, 3, 2, pos);
    }

    /*
     * summary: checks game level up mechanics
     */
    @Test
    void testLevelUpIncreasesStats() {
        int initialLevel = player.getLevel();
        int inialHealth = player.healthPool;
        int initialAttack = player.getAttackPoints();

        player.addExperience(1000);

        assertTrue(player.getLevel() > initialLevel, "expected player to level up after gaining enough xp");
        assertTrue(player.healthPool > inialHealth, "expected player health pool to increase after leveling up");
        assertTrue(player.getAttackPoints() > initialAttack, "expected player attack stat to increase after leveling up");
    }

    /*
     * Summary: Checks that casting a special ability by a Mage consumes mana.
     */
    @Test
    void testAbilityConsumesMana() {
        Position magePos = new Position(5, 5);
        Mage mage = new Mage(
                "Gandalf",
                100, 20, 5,
                100, // mana pool
                30,  // mana cost
                25, 2, 3,
                magePos);

        Enemy dummyEnemy = new Monster("Dummy", 'E', 10, 1, 1, 1, new Position(6, 5));
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(dummyEnemy);

        int manaBefore = mage.getMana();

        mage.castAbility(enemies);

        int manaAfter = mage.getMana();
        assertTrue(manaAfter < manaBefore, "Mana should decrease after casting ability");
    }

}
