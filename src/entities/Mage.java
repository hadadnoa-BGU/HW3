package entities;

import board.Tile;
import utils.Position;
import utils.RandomUtils;

public class Mage extends Player {

    private int manaPool;
    private int currentMana;
    private final int manaCost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;

    public Mage(String name, int healthPool, int attackPoints, int defensePoints, int manaPool, int manaCost,
                int spellPower, int hitsCount, int abilityRange, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
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
        manaPool += 25 * playerLevel;
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        spellPower += 10 * playerLevel;
    }

    public void gameTick() {
        currentMana = Math.min(currentMana + playerLevel, manaPool);
    }

    @Override
    public void abilityCast() {
        if (currentMana < manaCost) {
            System.out.println(getName() + " tried to use Blizzard, but doesn't have enough mana.");
            return;
        }

        currentMana -= manaCost;
        System.out.println(getName() + " casts Blizzard!");

        int hits = 0;
        // Actual logic to get living enemies within range handled by board/controller
        while (hits < hitsCount /* && there are enemies in range */) {
            // Placeholder for selecting random enemy:
            System.out.println(getName() + " hits a random enemy within range " + abilityRange + " for " + spellPower + " damage.");
            hits++;
        }
    }

    @Override
    public String description() {
        return super.description() + String.format("\tMana: %d/%d\tSpell Power: %d", currentMana, manaPool, spellPower);
    }
}
