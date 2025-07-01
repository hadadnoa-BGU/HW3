package entities;

import board.GameBoard;
import board.Tile;
import callbackMessages.*;
import utils.Position;
import utils.RandomUtils;

public class Monster extends Enemy {

    private final int visionRange;
    protected GetTileCallback getTileCallback;
    public Monster(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue,
                   int visionRange, Position position) {
        super(name, healthPool, attackPoints, defensePoints, experienceValue, position);
        this.visionRange = visionRange;
    }

    public void initialize(DeathCallback dthCallback, MessageCallbacks msgCallback, ChangedPositionCallback cPosCallback, GetTileCallback getTileCallback) {
        super.initialize(dthCallback, msgCallback, cPosCallback);
        this.getTileCallback = getTileCallback;
    }

    @Override
    public void playTurn(Tile targetTile) {
        interact(targetTile);  // Visitor pattern handles interaction
    }


    @Override
    public void playTurn() {
        if (player == null) return;
        takeTurn(player.getPosition());
    }

    @Override
    public void onTick() {
        // For now, basic enemies might have no per-tick logic
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

    private void moveUp() {

        interact(getTileCallback.getTile(getPosition().up()));
    }

    private void moveDown() {
        interact(getTileCallback.getTile(getPosition().down()));
    }

    private void moveLeft() {
        interact(getTileCallback.getTile(getPosition().left()));
    }

    private void moveRight() {
        interact(getTileCallback.getTile(getPosition().right()));
    }

    private void stay() {
        // Do nothing
    }

    @Override
    public String toString() {
        return "M";  // Override per enemy type, e.g., 's' for Soldier, 'k' for Knight
    }
}
