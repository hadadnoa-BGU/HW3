package app;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Missing levels directory argument.");
            return;
        }

        String levelsPath = args[0];

        DnDGame game = new DnDGame(levelsPath);
        game.start();
    }
}