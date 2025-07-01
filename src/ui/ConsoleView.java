package ui;

import callbackMessages.MessageCallbacks;
import entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;
    private final List<Player> allPlayers;

    public ConsoleView() {
        scanner = new Scanner(System.in);
        allPlayers = createPlayerList();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void display(String message) {
        System.out.println(message);
    }

    public MessageCallbacks getMessageCallback() {
        return this::display;
    }

    public Player getPlayer() {
        int choice = -1;
        while (choice < 1 || choice > allPlayers.size()) {
            printPlayers();
            System.out.print("Select your player (1 - " + allPlayers.size() + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid input, enter a number.");
            }
        }
        return allPlayers.get(choice - 1);
    }

    private void printPlayers() {
        for (int i = 0; i < allPlayers.size(); i++) {
            Player p = allPlayers.get(i);
            System.out.println((i + 1) + ". " + p.description());
        }
    }

    private List<Player> createPlayerList() {
        List<Player> players = new ArrayList<>();
        // Example placeholder players, adjust based on your Player constructors
        players.add(new entities.Warrior("Warrior", 100, 20, 10, 3, null));
        players.add(new entities.Mage("Mage", 80, 15, 5, 100, 20, 15, 5, 6, null));
        players.add(new entities.Rogue("Rogue", 90, 18, 8, 15, null));

        return players;
    }
}
