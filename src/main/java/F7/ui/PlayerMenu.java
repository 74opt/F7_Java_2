package F7.ui;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class PlayerMenu {
    public static void menu() throws Exception {
        Lanterna.clear();

        // Stats View
        Lanterna.printfln("""
            ^CF7 Chassis - Model 891Z
            Created by RRS Industries
            ^WVersion: ^RIllegal. Software may be pirated or tampered with.
            
            ^WSpecified ID: ^G%s
            ^WLevel: ^G%s
            %s
            %s
            
            ^WWeapon Equipped: %s
            
            ^WWeapons:""",
            Players.player.getName(),
            Players.player.getLevel(),
            Utils.outOf("^WExperience Points:", Players.player.expRequired(), Players.player.getExp(), "^g"),
            Utils.outOf("^WHealth:", Players.player.getHealth(), Players.player.getTempHealth(), "^R"),
            Players.player.weaponEquipped().toString(false)
        );

        for (int i = 0; i < 4; i++) {
            if (i == Players.player.getEquippedIndex()) {
                // TODO: DISPLAY EQUIPPED WEAPON USING simple = false
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
            
            ^GW) Scroll up weapon list
            S) Scroll down weapon list
            D) Delete selected weapon
            1) Heal
            2) Exit"""
        );

        new Thread(() -> {
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
                                }

                                case '2' -> {
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

                                    for (int i = 10; i < 17; i++) {
                                        Lanterna.clear(i);
                                    }

                                    Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.player.weaponEquipped().toString(false));

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.player.getEquippedIndex()) {
                                            Lanterna.print(1, 18 + i, "^g> " + (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.print(1, 18 + i, (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }
                                case 's' -> { // ++
                                    if (Players.player.getEquippedIndex() == 3) {
                                        Players.player.setEquippedIndex(0);
                                    } else {
                                        Players.player.setEquippedIndex(Players.player.getEquippedIndex() + 1);
                                    }

                                    for (int i = 10; i < 17; i++) {
                                        Lanterna.clear(i);
                                    }
                                    
                                    Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.player.weaponEquipped().toString(false));

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.player.getEquippedIndex()) {
                                            Lanterna.print(1, 18 + i, "^g> " + (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.print(1, 18 + i, (Players.player.getWeapons()[i] == null ? "^GNo Weapon" : Players.player.getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }

                                // To delete weapon
                                // TODO: woah can i get rid of the numbers entirely on this one
                                case 'd' -> {
                                    if (!Players.player.weaponEquipped().equals(Weapons.getFists())) {

                                        int row = 18 + Players.player.getEquippedIndex();

                                        Lanterna.print(1, row, "^g> " + Players.player.weaponEquipped().toString(true) + " ^GAre you sure you want to delete this weapon? You can't get it back! (^gQ^G to confirm, ^RE^G to cancel)");

                                        // This is blocking, no other action can be done until Q or E is pressed
                                        while (true) {
                                            KeyStroke choice = Lanterna.getScreen().readInput();

                                            if (choice.getCharacter() == 'q') {
                                                Players.player.setWeapon(null, Players.player.getEquippedIndex());
                                                Lanterna.clear(row);
                                                Lanterna.clear(10);
                                                Lanterna.print(1, row, "^g> " + "^GNo Weapon");
                                                Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.player.weaponEquipped().toString(false));
                                                break;
                                            } else if (choice.getCharacter() == 'e') {
                                                Lanterna.clear(row);
                                                Lanterna.print(1, row, "^g> " + Players.player.weaponEquipped().toString(true));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: CONVERT TO LANTERNA
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

            Lanterna.printf("\n^R%s^G health restored.", "" + Utils.round(restoration, 2));
        } else {
            Lanterna.printf("\n^GYou don't have %s available.", Consumables.medkit.toString());
        }
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }
}
