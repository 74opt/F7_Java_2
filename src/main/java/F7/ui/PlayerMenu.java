package F7.ui;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import com.diogonunes.jcolor.*; //https://github.com/dialex/JColor
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.Arrays;

public class PlayerMenu { //TODO: make menu code look nicer with """""" and String.format if have time
    private static Thread keyboardListen = new Thread(() -> {
        boolean running = true;

        while (running) {
            try {
                KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                if (keyPressed != null) {
                    try {
                        switch (keyPressed.getCharacter()) {
                            // TODO: running = false should be at the front if necessary
                            case '1' -> {
                                heal();
                                // Do i need running = false?
                                running = false;
                            }
                            case '2' -> {
                                equip();
                                running = false;
                            }
                            case '3' -> {
                                discard();
                                running = false;
                            }
                            case '4' -> {
                                running = false;
                                MapMenu.menu();
                            }

                            // go from 0-3
                            case 'w' -> { // --
                                if (Players.player.getEquippedIndex() == 0) {
                                    Players.player.setEquippedIndex(3);
                                } else {
                                    Players.player.setEquippedIndex(Players.player.getEquippedIndex() - 1);
                                }

                                // Starts at 10
                                Lanterna.print(18, 10, Players.player.weaponEquipped().toString(true)  + "                                                                       ");

                                for (int i = 0; i < 4; i++) {
                                    if (i == Players.player.getEquippedIndex()) {
                                        Lanterna.print(1, 11 + i, "^g> " + (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)));
                                    } else {
                                        Lanterna.print(1, 11 + i, (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)) + "  ");
                                    }
                                }
                            }
                            case 's' -> { // ++
                                if (Players.player.getEquippedIndex() == 3) {
                                    Players.player.setEquippedIndex(0);
                                } else {
                                    Players.player.setEquippedIndex(Players.player.getEquippedIndex() + 1);
                                }

                                Lanterna.print(18, 10, Players.player.weaponEquipped().toString(true) + "                                                                       ");

                                for (int i = 0; i < 4; i++) {
                                    if (i == Players.player.getEquippedIndex()) {
                                        Lanterna.print(1, 11 + i, "^g> " + (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)));
                                    } else {
                                        Lanterna.print(1, 11 + i, (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)) + "  ");
                                    }
                                }
                            }

                            // To delete weapon
                            // TODO: woah can i get rid of the numbers entirely on this one
                            case 'd' -> {

                            }
                        }
                    } catch (Exception ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    public static void menu() throws Exception {
        Lanterna.clear();
        keyboardListen.start();

        // Stats View
        Lanterna.println(
            String.format("""
            ^CF7 Chassis - Model 891Z
            Created by RRS Industries
            ^WVersion: ^RIllegal. Software may be pirated or tampered with.
            
            ^WSpecified ID: ^G%s
            ^WLevel: ^G%s
            %s
            %s
            
            ^WWeapon Equipped: %s""",
            Players.player.getName(),
            Players.player.getLevel(),
            Utils.outOf("^WExperience Points:", Players.player.expRequired(), Players.player.getExp(), "^g"),
            Utils.outOf("^WHealth:", Players.player.getHealth(), Players.player.getTempHealth(), "^R"),
            Players.player.weaponEquipped().toString(true))
        );

        for (int i = 0; i < 4; i++) {
            if (i == Players.player.getEquippedIndex()) {
                Lanterna.println("^g> " + (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)));
            } else {
                Lanterna.println((Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)) + "  ");
            }
        }

        Lanterna.println("\n^WShield:\n" + Players.player.getShield().toString(false));
        Lanterna.println("\n^WConsumables:\n" + Players.player.displayConsumables());

        // Commands
        Lanterna.println(
            """
            
            ^G1) Heal
            2) Equip New Weapon
            3) Discard Weapon
            4) Exit"""
        );
    }

    private static void heal() throws Exception {
        if (Players.player.getConsumables().contains(Consumables.medkit)) {
            double restoration = Utils.randomRange(15, 21) / 100.0;
            restoration *= Players.player.getHealth();
            double overheal;
            
            Players.player.getConsumables().remove(Consumables.medkit);
            Players.player.setTempHealth(Players.player.getTempHealth() + restoration);

            if (Players.player.getTempHealth() > Players.player.getHealth()) {
                overheal = Players.player.getTempHealth() - Players.player.getHealth();

                Players.player.setTempHealth(Players.player.getHealth());

                restoration -= overheal;
            }

            System.out.printf("\n%s health restored.", Ansi.colorize("" + Utils.round(restoration, 2), Attribute.TEXT_COLOR(9)));
        } else {
            System.out.printf("\nYou don't have %s available.", Consumables.medkit.toString());
        }
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    private static void equip() throws Exception {
        int index = 0;
        String[] possibilies = new String[4];

        Utils.clear();
        System.out.println("Select a weapon to equip or type \"exit\" to exit:\n");

        for (int i = 0; i < 4; i++) {
            if (Players.player.getWeapons()[i] != null) {
                System.out.print(i + 1 + ") ");
                System.out.println(Players.player.getWeapons()[i].toString(false));
                System.out.println();
                possibilies[i] = "" + (i + 1);
            }
        }

        String choice = Utils.input(false);

        if (choice.equals("exit")) {
            menu();
        }

        switch (choice) {
            case "1", "2", "3", "4":
                if (Arrays.asList(possibilies).contains(choice)) {
                    index = Integer.parseInt(choice);
                } else {
                    Utils.invalidOption();
                    equip();
                }
                break;
            default:
                Utils.invalidOption();
                equip();
                break;
        }

        Players.player.setEquippedIndex(index - 1);
        System.out.printf("%s has been equipped.", Players.player.getWeapons()[index - 1].getNAME());
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    private static void discard() throws Exception {
        int index = 0;
        String[] possibilies = new String[4];

        Utils.clear();
        System.out.println("Select a weapon to discard or type \"exit\" to exit:\n");

        for (int i = 0; i < 4; i++) {
            if (Players.player.getWeapons()[i] != null) {
                System.out.print(i + 1 + ") ");
                System.out.println(Players.player.getWeapons()[i].toString(false));
                System.out.println();
                possibilies[i] = "" + (i + 1);
            }
        }

        String choice = Utils.input(false);

        if (choice.equals("exit")) {
            menu();
        }

        switch (choice) {
            case "1", "2", "3", "4":
                if (Arrays.asList(possibilies).contains(choice)) {
                    index = Integer.parseInt(choice);
                } else {
                    Utils.invalidOption();
                    discard();
                }
                break;
            default:
                Utils.invalidOption();
                discard();
                break;
        }

        System.out.printf("%s has been discarded.", Players.player.getWeapons()[index - 1].getNAME());
        Players.player.setWeapon(null, index - 1);
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    // inaccessable by player, used to equip, inspect, and discard
    // private static String displayWeapons() {
        
    // }
}
