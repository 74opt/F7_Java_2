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

    public static Map getCurrentMap() {return currentMap;}
    public static void setCurrentMap(Map currentMap) {MapMenu.currentMap = currentMap;}

    private static Thread keyboardListen = new Thread(() -> {
        boolean running = true;

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
                                running = false;
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
    });

    public static void menu() throws Exception {
        Lanterna.clear();
        keyboardListen.start();

        Lanterna.println(
            "^CGMapping v75.221^G\nCoordinates: ^R#%&V;}!%@( ERROR ;}=@&!&(/?{\n" + currentMap.toString() +
            """
            ^G1) Fight
            2) Access Inventory
            3) Save
            4) Exit Game
            """
        );
    }

    private static void fight() throws Exception {
        CombatMenu.start();
    }

    private static void inventory() throws Exception {
        PlayerMenu.menu();
    }

    // TODO: Modify for Lanterna
    private static void save() throws Exception {
        File save = new File(Utils.SAVE_PATH);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(save, Players.player);

        System.out.println("Saved.");
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    // TODO: Modify for Lanterna
    private static void exit() throws Exception {
        System.out.println("Are you sure you want to exit? Make sure you save your game! (Y/N)");

        String choice = Utils.input(false);

        switch (choice) {
            case "y":
                MainMenu.menu();
                break;
            case "n":
                menu();
                break;
            default:
                Utils.invalidOption();
                menu();
                break;
        }
    }
}


