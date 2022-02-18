package F7.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import F7.entities.classes.*;
import com.googlecode.lanterna.input.KeyStroke;

// I'm gonna DOTADIW your mom

/*
 TODO:
  - Ending game
    - Put DeathMenu and WinMenu stuff into the CombatMenu
    - Have enemies drop consumables
  - Shit code
    - Dotadiw
    - Make consumable stuff better with arraylist methods
  - Balancing Issues:
      - Literally buff the enemies by a lot
      - Reduce XP drop
      - Reduce how much the player gains in terms of health and stuff
      - I think item drops needs to be fixed/reworked
      - Make change rarity values to get a higher chance of having rarer things
  - Ending game
    - Put DeathMenu and WinMenu stuff into the CombatMenu
*/
public class CombatMenu {
    private static Enemy enemy;
    private static boolean running;
    private static long timeElapsed;
    private static String[] information;

    // Shield variables
    // THESE TWO VALUES SHOULD NEVER BE NEGATIVE
    private static int shieldTime;
    private static int shieldChargingTime;

    private static final int enemyAttackInterval = 2;

    private static HashMap<Consumable, Integer> statusHashMap = new HashMap<>();
    private static HashMap<Weapon, Integer> weaponReload = new HashMap<>();
    private static HashMap<Weapon, Integer> weaponRof = new HashMap<>();

    public static Enemy getEnemy() {return enemy;}

    private static void setCombatHashMaps() {
        statusHashMap.put(Consumables.smoke, 0);
        statusHashMap.put(Consumables.corrosive, 0);
        statusHashMap.put(Consumables.target, 0);
        statusHashMap.put(Consumables.amplifier, 0);
        statusHashMap.put(Consumables.flashbang, 0);

        for (Weapon w : Players.getPlayer().getWeapons()) {
            weaponReload.put((w == null ? Weapons.getFists() : w), 0);
            weaponRof.put((w == null ? Weapons.getFists() : w), (w == null ? Weapons.getFists().getRof() : w.getRof()));
        }
    }

    public static void start() throws Exception {
        Lanterna.clear();

        setCombatHashMaps();

        int enemyRarity = Utils.randomRange(0, 101);

        enemy = new Enemy(Enemies.getEnemyHashMap().get(Rarities.getRarityArrayList().get(enemyRarity))[Utils.randomRange(0, Enemies.getEnemyHashMap().get(Rarities.getRarityArrayList().get(enemyRarity)).length)]);

        running = true;
        timeElapsed = 0;
        information = new String[15];

        enemy.setLevel(Players.getPlayer().getLevel() + Utils.randomRange(-2, 2));

        if (enemy.getLevel() <= 0) {
            enemy.setLevel(1);
        }

        Lanterna.printf(1, 1, "%s ^Ghas come to fight!", enemy.toString(true));
        Thread.sleep(Utils.STANDARD);
        menu();
    }

