package F7.ui;
import F7.Utils;
import F7.entities.construction.*;
import com.diogonunes.jcolor.*; //https://github.com/dialex/JColor
import java.util.Arrays;

public class PlayerMenu { //TODO: make menu code look nicer with """""" and String.format if have time
    public static void menu() throws Exception {
        Utils.clear();

        // Stats View
        System.out.println(
            Ansi.colorize("F7 Chassis - Model 891Z", Attribute.TEXT_COLOR(50)) +
            Ansi.colorize("\nCreated by RRS Industries", Attribute.TEXT_COLOR(50)) +
            Ansi.colorize("\nVersion: ", Attribute.TEXT_COLOR(231)) + Ansi.colorize(
                    "Illegal. Software may be pirated or tampered with.", Attribute.TEXT_COLOR(1)) +
            Ansi.colorize("\n\nSpecified ID: ", Attribute.TEXT_COLOR(231)) + Players.player.getName() +
            Ansi.colorize("\nLevel: ", Attribute.TEXT_COLOR(231)) + Players.player.getLevel() +
            Utils.outOf("\nExperience Points:", Players.player.expRequired(), Players.player.getExp(), 46) +
            Utils.outOf("\nHealth:", Players.player.getHealth(), Players.player.getTempHealth(), 9) +
            String.format("\n\n%s %s", Ansi.colorize("Weapon Equipped:", Attribute.TEXT_COLOR(231)),
                    Players.player.weaponEquipped().toString(true)) +
            Ansi.colorize("\nWeapons:", Attribute.TEXT_COLOR(231))
        );

        for (int i = 0; i < 4; i++) {
            if (Players.player.getWeapons()[i] == null) {
                System.out.println("No Weapon");
            } else {
                System.out.println(Players.player.getWeapons()[i].toString(true));
            }
        }

        System.out.println("\nShield:\n" + Players.player.getShield().toString(false)); 
        System.out.println("\nConsumables:\n" + Players.player.displayConsumables());

        // Commands
        System.out.println(
            """
            
            1) Heal
            2) Equip New Weapon
            3) Discard Weapon
            4) Exit"""
        );

        String choice = Utils.input(false);

        switch (choice) {
            case "1":
                heal();
                break;
            case "2":
                equip();
                break;
            case "3":
                discard();
                break;
            case "4":
                exit();
                break;
            default:
                Utils.invalidOption();
                menu();
                break;
        }
    }

    private static void exit() throws Exception {
        MapMenu.menu();
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
            Thread.sleep(Utils.QUICK_STANDARD);
            menu();
        } else {
            System.out.printf("\nYou don't have %s available.", Consumables.medkit.toString());
            Thread.sleep(Utils.QUICK_STANDARD);
            menu();
        }
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
