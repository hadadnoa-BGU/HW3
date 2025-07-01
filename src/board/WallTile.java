package board;

import entities.Unit;
import utils.Position;

public class WallTile extends Tile {

    public WallTile(Position position) {
        super(position.getX(), position.getY());
    }


    @Override
    public void accept(Unit visitor) {
        // Wall blocks movement; nothing happens
    }

    @Override
    public String toString() {
        return "#";
    }
}