    private static void menu() throws Exception {
        initialDraw();

        // Handles updates every second
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (running) {
                try {
                    //* Time Handler
                    timeElapsed++;
                    Lanterna.clear(12, 85, 54);
                    Lanterna.print(85, 12, "^G" + displayTime());

                    //* Status Handler
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

                    /// Corrosion Code
                    if (checkStatus(Consumables.corrosive)) {
                        int corrosiveDamage = (int) (enemy.getHealth() * (Utils.randomRange(17, 28) / 1000.0));

                        enemy.setTempHealth(enemy.getTempHealth() - corrosiveDamage);

                        addInfo(Consumables.corrosive.toString() + " ^Gdealt " + corrosiveDamage + " damage to " + enemy.getNAME());

                        for (int i = 0; i < 2; i++) {
                            Lanterna.clear(5, 106 + i, 105);
                        }

                        Lanterna.printf(106, 5,
                                """
                                %s
                                        %s""",
                                Utils.outOf("Health:", enemy.getHealth(), enemy.getTempHealth(), "^R"),
                                Utils.percentBar(80, enemy.getHealth(), enemy.getTempHealth(), "^R")
                        );

                        if (enemy.getTempHealth() <= 0) {
                            enemyDead();
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        Lanterna.clear(32 + i, 71, 69);
                    }

                    int statusIndexReplacement = 0;
                    for (Map.Entry<Consumable, Integer> element : statusHashMap.entrySet()) {
                        Consumable key = (Consumable) element.getKey();
                        int value = statusHashMap.get(key);

                        if (value > 0) {
                            Lanterna.print(71, 32 + statusIndexReplacement, key.toString() + "^G effect: ^W" + value + " seconds^G remaining");
                            statusIndexReplacement++;
                        }
                    }

                    //* Shield Handler
                    if (shieldStatus().equals("Up")) {
                        shieldTime--;

                        Lanterna.clear(42, 1, 68);

                        Lanterna.printf(1, 42, "^WShield (%s):", shieldStatus());
                        Lanterna.print(1, 48, "^GShield Timer:   " + Utils.percentBar(30, Players.getPlayer().getShield().getTURNS(), shieldTime, "^C"));

                        if (shieldTime == 0) {
                            addInfo(Players.getPlayer().getShield().getNAME() + " charging");

                            shieldChargingTime = Players.getPlayer().getShield().getCOOLDOWN();

                            Lanterna.printf(1, 42, "^WShield (%s):", shieldStatus());
                            Lanterna.print(1, 47, "^GCharging Timer: " + Utils.percentBar(30, Players.getPlayer().getShield().getCOOLDOWN(), shieldChargingTime, "^O"));
                        }
                    } else if (shieldStatus().equals("Charging")) {
                        shieldChargingTime--;

                        Lanterna.clear(42, 1, 68);

                        Lanterna.printf(1, 42, "^WShield (%s):", shieldStatus());
                        Lanterna.print(1, 47, "^GCharging Timer: " + Utils.percentBar(30, Players.getPlayer().getShield().getCOOLDOWN(), shieldChargingTime, "^O"));

                        if (shieldChargingTime == 0) {
                            addInfo(Players.getPlayer().getShield().getNAME() + " finished charging");
                        }
                    }

                    //* Weapon Reload Handler
                    weaponReload.forEach((key, value) -> {
                        if (value > 0) {
                            weaponReload.replace(key, value - 1);

                            if (value == 1) {
                                try {
                                    addInfo(key.getNAME() + " reloaded");

                                    weaponRof.put(key, key.getRof());

                                    Lanterna.clear((Arrays.asList(Players.getPlayer().getWeapons()).indexOf(key == Weapons.getFists() ? null : key) * 3) + 22, 1, 68);
                                    Lanterna.print(1, (Arrays.asList(Players.getPlayer().getWeapons()).indexOf(key == Weapons.getFists() ? null : key) * 3) + 22, String.format("^G%s/%s", weaponRof.get(key), (key.getRof())));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    for (int i = 0; i < 4; i++) {
                        Lanterna.clear((i * 3) + 23, 1, 50);
                        Lanterna.print(1, (i * 3) + 23, Utils.percentBar(50, (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getReloadTime(), weaponReload.get((Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i])), "^G"));
                    }

                    //* Enemy Attack
                    if (timeElapsed % enemyAttackInterval == 0 && Utils.chance(70) && !checkStatus(Consumables.flashbang)) {
                        enemyAttack();
                    }

                    // Please wait a second ok
                    Thread.sleep(1000);
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
                                    Lanterna.print(1, 20, "^WWeapons: ");

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.printf(1, (i * 3) + 21, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.printf(1, (i * 3) + 21, (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                        Lanterna.print(1, (i * 3) + 22, String.format("^G%s/%s", weaponRof.get(Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]), (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getRof()));
                                        Lanterna.print(1, (i * 3) + 23, Utils.percentBar(50, (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getReloadTime(), weaponReload.get((Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i])), "^G"));
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
                                    Lanterna.print(1, 20, "^WWeapons: ");

                                    for (int i = 0; i < 4; i++) {
                                        if (i == Players.getPlayer().getEquippedIndex()) {
                                            Lanterna.printf(1, (i * 3) + 21, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)));
                                        } else {
                                            Lanterna.printf(1, (i * 3) + 21, (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
                                        }
                                        Lanterna.print(1, (i * 3) + 22, String.format("^G%s/%s", weaponRof.get(Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]), (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getRof()));
                                        Lanterna.print(1, (i * 3) + 23, Utils.percentBar(50, (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getReloadTime(), weaponReload.get((Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i])), "^G"));
                                    }
                                }

                                // Attack
                                case 'f' -> playerAttack();

                                // Shield
                                case 'x' -> shield();

                                // Run
                                case 'r' -> {
                                    running = false;
                                    run();
                                }

                                //* Consumable cases
                                // Medkit
                                case 'm' -> {
                                    if (Players.getPlayer().hasConsumable(Consumables.medkit)) {
                                        Players.getPlayer().removeConsumable(Consumables.medkit);
                                        addInfo(Consumables.medkit.toString() + " used");

                                        for (int i = 0; i < 7; i++) {
                                            Lanterna.clear(34 + i, 1, 69);
                                        }

                                        Lanterna.print(1, 34, "^WConsumables:\n" + Players.getPlayer().displayConsumables());

                                        double restoration = Utils.randomRange(15, 21) / 100.0;
                                        restoration *= Players.getPlayer().getHealth();
                                        double overheal;

                                        Players.getPlayer().getConsumables().remove(Consumables.medkit);
                                        Players.getPlayer().setTempHealth(Players.getPlayer().getTempHealth() + restoration);

                                        if (Players.getPlayer().getTempHealth() > Players.getPlayer().getHealth()) {
                                            overheal = Players.getPlayer().getTempHealth() - Players.getPlayer().getHealth();

                                            Players.getPlayer().setTempHealth(Players.getPlayer().getHealth());

                                            restoration -= overheal;
                                        }

                                        addInfo(String.format("%s ^Ghealth restored.", "^R" + Utils.round(restoration, 2)));

                                        for (int i = 0; i < 2; i++) {
                                            Lanterna.clear(5 + i, 1, 104);
                                        }

                                        Lanterna.printf(1, 5,
                                            """
                                            %s
                                                    %s""",
                                            Utils.outOf("Health:", Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R"),
                                            Utils.percentBar(80, Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R")
                                        );
                                    }
                                }

                                // Smoke Grenade
                                case 'o' -> useConsumable(Consumables.smoke);

                                // Corrosion
                                case 'c' -> useConsumable(Consumables.corrosive);

                                // Taring
                                case 't' -> useConsumable(Consumables.target);

                                // Damage -> {
                                case 'd' -> useConsumable(Consumables.amplifier);

                                // Flashbang
                                case 'l' -> useConsumable(Consumables.flashbang);
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

    private static void initialDraw() throws Exception {
        Lanterna.clear();

        //* Sections for the UI
        //* Lines to separate the sections
        // Setting the color of the lines with this print statement
        Lanterna.print(0, 0, "^G");

        // Line splitting player and enemy
        for (int i = 0; i < 10; i++) {
            Lanterna.print(105, i, "║");
        }

        // First horizontal line
        new Thread(() -> {
            try {
                for (int i = 0; i < 211; i++) {
                    switch (i) {
                        case 105 -> Lanterna.print(i, 10, "╩");
                        case 70, 140 -> Lanterna.print(i, 10, "╦");
                        default -> Lanterna.print(i, 10, "═");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Second horizontal line
        for (int i = 0; i < 211; i++) {
            if (i == 70 || i == 140) {
                Lanterna.print(i, 50, "╩");
            } else {
                Lanterna.print(i, 50, "═");
            }
        }

        // Line separating Player extended and middle column
        new Thread(() -> {
            try {
                for (int i = 11; i < 50; i++) {
                    if (i == 30) {
                        Lanterna.print(70, i, "╠");
                    } else {
                        Lanterna.print(70, i, "║");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        
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
        Lanterna.print(1, 20, "^WWeapons: ");

        for (int i = 0; i < 4; i++) {
            if (i == Players.getPlayer().getEquippedIndex()) {
                Lanterna.printf(1, (i * 3) + 21, "^g> " + (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)));
            } else {
                Lanterna.printf(1, (i * 3) + 21, (Players.getPlayer().getWeapons()[i] == null ? "^GFists" : Players.getPlayer().getWeapons()[i].toString(true)) + "  ");
            }
            Lanterna.print(1, (i * 3) + 22, String.format("^G%s/%s", weaponRof.get(Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]), (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getRof()));
            Lanterna.print(1, (i * 3) + 23, Utils.percentBar(50, (Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i]).getReloadTime(), weaponReload.get((Players.getPlayer().getWeapons()[i] == null ? Weapons.getFists() : Players.getPlayer().getWeapons()[i])), "^G"));
        }

        Lanterna.print(1, 34, "^WConsumables:\n" + Players.getPlayer().displayConsumables());

        Lanterna.printf(1, 42, "^WShield (%s):\n%s", shieldStatus(), Players.getPlayer().getShield().toString(false));

        Lanterna.print(1, 47, "^GCharging Timer: " + Utils.percentBar(30, Players.getPlayer().getShield().getCOOLDOWN(), shieldChargingTime, "^O"));
        Lanterna.print(1, 48, "^GShield Timer:   " + Utils.percentBar(30, Players.getPlayer().getShield().getTURNS(), shieldTime, "^C"));

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

        // Don't think i need, since there won't be any statuses active
        // int statusIndex = 0;
        // for (Map.Entry element : statusHashMap.entrySet()) {
        //     Consumable key = (Consumable) element.getKey();
        //     int value = statusHashMap.get(key);

        //     if (value > 0) {
        //         Lanterna.print(71, 31 + statusIndex, key.toString() + "^G effect: ^W" + value + " seconds^G remaining");
        //         statusIndex++;
        //     }
        // }

        //* Controls/Keybindings
        Lanterna.print(1, 51,
            """
            ^WControls:
            ^WW^W) ^GMove up weapon list                    ^gF^W) ^GAttack with selected weapon                       ^RR^W) ^GRun away
            ^WS^W) ^GMove down weapon list                  ^OX^W) ^GUse shield
            
            ^WConsumable Controls:
            ^w[M]edkit                                  ^g[C]orrosive Acid Grenade                              ^7[D]amage Amplifier
            ^wSm[o]ke Grenade                           ^g[T]argeting-Assistance Chip                           ^7F[l]ashbang"""
        );
    }

    private static void playerAttack() throws Exception {
        if (weaponRof.get(Players.getPlayer().weaponEquipped()) > 0) {
            weaponRof.put(Players.getPlayer().weaponEquipped(), weaponRof.get(Players.getPlayer().weaponEquipped()) - 1);

            int damage = 0;

            if (checkStatus(Consumables.target) || Utils.chance(Players.getPlayer().weaponEquipped().getAccuracy())) {
                if (checkStatus(Consumables.amplifier)) {
                    damage += (int) (Players.getPlayer().weaponEquipped().getDamage() * (Utils.randomRange(109, 135) / 100.0));
                } else {
                    damage += (int) (Players.getPlayer().weaponEquipped().getDamage() * (Utils.randomRange(93, 108) / 100.0));
                }
            }

            enemy.setTempHealth(enemy.getTempHealth() - damage);

            if (damage != 0) {
                addInfo(String.format("%s dealt %s damage to %s", Players.getPlayer().weaponEquipped().getNAME(), damage, enemy.getNAME()));
            } else {
                addInfo(String.format("%s missed", Players.getPlayer().weaponEquipped().getNAME()));
            }

            // Modify enemy main and player extended
            Lanterna.clear((Players.getPlayer().getEquippedIndex() * 3) + 22, 1, 68);

            for (int i = 0; i < 2; i++) {
                Lanterna.clear(5, 106 + i, 105);
            }

            Lanterna.print(1, (Players.getPlayer().getEquippedIndex() * 3) + 22, String.format("^G%s/%s", weaponRof.get(Players.getPlayer().weaponEquipped()), (Players.getPlayer().weaponEquipped().getRof())));

            Lanterna.printf(106, 5,
                """
                %s
                        %s""",
                Utils.outOf("Health:", enemy.getHealth(), enemy.getTempHealth(), "^R"),
                Utils.percentBar(80, enemy.getHealth(), enemy.getTempHealth(), "^R")
            );

            if (enemy.getTempHealth() <= 0) {
                enemyDead();
            }
        } else {
            if (!(weaponReload.get(Players.getPlayer().weaponEquipped()) > 0)) {
                weaponReload.put(Players.getPlayer().weaponEquipped(), Players.getPlayer().weaponEquipped().getReloadTime());
                Lanterna.clear((Players.getPlayer().getEquippedIndex() * 3) + 23, 1, 68);
                Lanterna.print(1, (Players.getPlayer().getEquippedIndex() * 3) + 23, Utils.percentBar(50, Players.getPlayer().weaponEquipped().getReloadTime(), weaponReload.get(Players.getPlayer().weaponEquipped()), "^G"));
            }
        }
    }

    private static void shield() throws Exception {
        if (!shieldStatus().equals("Up") && !shieldStatus().equals("Charging")) {
            shieldTime = Players.getPlayer().getShield().getTURNS();

            addInfo(Players.getPlayer().getShield().getNAME() + " activated");

            Lanterna.printf(1, 42, "^WShield (%s):", shieldStatus());
            Lanterna.print(1, 48, "^GShield Timer:   " + Utils.percentBar(30, Players.getPlayer().getShield().getTURNS(), shieldTime, "^C"));
        }
    }

    private static void run() {
        new Thread(() -> {
            try {
                addInfo("Attempting to run away...");
                Thread.sleep(Utils.STANDARD);

                if (Utils.chance(67)) {
                    double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

                    if (shieldStatus().equals("Up")) {
                        damage *= (100 - Players.getPlayer().getShield().getDAMAGE_REDUCTION()) / 100.0; //* trying something out where i use the normal attack code but i'll make sure the player doesnt die
                    }

                    while (damage >= Players.getPlayer().getTempHealth()) {
                        damage *= .75;
                    }

                    damage = Utils.round(damage, 2);

                    Players.getPlayer().setTempHealth(Players.getPlayer().getTempHealth() - damage);

                    addInfo(String.format("%s was able to hit you, dealing %s damage", enemy.getNAME(), damage));
                }

                MapMenu.menu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //* These methods should be inaccessible to the player
    private static void enemyAttack() throws Exception {
        String[] missText = {
            "just barely gets you, but misses!", "tries to attack, but you dodge it!", "embarrassingly misses!", "accidentally hits the ground!", "attacks the air instead of you!", "almost hits you by an inch!",
            "hesitates and doesn't get the chance to attack!", "zones out and doesn't attack!", "gets distracted and doesn't attack!", "was so close to hitting you!", "can't seem to get you!",
            "doesn't hit!", "misses!", "attacks, and you successfully dodge!", "gets frustrated and misses!", "gets frustrated from your dodge!", "was about to hit you, but you dodge!"
        };

        if (checkStatus(Consumables.smoke) ? Utils.chance(enemy.getAccuracy() - Utils.randomRange(30, 90)) : Utils.chance(enemy.getAccuracy())) {
            double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

            if (shieldStatus().equals("Up")) {
                damage *= (100 - Players.getPlayer().getShield().getDAMAGE_REDUCTION()) / 100.0;
                damage = Utils.round(damage, 2);
            }

            Players.getPlayer().setTempHealth(Players.getPlayer().getTempHealth() - damage);
            addInfo(String.format("%s dealt %s damage to %s", enemy.getNAME(), damage, Players.getPlayer().getName()));

            Lanterna.clear(5, 1, 103);
            Lanterna.printf(1, 5,
                """
                %s
                        %s""",
                Utils.outOf("Health:", Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R"),
                Utils.percentBar(80, Players.getPlayer().getHealth(), Players.getPlayer().getTempHealth(), "^R")
            );

            if (Players.getPlayer().getTempHealth() <= 0) {
                playerDead();
            }
        } else {
            addInfo(String.format("%s %s", enemy.getNAME(), missText[Utils.randomRange(0, missText.length)]));
        }
    }

    // These should happen in the combat menu
    private static void enemyDead() throws Exception {
        running = false;
        WinMenu.menu();
    }

    private static void playerDead() throws Exception {
        running = false;
        DeathMenu.menu();
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

        for (int i = 0; i < 15; i++) {
            Lanterna.clear(14 + i, 71, 68);
        }

        int infoIndex = 0;
        for (String s : information) {
            if (s != null) {
                Lanterna.print(71, 14 + infoIndex, s);
                infoIndex++;
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

    private static void useConsumable(Consumable consumable) throws Exception {
        if (Players.getPlayer().hasConsumable(consumable)) {
            Players.getPlayer().removeConsumable(consumable);
            statusHashMap.put(consumable, statusHashMap.get(consumable) + consumable.TURNS());
            addInfo(consumable.toString() + "^G used");

            for (int i = 0; i < 7; i++) {
                Lanterna.clear(34 + i, 1, 69);
            }

            Lanterna.print(1, 34, "^WConsumables:\n" + Players.getPlayer().displayConsumables());
        }
    }

    private static String shieldStatus() {
        if (shieldChargingTime == 0 && shieldTime == 0) {
            return "Ready";
        } else {
            if (shieldChargingTime > 0) {
                return "Charging";
            } else if (shieldTime > 0) {
                return "Up";
            }
        }

        return null;
    }
}
