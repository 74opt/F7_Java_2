package F7.ui;

import java.util.*;
import F7.Utils;
import F7.entities.construction.*;
import F7.entities.classes.*;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

/*
TODO: Todos part 2 
you must
- fix shit code (found in looting mainly)
 - Use smth like rarityArrayList.get(random.nextInt(0, rarityArrayList.size()));
- replace json code because jackson
*/

public class CombatMenu {
    private static Enemy enemy;
    private static boolean isPlayerTurn; 
    private final static Random random = new Random();

    // Shield variables
    private static boolean shieldUp;
    private static boolean shieldCharging;
    private static int shieldTurns;
    private static int shieldChargingTurns;

    // Booleans for the consumables
    private static boolean smokeActive; // affects enemy
    private static boolean corrosiveActive; // affects player
    private static boolean targetActive; // affects player
    private static boolean amplifierActive; // affects player
    private static boolean flashbangActive; // affects enemy

    // Timers for consumables
    private static int smokeTurns;
    private static int corrosiveTurns;
    private static int targetTurns;
    private static int amplifierTurns;
    private static int flashbangTurns;
    
    public static void start() throws Exception {
        Utils.clear();

        isPlayerTurn = random.nextBoolean();

        shieldUp = false;
        shieldCharging = false;
        shieldTurns = 0;

        smokeActive = false;
        corrosiveActive = false;
        targetActive = false;
        amplifierActive = false;
        flashbangActive = false;

        smokeTurns = 0;
        corrosiveTurns = 0;
        targetTurns = 0;
        amplifierTurns = 0;
        flashbangTurns = 0;

        int enemyRarity = random.nextInt(101);

        if (enemyRarity <= Rarities.common.CHANCE()) {
            enemy = Enemies.enemyHashMap.get(Rarities.common)[random.nextInt(Enemies.enemyHashMap.get(Rarities.common).length)];
        } else if (enemyRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE()) {
            enemy = Enemies.enemyHashMap.get(Rarities.uncommon)[random.nextInt(Enemies.enemyHashMap.get(Rarities.uncommon).length)];
        } else if (enemyRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE()) {
            enemy = Enemies.enemyHashMap.get(Rarities.rare)[random.nextInt(Enemies.enemyHashMap.get(Rarities.rare).length)];
        } else if (enemyRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE() + Rarities.exceptional.CHANCE()) {
            enemy = Enemies.enemyHashMap.get(Rarities.exceptional)[random.nextInt(Enemies.enemyHashMap.get(Rarities.exceptional).length)];
        } else {
            enemy = Enemies.enemyHashMap.get(Rarities.godly)[random.nextInt(Enemies.enemyHashMap.get(Rarities.godly).length)];
        }

        enemy = new Enemy(enemy);

        enemy.setLevel(Players.player.getLevel() + Utils.randomRange(-2, 2));
        
        if (enemy.getLevel() <= 0) {
            enemy.setLevel(1);
        }

        int damageRandom = (int) (Utils.randomRange(-3, 5) * (((double) enemy.getLevel()) / Utils.randomRange(1, 4)));
        int healthRandom = (int) (Utils.randomRange(-2, 3) * (((double) enemy.getLevel()) / Utils.randomRange(1, 3)) + (((double) damageRandom) / Utils.randomRange(2, 5)));
        enemy.setHealth(enemy.getHealth() * enemy.getLevel() + healthRandom);
        enemy.setTempHealth(enemy.getHealth());
        enemy.setDamage(enemy.getDamage() * enemy.getLevel() + damageRandom);
        
        System.out.printf("%s has come to fight!", enemy.toString(true));
        Thread.sleep(Utils.STANDARD);
        menu();
    }

