package ui;

import callbackMessages.MessageCallbacks;
import entities.*;
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
            String name = String.format("%-20s", p.getName());
            String commonStats = String.format(
                    "Health: %d/%d\tAttack: %d\tDefense: %d\tLevel: %d\tExperience: %d/%d",
                    p.getCurrentHealth(), p.getHealthPool(), p.getAttackPoints(), p.getDefensePoints(),
                    p.getLevel(), p.getExperience(), p.getExperienceThreshold());

            String extraStats = "";

            if (p instanceof Warrior w) {
                extraStats = String.format("\tCooldown: %d/%d", w.getAbilityCooldown(), w.getMaxCooldown());
            } else if (p instanceof Mage m) {
                extraStats = String.format("\tMana: %d/%d\tSpell Power: %d", m.getCurrentMana(), m.getManaPool(), m.getSpellPower());
            } else if (p instanceof Rogue r) {
                extraStats = String.format("\tEnergy: %d/%d", r.getCurrentEnergy(), r.getMaxEnergy());
            } else if (p instanceof Hunter h) {
                extraStats = String.format("\tArrows: %d\tRange: %d", h.getArrows(), h.getRange());
            }

            System.out.println((i + 1) + ". " + name + commonStats + extraStats);
        }
    }

    private List<Player> createPlayerList() {
        List<Player> players = new ArrayList<>();



        // Warriors
        players.add(new Warrior("Jon Snow", 300, 30, 4, 3, null));
        players.add(new Warrior("The Hound", 400, 20, 6, 5, null));

        // Mages
        players.add(new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, null));
        players.add(new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4, null));

        // Rogues
        players.add(new Rogue("Arya Stark", 150, 40, 2, 20, null));
        players.add(new Rogue("Bronn", 250, 35, 3, 50, null));

        // Hunters (once we implement)
        players.add(new Hunter("Ygritte", 220, 30, 2, 10, 6, null));

        return players;
    }
}
