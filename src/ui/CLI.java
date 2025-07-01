package view;

import utils.callbacks.MessageCallbacks;
import model.tiles.units.players.Player;
import control.initializers.TileFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLI 
{
    private static volatile CLI instance;
    private final MessageCallbacks msg;
    private final View view;
    private final Scanner scanner;
    private final TileFactory factory;
    private final List<Player> allPlayers;
    private final int numOfPlayers;

    private CLI() {
        this.msg = this::displayMessage;
        this.view = this::readLine;
        this.scanner = new Scanner(System.in);
        this.factory = new TileFactory();
        this.numOfPlayers = factory.getPlayerCount();
        this.allPlayers = new LinkedList<>();
        initializePlayers();
    }

    public static CLI getInstance() 
    {
        if (instance == null) 
        {
            synchronized (CLI.class) 
            {
                if (instance == null) 
                {
                    instance = new CLI();
                }
            }
        }
        return instance;
    }

    private void initializePlayers() 
    {
        for (int i = 1; i <= numOfPlayers; i++) 
        {
            allPlayers.add(factory.producePlayer(i, null, null, null, null, null));
        }
    }

    private void displayMessage(String message) 
    {
        System.out.println(message);
    }

    private String readLine() 
    {
        return scanner.nextLine();
    }

    public MessageCallbacks getMessageCallback() 
    {
        return msg;
    }

    public View getView() 
    {
        return view;
    }

    public Player getPlayer() 
    {
        int playerIndex = -1;
        while (playerIndex == -1) 
        {
            printPlayers();
            try {
                int temp = Integer.parseInt(readLine());
                if (temp > 0 && temp <= numOfPlayers) 
                {
                    playerIndex = temp;
                } 
                else 
                {
                    System.out.println("Invalid input! Please enter a number between 1 and " + numOfPlayers);
                }
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        return factory.producePlayer(playerIndex, null, null, null, null, null);
    }

    private void printPlayers() 
    {
        for (int i = 0; i < numOfPlayers; i++) 
        {
            Player player = allPlayers.get(i);
            System.out.println((i + 1) + ". " + player.describe());
        }
    }
}