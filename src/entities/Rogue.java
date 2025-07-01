package entities;

import util.Position;
import util.RandomUtils;

public class Rogue extends Player {

    private final int abilityCost;
    private int currentEnergy;

    public Rogue(String name, int healthPool, int attackPoints, int defensePoints, int abilityCost, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.abilityCost = abilityCost;
        this.currentEnergy = 100;
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        currentEnergy = 100;
        attackPoints += 3 * playerLevel;
    }

    public void gameTick() {
        currentEnergy = Math.min(currentEnergy + 10, 100);
    }

    @Override
    public void abilityCast() {
        if (currentEnergy < abilityCost) {
            System.out.println(getName() + " tried to use Fan of Knives, but doesn't have enough energy.");
            return;
        }

        currentEnergy -= abilityCost;
        System.out.println(getName() + " casts Fan of Knives, hitting all enemies within range < 2 for " + attackPoints + " damage.");

        // Actual area-of-effect handled by controller or board
    }

    @Override
    public String description() {
        return super.description() + String.format("\tEnergy: %d/100", currentEnergy);
    }
}
