package board;

import callbackMessages.ChangedPositionCallback;
import callbackMessages.DeathCallback;
import callbackMessages.GetTileCallback;
import callbackMessages.MessageCallbacks;
import entities.Enemy;
import entities.Player;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final String levelsPath;
    private final int maxLevels;

    private int currentLevel;

    private GameBoard board;
    private List<Enemy> enemies;
    private final Player player;

    private final MessageCallbacks msgCallback;
    private final DeathCallback dthCallback;
    private final ChangedPositionCallback cPositionCallback;
    private final GetTileCallback getTileCallback;

    public Level(String levelsPath, int maxLevels, Player player,
                 MessageCallbacks msgCallback, DeathCallback dthCallback,
                 ChangedPositionCallback cPositionCallback, GetTileCallback getTileCallback) {

        this.levelsPath = levelsPath;
        this.maxLevels = maxLevels;
        this.currentLevel = 0;

        this.player = player;
        this.msgCallback = msgCallback;
        this.dthCallback = dthCallback;
        this.cPositionCallback = cPositionCallback;
        this.getTileCallback = getTileCallback;
    }

    public boolean hasNextLevel() {
        return currentLevel < maxLevels;
    }

    public void loadNextLevel() {
        if (!hasNextLevel()) {
            msgCallback.send("No more levels to load.");
            return;
        }

        currentLevel++;
        msgCallback.send("Loading level " + currentLevel);

        String levelFilePath = levelsPath + "/level" + currentLevel + ".txt";



        msgCallback.send("Level " + currentLevel + " loaded successfully.");
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public GameBoard getBoard() {
        return board;
    }

    public Tile getTile(Position p) {
        return board.getTile(p.getX(), p.getY());
    }

    public void setLevelData(GameBoard board, List<Enemy> enemies) {
        this.board = board;
        this.enemies = enemies;

        for (Enemy e : enemies) {
            e.setPlayer(player);
            e.initialize(dthCallback, msgCallback, cPositionCallback);

        }
    }
}
