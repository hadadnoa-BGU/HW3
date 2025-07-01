package entities;

import board.EmptyTile;
import board.Tile;
import board.WallTile;
import callbackMessages.*;
import utils.Position;

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

    public int getLevel() {
        return playerLevel;
    }

    public int getExperience() {
        return experience;
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
        // Move to empty tile
        Position oldPos = getPosition();
        setPosition(empty.getPosition());
        empty.setPosition(oldPos);
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
        tile.accept(this);  // Triggers interaction based on the tile type (EmptyTile, WallTile, Enemy, etc.)
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
        // Example: damage all enemies
        for (Enemy e : enemies) {
            engageCombat(e);
        }
    }

    // Player-specific methods
    public abstract void abilityCast();

    @Override
    public String toString() {
        return "@";
    }
}
