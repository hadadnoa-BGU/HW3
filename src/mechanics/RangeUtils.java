package mechanics;

import util.Position;

public class RangeUtils {

    /**
     * Calculates Euclidean distance between two positions.
     */
    public static double euclideanDistance(Position a, Position b) {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates Manhattan distance between two positions (if needed).
     */
    public static int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    /**
     * Checks if two positions are within a specified range (Euclidean).
     */
    public static boolean withinRange(Position a, Position b, double range) {
        return euclideanDistance(a, b) <= range;
    }
}
