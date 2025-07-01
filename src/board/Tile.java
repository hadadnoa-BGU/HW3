package board;

import entities.Unit;
import utils.Position;

public abstract class Tile {

    protected Position position;

    public Tile(int x, int y) {
        this.position = new Position(x, y);
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    // Visitor pattern: what happens when a Unit steps onto this Tile
    public abstract void accept(Unit visitor);

    @Override
    public abstract String toString();
}
