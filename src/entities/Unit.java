package entities;

import util.Position;

public abstract class Unit {

    protected String name;
    protected int healthPool;
    protected int currentHealth;
    protected int attackPoints;
    protected int defensePoints;
    protected Position position;

    public Unit(String name, int healthPool, int attackPoints, int defensePoints, Position position) {
        this.name = name;
        this.healthPool = healthPool;
        this.currentHealth = healthPool;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public String description() {
        return String.format("%s\tHealth: %d/%d\tAttack: %d\tDefense: %d",
                name, currentHealth, healthPool, attackPoints, defensePoints);
    }

    @Override
    public String toString() {
        return "?";  // Override this in Player/Enemy to provide board symbol
    }

    public void interact(Tile targetTile) {
        targetTile.accept(this);  // Visitor pattern entry point
    }

    public abstract void accept(Unit visitor);

    public abstract void visit(Player p);

    public abstract void visit(Enemy e);

    public abstract void visit(EmptyTile empty);

    public abstract void visit(WallTile wall);


    // Combat logic (shared for Player/Enemy)
    public void engageCombat(Unit defender) {
        int attackRoll = (int) (Math.random() * (this.attackPoints + 1));
        int defenseRoll = (int) (Math.random() * (defender.defensePoints + 1));
        int damage = attackRoll - defenseRoll;

        if (damage > 0) {
            defender.receiveDamage(damage);
        }
    }

    public void receiveDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            // Dead logic (handled by subclasses or board)
        }
    }
}
