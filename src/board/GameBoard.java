package board;

import entities.Unit;

public class GameBoard {

    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];  // [row][col]
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }
        return tiles[y][x];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[y][x] = tile;
    }

    public void swapTiles(Tile a, Tile b) {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();

        tiles[ay][ax] = b;
        tiles[by][bx] = a;

        a.setPosition(bx, by);
        b.setPosition(ax, ay);
    }

    public void displayBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(tiles[y][x].toString());
            }
            System.out.println();
        }
    }
}
