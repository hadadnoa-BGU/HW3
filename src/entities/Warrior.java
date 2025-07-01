package entities;

import board.Tile;
import utils.Position;
import utils.RandomUtils;

import java.util.List;

public class Warrior extends Player {

    private final int abilityCooldown;
    private int remainingCooldown;

    private int maxCooldown;

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }

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

    public void castAbility(List<Enemy> enemies) {
        if (remainingCooldown > 0) {
            System.out.println(getName() + " tried to use Avenger's Shield, but it's still on cooldown (" + remainingCooldown + " turns left).");
            return;
        }

        remainingCooldown = abilityCooldown;
        int healAmount = 10 * defensePoints;
        currentHealth = Math.min(currentHealth + healAmount, healthPool);

        System.out.println(getName() + " casts Avenger's Shield and heals for " + healAmount + " health.");

        // Filter enemies within range < 3
        List<Enemy> inRange = enemies.stream()
                .filter(e -> e.isAlive() && position.distance(e.getPosition()) < 3)
                .toList();

        if (inRange.isEmpty()) {
            System.out.println("No enemies in range to hit.");
            return;
        }

        // Select random target
        int index = RandomUtils.randomInt(0, inRange.size() - 1);
        Enemy target = inRange.get(index);

        int damage = (int) (0.1 * healthPool);
        System.out.println(getName() + " hits " + target.getName() + " for " + damage + " damage.");
        target.receiveDamage(damage);
    }


    @Override
    public String description() {
        return super.description() + String.format("\tCooldown: %d/%d", remainingCooldown, abilityCooldown);
    }
}
