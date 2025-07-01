package entities;

import board.Tile;
import utils.Position;

import java.util.List;

public class Hunter extends Player {

    private final int range;
    private int arrowsCount;
    private int ticksCount;
    private int arrows;

    public Hunter(String name, int health, int attack, int defense, int arrows, int range, Position position) {
        super(name, health, attack, defense, position);
        this.arrows = arrows;
        this.range = range;
    }

    public int getArrows() {
        return arrows;
    }

    public int getRange() {
        return range;
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
    public void castAbility(List<Enemy> enemies) {
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
