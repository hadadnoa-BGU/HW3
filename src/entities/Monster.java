package entities;

import util.Position;
import util.RandomUtils;

public class Monster extends Enemy {

    private final int visionRange;

    public Monster(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue,
                   int visionRange, Position position) {
        super(name, healthPool, attackPoints, defensePoints, experienceValue, position);
        this.visionRange = visionRange;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void takeTurn(Position playerPosition) {
        double distance = position.distance(playerPosition);

        if (distance < visionRange) {
            int dx = position.getX() - playerPosition.getX();
            int dy = position.getY() - playerPosition.getY();

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    moveLeft();
                } else {
                    moveRight();
                }
            } else {
                if (dy > 0) {
                    moveUp();
                } else {
                    moveDown();
                }
            }
        } else {
            performRandomMovement();
        }
    }

    private void performRandomMovement() {
        int direction = RandomUtils.randomInt(0, 4);
        switch (direction) {
            case 0 -> moveUp();
            case 1 -> moveDown();
            case 2 -> moveLeft();
            case 3 -> moveRight();
            case 4 -> stay();
        }
    }

    private void moveUp() { /* movement logic handled by controller/board */ }
    private void moveDown() { /* same here */ }
    private void moveLeft() { /* same */ }
    private void moveRight() { /* same */ }
    private void stay() { /* no movement */ }

    @Override
    public String toString() {
        return "M";  // Override per enemy type, e.g., 's' for Soldier, 'k' for Knight
    }
}
