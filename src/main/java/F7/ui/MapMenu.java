package F7.ui;

import F7.entities.classes.*;
import F7.Utils;
import F7.entities.construction.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import F7.Lanterna;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.*;

public class MapMenu {
    private static Map currentMap;
    private static boolean running;

    public static Map getCurrentMap() {return currentMap;}
    public static void setCurrentMap(Map currentMap) {MapMenu.currentMap = currentMap;}

    public static void menu() throws Exception {
        Lanterna.clear();

        running = true;

        Lanterna.print(1, 1,
            "^CGMapping v75.221^G\nCoordinates: ^R#%&V;}!%@( ERROR ;}=@&!&(/?{\n" + currentMap.toString() +
            """
            
            ^G1) Fight
            2) Access Inventory
            3) Save
            4) Exit Game
            """
        );

        new Thread(() -> {
            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case '1' -> {
                                    running = false;
                                    fight();
                                }
                                case '2' -> {
                                    running = false;
                                    inventory();
                                }
                                case '3' -> {
                                    save();
                                }
                                case '4' -> {
                                    exit();
                                }
                                case 'w' -> {
                                    currentMap.movePlayer("up", 1);
                                    Lanterna.print(1, 3, currentMap.toString());
                                    Lanterna.getScreen().refresh();
                                }
                                case 'a' -> {
                                    currentMap.movePlayer("left", 1);
                                    Lanterna.print(1, 3, currentMap.toString());
                                    Lanterna.getScreen().refresh();
                                }
                                case 's' -> {
                                    currentMap.movePlayer("down", 1);
                                    Lanterna.print(1, 3, currentMap.toString());
                                    Lanterna.getScreen().refresh();
                                }
                                case 'd' -> {
                                    currentMap.movePlayer("right", 1);
                                    Lanterna.print(1, 3, currentMap.toString());
                                    Lanterna.getScreen().refresh();
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

    private static void fight() throws Exception {
        CombatMenu2.start();
    }

    private static void inventory() throws Exception {
        PlayerMenu.menu();
    }

    private static void save() throws Exception {
        File playerSave = new File(Utils.getPLAYER_SAVE_PATH());
        File mapSave = new File(Utils.getMAP_SAVE_PATH());
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(playerSave, Players.getPlayer());
        objectMapper.writeValue(mapSave, currentMap);

        new Thread(() -> {
            try {

                Lanterna.print(65, 1,
                        """
                        ^W╔═════════════╗
                        ║ ^GSaved Game.^W ║
                        ╚═════════════╝""");
                Thread.sleep(Utils.getSTANDARD());

                for (int i = 1; i < 4; i++) {
                    Lanterna.print(65, i, "               ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // TODO: Modify for Lanterna
    private static void exit() throws Exception {
        Lanterna.print(14, 28, "^GAre you sure you want to quit? All unsaved progress will be lost. (^gQ^G to confirm, ^RE^G to cancel)");

        // Blocking, will not let player do anything until Q or E is pressed, taken from PlayerMenu
        while (true) {
            KeyStroke choice = Lanterna.getScreen().readInput();

            if (choice.getCharacter() == 'q') {
                running = false;
                MainMenu.menu();
                break;
            } else if (choice.getCharacter() == 'e') {
                Lanterna.clear(28);
                Lanterna.print(1, 28, "^G4) Exit Game");
                break;
            }
        }
    }
}


