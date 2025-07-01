package callbackMessages;

import entities.Unit;
import utils.Position;

@FunctionalInterface
public interface ChangedPositionCallback {
    void onPositionChanged(Unit unit, Position newPosition);
}
