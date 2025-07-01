package app;

import board.Level;
import callbackMessages.*;
import entities.HeroicUnit;
import entities.Player;
import ui.ConsoleView;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Missing levels directory argument.");
            return;
        }

        String levelsPath = args[0];
        int maxLevels = 3; // Or dynamically detect number of levels

        ConsoleView view = new ConsoleView();
        MessageCallbacks msgCallback = view.getMessageCallback();

        Player player = view.getPlayer();

        LevelManager levelManager = new LevelManager(levelsPath, maxLevels);
        Level level = new Level(levelsPath, maxLevels, player, msgCallback, null, null, null);

        DeathCallback dthCallback = (enemy) -> {
            msgCallback.send(enemy.getName() + " has died! Game Over.");
            System.exit(0);
        };


        ChangedPositionCallback cPosCallback = (unit, newPosition) -> {
            level.getBoard().switchPosition(unit.getPosition(), newPosition);
        };


        GetTileCallback getTileCallback = pos -> level.getTile(pos);

        while (levelManager.hasNextLevel()) {

            level.loadNextLevel();

            Object[] boardData = levelManager.loadNextLevel(player, msgCallback, dthCallback, cPosCallback);
            level.setLevelData((board.GameBoard) boardData[0], (java.util.List) boardData[1]);

            msgCallback.send("Starting Level " + (level.getBoard() != null ? "Loaded" : ""));

            boolean levelInProgress = true;
            while (levelInProgress) {

                // Print board and player stats
                msgCallback.send(level.getBoard().toString());
                msgCallback.send(player.description());

                String input = view.readLine().toLowerCase();

                switch (input) {
                    case "w" -> player.playTurn(level.getTile(player.getPosition().up()));
                    case "s" -> player.playTurn(level.getTile(player.getPosition().down()));
                    case "a" -> player.playTurn(level.getTile(player.getPosition().left()));
                    case "d" -> player.playTurn(level.getTile(player.getPosition().right()));
                    case "e" -> {
                        if (player instanceof HeroicUnit hero) {
                            hero.castAbility(null);  // You still need proper target logic
                        }
                    }
                    case "q" -> {
                        // Do nothing
                    }
                    default -> msgCallback.send("Invalid input.");
                }

                // Tick enemies
                for (var enemy : level.getEnemies()) {
                    enemy.playTurn();
                    enemy.onTick();
                }

                // Tick player
                player.onTick();

                // Remove dead enemies
                level.getEnemies().removeIf(e -> !e.isAlive());

                if (level.getEnemies().isEmpty()) {
                    msgCallback.send("Level Complete!");
                    levelInProgress = false;
                }
            }
        }

        msgCallback.send("You cleared all levels! You Win!");
    }
}
