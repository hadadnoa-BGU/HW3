package entities;

import util.Position;
import util.RandomUtils;

public class Warrior extends Player {

    private final int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name, int healthPool, int attackPoints, int defensePoints, int abilityCooldown, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        remainingCooldown = 0;
        healthPool += 5 * playerLevel;
        attackPoints += 2 * playerLevel;
        defensePoints += 1 * playerLevel;
        currentHealth = healthPool;
    }

    public void gameTick() {
        if (remainingCooldown > 0)
            remainingCooldown--;
    }

    @Override
    public void abilityCast() {
        if (remainingCooldown > 0) {
            System.out.println(getName() + " tried to use Avenger's Shield, but it's still on cooldown (" + remainingCooldown + " turns left).");
            return;
        }

        remainingCooldown = abilityCooldown;
        int healAmount = 10 * defensePoints;
        currentHealth = Math.min(currentHealth + healAmount, healthPool);

        System.out.println(getName() + " casts Avenger's Shield and heals for " + healAmount + " health.");

        // Logic to select random enemy within range < 3 happens in the game controller
        // For now, just leaving placeholder:
        System.out.println(getName() + " randomly hits an enemy within range < 3 for " + (int)(0.1 * healthPool) + " damage.");
    }

    @Override
    public String description() {
        return super.description() + String.format("\tCooldown: %d/%d", remainingCooldown, abilityCooldown);
    }
}
