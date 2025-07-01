package control.initializers;

import model.game.Board;
import utils.callbacks.ChangedPositionCallback;
import utils.callbacks.DeathCallback;
import utils.callbacks.GetTileCallback;
import utils.callbacks.MessageCallbacks;
import model.game.Level;
import model.tiles.units.Enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import model.tiles.Tile;
import control.initializers.TileFactory;
import utils.generators.RandomGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class LevelInitializer {
    protected final String PATH;
    protected final String BASE_FILE_NAME = "level";
    protected TileFactory tileFactory;

    public LevelInit(String path) {
        tileFactory = new TileFactory();
        PATH = path;
        
    }

    public void loadNextLevel(Level level) {
        TreeMap<Position, Tile> tiles = new TreeMap<>();
        LinkedList<Enemy> enemies = new LinkedList<>();
        int width = 0;
        level.incrementLevelOrdinal();
        Path filePath = Path.of(PATH + "/" + BASE_FILE_NAME + level.getLevelOrdinal() + ".txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                width = line.length();
                for (int x = 0; x < width; x++) {
                    char c = line.charAt(x);
                    tileInit(x, y, c, tiles, enemies, level);
                }
            }
            level.setBoard(new Board(tiles, level.getPlayer(), enemies, width));
            for (Enemy e : enemies) {
                e.setPlayer(level.getPlayer());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void tileInit(int x, int y, char c, TreeMap<Position, Tile> tiles, LinkedList<Enemy> enemies, Level level) {
        Position position = new Position(x, y);
        switch (c) {
            case '#':
                tiles.put(position, tileFactory.produceWall(position));
                break;
            case '.':
                tiles.put(position, tileFactory.produceEmpty(position));
                break;
            case '@':
                level.getPlayer().setPosition(position);
                tiles.put(position, level.getPlayer());
                break;
            default:
                Enemy e = tileFactory.produceEnemy(c, new RandomGenerator(), position, level.getMsgCallback(), level.getDthCallback(), level.getcPositionCallback(), level.getGetTileCallback());
                enemies.add(e);
                tiles.put(position, e);
                break;
        }
    }
}
