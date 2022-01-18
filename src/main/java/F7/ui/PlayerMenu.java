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
        Lanterna.printf(1, 1, """
            ^CF7 Chassis - Model 891Z
            Created by RRS Industries
            ^WVersion: ^RIllegal. Software may be pirated or tampered with.
            
            ^WSpecified ID: ^G%s
            ^WLevel: ^G%s
            %s
            %s
            
            ^WWeapon Equipped: %s
            
            ^WWeapons:
            """,
            Players.getPlayer().getName(),
            Players.getPlayer().getLevel(),
            Utils.outOf("^WExperience Points:", Players.getPlayer().expRequired(), Players.getPlayer().getExp(), "^g"),
            Utils.outOf("^WHealth:", Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R"),
            Players.getPlayer().weaponEquipped().toString(false)
        );

        for (int i = 0; i < 4; i++) {
            if (i == Players.getPlayer().getEquippedIndex()) {
                Lanterna.print(1, 19 + i, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
            } else {
                Lanterna.print(1, 19 + i, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
            }
        }

        Lanterna.print(1, 23, "\n^WShield:\n" + Players.getPlayer().getShield().toString(false));
        Lanterna.print(1, 29, "\n^WConsumables:\n" + Players.getPlayer().displayConsumables());

        // Commands
        Lanterna.print(1, 38,
            """
            ^GW) Scroll up weapon list
            S) Scroll down weapon list
            D) Delete selected weapon
            1) Exit"""
        );

        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case '1' -> {
                                    running = false;
                                    MapMenu.menu();
                                }

                                // go from 0-3
                                case 'w' -> { // --
                                    if (Players.getPlayer().getEquippedIndex() == 0) {
                                        Players.getPlayer().setEquippedIndex(3);
                                    } else {
                                        Players.getPlayer().setEquippedIndex(Players.getPlayer().getEquippedIndex() - 1);
                                    }

                                    for (int i = 10; i < 17; i++) {
                                        Lanterna.clear(i);
                                    }

                                    Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.getPlayer().weaponEquipped().toString(false));

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.print(1, 19 + i, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.print(1, 19 + i, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }
                                case 's' -> { // ++
                                    if (Players.getPlayer().getEquippedIndex() == 3) {
                                        Players.getPlayer().setEquippedIndex(0);
                                    } else {
                                        Players.getPlayer().setEquippedIndex(Players.getPlayer().getEquippedIndex() + 1);
                                    }

                                    for (int i = 10; i < 17; i++) {
                                        Lanterna.clear(i);
                                    }
                                    
                                    Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.getPlayer().weaponEquipped().toString(false));

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.print(1, 19 + i, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.print(1, 19 + i, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }

                                // To delete weapon
                                case 'd' -> {
                                    if (!Players.getPlayer().weaponEquipped().equals(Weapons.getFists())) {

                                        int row = 19 + Players.getPlayer().getEquippedIndex();

                                        Lanterna.print(1, row, "^g> " + Players.getPlayer().weaponEquipped().toString(true) + " ^GAre you sure you want to delete this weapon? You can't get it back! (^gQ^G to confirm, ^RE^G to cancel)");

                                        // This is blocking, no other action can be done until Q or E is pressed
                                        while (true) {
                                            KeyStroke choice = Lanterna.getScreen().readInput();

                                            if (choice.getCharacter() == 'q') {
                                                Players.getPlayer().setWeapon(null, Players.getPlayer().getEquippedIndex());
                                                Lanterna.clear(row);
                                                Lanterna.clear(10);
                                                Lanterna.print(1, row, "^g> " + "^GNo Weapon");
                                                Lanterna.print(1, 10, "^WWeapon Equipped: " + Players.getPlayer().weaponEquipped().toString(false));
                                                break;
                                            } else if (choice.getCharacter() == 'e') {
                                                Lanterna.clear(row);
                                                Lanterna.print(1, row, "^g> " + Players.getPlayer().weaponEquipped().toString(true));
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
}
