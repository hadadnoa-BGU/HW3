package board;

import entities.Unit;
import utils.Position;

public class GameBoard {

    private Tile[][] board;

    public GameBoard(Tile[][] board) {
        this.board = board;
    }

    public void switchPosition(Position from, Position to) {
        Tile temp = board[to.getY()][to.getX()];
        board[to.getY()][to.getX()] = board[from.getY()][from.getX()];
        board[from.getY()][from.getX()] = temp;
    }

    public void remove(Unit u) {
        Position pos = u.getPosition();
        board[pos.getY()][pos.getX()] = new EmptyTile(pos);
    }

    public Tile getTile(Position p) {
        return board[p.getY()][p.getX()];
    }

    public void setTile(int x, int y, Tile tile) {
        board[y][x] = tile;
    }

    public Tile getTile(int x, int y) {
        return board[y][x];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tile[] row : board) {
            for (Tile t : row) {
                sb.append(t.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
