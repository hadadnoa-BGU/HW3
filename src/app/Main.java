package control;

import model.game.Game;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a folder path.");
            return;
        }

        if (args.length > 1) {
            System.out.println("Please enter only one folder path.");
            return;
        }

        String path = args[0];
        File directory = new File(path);

        if (!directory.exists()) {
            System.out.println("The specified folder path does not exist: " + path);
            return;
        }

        if (!directory.isDirectory()) {
            System.out.println("The specified path is not a directory: " + path);
            return;
        }

        Game game = new Game(path);
        System.out.println("Starting the game with level files from: " + path);
        game.playGame();
    }
}
