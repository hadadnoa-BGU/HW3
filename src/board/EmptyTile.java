package board;

import entities.Unit;

public class EmptyTile extends Tile {

    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);  // Visitor pattern lets unit move onto this tile
    }

    @Override
    public String toString() {
        return ".";  // Symbol for empty space on the board
    }
}
