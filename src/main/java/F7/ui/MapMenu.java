package F7.ui;

import F7.entities.classes.*;
import F7.Utils;
import F7.entities.construction.*;
import com.diogonunes.jcolor.*;
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

    private static void save() throws Exception {
        File save = new File(Utils.SAVE_PATH);

        FileWriter writer = new FileWriter(save);

        String consumableString = "[\n";

        for (Consumable consumable : Players.player.getConsumables()) {
            consumableString += String.format(
            """
                {
                    \"NAME\": \"%s\", 
                    \"TURNS\": %s, 
                    \"RARITY\": 
                    {
                        \"NAME\": \"%s\",
                        \"COLOR\": %s, 
                        \"CHANCE\": %s
                    }
                },
            """,
            consumable.NAME(),
            consumable.TURNS(),
            consumable.RARITY().NAME(),
            consumable.RARITY().COLOR(),
            consumable.RARITY().CHANCE()
            );
        }

        consumableString = consumableString.substring(0, consumableString.length() - 2) + "]";

        String weaponString = "[\n";

        for (Weapon weapon : Players.player.getWeapons()) {
            if (weapon != null) {
                weaponString += String.format(
                """
                    {
                        \"NAME\": \"%s\", 
                        \"damage\": %s, 
                        \"accuracy\": %s,
                        \"level\": %s,
                        \"rof\": %s,
                        \"RARITY\": 
                        {
                            \"NAME\": \"%s\",
                            \"COLOR\": %s, 
                            \"CHANCE\": %s
                        }
                    },
                """,
                weapon.getNAME(),
                weapon.getDamage(),
                weapon.getAccuracy(),
                weapon.getLevel(),
                weapon.getRof(),
                weapon.getRARITY().NAME(),
                weapon.getRARITY().COLOR(),
                weapon.getRARITY().CHANCE()
                );
            } else {
                weaponString += "\nnull,\n";
            }
        }

        weaponString = weaponString.substring(0, weaponString.length() - 2) + "]";

        String json = String.format(
        """
        {
        \"name\": \"%s\",
        \"health\": %s,
        \"tempHealth\": %s,
        \"exp\": %s,
        \"level\": %s,
        \"x\": %s,
        \"y\": %s,
        \"equippedIndex\": %s,
        \"shield\": %s,
        \"consumables\": %s,
        \"healthLevel\": %s,
        \"damageLevel\": %s,
        \"evasionLevel\": %s,
        \"accuracyLevel\": %s,
        \"luckLevel\": %s,
        \"weapons\": %s
        }
        """,
        Players.player.getName(),
        Players.player.getHealth(),
        Players.player.getTempHealth(),
        Players.player.getExp(),
        Players.player.getLevel(),
        Players.player.getX(),
        Players.player.getY(),
        Players.player.getEquippedIndex(),
        Players.player.getShield() != null ?
        String.format(
        """
        {
            \"NAME\": \"%s\",
            \"DAMAGE_REDUCTION\": %s,
            \"TURNS\": %s,
            \"COOLDOWN\": %s,
            \"RARITY\": 
            {
                \"NAME\": \"%s\",
                \"COLOR\": %s, 
                \"CHANCE\": %s
            }
        }""",
        Players.player.getShield().getNAME(),
        Players.player.getShield().getDAMAGE_REDUCTION(),
        Players.player.getShield().getTURNS(),
        Players.player.getShield().getCOOLDOWN(),
        Players.player.getShield().getRARITY().NAME(),
        Players.player.getShield().getRARITY().COLOR(),
        Players.player.getShield().getRARITY().CHANCE()
        ) 
        :
        "null",
        consumableString,
        Players.player.getHealthLevel(),
        Players.player.getDamageLevel(),
        Players.player.getEvasionLevel(),
        Players.player.getAccuracyLevel(),
        Players.player.getLuckLevel(),
        weaponString
        );

        writer.write(json);
        writer.flush();
        writer.close();

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


