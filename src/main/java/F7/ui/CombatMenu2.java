package F7.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.io.IOException;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import F7.entities.classes.*;
import com.googlecode.lanterna.input.KeyStroke;

// I'm gonna DOTADIW your mom

public class CombatMenu2 {
    private static Enemy enemy;
    private static boolean running;
    private static long timeElapsed;
    private static String[] information = new String[15];
    private final static Random random = new Random();

    // Shield variables
    // THESE TWO VALUES SHOULD NEVER BE NEGATIVE
    private static int shieldTime;
    private static int shieldChargingTime;

    private static HashMap<Consumable, Integer> statusHashMap = new HashMap<>();
    private static HashMap<Rarity, Double> rarityMultipliers = new HashMap<>();

    public static void setCombatHashMaps() {
        statusHashMap.put(Consumables.getSmoke(), 0);
        statusHashMap.put(Consumables.getCorrosive(), 0);
        statusHashMap.put(Consumables.getTarget(), 0);
        statusHashMap.put(Consumables.getAmplifier(), 0);
        statusHashMap.put(Consumables.getFlashbang(), 0);

        rarityMultipliers.put(Rarities.common, 1.0);
        rarityMultipliers.put(Rarities.uncommon, 1.5);
        rarityMultipliers.put(Rarities.rare, 2.0);
        rarityMultipliers.put(Rarities.exceptional, 2.5);
        rarityMultipliers.put(Rarities.godly, 3.0);
    }

    public static void start() throws Exception {
        Lanterna.clear();


        int enemyRarity = Utils.randomRange(0, 101);

        enemy = new Enemy(Enemies.getEnemyHashMap().get(Rarities.getRarityArrayList().get(enemyRarity))[Utils.randomRange(0, Enemies.getEnemyHashMap().get(Rarities.getRarityArrayList().get(enemyRarity)).length)]);

        running = true;
        timeElapsed = 0;

        enemy.setLevel(Players.getPlayer().getLevel() + Utils.randomRange(-2, 2));

        if (enemy.getLevel() <= 0) {
            enemy.setLevel(1);
        }

        Lanterna.printf(1, 1, "%s ^Ghas come to fight!", enemy.toString(true));
        Thread.sleep(Utils.getSTANDARD());
        menu();
    }

