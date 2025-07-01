package board;

import entities.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest
{

    private GameBoard gameBoard;
    private Tile[][] board;

    @BeforeEach
    void setUp()
    {
        board = new Tile[3][3];
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 3; x++)
            {
                board[y][x] = new EmptyTile(new Position(x, y));
            }
        }
        gameBoard = new GameBoard(board);
    }

    /*
     * summary: verifies that getTile(Position) correctly retrieves the tile at a specific position
     * expected: the returned tile is not null and matches the requested position
     */
    @Test
    void testGetTile()
    {
        Position pos = new Position(1, 1);
        Tile tile = gameBoard.getTile(pos);

        assertNotNull(tile);
        assertEquals(pos.getX(), tile.getPosition().getX());
        assertEquals(pos.getY(), tile.getPosition().getY());
    }

    /*
     * summary: verifies that switchPosition correctly swaps two tiles on the board
     * expected: tile at pos1 is now at pos2 and vice versa
     */
    @Test
    void testSwitchPosition()
    {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 1);

        Tile tile1 = gameBoard.getTile(pos1);
        Tile tile2 = gameBoard.getTile(pos2);

        gameBoard.switchPosition(pos1, pos2);

        assertSame(tile1, gameBoard.getTile(pos2));
        assertSame(tile2, gameBoard.getTile(pos1));
    }

    /*
     * summary: checks that the board's toString method includes all tile representations
     * expected: each tile's string representation appears in the board's string output
     */
    @Test
    void testToStringContainsAllTiles()
    {
        String str = gameBoard.toString();
        assertNotNull(str);

        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 3; x++)
            {
                assertTrue(str.contains(board[y][x].toString()));
            }
        }
    }
}
