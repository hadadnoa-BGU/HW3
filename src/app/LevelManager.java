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

                        // Monsters from PDF:
                        case 's': { // Lannister Soldier
                            Monster m = new Monster("Lannister Soldier", 80, 8, 3, 25, 3, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'k': { // Lannister Knight
                            Monster m = new Monster("Lannister Knight", 200, 14, 8, 50, 4, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'q': { // Queen's Guard
                            Monster m = new Monster("Queen's Guard", 400, 20, 15, 100, 5, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'z': { // Wight
                            Monster m = new Monster("Wight", 600, 30, 15, 100, 3, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'b': { // Bear-Wright
                            Monster m = new Monster("Bear-Wright", 1000, 75, 30, 250, 4, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'g': { // Giant-Wright
                            Monster m = new Monster("Giant-Wright", 1500, 100, 40, 500, 5, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'w': { // White Walker
                            Monster m = new Monster("White Walker", 2000, 150, 50, 1000, 6, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'M': { // The Mountain
                            Monster m = new Monster("The Mountain", 1000, 60, 25, 500, 6, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'C': { // Queen Cersei
                            Boss m = new Boss("Queen Cersei", 1000, 10, 10, 1000, 1, 1, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }
                        case 'K': { // Night's King
                            Boss m = new Boss("Night's King", 5000, 300, 150, 5000, 8, 8, pos);
                            m.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, m);
                            enemies.add(m);
                            break;
                        }

                        //Traps:
                        case 'B': {
                            BonusTrap t = new BonusTrap(pos);
                            t.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, t);
                            break;
                        }
                        case 'Q': {
                            QueensTrap t = new QueensTrap(pos);
                            t.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, t);
                            break;
                        }
                        case 'D': {
                            DeathTrap t = new DeathTrap(pos);
                            t.initialize(dthCallback, msgCallback, cPosCallback, getTileCallback);
                            board.setTile(x, y, t);
                            break;
                        }



                        default:
                            msgCallback.send("Unknown tile '" + c + "' at (" + x + "," + y + "), defaulting to EmptyTile.");
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
