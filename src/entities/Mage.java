package entities;

import board.Tile;
import utils.Position;
import utils.RandomUtils;

import java.util.List;

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
    public void castAbility(List<Enemy> enemies) {
        if (currentMana < manaCost) {
            System.out.println(getName() + " tried to use Blizzard, but doesn't have enough mana.");
            return;
        }

        currentMana -= manaCost;
        System.out.println(getName() + " casts Blizzard!");

        int hits = 0;
        while (hits < hitsCount) {
            // Filter living enemies within range
            List<Enemy> inRange = enemies.stream()
                    .filter(e -> e.isAlive() && position.distance(e.getPosition()) <= abilityRange)
                    .toList();

            if (inRange.isEmpty()) break;  // No valid targets

            // Pick random enemy
            int index = RandomUtils.randomInt(0, inRange.size() - 1);
            Enemy target = inRange.get(index);

            System.out.println(getName() + " hits " + target.getName() + " for " + spellPower + " damage.");
            target.receiveDamage(spellPower);

            hits++;
        }
    }


    @Override
    public String description() {
        return super.description() + String.format("\tMana: %d/%d\tSpell Power: %d", currentMana, manaPool, spellPower);
    }
    public int getMana()
    {
        return this.currentMana;
    }
}
