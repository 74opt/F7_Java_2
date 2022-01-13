package F7.ui;

import java.util.*;
import java.util.Map;
import java.io.IOException;
import F7.Lanterna;
import F7.Utils;
import F7.entities.construction.*;
import F7.entities.classes.*;
import com.googlecode.lanterna.input.KeyStroke;

/*
TODO: Todos part 2 
you must
- fix shit code (found in looting mainly)
- Use smth like rarityArrayList.get(random.nextInt(0, rarityArrayList.size()));
- Use a hashmap of String consumableEffect and int turns to determine consumable effects
- lanterna
*/

// BIG THING
// PORT COMBAT 1.0 TO LANTERNA AND THEN WORK ON COMBAT 2.0

// DOTADIW

// TODO: Implement Combat 2.0
@Deprecated
public class CombatMenu {
    private static Enemy enemy;
    private static boolean isPlayerTurn; 
    private final static Random random = new Random();

    // Shield variables
    // THESE TWO VALUES SHOULD NEVER BE NEGATIVE
    private static int shieldTurns;
    private static int shieldChargingTurns;

    // Booleans for the consumables
    @Deprecated
    private static boolean smokeActive; // affects enemy
    @Deprecated
    private static boolean corrosiveActive; // affects player
    @Deprecated
    private static boolean targetActive; // affects player
    @Deprecated
    private static boolean amplifierActive; // affects player
    @Deprecated
    private static boolean flashbangActive; // affects enemy

    private static HashMap<Consumable, Integer> statusHashMap = new HashMap<>();

    public static void setStatusHashMap() {
        statusHashMap.put(Consumables.smoke, 0);
        statusHashMap.put(Consumables.corrosive, 0);
        statusHashMap.put(Consumables.target, 0);
        statusHashMap.put(Consumables.amplifier, 0);
        statusHashMap.put(Consumables.flashbang, 0);
    }
    
