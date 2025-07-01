package app;

import board.*;
import callbackMessages.*;
import entities.*;
import utils.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class LevelManager {

    private final String levelsPath;
    private final int maxLevels;
    private int currentLevel;

    public LevelManager(String levelsPath, int maxLevels) {
        this.levelsPath = levelsPath;
        this.maxLevels = maxLevels;
        this.currentLevel = 1;
    }

    public boolean hasNextLevel() {
        return currentLevel <= maxLevels;
    }

    public Object[] loadNextLevel(Player player, MessageCallbacks msgCallback, DeathCallback dthCallback, ChangedPositionCallback cPosCallback, GetTileCallback getTileCallback) {
        {

            if (!hasNextLevel()) {
                throw new IllegalStateException("No more levels available.");
            }

            String levelFile = levelsPath + "/level" + currentLevel + ".txt";
            currentLevel++;

            try {
                List<String> lines = Files.readAllLines(Paths.get(levelFile));
                int height = lines.size();
                int width = lines.get(0).length();

                Tile[][] tiles = new Tile[height][width];
                GameBoard board = new GameBoard(tiles);
                List<Enemy> enemies = new LinkedList<>();

                for (int y = 0; y < height; y++) {
                    String line = lines.get(y);
                    for (int x = 0; x < width; x++) {
                        char c = line.charAt(x);
                        Position pos = new Position(x, y);

                        switch (c) {
                            case '#':
                                board.setTile(x, y, new WallTile(pos));
                                break;
                            case '.':
                                board.setTile(x, y, new EmptyTile(pos));
                                break;
                            case '@':
                                player.setPosition(pos);
                                board.setTile(x, y, player);
                                break;
                            case 's':
                                Monster skeleton = new Monster("Skeleton", 50, 10, 5, 20, 3, pos);
                                skeleton.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);

                                board.setTile(x, y, skeleton);
                                enemies.add(skeleton);
                                break;
                            case 'k':
                                Monster king = new Boss("The King", 100, 20, 10, 50, 5, 3, pos);
                                king.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                                board.setTile(x, y, king);
                                enemies.add(king);
                                break;
                            default:
                                board.setTile(x, y, new EmptyTile(pos));
                                break;
                        }
                    }
                }

                return new Object[]{board, enemies};

            } catch (IOException e) {
                throw new RuntimeException("Failed to load level file: " + levelFile, e);
            }
        }
    }
}
