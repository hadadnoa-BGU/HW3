package board;

import entities.Unit;
import utils.Position;

public class EmptyTile extends Tile {

    public EmptyTile(Position position) {
        super(position.getX(), position.getY());
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
