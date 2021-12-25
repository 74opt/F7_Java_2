package F7.ui;

import F7.entities.classes.*;
import F7.Utils;
import F7.entities.construction.*;
import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import F7.Lanterna;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.*;
import java.util.Arrays;

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
                                fight();
                                running = false;
                            }
                            case '2' -> {
                                inventory();
                                running = false;
                            }
                            case '3' -> {
                                save();
                                // don't think i need running = false here
                            }
                            case '4' -> {
                                exit();
                                running = false;
                            }
                            case 'w' -> {
                                currentMap.movePlayer("up", 1);
                                Lanterna.getScreen().refresh();
                            }
                            case 'a' -> {}
                            case 's' -> {}
                            case 'd' -> {
                                currentMap.movePlayer("right", 1);
                                Lanterna.getScreen().refresh();
                                running = false;
                                System.out.println("test");
                                menu();
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
        keyboardListen.start(); // doesnt start the second time

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

    private static void movement() throws Exception {
        System.out.println(
            Ansi.colorize("GMapping v75.221", Attribute.TEXT_COLOR(14)) + "\nCoordinates: " +
            Ansi.colorize("#%&V;}!%@( ERROR ;}=@&!(/?{", Attribute.TEXT_COLOR(1)) + "\n" + currentMap.toString()
        );

        String direction = Utils.input("Input up, down, left, or right. Input \"exit\" to exit.", false);

        if (Arrays.asList(new String[] {"up", "down", "left", "right"}).contains(direction)) {
            try {
                int tiles = Integer.parseInt(Utils.input("How many tiles do you want to move?", false));

                for (int i = 0; i < tiles; i++) {
                    Thread.sleep(Utils.TWENTY_FOUR_FRAMES);
                    if (!currentMap.movePlayer(direction, 1)) {
                        break;
                    }

                    Utils.clear();

                    System.out.println(
                        Ansi.colorize("GMapping v75.221", Attribute.TEXT_COLOR(14)) + "\nCoordinates: " +
                        Ansi.colorize("#%&V;}!%@( ERROR ;}=@&!(/?{", Attribute.TEXT_COLOR(1)) + "\n" +
                        currentMap.toString()
                    );

                }

                System.out.println("Finished moving.");
                Thread.sleep(Utils.STANDARD);
                movement();
            } catch (NumberFormatException exception) {
                Utils.invalidOption();
                movement();
            }

        } else if (direction.equals("exit")) {
            menu();
        } else {
            Utils.invalidOption();
            movement();
        }
    }

    private static void fight() throws Exception {
        CombatMenu.start();
    }

    private static void inventory() throws Exception {
        PlayerMenu.menu();
    }

    // TODO: move to jackson
    private static void save() throws Exception {
        File save = new File(Utils.SAVE_PATH);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(save, Players.player);

        System.out.println("Saved.");
        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

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


