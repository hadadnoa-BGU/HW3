package mechanics;

import board.*;
import entities.Mage;
import entities.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class MovementHandlerTest {

    private GameBoard gameBoard;
    private MovementHandler movementHandler;
    private Unit unit;

    @BeforeEach
    void setUp() {
        // Create a 3x3 empty board
        Tile[][] tiles = new Tile[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                tiles[y][x] = new EmptyTile(new Position(x, y));
            }
        }
        gameBoard = new GameBoard(tiles);
        movementHandler = new MovementHandler(gameBoard);

        // Place Mage at center
        unit = new Mage("Merlin", 100, 20, 10, 100, 15, 10, 3, 2, new Position(1, 1));
        gameBoard.setTile(1, 1, unit);
    }

    /**
     * summary: verifies that moving up updates the unit's position and board tile.
     */
    @Test
    void testMoveUp() {
        movementHandler.moveUp(unit);
        Position newPos = unit.getPosition();
        assertEquals(1, newPos.getX());
        assertEquals(0, newPos.getY());
        assertSame(unit, gameBoard.getTile(1, 0));
    }

    /**
     * summary: verifies that moving down updates the unit's position and board tile.
     */
    @Test
    void testMoveDown() {
        movementHandler.moveDown(unit);
        Position newPos = unit.getPosition();
        assertEquals(1, newPos.getX());
        assertEquals(2, newPos.getY());
        assertSame(unit, gameBoard.getTile(1, 2));
    }

    /**
     * summary: verifies that moving left updates the unit's position and board tile.
     */
    @Test
    void testMoveLeft() {
        movementHandler.moveLeft(unit);
        Position newPos = unit.getPosition();
        assertEquals(0, newPos.getX());
        assertEquals(1, newPos.getY());
        assertSame(unit, gameBoard.getTile(0, 1));
    }

    /**
     * summary: verifies that moving right updates the unit's position and board tile.
     */
    @Test
    void testMoveRight() {
        movementHandler.moveRight(unit);
        Position newPos = unit.getPosition();
        assertEquals(2, newPos.getX());
        assertEquals(1, newPos.getY());
        assertSame(unit, gameBoard.getTile(2, 1));
    }

    /**
     * summary: verifies that out-of-bounds move does not crash and does not update position.
     */
    @Test
    void testInvalidMove() {
        unit.setPosition(new Position(0, 0));
        gameBoard.setTile(0, 0, unit);

        movementHandler.moveUp(unit); // Out of bounds

        Position pos = unit.getPosition();
        assertEquals(0, pos.getX());
        assertEquals(0, pos.getY());  // Position should remain unchanged
        assertSame(unit, gameBoard.getTile(0, 0));
    }
}