    public static void start() throws Exception {
        Lanterna.clear();

        isPlayerTurn = random.nextBoolean();

        // Only for testing
        isPlayerTurn = true;

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

        if (shieldTurns > 0) {
            shieldStatus = String.format("^gActive (%s turns left)", Players.player.getShield().getTURNS() - shieldTurns);
        } else if (shieldChargingTurns > 0) {
            shieldStatus = String.format("^OCharging (%s turns left)", Players.player.getShield().getCOOLDOWN() - shieldChargingTurns);
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
                displayStatuses(),
                enemy.toString(false)
        );

        if (isPlayerTurn) {
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
                                switch (keyPressed.getCharacter()) {
                                    case '1' -> {
                                        attack();
                                    }
                                    case '2' -> {
                                        running = false;
                                        consumable();
                                    }
                                    case '3' -> {
                                        shield();
                                    }
                                    case '4' -> {
                                        equip();
                                    }
                                    case '5' -> {
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
        } else {
            //* do enemy things
            enemyAttack();

            if (Players.player.getHealth() <= 0) {
                playerDead();
            } else {
                setTurn();
                menu();
            }
        }
    }

    // Methods accessible by player
    private static void attack() throws Exception {
        int damage = 0; // Player damage fluctuates between 93% and 107%

        for (int i = 0; i < Players.player.weaponEquipped().getRof(); i++) {
            if (targetActive || Utils.chance(Players.player.weaponEquipped().getAccuracy())) {
                if (amplifierActive) {
                    damage += (int) (Players.player.weaponEquipped().getDamage() * (Utils.randomRange(109, 135) / 100.0));
                } else {
                    damage += (int) (Players.player.weaponEquipped().getDamage() * (Utils.randomRange(93, 108) / 100.0));
                } 
            }
        }

        enemy.setTempHealth(enemy.getTempHealth() - damage);

        if (damage <= 0) {
            Lanterna.printf("%s missed!");
            Thread.sleep(Utils.QUICK_STANDARD);
        }

        Lanterna.printf("%s dealt %s damage to %s!", Players.player.weaponEquipped().getNAME(), damage, enemy.getNAME());
        Thread.sleep(Utils.QUICK_STANDARD);

        if (corrosiveActive) {
            int corrosiveDamage = (int) (enemy.getHealth() * (Utils.randomRange(17, 28) / 1000.0));
         
            enemy.setTempHealth(enemy.getTempHealth() - corrosiveDamage);
            Lanterna.printf("\nThe corrosive acid deals %s damage!", corrosiveDamage);
            Thread.sleep(Utils.QUICK_STANDARD);
        }

        if (enemy.getTempHealth() <= 0) {
            enemyDead();
        } else {
            setTurn();
            menu();
        }
    }

    private static void consumable() throws Exception {
        Lanterna.clear();

        int medkits = 0;
        int smokes = 0;
        int corrosives = 0;
        int targets = 0;
        int amplifiers = 0;
        int flashbangs = 0;

        for (Consumable consumable : Players.player.getConsumables()) { // yes this code is taken from Player
            switch (consumable.getNAME()) {
                case "Medkit":
                    medkits++;
                    break;
                case "Smoke Grenade":
                    smokes++;
                    break;    
                case "Corrosive Acid Grenade":
                    corrosives++;
                    break;
                case "Targeting-Assistance Chip":
                    targets++;
                    break;
                case "Damage Amplifier":
                    amplifiers++;
                    break;
                case "Flashbang":
                    flashbangs++;
                    break;
                default:
                    throw new Exception("Invalid consumable");
            }
        }

        Lanterna.printf(
        """
        1) %s: %s
        2) %s: %s
        3) %s: %s
        4) %s: %s
        5) %s: %s
        6) %s: %s
        7) Exit

        """, 
        Consumables.medkit.toString(), medkits,
        Consumables.smoke.toString(), smokes,
        Consumables.corrosive.toString(), corrosives,
        Consumables.target.toString(), targets,
        Consumables.amplifier.toString(), amplifiers,
        Consumables.flashbang.toString(), flashbangs
        );

        final int medkitsFinal = medkits;
        final int smokesFinal = smokes;
        final int corrosivesFinal = corrosives;
        final int targetsFinal = targets;
        final int amplifiersFinal = amplifiers;
        final int flashbangsFinal = flashbangs;

        // TODO: use that thing i did with weapon selection but with this thanks babe
        // TODO: rewrite with the new hashmap although porting is most important, fixing shit code comes second

        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case '1' -> {
                                    if (medkitsFinal > 0) {
                                        running = false;
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
                    
                                        Lanterna.printf("\n%s health restored.", "^O" + Utils.round(restoration, 2));
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.medkit.toString());
                                    }
                                }
                                case '2' -> {
                                    if (smokesFinal > 0 && !smokeActive) {
                                        running = false;
                                        Players.player.getConsumables().remove(Consumables.smoke);
                    
                                        Lanterna.printf("%s used.", Consumables.smoke.toString());
                                        smokeActive = true;
                    
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else if (smokeActive) {
                                        Lanterna.printf("%s is already active", Consumables.smoke.toString());
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.smoke.toString());
                                    }
                                }
                                case '3' -> {
                                    if (corrosivesFinal > 0 && !corrosiveActive) {
                                        running = false;
                                        Players.player.getConsumables().remove(Consumables.corrosive);
                    
                                        Lanterna.printf("%s used.", Consumables.corrosive.toString());
                                        corrosiveActive = true;
                    
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else if (corrosiveActive) {
                                        Lanterna.printf("%s is already active", Consumables.corrosive.toString());
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.corrosive.toString());
                                    }
                                }
                                case '4' -> {
                                    if (targetsFinal > 0 && !targetActive) {
                                        running = false;
                                        Players.player.getConsumables().remove(Consumables.target);
                    
                                        Lanterna.printf("%s used.", Consumables.target.toString());
                                        targetActive = true;
                    
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else if (targetActive) {
                                        Lanterna.printf("%s is already active", Consumables.target.toString());
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.target.toString());
                                    }
                                }
                                case '5' -> {
                                    if (amplifiersFinal > 0 && !amplifierActive) {
                                        running = false;
                                        Players.player.getConsumables().remove(Consumables.amplifier);
                    
                                        Lanterna.printf("%s used.", Consumables.amplifier.toString());
                                        amplifierActive = true;
                    
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else if (amplifierActive) {
                                        Lanterna.printf("%s is already active", Consumables.amplifier.toString());
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.amplifier.toString());
                                    }
                                }
                                case '6' -> {
                                    if (flashbangsFinal > 0 && !flashbangActive) {
                                        running = false;
                                        Players.player.getConsumables().remove(Consumables.flashbang);
                    
                                        Lanterna.printf("%s used.", Consumables.flashbang.toString());
                                        flashbangActive = true;
                    
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        setTurn();
                                        menu();
                                    } else if (flashbangActive) {
                                        Lanterna.printf("%s is already active", Consumables.flashbang.toString());
                                    } else {
                                        Lanterna.printf("You don't have %s available.", Consumables.flashbang.toString());
                                    }
                                }
                                case '7' -> {
                                    running = false;
                                    menu();
                                }
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    private static void shield() throws Exception { //TODO pls
        if (shieldChargingTurns == 0 && shieldTurns == 0) {
            //shieldUp = true;
            Lanterna.printf("%s has been activated, lasting for %s turns.", Players.player.getShield().getNAME(), Players.player.getShield().getTURNS());
            Thread.sleep(Utils.getSTANDARD());
            setTurn();
        } else {
            if (shieldChargingTurns > 0) {
                Lanterna.printf("%s is still charging. %s turns until usable.", Players.player.getShield().getNAME(), 2);
            } else if (shieldTurns > 0) {
                Lanterna.printf("%s is already up.", Players.player.getShield().getNAME());
            }
            Thread.sleep(Utils.QUICK_STANDARD);
        }

        menu();
    }


    // get rid of this method and use keyboard listener like in PlayerMenu
    @Deprecated
    private static void equip() throws Exception { 
        int index = 0;
        String[] possibilies = new String[4];
        Lanterna.println("Select a weapon to equip or type \"exit\" to exit:\n");

        for (int i = 0; i < 4; i++) {
            if (Players.player.getWeapons()[i] != null) {
                Lanterna.print(i + 1 + ") ");
                Lanterna.println(Players.player.getWeapons()[i].toString(false));
                Lanterna.println("");
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
        Lanterna.printf("%s has been equipped.", Players.player.getWeapons()[index - 1].getNAME());
        Thread.sleep(Utils.QUICK_STANDARD);
        setTurn();
        menu();
    }

    private static void run() throws Exception {
        Lanterna.println("Are you sure you want to run? The enemy might get a final hit on you! (Y/N)");

        String choice = Utils.input(false);
        
        switch (choice) {
            case "y":
                if (Utils.chance(67)) {
                    //double damage = Utils.round(Players.player.getTempHealth() * (Utils.randomRange(15, 22) / 100.0), 2); // this way, i don't kill the player but still punish them for running
                    double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

                    if (shieldTurns > 0) {
                        damage *= (100 - Players.player.getShield().getDAMAGE_REDUCTION()) / 100.0; //* trying something out where i use the normal attack code but i'll make sure the player doesnt die
                        damage = Utils.round(damage, 2);
                    }

                    while (damage >= Players.player.getTempHealth()) {
                        damage *= .75;
                    }

                    damage = Utils.round(damage, 2);

                    Players.player.setTempHealth(Players.player.getTempHealth() - damage); // TODO: make the damage relate to enemy level as well instead of just removing percentage

                    Lanterna.printf("%s was able to hit you while you were running, dealing %s damage!", enemy.getNAME(), damage);
                    Thread.sleep(Utils.getSTANDARD());
                    
                    if (Players.player.getHealth() <= 0) {
                        Lanterna.print("You died!");
                        Thread.sleep(Utils.QUICK_STANDARD);
                        DeathMenu.menu();
                    }
                }
                Lanterna.println("\nYou run away from the battle.");
                Thread.sleep(Utils.QUICK_STANDARD);
                MapMenu.menu();
                break;
            case "n":
                menu();
                break;
            default:
                Utils.invalidOption();
                Lanterna.clear();
                menu();
                break;
        }
    }

    // Methods inaccessible by player
    private static void enemyAttack() throws Exception {
        String[] missText = {
            "just barely gets you, but misses!", "tries to attack, but you dodge it!", "embarrassingly misses!", "accidentally hits the ground!", "attacks the air instead of you!", "almost hits you by an inch!",
            "hesitates and doesn't get the chance to attack!", "zones out and doesn't attack!", "gets distracted and doesn't attack!", "was so close to hitting you!", "can't seem to get you!",
            "doesn't hit!", "misses!", "attacks, and you successfully dodge!", "gets frustrated and misses!", "gets frustrated from your dodge!", "was about to hit you, but you dodge!"
        };

        Lanterna.printf("\n%s is going to attack!\n", enemy.getNAME());
        Thread.sleep(Utils.getSTANDARD());
        if ((smokeActive ? Utils.chance(enemy.getAccuracy() - Utils.randomRange(30, 90)) : Utils.chance(enemy.getAccuracy())) && !flashbangActive) { // if hits
            double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

            if (shieldTurns > 0) {
                damage *= (100 - Players.player.getShield().getDAMAGE_REDUCTION()) / 100.0;
                damage = Utils.round(damage, 2);
            }

            Players.player.setTempHealth(Players.player.getTempHealth() - damage); //* Gordon: permanent damage idea might not implement
            Lanterna.printf("%s dealt %s damage!", enemy.getNAME(), damage); //
            Thread.sleep(Utils.getSTANDARD());
            setTurn();
            menu();
        } else {
            Lanterna.printf("%s %s", enemy.getNAME(), missText[random.nextInt(missText.length)]);
            Thread.sleep(Utils.getSTANDARD());
            setTurn();
            menu();
        }
    }

    //! warning: shit code
    // FIXME: this is shit code
    // TODO: fix the shit code
    //? this may be shit code
    //* this is shit code
    // shit code
    private static void enemyDead() throws Exception {
        HashMap<Rarity, Double> rarityMultipliers = new HashMap<Rarity, Double>();
        rarityMultipliers.put(Rarities.common, 1.0);
        rarityMultipliers.put(Rarities.uncommon, 1.5);
        rarityMultipliers.put(Rarities.rare, 2.0);
        rarityMultipliers.put(Rarities.exceptional, 2.5);
        rarityMultipliers.put(Rarities.godly, 3.0);
        
        int exp = (int) (((200 + 300 * Math.pow(enemy.getLevel(), 1.6)) * (Utils.randomRange(73, 97) / 100.0)) * rarityMultipliers.get(enemy.getRARITY())); //? should i change 1.6 to less?
        Players.player.setExp(Players.player.getExp() + exp);

        Lanterna.printf("\nYou defeated %s! %s exp awarded.", enemy.getNAME(), exp);
        Thread.sleep(Utils.getSTANDARD());

        //TODO: implement shield loot as the rarest thing possible ok thanks 
        if (Utils.chance(34)) { //* weapon looting and shield looting
            Weapon weapon;
            int weaponRarity = Utils.randomRange(0, enemy.getRARITY().getCHANCE() + 5);

            if (weaponRarity <= Rarities.common.getCHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.common)[random.nextInt(Weapons.weaponHashMap.get(Rarities.common).length)];
            } else if (weaponRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.uncommon)[random.nextInt(Weapons.weaponHashMap.get(Rarities.uncommon).length)];
            } else if (weaponRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE() + Rarities.rare.getCHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.rare)[random.nextInt(Weapons.weaponHashMap.get(Rarities.rare).length)];
            } else if (weaponRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE() + Rarities.rare.getCHANCE() + Rarities.exceptional.getCHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.exceptional)[random.nextInt(Weapons.weaponHashMap.get(Rarities.exceptional).length)];
            } else if (weaponRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE() + Rarities.rare.getCHANCE() + Rarities.exceptional.getCHANCE() + Rarities.godly.getCHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.godly)[random.nextInt(Weapons.weaponHashMap.get(Rarities.godly).length)];
            } else { // if goes above 100 you get a shield! yay!!!!
                weapon = null; //! bad idea, should do like 33%-50% (must decide on value) chance to loot, then create different probabilities as to what you could be looting
                               // although i would like 100% of getting currency if i ever implement that

                int shieldRarity = random.nextInt(101);
                Shield shield;
                
                if (shieldRarity <= Rarities.common.getCHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.common)[random.nextInt(Shields.shieldHashMap.get(Rarities.common).length)];
                } else if (shieldRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.uncommon)[random.nextInt(Shields.shieldHashMap.get(Rarities.uncommon).length)];
                } else if (shieldRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE() + Rarities.rare.getCHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.rare)[random.nextInt(Shields.shieldHashMap.get(Rarities.rare).length)];
                } else if (shieldRarity <= Rarities.common.getCHANCE() + Rarities.uncommon.getCHANCE() + Rarities.rare.getCHANCE() + Rarities.exceptional.getCHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.exceptional)[random.nextInt(Shields.shieldHashMap.get(Rarities.exceptional).length)];
                } else {
                    shield = Shields.shieldHashMap.get(Rarities.godly)[random.nextInt(Shields.shieldHashMap.get(Rarities.godly).length)];
                }

                shield = new Shield(shield);

                while (true) {
                    Lanterna.printf("\n%s dropped %s! Will you take it? (Y/N)\n", enemy.toString(true), shield.toString(true));
                    Lanterna.println(shield.toString(false));

                    String choice = Utils.input(false);

                    switch (choice) {
                        case "y":
                            if (Players.player.getShield() != null) {
                                Lanterna.printf("%s already equipped. Do you want to replace the shield? (Y/N)");

                                String takeShield = Utils.input(false);

                                switch (takeShield) {
                                    case "y":
                                        Players.player.setShield(shield);
                                        Lanterna.printf("%s equipped.", shield.toString(true));
                                        Thread.sleep(Utils.QUICK_STANDARD);
                                        MapMenu.menu();
                                        break;
                                    case "n":
                                        MapMenu.menu();
                                        break;
                                    default:
                                        Utils.invalidOption();
                                        break;
                                }
                            } else {
                                Players.player.setShield(shield);
                                Lanterna.printf("%s equipped.", shield.toString(true));
                                Thread.sleep(Utils.QUICK_STANDARD);
                                MapMenu.menu();
                            }
                            break;
                        case "n":
                            MapMenu.menu();
                            break;
                        default:
                            Utils.invalidOption();
                            break;
                    }
                }
            }

            weapon = new Weapon(weapon);

            weapon.setLevel(enemy.getLevel() + Utils.randomRange(-3, 2));

            if (weapon.getLevel() > 1) {
                weapon.setLevel(1);
            }

            weapon.setDamage((int) (weapon.getDamage() * Math.pow(weapon.getLevel(), 2.3)));

            while (true) {
                Lanterna.printf("\n%s dropped %s! Will you take it? (Y/N)\n", enemy.toString(true), weapon.toString(true));
                Lanterna.println(weapon.toString(false));

                String choice = Utils.input(false);

                switch (choice) {
                    case "y":
                        int search = Arrays.asList(Players.player.getWeapons()).indexOf(null);

                        if (search != -1) {
                            Players.player.setWeapon(weapon, search);
                            Lanterna.printf("%s equipped.\n", weapon.toString(true));
                        } else {
                            while (true) {
                                Lanterna.println("Weapon inventory full. Remove a weapon or don't take the new weapon by typing \"exit\".");

                                int index = 0;
                                String[] possibilies = new String[4];

                                for (int i = 0; i < 4; i++) {
                                    if (Players.player.getWeapons()[i] != null) {
                                        Lanterna.print(i + 1 + ") ");
                                        Lanterna.println(Players.player.getWeapons()[i].toString(false));
                                        Lanterna.println("");
                                        possibilies[i] = "" + (i + 1);
                                    }
                                }

                                choice = Utils.input(false);

                                if (choice.equals("exit")) {
                                    MapMenu.menu();
                                }

                                switch (choice) {
                                    case "1", "2", "3", "4":
                                        if (Arrays.asList(possibilies).contains(choice)) {
                                            index = Integer.parseInt(choice);

                                            Lanterna.printf("%s equipped in slot %s.", weapon.toString(true), index);
                                            Players.player.setWeapon(weapon, index - 1);

                                            Thread.sleep(Utils.QUICK_STANDARD);
                                            MapMenu.menu();
                                        } else {
                                            Utils.invalidOption();
                                        }
                                        break;
                                    default:
                                        Utils.invalidOption();
                                        break;
                                }
                            }
                        }
                        break;
                    case "n":
                        MapMenu.menu();
                        break;
                    default:
                        Utils.invalidOption();
                        break;
                }
            }
        }

        MapMenu.menu();
    }

    private static void playerDead() throws Exception {
        Lanterna.print("You died!");
        Thread.sleep(Utils.QUICK_STANDARD);
        DeathMenu.menu();
    }

    private static void setTurn() {
        isPlayerTurn = !isPlayerTurn;
    }

    // TODO: replace this stuff
    private static String displayStatuses() {
        String status = "";

        for (Map.Entry element : statusHashMap.entrySet()) {
            Consumable key = (Consumable) element.getKey();
            int value = statusHashMap.get(key);

            status += addStatus(value > 0, key.toString(), key.getTURNS(), value);
        }

//        statusHashMap.forEach((key, value) -> {
//            status += addStatus(value > 0, key.toString(), key.getTURNS(), value);
//        });

//        status += addStatus(smokeActive, Consumables.smoke.toString(), Consumables.smoke.getTURNS(), smokeTurns);
//        status += addStatus(corrosiveActive, Consumables.corrosive.toString(), Consumables.corrosive.getTURNS(), corrosiveTurns);
//        status += addStatus(targetActive, Consumables.target.toString(), Consumables.target.getTURNS(), targetTurns);
//        status += addStatus(amplifierActive, Consumables.amplifier.toString(), Consumables.amplifier.getTURNS(), amplifierTurns);
//        status += addStatus(flashbangActive, Consumables.flashbang.toString(), Consumables.flashbang.getTURNS(), flashbangTurns);
        
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
