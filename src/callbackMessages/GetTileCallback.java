package utils.callbacks;

import model.tiles.Tile;
import utils.Position;

public interface GetTileCallback {
    public Tile send(Position p);
}
