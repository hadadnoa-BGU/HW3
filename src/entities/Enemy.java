package entities;

import util.Position;

public abstract class Enemy extends Unit {

    protected int experienceValue;

    public Enemy(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.experienceValue = experienceValue;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d\tXP Value: %d",
                name, currentHealth, healthPool, attackPoints, defensePoints, experienceValue);
    }

    @Override
    public String toString() {
        return "E";  // Placeholder, subclasses override for unique symbols
    }

    // Enemy-specific logic can be added by subclasses
}
