package F7.ui;

import java.util.*;
import java.util.Map;
import java.io.IOException;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import F7.entities.classes.*;
import com.googlecode.lanterna.input.KeyStroke;

public class CombatMenu2 {
    private static Enemy enemy;
    private final static Random random = new Random();

    // Shield variables
    // THESE TWO VALUES SHOULD NEVER BE NEGATIVE
    private static int shieldTime;
    private static int shieldChargingTime;

    private static HashMap<Consumable, Integer> statusHashMap = new HashMap<>();
    private static HashMap<Rarity, Double> rarityMultipliers = new HashMap<>();

    public static void setCombatHashMaps() {
        statusHashMap.put(Consumables.smoke, 0);
        statusHashMap.put(Consumables.corrosive, 0);
        statusHashMap.put(Consumables.target, 0);
        statusHashMap.put(Consumables.amplifier, 0);
        statusHashMap.put(Consumables.flashbang, 0);

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

        enemy.setLevel(Players.player.getLevel() + Utils.randomRange(-2, 2));

        if (enemy.getLevel() <= 0) {
            enemy.setLevel(1);
        }

        Lanterna.printf("%s ^Ghas come to fight!", enemy.toString(true));
        Thread.sleep(Utils.getSTANDARD());
        menu();
    }

    private static void menu() throws Exception {
        Lanterna.clear();

        String shieldStatus;

        // shieldTurns > 0 means shieldUp = true
        // shieldTurns should override all other shield status checks

        // shieldChargingTurns > 0 means shieldCharging = true
        // both values < 0 means the shield is ready to be used

        if (shieldTime > 0) {
            shieldStatus = String.format("^gActive (%s turns left)", Players.player.getShield().getTURNS() - shieldTime);
        } else if (shieldChargingTime > 0) {
            shieldStatus = String.format("^OCharging (%s turns left)", Players.player.getShield().getCOOLDOWN() - shieldChargingTime);
        } else {
            shieldStatus = "^CReady";
        }

        // TODO: change a buncha things to printf AND get rid of ansi colorize
        // TODO: also, any strings w/o a color tag gets ^G by default
        Lanterna.printf(
                """
                ^gModel-F v5.032 Targeting Chip
                ^GAssigned and Calibrated for: ^W%s^G

                %s
                ^WWeapon Equipped: %s
                ^WShield: %s (%s)

                ^WConsumables:
                %s

                ^GActive Statuses:
                %s
                %s
                """,
                Players.player.getName(),
                Utils.outOf("Health:", Players.player.getHealth(), Players.player.getTempHealth(), "^O"),
                Players.player.weaponEquipped().toString(true),
                Players.player.getShield().toString(true), shieldStatus,
                Players.player.displayConsumables(),
                //displayStatuses(),
                enemy.toString(false)
        );

        statusHashMap.forEach((key, value) -> {
            if (value > 0) {
                statusHashMap.replace(key, value - 1);
            }
        });

        Lanterna.printf(
            """

            1) Attack with ^W%s^G
            2) Use Consumable
            3) Enable Shield
            4) Equip New Weapon
            5) Run Away
            """,
            Players.player.weaponEquipped().getNAME()
        );

        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();
                                                                                        
                    if (keyPressed != null) {
                        try {
                            // TODO: use WASD to navigate consumables or weapons
                            // TODO: E key will use consumable OR use weapon

                            switch (keyPressed.getCharacter()) {
                                case 'e' -> {
                                    
                                }
                                case 'f' -> {
                                    shield();
                                }
                                case 'r' -> {
                                    run();
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

    private static void attack() throws Exception {

    }

    private static void shield() throws Exception {

    }

    private static void run() throws Exception {

    }

    // These methods should be inaccessible to the player 
    private static void enemyAttack() throws Exception {
        String[] missText = {
            "just barely gets you, but misses!", "tries to attack, but you dodge it!", "embarrassingly misses!", "accidentally hits the ground!", "attacks the air instead of you!", "almost hits you by an inch!",
            "hesitates and doesn't get the chance to attack!", "zones out and doesn't attack!", "gets distracted and doesn't attack!", "was so close to hitting you!", "can't seem to get you!",
            "doesn't hit!", "misses!", "attacks, and you successfully dodge!", "gets frustrated and misses!", "gets frustrated from your dodge!", "was about to hit you, but you dodge!"
        };
    }

    //! do not disappoint me again
    //! do not write shit code
    //! ill have tallulah break each and every fucking bone in your body if you do
    private static void enemyDead() throws Exception {

    }

    private static void playerDead() throws Exception {

    }

    private static String displayConsumables() throws Exception {
        String status = "";

        for (Map.Entry element : statusHashMap.entrySet()) {
            Consumable key = (Consumable) element.getKey();
            int value = statusHashMap.get(key);

            status += addStatus(value > 0, key.toString(), key.getTURNS(), value);
        }
        
        return status;
    }

    private static String addStatus(boolean condition, String consumableName, int time, int timer) {
        if (condition) {
            return String.format("%s (%s turns left)\n", consumableName, time - timer);
        } else {
            return "";
        }
    }

    private static boolean checkStatus(Consumable status) {
        return statusHashMap.get(status) > 0;
    }
}
