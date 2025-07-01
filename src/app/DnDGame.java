package model.game;

import utils.callbacks.*;
import model.tiles.units.Enemies.Enemy;
import model.tiles.units.players.Player;
import model.tiles.units.Unit;
import utils.Position;
import model.tiles.Tile;
import utils.generators.Generator;
import utils.generators.RandomGenerator;
import view.CLI;
import view.View;

import java.util.List;

public class DnDGame 
{
    private String levelsPath;
    private final String LEVELS_BASE_FILE_NAME = "level";
    private final int END_LEVEL = 4;
    private Player player;
    private List<Enemy> enemies;
    private MessageCallbacks msgCallback;
    private DeathCallback dthCallback;
    private ChangedPositionCallback cPositionCallback;
    private GetTileCallback getTileCallback;
    private Level level;
    private View view;
    private Generator generator;
    private CLI cli;

    public Game(String path) 
    {
        this.path = path;
        this.generator = new RandomGenerator();
    }

    public void start() 
    {
        initGame();
        while (level.hasNextLevel() && player.alive())
        {
            level.loadNextLevel();
            playLevel();
        }
        if (!player.alive())
        {
            msgCallback.send("Player died! Game Over.");
        }
        else
        {
            msgCallback.send("Player Won!");
        }
    }

    private void playLevel() 
    {
        enemies = level.getBoard().getEnemies();
        while (!enemies.isEmpty() && player.alive())
        {
            msgCallback.send(level.getBoard().toString());
            playTurn(view.read());
        }
    }

    private void initGame() 
    {
        cli = CLI.getInstance();
        view = cli.getView();
        msgCallback = cli.getMessageCallback();
        dthCallback = (e) -> enemyDied(e);
        cPositionCallback = (u, t) -> unitChangedPosition(u, t);
        getTileCallback = (p) -> getTile(p);
        player = cli.getPlayer();
        level = new Level(path, BASE_FILE_NAME, END_LEVEL, player, msgCallback, dthCallback, cPositionCallback, getTileCallback);
        player.initialize(null, generator, dthCallback, msgCallback, cPositionCallback);
    }

    private void enemyDied(Enemy e) 
    {
        level.getBoard().remove(e);
        enemies.remove(e);
        player.addExperience(e.experienceValue());
    }

    private List<Enemy> getEnemies()
    {
        return enemies;
    }

    private Tile getTile(Position p) 
    {
        return level.getTile(p);
    }

    private void unitChangedPosition(Unit u, Position to) 
    {
        level.getBoard().switchPosition(u.getPosition(), to);
    }

    private void playTurn(String input) 
    {
        Position p = player.getPosition();
        switch (input) {
            case "a":
                Position moveLeft = new Position(p.getX() - 1, p.getY());
                player.playTurn(level.getTile(moveLeft));
                break;
            case "s":
                Position moveDown = new Position(p.getX(), p.getY() + 1);
                player.playTurn(level.getTile(moveDown));
                break;
            case "d":
                Position moveRight = new Position(p.getX() + 1, p.getY());
                player.playTurn(level.getTile(moveRight));
                break;
            case "w":
                Position moveUp = new Position(p.getX(), p.getY() - 1);
                player.playTurn(level.getTile(moveUp));
                break;
            case "e":
                player.castAbility(enemies);
                break;
            case "q":
                msgCallback.send("Chose to do nothing.");
                player.playTurn(level.getTile(p));
                break;
            default:
                msgCallback.send("Invalid input!");
                return;
        }
        for (Enemy e : enemies) 
        {
            e.playTurn();
        }
        player.onTick();
    }
}
