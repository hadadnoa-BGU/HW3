package entities;

import board.Tile;
import utils.Position;
import utils.RandomUtils;

import java.util.List;

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

    @Override
    public void playTurn(Tile tile) {
        interact(tile);
    }

    @Override
    public void playTurn() {
        // Players don't act autonomously
    }

    @Override
    public void onTick() {
        gameTick();
    }



    public void gameTick() {
        currentEnergy = Math.min(currentEnergy + 10, 100);
    }

    @Override

    public void castAbility(List<Enemy> enemies) {
        if (currentEnergy < abilityCost) {
            System.out.println(getName() + " tried to use Fan of Knives, but doesn't have enough energy.");
            return;
        }

        currentEnergy -= abilityCost;
        System.out.println(getName() + " casts Fan of Knives, hitting all enemies within range < 2 for " + attackPoints + " damage.");

        for (Enemy e : enemies) {
            if (e.isAlive() && position.distance(e.getPosition()) < 2) {
                System.out.println(getName() + " hits " + e.getName() + " for " + attackPoints + " damage.");
                e.receiveDamage(attackPoints);
            }
        }
    }


    @Override
    public String description() {
        return super.description() + String.format("\tEnergy: %d/100", currentEnergy);
    }
}
