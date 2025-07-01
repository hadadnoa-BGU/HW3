package entities;

import util.Position;

public abstract class Player extends Unit {

    protected int experience;
    protected int playerLevel;

    public Player(String name, int healthPool, int attackPoints, int defensePoints, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.experience = 0;
        this.playerLevel = 1;
    }

    public int getLevel() {
        return playerLevel;
    }

    public int getExperience() {
        return experience;
    }

    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= 50 * playerLevel) {
            levelUp();
        }
    }

    protected void levelUp() {
        experience -= 50 * playerLevel;
        playerLevel++;
        healthPool += 10 * playerLevel;
        currentHealth = healthPool;
        attackPoints += 4 * playerLevel;
        defensePoints += 1 * playerLevel;
    }

    @Override
    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d\tLevel: %d\tExperience: %d/%d",
                name, currentHealth, healthPool, attackPoints, defensePoints, playerLevel, experience, 50 * playerLevel);
    }

    // Player-specific methods
    public abstract void abilityCast();

    @Override
    public String toString() {
        return "@";
    }
}
