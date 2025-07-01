package entities;

import board.Tile;
import utils.Position;

public class Hunter extends Player {

    private final int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name, int healthPool, int attackPoints, int defensePoints, int range, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.range = range;
        this.arrowsCount = 10 * playerLevel;
        this.ticksCount = 0;
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


    @Override
    protected void levelUp() {
        super.levelUp();
        arrowsCount += 10 * playerLevel;
        attackPoints += 2 * playerLevel;
        defensePoints += 1 * playerLevel;
    }

    public void gameTick() {
        ticksCount++;
        if (ticksCount == 10) {
            arrowsCount += playerLevel;
            ticksCount = 0;
        }
    }

    @Override
    public void abilityCast() {
        if (arrowsCount <= 0) {
            System.out.println(getName() + " tried to use Shoot, but has no arrows left.");
            return;
        }

        // Logic for finding the closest enemy within range happens at controller level
        System.out.println(getName() + " shoots the closest enemy within range " + range +
                " for " + attackPoints + " damage.");

        arrowsCount--;
    }

    @Override
    public String description() {
        return super.description() + String.format("\tArrows: %d\tRange: %d", arrowsCount, range);
    }
}
