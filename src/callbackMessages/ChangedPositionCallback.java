package callbackMessages;

import entities.Unit;
import utils.Position;

@FunctionalInterface
public interface ChangedPositionCallback {
    void onPositionChanged(Position from, Position to);
}
