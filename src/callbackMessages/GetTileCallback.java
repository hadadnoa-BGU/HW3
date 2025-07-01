package callbackMessages;

import board.Tile;
import utils.Position;

@FunctionalInterface
public interface GetTileCallback {
    Tile getTile(Position position);
}
