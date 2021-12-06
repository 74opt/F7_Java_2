package F7.ui;

import F7.entities.classes.*;
import F7.Utils;
import F7.entities.construction.*;
import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Arrays;

public class MapMenu {
    private static Map currentMap;

    public static Map getCurrentMap() {return currentMap;}
    public static void setCurrentMap(Map currentMap) {MapMenu.currentMap = currentMap;}

    public static void menu() throws Exception {
        Utils.clear();

        System.out.println(
            Ansi.colorize("GMapping v75.221", Attribute.TEXT_COLOR(14)) + "\nCoordinates: " +
            Ansi.colorize("#%&V;}!%@( ERROR ;}=@&!(/?{", Attribute.TEXT_COLOR(1)) + "\n" + currentMap.toString() +
            """
            1) Move
            2) Fight
            3) Access Inventory
            4) Save
            5) Exit Game
            """
        );

        String choice = Utils.input("Select an option:", false);

        switch (choice) {
            case "1":
                movement();
                break;
            case "2":
                fight();
                break;
            case "3":
                inventory();
                break;
            case "4":
                save();
                break;
            case "5":
                exit();
                break;
            default:
                Utils.invalidOption();
                menu();
                break;
        }
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


