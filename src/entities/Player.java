package entities;

import board.EmptyTile;
import board.Tile;
import board.WallTile;
import callbackMessages.*;
import mechanics.CombatSystem;
import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public abstract class Player extends Unit {
    private IntBinaryOperator randomInt;
    protected int experience;
    protected int playerLevel;

    protected DeathCallback dthCallback;
    protected MessageCallbacks msgCallback;
    protected ChangedPositionCallback cPosCallback;


    public Player(String name, int healthPool, int attackPoints, int defensePoints, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.experience = 0;
        this.playerLevel = 1;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public int getExperience() {
        return experience;
    }

    public int getExperienceThreshold() {
        return 50 * playerLevel;
    }


    public int getLevel() {
        return playerLevel;
    }



    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= 50 * playerLevel) {
            levelUp();
        }
    }




    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);  // goes back to visitor logic
    }

    public void initialize(IntBinaryOperator randomInt, DeathCallback dthCallback, MessageCallbacks msgCallback, ChangedPositionCallback cPosCallback) {
        this.randomInt = randomInt;
        this.dthCallback = dthCallback;
        this.msgCallback = msgCallback;
        this.cPosCallback = cPosCallback;
    }


    @Override
    public void visit(Player p) {
        // Player visiting another player: do nothing
    }

    @Override
    public void visit(Enemy e) {
        // Player stepping onto an enemy combat triggers
        System.out.println(getName() + " engages in combat with " + e.getName());
        engageCombat(e);
    }




    @Override
    public void visit(EmptyTile empty) {
        Position oldPos = getPosition();
        setPosition(empty.getPosition());
        empty.setPosition(oldPos);

        System.out.println("Moved from " + oldPos.getX() + "," + oldPos.getY() +
                " to " + getPosition().getX() + "," + getPosition().getY());

        if (cPosCallback != null)
            cPosCallback.onPositionChanged(oldPos, getPosition());
    }


    @Override
    public void visit(WallTile wall) {
        // Block movement, nothing happens
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

    public void playTurn(Tile tile) {
        if (tile instanceof EmptyTile) {
            tile.accept(this);  // Move onto empty tile
        } else if (tile instanceof Enemy) {
            Enemy enemy = (Enemy) tile;
            CombatSystem.engageCombat(this, enemy);

            // After combat, check if enemy died:
            if (enemy.isDead()) {
                if (dthCallback != null) dthCallback.onDeath(enemy);
                // Replace enemy with EmptyTile
                Position enemyPos = enemy.getPosition();
                EmptyTile newTile = new EmptyTile(enemyPos);
                newTile.accept(this);  // Now player moves into dead enemy's tile
            }
        } else {
            tile.accept(this);  // Handle other interactions (walls etc.)
        }

        if (msgCallback != null) {
            msgCallback.send(this.description());  // Print player stats
        }
    }


    @Override
    public void onTick() {
        // Example: Regenerate small health each turn
        if (currentHealth < healthPool) {
            currentHealth = Math.min(healthPool, currentHealth + 1);
        }
    }

    @Override
    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d\tLevel: %d\tExperience: %d/%d",
                name, currentHealth, healthPool, attackPoints, defensePoints, playerLevel, experience, 50 * playerLevel);
    }
    public void addExperience(int amount) {
        gainExperience(amount);
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        System.out.println(getName() + " casts a fireball!");

        // Collect enemies that died during the ability
        List<Enemy> toRemove = new ArrayList<>();

        for (Enemy e : enemies) {
            engageCombat(e);
            if (!e.isAlive()) {
                if (dthCallback != null)
                    dthCallback.onDeath(e);
                toRemove.add(e);
            }
        }

        enemies.removeAll(toRemove);
    }



    @Override
    public String toString() {
        return "@";
    }
}
