package utils.callbacks;

import model.tiles.units.Unit;
import utils.Position;

public interface ChangedPositionCallback {
public void call(Unit u, Position p);
}
