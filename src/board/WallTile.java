package board;

import entities.Unit;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void accept(Unit visitor) {
        visitor.visit(this);  // Follows Visitor logic to block movement
    }

    @Override
    public String toString() {
        return "#";  // Symbol for wall tiles in board display
    }
}