    private static void menu() throws Exception {
        Lanterna.clear();

        // TODO: also, any strings w/o a color tag gets ^G by default

        //! DO NOT USE ANY AUTO PRINTING, GIVE SPECIFIC COORDINATES
        //* Sections for the UI

        //! These parts stay constant throughout a fight
        //* Lines to separate the sections
        // Setting the color of the lines with this print statement
        Lanterna.print(0, 0, "^G");

        // Line splitting player and enemy
        for (int i = 0; i < 10; i++) {
            Lanterna.print(105, i, "║");
        }

        // First horizontal line
        for (int i = 0; i < 211; i++) {
            switch (i) {
                case 105 -> Lanterna.print(i, 10, "╩");
                case 70, 140 -> Lanterna.print(i, 10, "╦");
                default -> Lanterna.print(i, 10, "═");
            }
        }

        // Line separating Player extended and middle column
        for (int i = 11; i < 50; i++) {
            if (i == 30) {
                Lanterna.print(70, i, "╠");
            } else {
                Lanterna.print(70, i, "║");
            }
        }

        // Line separating middle column and enemy extended
        for (int i = 11; i < 50; i++) {
            if (i == 30) {
                Lanterna.print(140, i, "╣");
            } else {
                Lanterna.print(140, i, "║");
            }
        }

        // Line separating info and statuses
        for (int i = 71; i < 140; i++) {
            Lanterna.print(i, 30, "═");
        }

        // Second horizontal line
        for (int i = 0; i < 211; i++) {
            if (i == 70 || i == 140) {
                Lanterna.print(i, 50, "╩");
            } else {
                Lanterna.print(i, 50, "═");
            }
        }

        addInfo("^GStarted combat with ^W" + enemy.getNAME());

        //* Player Stats
        Lanterna.printf(1, 1, 
            """
            ^gModel-F v5.032 Targeting Chip
            ^GAssigned and Calibrated for: ^W%s^G
            
            ^WLevel: %s
            %s
                    %s""",
            Players.getPlayer().getName(),
            Players.getPlayer().getLevel(),
            Utils.outOf("Health:", Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R"),
            Utils.percentBar(80, Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R")
        );

        //* Enemy Stats
        Lanterna.printf(106, 2, 
            """
            ^GEnemy Detected:
            ^W%s^G

            %s
                    %s""",
            enemy.toString(true),
            Utils.outOf("Health:", enemy.getHealth(), enemy.getTempHealth(), "^R"),
            Utils.percentBar(80, enemy.getHealth(), enemy.getTempHealth(), "^R")
        );

        //* Player extended
        Lanterna.printf(1, 11, "^WEquipped:\n%s", Players.getPlayer().weaponEquipped().toString(false));
        Lanterna.print(1, 21, "^WWeapons: ");

        for (int i = 0; i < 4; i++) {
            if (i == Players.getPlayer().getEquippedIndex()) {
                Lanterna.printf(1, (i * 2) + 22, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
            } else {
                Lanterna.printf(1, (i * 2) + 22, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
            }
        }

        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());

        Lanterna.print(1, 39, "^WShield (status here):\n" + Players.getPlayer().getShield().toString(false));

        //* Enemy Stats Extended
        Lanterna.printf(141, 11, "" +
            """
            ^WEnemy Info:
            %s
            ^WDamage: ^G%s
            ^WAccuracy: ^G%s%%
            
            ^WDescription:
            ^G%s""",
            enemy.toString(true),
            enemy.getDamage(),
            enemy.getAccuracy(),
            enemy.getDESCRIPTION()
        );

        //* Info
        Lanterna.print(71, 11, "^WInfo:");
        Lanterna.print(71, 12, "^WTime Elapsed: ^G" + displayTime());

        int infoIndex = 0;
        for (String s : information) {
            if (s != null) {
                Lanterna.print(71, 14 + infoIndex, s);
                infoIndex++;
            }
        }

        //* Statuses
        Lanterna.print(71, 31, "^WStatuses:");

        int statusIndex = 0;
        for (Map.Entry element : statusHashMap.entrySet()) {
            Consumable key = (Consumable) element.getKey();
            int value = statusHashMap.get(key);

            if (value > 0) {
                Lanterna.print(71, 31 + statusIndex, key.toString() + "^G effect: ^W" + value + " seconds^G remaining");
                statusIndex++;
            }
        }

        //* Controls/Keybindings
        Lanterna.print(1, 51,
            """
            ^WControls:
            ^WW^W) ^GMove up item list                      ^gF^W) ^GInteract with selected item                        ^RR^W) ^GRun away
            ^WS^W) ^GMove down item list                    ^OX^W) ^GUse shield
            
            ^WConsumable Controls:
            ^w[M]edkit                                  ^g[C]orrosive Acid Grenade                              ^7[D]amage Amplifier
            ^wSm[o]ke Grenade                           ^g[T]argeting-Assistance Chip                           ^7F[l]ashbang"""
        );

        // Handles updates every second
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                    timeElapsed++;
                    Lanterna.clear(12, 85, 54);
                    Lanterna.print(85, 12, "^G" + displayTime());

                    statusHashMap.forEach((key, value) -> {
                        if (value > 0) {
                            statusHashMap.replace(key, value - 1);
                            if (value == 1) {
                                try {
                                    addInfo(key.toString() + "^G effect ended");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    for (int i = 0; i < 6; i++) {
                        Lanterna.clear(32 + i, 71, 69);
                    }

                    int statusIndexReplacement = 0;
                    for (Map.Entry element : statusHashMap.entrySet()) {
                        Consumable key = (Consumable) element.getKey();
                        int value = statusHashMap.get(key);

                        if (value > 0) {
                            Lanterna.print(71, 32 + statusIndexReplacement, key.toString() + "^G effect: ^W" + value + " seconds^G remaining");
                            statusIndexReplacement++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //* Interactions
        new Thread(() -> {
            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                // Go up list
                                case 'w' -> {
                                    if (Players.getPlayer().getEquippedIndex() == 0) {
                                        Players.getPlayer().setEquippedIndex(3);
                                    } else {
                                        Players.getPlayer().setEquippedIndex(Players.getPlayer().getEquippedIndex() - 1);
                                    }

                                    for (int i = 11; i < 31; i++) {
                                        Lanterna.clear(i, 1, 69);
                                    }

                                    Lanterna.printf(1, 11, "^WEquipped:\n%s", Players.getPlayer().weaponEquipped().toString(false));
                                    Lanterna.print(1, 21, "^WWeapons: ");

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.printf(1, (i * 2) + 22, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.printf(1, (i * 2) + 22, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }

                                // Go down list
                                case 's' -> {
                                    if (Players.getPlayer().getEquippedIndex() == 3) {
                                        Players.getPlayer().setEquippedIndex(0);
                                    } else {
                                        Players.getPlayer().setEquippedIndex(Players.getPlayer().getEquippedIndex() + 1);
                                    }

                                    for (int i = 11; i < 31; i++) {
                                        Lanterna.clear(i, 1, 69);
                                    }

                                    Lanterna.printf(1, 11, "^WEquipped:\n%s", Players.getPlayer().weaponEquipped().toString(false));
                                    Lanterna.print(1, 21, "^WWeapons: ");

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.printf(1, (i * 2) + 22, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.printf(1, (i * 2) + 22, (Players.getPlayer().getWeapons()[i] == null ? "^GNo Weapon" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                    }
                                }

                                // Interact
                                case 'f' -> {

                                }

                                // Shield
                                case 'x' -> {
                                    shield();
                                }

                                // Run (q and e to confirm/deny
                                case 'r' -> {
                                    run();
                                }

                                //* Consumable cases
                                // Medkit
                                case 'm' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getMedkit())) {
                                        Players.getPlayer().removeConsumable(Consumables.getMedkit());
                                        addInfo(Consumables.getMedkit().toString() + " used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }

                                // Smoke Grenade
                                case 'o' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getSmoke())) {
                                        Players.getPlayer().removeConsumable(Consumables.getSmoke());
                                        statusHashMap.put(Consumables.getSmoke(), statusHashMap.get(Consumables.getSmoke()) + Consumables.getSmoke().getTURNS());
                                        addInfo(Consumables.getSmoke().toString() + "^G used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }

                                // Corrosion
                                case 'c' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getCorrosive())) {
                                        Players.getPlayer().removeConsumable(Consumables.getCorrosive());
                                        statusHashMap.put(Consumables.getCorrosive(), statusHashMap.get(Consumables.getCorrosive()) + Consumables.getCorrosive().getTURNS());
                                        addInfo(Consumables.getCorrosive().toString() + "^G used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }

                                // Targeting
                                case 't' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getTarget())) {
                                        Players.getPlayer().removeConsumable(Consumables.getTarget());
                                        statusHashMap.put(Consumables.getTarget(), statusHashMap.get(Consumables.getTarget()) + Consumables.getTarget().getTURNS());
                                        addInfo(Consumables.getTarget().toString() + "^G used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }

                                // Damage -> {
                                case 'd' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getAmplifier())) {
                                        Players.getPlayer().removeConsumable(Consumables.getAmplifier());
                                        statusHashMap.put(Consumables.getAmplifier(), statusHashMap.get(Consumables.getAmplifier()) + Consumables.getAmplifier().getTURNS());
                                        addInfo(Consumables.getAmplifier().toString() + "^G used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }

                                // Flashbang
                                case 'l' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.getFlashbang())) {
                                        Players.getPlayer().removeConsumable(Consumables.getFlashbang());
                                        statusHashMap.put(Consumables.getFlashbang(), statusHashMap.get(Consumables.getFlashbang()) + Consumables.getFlashbang().getTURNS());
                                        addInfo(Consumables.getFlashbang().toString() + "^G used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(31 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 31, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void shield() throws Exception {

    }

    private static void run() throws Exception {

    }

    //* These methods should be inaccessible to the player
    private static void enemyAttack() throws Exception {
        String[] missText = {
            "just barely gets you, but misses!", "tries to attack, but you dodge it!", "embarrassingly misses!", "accidentally hits the ground!", "attacks the air instead of you!", "almost hits you by an inch!",
            "hesitates and doesn't get the chance to attack!", "zones out and doesn't attack!", "gets distracted and doesn't attack!", "was so close to hitting you!", "can't seem to get you!",
            "doesn't hit!", "misses!", "attacks, and you successfully dodge!", "gets frustrated and misses!", "gets frustrated from your dodge!", "was about to hit you, but you dodge!"
        };
    }

    // be sure to unshit code
    private static void enemyDead() throws Exception {
        running = false;
    }

    private static void playerDead() throws Exception {
        running = false;
    }

    private static boolean checkStatus(Consumable status) {
        return statusHashMap.get(status) > 0;
    }

    private static void addInfo(String info) throws Exception {
        if (Arrays.asList(information).contains(null)) {
            information[Arrays.asList(information).indexOf(null)] = "^W[" + displayTime() + "]: " + info;
        } else {
            for (int i = 0; i < information.length - 1; i++) {
                information[i] = information[i + 1];
            }

            information[information.length - 1] = "^W[" + displayTime() + "]: " + info;
        }

        int i = 0;
        for (String s : information) {
            if (s != null) {
                Lanterna.print(71, 14 + i, s);
                i++;
            }
        }
    }

    private static String displayTime() {
        long minutes = timeElapsed / 60;
        long seconds = timeElapsed - (minutes * 60);

        if (("" + seconds).length() < 2) {
            return String.format("%s:0%s", minutes, seconds);
        }

        return String.format("%s:%s", minutes, seconds);
    }
}
