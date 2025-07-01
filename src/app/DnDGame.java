package app;

import board.GameBoard;
import board.Level;
import board.Tile;
import callbackMessages.*;
import entities.Enemy;
import entities.Player;
import ui.ConsoleView;
import utils.Position;
import utils.RandomUtils;

import java.util.List;

public class DnDGame {

    private final String levelsPath;
    private final int MAX_LEVELS = 4;

    private Player player;
    private List<Enemy> enemies;
    private Level level;

    private MessageCallbacks msgCallback;
    private DeathCallback dthCallback;
    private ChangedPositionCallback cPositionCallback;
    private GetTileCallback getTileCallback;
    private LevelManager levelManager;
    private ConsoleView console;

    public DnDGame(String levelsPath) {
        this.levelsPath = levelsPath;
    }

    // In app.DnDGame
    public void start() {
        initGame();

        while (levelManager.hasNextLevel() && player.isAlive()) {

            Object[] boardData = levelManager.loadNextLevel(player, msgCallback, dthCallback, cPositionCallback, getTileCallback);

            level.setLevelData((GameBoard) boardData[0], (List<Enemy>) boardData[1]);

            msgCallback.send("Level loaded successfully.");

            enemies = level.getEnemies();
            if (enemies == null || enemies.isEmpty()) {
                msgCallback.send("Level failed to load properly or no enemies present.");
                return;
            }

            playLevel();
        }

        if (player.isAlive()) {
            msgCallback.send("Player Won!");
        } else {
            msgCallback.send("Player died! Game Over.");
        }
    }




    private void initGame() {
        console = new ConsoleView();
        msgCallback = console::display;
        dthCallback = this::enemyDied;
        cPositionCallback = this::unitChangedPosition;
        getTileCallback = this::getTile;

        player = console.getPlayer();
        player.initialize(RandomUtils::randomInt, dthCallback, msgCallback, cPositionCallback);

        level = new Level(levelsPath, MAX_LEVELS, player, msgCallback, dthCallback, cPositionCallback, getTileCallback);
        levelManager = new LevelManager(levelsPath, MAX_LEVELS);
    }


    private void playLevel() {
        enemies = level.getEnemies();
        while (!enemies.isEmpty() && player.isAlive()) {
            msgCallback.send(level.getBoard().toString());
            String input = console.readLine();
            playTurn(input);
        }
    }

    private void playTurn(String input) {
        Position p = player.getPosition();
        switch (input) {
            case "a" -> player.playTurn(level.getTile(p.left()));
            case "d" -> player.playTurn(level.getTile(p.right()));
            case "w" -> player.playTurn(level.getTile(p.up()));
            case "s" -> player.playTurn(level.getTile(p.down()));
            case "e" -> player.castAbility(enemies);
            case "q" -> {
                msgCallback.send("Chose to do nothing.");
                player.playTurn(level.getTile(p));
            }
            default -> msgCallback.send("Invalid input!");
        }

        for (Enemy e : enemies) {
            e.playTurn();
        }
        player.onTick();
    }

    private Tile getTile(Position p) {
        return level.getTile(p);
    }

    private void unitChangedPosition(Position from, Position to) {
        level.getBoard().switchPosition(from, to);
    }

    private void enemyDied(Enemy e) {
        level.getBoard().remove(e);
        enemies.remove(e);
        player.addExperience(e.getExperienceValue());
    }
}
