package mechanics;

import board.GameBoard;
import board.Tile;
import entities.Unit;
import utils.Position;

public class MovementHandler {

    private final GameBoard board;

    public MovementHandler(GameBoard board) {
        this.board = board;
    }

    /**
     * Attempts to move a unit to the target tile's position.
     * Handles all Visitor logic behind the scenes.
     */
    public void move(Unit mover, int targetX, int targetY) {
        Position oldPos = mover.getPosition();
        Tile targetTile = board.getTile(targetX, targetY);

        if (targetTile == null) {
            System.out.println("Invalid move: Out of bounds.");
            return;
        }

        mover.interact(targetTile);  // Visitor pattern resolves interaction
        board.setTile(targetX, targetY, mover);
        board.setTile(oldPos.getX(), oldPos.getY(), targetTile);
    }

    /**
     * Convenience methods for directional movement.
     */
    public void moveUp(Unit mover)
    {
        move(mover, mover.getPosition().getX(), mover.getPosition().getY() - 1);
    }

    public void moveDown(Unit mover) {
        move(mover, mover.getPosition().getX(), mover.getPosition().getY() + 1);
    }

    public void moveLeft(Unit mover) {
        move(mover, mover.getPosition().getX() - 1, mover.getPosition().getY());
    }

    public void moveRight(Unit mover) {
        move(mover, mover.getPosition().getX() + 1, mover.getPosition().getY());
    }
}
