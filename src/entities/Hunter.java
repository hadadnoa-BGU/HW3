package entities;

import board.EmptyTile;
import board.Tile;
import utils.Position;

public class Hunter extends Player {

    private final int range;
    private int arrows;

    public Hunter(String name, int healthPool, int attackPoints, int defensePoints, int range, int arrows, Position position) {
        super(name, healthPool, attackPoints, defensePoints, position);
        this.range = range;
        this.arrows = arrows;
    }

    public void playTurn(Tile targetTile, Tile[][] board, int dx, int dy) {
        if (arrows <= 0) {
            super.playTurn(targetTile); // No arrows, normal behavior
            return;
        }

        Position pos = getPosition();

        for (int i = 1; i <= range; i++) {
            int nx = pos.getX() + dx * i;
            int ny = pos.getY() + dy * i;

            if (ny < 0 || ny >= board.length || nx < 0 || nx >= board[0].length) break;

            Tile t = board[ny][nx];

            if (t instanceof Enemy enemy) {
                System.out.println(getName() + " shoots an arrow at " + enemy.getName());
                engageCombat(enemy);
                arrows--;

                if (!enemy.isAlive() && dthCallback != null)
                    dthCallback.onDeath(enemy);
                return;
            } else if (!(t instanceof EmptyTile)) {
                break;
            }
        }

        // No enemy in line, move normally
        super.playTurn(targetTile);
    }

    @Override
    public void playTurn() {
        // Hunters don't act autonomously
    }

    @Override
    public String description() {
        return super.description() + String.format("\tArrows: %d\tRange: %d", arrows, range);
    }

    public int getRange() { return range; }
    public int getArrows() { return arrows; }
}
