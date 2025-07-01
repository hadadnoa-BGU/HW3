package board;

import entities.Unit;

public abstract class Tile {

    protected int x;
    protected int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void accept(Unit visitor);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public abstract String toString();  // Displays tile symbol
}
