package entities;

import board.EmptyTile;
import board.WallTile;
import callbackMessages.*;
import utils.Position;

import java.util.List;

public abstract class Enemy extends Unit {

    protected int experienceValue;
    protected DeathCallback dthCallback;
    protected MessageCallbacks msgCallback;
    protected ChangedPositionCallback cPosCallback;
    protected Player player;

    public Enemy(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.experienceValue = experienceValue;
    }


    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d\tXP Value: %d",
                name, currentHealth, healthPool, attackPoints, defensePoints, experienceValue);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public String toString() {
        return "E";  // Placeholder, subclasses override for unique symbols
    }

    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);
    }

    @Override
    public void visit(Player p) {
        // Enemy stepping onto player: combat triggers
        System.out.println(getName() + " attacks " + p.getName());
        engageCombat(p);
    }

    @Override
    public void visit(Enemy e) {
        // Enemy visiting another enemy: do nothing
    }
    public boolean isDead() {
        return currentHealth <= 0;
    }

    @Override
    public void receiveDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            handleDeath();
        }
    }



    protected void handleDeath() {
        if (this instanceof Enemy enemy && enemy.dthCallback != null) {
            System.out.println(enemy.getName() + " has died.");
            enemy.dthCallback.onDeath(enemy);
        }
    }
    @Override
    public void castAbility(List<Enemy> enemies) {
        // Regular enemies have no special ability
    }

    // In entities.Enemy
    @Override
    public void visit(EmptyTile empty) {
        Position oldPos = getPosition();
        setPosition(empty.getPosition());
        empty.setPosition(oldPos);

        if (cPosCallback != null)
            cPosCallback.onPositionChanged(oldPos, getPosition());
    }


    public void initialize(DeathCallback dthCallback, MessageCallbacks msgCallback, ChangedPositionCallback cPosCallback) {
        this.dthCallback = dthCallback;
        this.msgCallback = msgCallback;
        this.cPosCallback = cPosCallback;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    @Override
    public void visit(WallTile wall) {
        // Block movement
    }
//.
    // Enemy-specific logic can be added by subclasses
}
