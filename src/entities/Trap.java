package entities;

import board.Tile;
import utils.Position;

public class Trap extends Enemy {

    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue,
                int visibilityTime, int invisibilityTime, Position position) {
        super(name, healthPool, attackPoints, defensePoints, experienceValue, position);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    public void takeTurn(Position playerPosition) {
        updateVisibility();

        if (visible && position.distance(playerPosition) < 2) {
            attackPlayer();
        }
    }

    @Override
    public void playTurn(Tile tile) {
        // Trap does not interact this way
    }

    @Override
    public void playTurn() {
        // Trap does not move
    }

    @Override
    public void onTick() {
        // Trap's tick logic goes here (e.g., countdowns, damage triggers)
    }

    private void updateVisibility() {
        ticksCount++;
        if (visible && ticksCount >= visibilityTime) {
            visible = false;
            ticksCount = 0;
        } else if (!visible && ticksCount >= invisibilityTime) {
            visible = true;
            ticksCount = 0;
        }
    }

    private void attackPlayer() {
        // Actual combat logic handled by controller or board system
        System.out.println(getName() + " attacks the player from stealth!");
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return visible ? getTrapSymbol() : ".";
    }

    protected String getTrapSymbol() {
        return "T";  // Override per specific trap type ('B', 'Q', 'D' etc.)
    }
}
