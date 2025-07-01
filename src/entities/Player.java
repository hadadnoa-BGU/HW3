package entities;

import util.Position;

public abstract class Player extends Unit {

    protected int experience;
    protected int playerLevel;

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
    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d\tLevel: %d\tExperience: %d/%d",
                name, currentHealth, healthPool, attackPoints, defensePoints, playerLevel, experience, 50 * playerLevel);
    }

    // Player-specific methods
    public abstract void abilityCast();

    @Override
    public String toString() {
        return "@";
    }
}
