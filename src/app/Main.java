package control;

import model.game.Game;
import java.io.File;

public class Main 
{
    private static String gameCommandLine = "DnD.java";
    public static void main(String[] args) 
    {
        if (args.length == 0) 
        {
            System.out.println("Levels folder not specified.\nUsage: " + gameCommandLine + " <level directory path>");
            return;
        }

        if (args.length > 1) 
        {
            System.out.println("More than 1 Folder paths specified.\nUsage: " + gameCommandLine + " <level directory path>");
            return;
        }

        String path = args[0];
        File directory = new File(path);

        if (!directory.exists()) 
        {
            System.out.println("The specified folder \"" + path + "\" does not exist");
            return;
        }

        if (!directory.isDirectory()) 
        {
            System.out.println("The specified folder \"" + path + "\" is not a directory");
            return;
        }
        
        directory = new File(path + "/level1.txt");
        if (!directory.exists()) 
        {
            System.out.println("The specified folder \"" + path + "\" do not contain a \"level1.txt\" file");
            return;
        }
        DnDGame game = new Game(path);
        game.start();
    }
}
