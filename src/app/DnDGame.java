package app;

import board.GameBoard;
import board.Level;
import board.Tile;
import callbackMessages.*;
import entities.Enemy;
import entities.Hunter;
import entities.Mage;
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
            msgCallback.send(player.description());
            String input = console.readLine();
            playTurn(input);
        }

        if (player.isAlive()) {
            msgCallback.send("Level Cleared!");
        }
    }


    private void playTurn(String input) {
        Position p = player.getPosition();
        Tile[][] board = level.getBoard().getTiles();  // Get full board for ranged logic

        switch (input) {
            case "a" -> {
                if (player instanceof Mage m) {
                    m.playTurn(level.getTile(p.left()), board, -1, 0);
                } else if (player instanceof Hunter h) {
                    h.playTurn(level.getTile(p.left()), board, -1, 0);
                } else {
                    player.playTurn(level.getTile(p.left()));
                }
            }
            case "d" -> {
                if (player instanceof Mage m) {
                    m.playTurn(level.getTile(p.right()), board, 1, 0);
                } else if (player instanceof Hunter h) {
                    h.playTurn(level.getTile(p.right()), board, 1, 0);
                } else {
                    player.playTurn(level.getTile(p.right()));
                }
            }
            case "w" -> {
                if (player instanceof Mage m) {
                    m.playTurn(level.getTile(p.up()), board, 0, -1);
                } else if (player instanceof Hunter h) {
                    h.playTurn(level.getTile(p.up()), board, 0, -1);
                } else {
                    player.playTurn(level.getTile(p.up()));
                }
            }
            case "s" -> {
                if (player instanceof Mage m) {
                    m.playTurn(level.getTile(p.down()), board, 0, 1);
                } else if (player instanceof Hunter h) {
                    h.playTurn(level.getTile(p.down()), board, 0, 1);
                } else {
                    player.playTurn(level.getTile(p.down()));
                }
            }
            case "e" -> player.castAbility(enemies);
            case "q" -> {
                msgCallback.send("Chose to do nothing.");
                player.playTurn(level.getTile(p));
            }
            default -> msgCallback.send("Invalid input!");
        }

        // Early exit if player cleared the level
        if (enemies.isEmpty()) {
            msgCallback.send("Level Cleared!");
            return;
        }

        // Enemies take their turn
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
