package entities;

import board.EmptyTile;
import board.Tile;
import utils.Position;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {

    private int manaPool;
    private int currentMana;
    private final int manaCost;
    private int spellPower;
    private final int hitCount;
    private final int range;

    public Mage(String name, int healthPool, int attackPoints, int defensePoints,
                int manaPool, int manaCost, int spellPower, int hitCount, int range, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitCount = hitCount;
        this.range = range;
    }

    public void playTurn(Tile targetTile, Tile[][] board, int dx, int dy) {
        Position pos = getPosition();

        for (int i = 1; i <= range; i++) {
            int nx = pos.getX() + dx * i;
            int ny = pos.getY() + dy * i;

            if (ny < 0 || ny >= board.length || nx < 0 || nx >= board[0].length) break;

            Tile t = board[ny][nx];

            if (t instanceof Enemy enemy) {
                System.out.println(getName() + " casts a ranged attack on " + enemy.getName());
                engageCombat(enemy);

                if (!enemy.isAlive() && dthCallback != null)
                    dthCallback.onDeath(enemy);
                return;
            } else if (!(t instanceof EmptyTile)) {
                break; // Hits wall or other obstacle
            }
        }

        // No enemy in line, move normally
        super.playTurn(targetTile);
    }

    public int getCurrentMana() { return currentMana; }
    public int getManaPool() { return manaPool; }
    public int getSpellPower() { return spellPower; }

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

        while (hits < hitCount) {
            List<Enemy> inRange = new ArrayList<>();

            // Manually build in-range enemy list
            for (Enemy e : enemies) {
                if (e.isAlive() && position.distance(e.getPosition()) <= range) {
                    inRange.add(e);
                }
            }

            if (inRange.isEmpty()) {
                break; // No more valid targets
            }

            // Pick random target
            int index = RandomUtils.randomInt(0, inRange.size() - 1);
            Enemy target = inRange.get(index);

            int dealtDamage = Math.max(spellPower - target.getDefensePoints(), 0);
            target.receiveDamage(dealtDamage);
            System.out.println(getName() + " hits " + target.getName() + " for " + dealtDamage + " damage.");

            if (!target.isAlive() && dthCallback != null) {
                dthCallback.onDeath(target);
            }

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