    private static void menu() throws Exception {
        Utils.clear();

        String shieldStatus;

        if (shieldUp) {
            shieldStatus = Ansi.colorize(String.format("Active (%s turns left)", Players.player.getShield().getTURNS() - shieldTurns), Attribute.TEXT_COLOR(46));
        } else if (shieldCharging) {
            shieldStatus = Ansi.colorize(String.format("Charging (%s turns left)", Players.player.getShield().getCOOLDOWN() - shieldChargingTurns), Attribute.TEXT_COLOR(208));
        } else {
            shieldStatus = Ansi.colorize("Ready", Attribute.TEXT_COLOR(50));
        }

        // TODO: change a buncha things to printf
        System.out.printf(
                """
                %s
                Assigned and Calibrated for: %s

                %s
                %s %s
                %s %s (%s)

                %s
                %s

                Active Statuses:
                %s
                %s
                """,
                Ansi.colorize("Model-F v5.032 Targeting Chip", Attribute.TEXT_COLOR(10)),
                Players.player.getName(),
                Utils.outOf("Health:", Players.player.getHealth(), Players.player.getTempHealth(), 9),
                Ansi.colorize("Weapon Equipped:", Attribute.TEXT_COLOR(231)), Players.player.weaponEquipped().toString(true),
                Ansi.colorize("Shield:", Attribute.TEXT_COLOR(231)), Players.player.getShield().toString(true), shieldStatus, 
                Ansi.colorize("Consumables:", Attribute.TEXT_COLOR(231)), Players.player.displayConsumables(), displayStatuses(), enemy.toString(false)
        );

        // can you find a way to turn these into methods
        if (isPlayerTurn) {
            if (shieldCharging) {
                shieldChargingTurns++;
    
                if (shieldChargingTurns > Players.player.getShield().getCOOLDOWN()) {
                    shieldCharging = false;
                    shieldChargingTurns = 0;
                }
            }

            if (shieldUp) {
                shieldTurns++;
    
                if (shieldTurns > Players.player.getShield().getTURNS()) {
                    shieldCharging = true;
                    shieldChargingTurns = 0;
                    shieldUp = false;
                }
            }

            if (smokeActive) {
                smokeTurns++;

                if (smokeTurns > Consumables.smoke.TURNS()) {
                    smokeActive = false;
                    smokeTurns = 0;
                }
            }

            if (corrosiveActive) {
                corrosiveTurns++;

                if (corrosiveTurns > Consumables.corrosive.TURNS()) {
                    corrosiveActive = false;
                    corrosiveTurns = 0;
                }
            }

            if (targetActive) {
                targetTurns++;

                if (targetTurns > Consumables.target.TURNS()) {
                    targetActive = false;
                    targetTurns = 0;
                }
            }

            if (amplifierActive) {
                amplifierTurns++;

                if (amplifierTurns > Consumables.amplifier.TURNS()) {
                    amplifierActive = false;
                    amplifierTurns = 0;
                }
            }

            if (flashbangActive) {
                flashbangTurns++;

                if (flashbangTurns > Consumables.flashbang.TURNS()) {
                    flashbangActive = false;
                    flashbangTurns = 0;
                }
            }

            System.out.printf(
                """

                1) Attack with %s
                2) Use Consumable
                3) Enable Shield
                4) Equip New Weapon
                5) Run Away
                """, 
                Ansi.colorize(Players.player.weaponEquipped().getNAME(), Attribute.TEXT_COLOR(231))
            );

            String choice = Utils.input(false);

            switch (choice) {
                case "1":
                    attack();
                    break;
                case "2":
                    consumable();
                    break;
                case "3":
                    shield();
                    break;
                case "4":
                    equip();
                    break;
                case "5":
                    run();
                    break;
                default:
                    Utils.invalidOption();
                    menu();
                    break;
            }
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
            System.out.printf("%s missed!");
            Thread.sleep(Utils.QUICK_STANDARD);
        }

        System.out.printf("%s dealt %s damage to %s!", Players.player.weaponEquipped().getNAME(), damage, enemy.getNAME());
        Thread.sleep(Utils.QUICK_STANDARD);

        if (corrosiveActive) {
            int corrosiveDamage = (int) (enemy.getHealth() * (Utils.randomRange(17, 28) / 1000.0));
            
            enemy.setTempHealth(enemy.getTempHealth() - corrosiveDamage);
            System.out.printf("\nThe corrosive acid deals %s damage!", corrosiveDamage);
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
        Utils.clear();

        int medkits = 0;
        int smokes = 0;
        int corrosives = 0;
        int targets = 0;
        int amplifiers = 0;
        int flashbangs = 0;

        for (Consumable consumable : Players.player.getConsumables()) { // yes this code is taken from Player
            switch (consumable.NAME()) {
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

        System.out.printf(
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

        String choice = Utils.input(false);

        switch (choice) {
            case "1":
                if (medkits > 0) {
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
                    setTurn();
                    menu();
                } else {
                    System.out.printf("You don't have %s available.", Consumables.medkit.toString());
                }
                break;
            case "2":
                if (smokes > 0 && !smokeActive) {
                    Players.player.getConsumables().remove(Consumables.smoke);

                    System.out.printf("%s used.", Consumables.smoke.toString());
                    smokeActive = true;

                    Thread.sleep(Utils.QUICK_STANDARD);
                    setTurn();
                    menu();
                } else if (smokeActive) {
                    System.out.printf("%s is already active", Consumables.smoke.toString());
                } else {
                    System.out.printf("You don't have %s available.", Consumables.smoke.toString());
                }
                break;
            case "3":
                if (corrosives > 0 && !corrosiveActive) {
                    Players.player.getConsumables().remove(Consumables.corrosive);

                    System.out.printf("%s used.", Consumables.corrosive.toString());
                    corrosiveActive = true;

                    Thread.sleep(Utils.QUICK_STANDARD);
                    setTurn();
                    menu();
                } else if (corrosiveActive) {
                    System.out.printf("%s is already active", Consumables.corrosive.toString());
                } else {
                    System.out.printf("You don't have %s available.", Consumables.corrosive.toString());
                }
                break;
            case "4":
                if (targets > 0 && !targetActive) {
                    Players.player.getConsumables().remove(Consumables.target);

                    System.out.printf("%s used.", Consumables.target.toString());
                    targetActive = true;

                    Thread.sleep(Utils.QUICK_STANDARD);
                    setTurn();
                    menu();
                } else if (targetActive) {
                    System.out.printf("%s is already active", Consumables.target.toString());
                } else {
                    System.out.printf("You don't have %s available.", Consumables.target.toString());
                }
                break;
            case "5":
                if (amplifiers > 0 && !amplifierActive) {
                    Players.player.getConsumables().remove(Consumables.amplifier);

                    System.out.printf("%s used.", Consumables.amplifier.toString());
                    amplifierActive = true;

                    Thread.sleep(Utils.QUICK_STANDARD);
                    setTurn();
                    menu();
                } else if (amplifierActive) {
                    System.out.printf("%s is already active", Consumables.amplifier.toString());
                } else {
                    System.out.printf("You don't have %s available.", Consumables.amplifier.toString());
                }
                break;
            case "6":
                if (flashbangs > 0 && !flashbangActive) {
                    Players.player.getConsumables().remove(Consumables.flashbang);

                    System.out.printf("%s used.", Consumables.flashbang.toString());
                    flashbangActive = true;

                    Thread.sleep(Utils.QUICK_STANDARD);
                    setTurn();
                    menu();
                } else if (flashbangActive) {
                    System.out.printf("%s is already active", Consumables.flashbang.toString());
                } else {
                    System.out.printf("You don't have %s available.", Consumables.flashbang.toString());
                }
                break;
            case "7":
                menu();
                break;
            default:
                Utils.invalidOption();
                consumable();
                break;
        }

        Thread.sleep(Utils.QUICK_STANDARD);
        menu();
    }

    private static void shield() throws Exception { //TODO pls
        if (!shieldCharging && !shieldUp) {
            shieldUp = true;
            shieldTurns = 0;
            System.out.printf("%s has been activated, lasting for %s turns.", Players.player.getShield().getNAME(), Players.player.getShield().getTURNS());
            Thread.sleep(Utils.STANDARD);
            setTurn();
        } else {
            if (shieldCharging) {
                System.out.printf("%s is still charging. %s turns until usable.", Players.player.getShield().getNAME(), 2);
            } else if (shieldUp) {
                System.out.printf("%s is already up.", Players.player.getShield().getNAME());
            }
            Thread.sleep(Utils.QUICK_STANDARD);
        }

        menu();
    }

    private static void equip() throws Exception { // copy of the method from playermenu
        int index = 0;
        String[] possibilies = new String[4];
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
        setTurn();
        menu();
    }

    private static void run() throws Exception {
        System.out.println("Are you sure you want to run? The enemy might get a final hit on you! (Y/N)");

        String choice = Utils.input(false);
        
        switch (choice) {
            case "y":
                if (Utils.chance(67)) {
                    //double damage = Utils.round(Players.player.getTempHealth() * (Utils.randomRange(15, 22) / 100.0), 2); // this way, i don't kill the player but still punish them for running
                    double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

                    if (shieldUp) {
                        damage *= (100 - Players.player.getShield().getDAMAGE_REDUCTION()) / 100.0; //* trying something out where i use the normal attack code but i'll make sure the player doesnt die
                        damage = Utils.round(damage, 2);
                    }

                    while (damage >= Players.player.getTempHealth()) {
                        damage *= .75;
                    }

                    damage = Utils.round(damage, 2);

                    Players.player.setTempHealth(Players.player.getTempHealth() - damage); // TODO: make the damage relate to enemy level as well instead of just removing percentage

                    System.out.printf("%s was able to hit you while you were running, dealing %s damage!", enemy.getNAME(), damage);
                    Thread.sleep(Utils.STANDARD);
                    
                    if (Players.player.getHealth() <= 0) {
                        System.out.print("You died!");
                        Thread.sleep(Utils.QUICK_STANDARD);
                        DeathMenu.menu();
                    }
                }
                System.out.println("\nYou run away from the battle.");
                Thread.sleep(Utils.QUICK_STANDARD);
                MapMenu.menu();
                break;
            case "n":
                menu();
                break;
            default:
                Utils.invalidOption();
                Utils.clear();
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

        System.out.printf("\n%s is going to attack!\n", enemy.getNAME());
        Thread.sleep(Utils.STANDARD);
        if ((smokeActive ? Utils.chance(enemy.getAccuracy() - Utils.randomRange(30, 90)) : Utils.chance(enemy.getAccuracy())) && !flashbangActive) { // if hits
            double damage = Utils.round(enemy.getDamage() * (Utils.randomRange(85, 116) / 100.0), 2); // damage fluctuates for 85% to 115%

            if (shieldUp) {
                damage *= (100 - Players.player.getShield().getDAMAGE_REDUCTION()) / 100.0;
                damage = Utils.round(damage, 2);
            }

            Players.player.setTempHealth(Players.player.getTempHealth() - damage); //* Gordon: permanent damage idea might not implement
            System.out.printf("%s dealt %s damage!", enemy.getNAME(), damage); // 
            Thread.sleep(Utils.STANDARD);
            setTurn();
            menu();
        } else { 
            System.out.printf("%s %s", enemy.getNAME(), missText[random.nextInt(missText.length)]);
            Thread.sleep(Utils.STANDARD);
            setTurn();
            menu();
        }
    }

    //! warning: shit code
    // FIXME: this is shit code
    private static void enemyDead() throws Exception {
        HashMap<Rarity, Double> rarityMultipliers = new HashMap<Rarity, Double>();
        rarityMultipliers.put(Rarities.common, 1.0);
        rarityMultipliers.put(Rarities.uncommon, 1.3); //? change to 1.5?
        rarityMultipliers.put(Rarities.rare, 2.0);
        rarityMultipliers.put(Rarities.exceptional, 2.5);
        rarityMultipliers.put(Rarities.godly, 3.0);
        
        int exp = (int) (((200 + 300 * Math.pow(enemy.getLevel(), 1.6)) * (Utils.randomRange(73, 97) / 100.0)) * rarityMultipliers.get(enemy.getRARITY())); //? should i change 1.6 to less?
        Players.player.setExp(Players.player.getExp() + exp); 

        System.out.printf("\nYou defeated %s! %s exp awarded.", enemy.getNAME(), exp);
        Thread.sleep(Utils.STANDARD);

        //TODO: implement shield loot as the rarest thing possible ok thanks 
        if (Utils.chance(34)) { //* weapon looting and shield looting
            Weapon weapon;
            int weaponRarity = Utils.randomRange(0, enemy.getRARITY().CHANCE() + 5);

            if (weaponRarity <= Rarities.common.CHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.common)[random.nextInt(Weapons.weaponHashMap.get(Rarities.common).length)];
            } else if (weaponRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.uncommon)[random.nextInt(Weapons.weaponHashMap.get(Rarities.uncommon).length)];
            } else if (weaponRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.rare)[random.nextInt(Weapons.weaponHashMap.get(Rarities.rare).length)];
            } else if (weaponRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE() + Rarities.exceptional.CHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.exceptional)[random.nextInt(Weapons.weaponHashMap.get(Rarities.exceptional).length)];
            } else if (weaponRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE() + Rarities.exceptional.CHANCE() + Rarities.godly.CHANCE()) {
                weapon = Weapons.weaponHashMap.get(Rarities.godly)[random.nextInt(Weapons.weaponHashMap.get(Rarities.godly).length)];
            } else { // if goes above 100 you get a shield! yay!!!!
                weapon = null; //! bad idea, should do like 33%-50% (must decide on value) chance to loot, then create different probabilities as to what you could be looting
                               // although i would like 100% of getting currency if i ever implement that

                int shieldRarity = random.nextInt(101);
                Shield shield;
                
                if (shieldRarity <= Rarities.common.CHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.common)[random.nextInt(Shields.shieldHashMap.get(Rarities.common).length)];
                } else if (shieldRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.uncommon)[random.nextInt(Shields.shieldHashMap.get(Rarities.uncommon).length)];
                } else if (shieldRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.rare)[random.nextInt(Shields.shieldHashMap.get(Rarities.rare).length)];
                } else if (shieldRarity <= Rarities.common.CHANCE() + Rarities.uncommon.CHANCE() + Rarities.rare.CHANCE() + Rarities.exceptional.CHANCE()) {
                    shield = Shields.shieldHashMap.get(Rarities.exceptional)[random.nextInt(Shields.shieldHashMap.get(Rarities.exceptional).length)];
                } else {
                    shield = Shields.shieldHashMap.get(Rarities.godly)[random.nextInt(Shields.shieldHashMap.get(Rarities.godly).length)];
                }

                shield = new Shield(shield);

                while (true) {
                    System.out.printf("\n%s dropped %s! Will you take it? (Y/N)\n", enemy.toString(true), shield.toString(true));
                    System.out.println(shield.toString(false));

                    String choice = Utils.input(false);

                    switch (choice) {
                        case "y":
                            if (Players.player.getShield() != null) {
                                System.out.printf("%s already equipped. Do you want to replace the shield? (Y/N)");

                                String takeShield = Utils.input(false);

                                switch (takeShield) {
                                    case "y":
                                        Players.player.setShield(shield);
                                        System.out.printf("%s equipped.", shield.toString(true));
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
                                System.out.printf("%s equipped.", shield.toString(true));
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
                System.out.printf("\n%s dropped %s! Will you take it? (Y/N)\n", enemy.toString(true), weapon.toString(true));
                System.out.println(weapon.toString(false));

                String choice = Utils.input(false);

                switch (choice) {
                    case "y":
                        int search = Arrays.asList(Players.player.getWeapons()).indexOf(null);

                        if (search != -1) {
                            Players.player.setWeapon(weapon, search);
                            System.out.printf("%s equipped.\n", weapon.toString(true));
                        } else {
                            while (true) {
                                System.out.println("Weapon inventory full. Remove a weapon or don't take the new weapon by typing \"exit\".");

                                int index = 0;
                                String[] possibilies = new String[4];

                                for (int i = 0; i < 4; i++) {
                                    if (Players.player.getWeapons()[i] != null) {
                                        System.out.print(i + 1 + ") ");
                                        System.out.println(Players.player.getWeapons()[i].toString(false));
                                        System.out.println();
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

                                            System.out.printf("%s equipped in slot %s.", weapon.toString(true), index);
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
        System.out.print("You died!");
        Thread.sleep(Utils.QUICK_STANDARD);
        DeathMenu.menu();
    }

    private static void setTurn() {
        isPlayerTurn = !isPlayerTurn;
    }

    private static String displayStatuses() {
        String status = "";

        status += addStatus(smokeActive, Consumables.smoke.toString(), Consumables.smoke.TURNS(), smokeTurns);
        status += addStatus(corrosiveActive, Consumables.corrosive.toString(), Consumables.corrosive.TURNS(), corrosiveTurns);
        status += addStatus(targetActive, Consumables.target.toString(), Consumables.target.TURNS(), targetTurns);
        status += addStatus(amplifierActive, Consumables.amplifier.toString(), Consumables.amplifier.TURNS(), amplifierTurns);
        status += addStatus(flashbangActive, Consumables.flashbang.toString(), Consumables.flashbang.TURNS(), flashbangTurns);
        
        return status;
    }

    private static String addStatus(boolean condition, String consumableName, int time, int timer) {
        if (condition) {
            return String.format("%s (%s turns left)\n", consumableName, time - timer);
        } else {
            return "";
        }
    }
}
