package F7.entities.classes;

import F7.Utils;
import F7.entities.construction.*;
import java.io.*;
import java.util.*;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;

public class Player {
    private String name;
    private double health, tempHealth;
    private int exp, level, x, y;
    //private Weapon weaponEquipped; //! will be replaced with equippedIndex
    private int equippedIndex;
    private Weapon[] weapons = new Weapon[4];
    private Shield shield;
    private ArrayList<Consumable> consumables = new ArrayList<>();

    // the following variables will be for skill points + tree
    //! no time for skill tree have fun with random unused code
    private int healthLevel;
    private int damageLevel;
    private int evasionLevel;
    private int accuracyLevel;
    private int luckLevel;

    // Setters and Getters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getHealth() {return health;}
    public void setHealth(double health) {this.health = health;}

    public double getTempHealth() {return tempHealth;}
    public void setTempHealth(double tempHealth) {this.tempHealth = tempHealth;}

    public int getExp() {return exp;}
    public void setExp(int exp) { // when modifying exp values, program should check if exp reaches requirement, and if so, level up.
        while (exp >= getExpRequired()) {
            exp -= getExpRequired();
            level++;
            health += (int) ((health * .95) * Math.pow(level, 0.0001));
            tempHealth = health;
            System.out.printf("\nYou leveled up to %s!\n", level);
        }
        this.exp = exp;
    }

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public Weapon[] getWeapons() {return weapons;}
    public void setWeapon(Weapon weapon, int index) {weapons[index] = weapon;}

    // @Deprecated
    // public Weapon getWeaponEquipped() {return weaponEquipped;}
    // @Deprecated
    // public void setWeaponEquipped(Weapon weapon) {this.weaponEquipped = weapon;}
    
    public int getEquippedIndex() {return equippedIndex;} // do i need?
    public void setEquippedIndex(int equippedIndex) {this.equippedIndex = equippedIndex;} 

    public Shield getShield() {return shield;}
    public void setShield(Shield shield) {this.shield = shield;}

    public ArrayList<Consumable> getConsumables() {return consumables;}
    //* No need for setter bc get on an arraylist will work with arraylist methods yeah ok

    // Setters and Getters for the skill stuff
    // no setter, increment instead ty
    public int getHealthLevel() {return healthLevel;}
    public void incrementHealthLevel() {healthLevel++;}

    public int getDamageLevel() {return damageLevel;}
    public void incrementDamageLevel() {damageLevel++;}

    public int getEvasionLevel() {return evasionLevel;}
    public void incrementEvasionLevel() {evasionLevel++;}

    public int getAccuracyLevel() {return accuracyLevel;}
    public void incrementAccuracyLevel() {accuracyLevel++;}

    public int getLuckLevel() {return luckLevel;}
    public void incrementLuckLevel() {luckLevel++;}

    // Constructors
    // To create new player
    public Player(String name) {
        this.name = name;
        this.health = 50;
        this.tempHealth = 50;
        this.level = 1;
        this.exp = 0;
        this.weapons[0] = Weapons.crowbar;
        this.equippedIndex = 0;
        this.shield = Shields.scrap;
        this.x = 19;
        this.y = 8;
        this.consumables = new ArrayList<Consumable>(Arrays.asList(Consumables.medkit, Consumables.medkit, Consumables.medkit, Consumables.target));
        this.healthLevel = 0;
        this.damageLevel = 0;
        this.evasionLevel = 0;
        this.accuracyLevel = 0;
        this.luckLevel = 0;
    }

    // To load existing player
    public Player(String name, double health, double tempHealth, int level, int exp, Weapon[] weapons, int equippedIndex, Shield shield, int x, int y, ArrayList<Consumable> consumables, int healthLevel, int damageLevel, int evasionLevel, int accuracyLevel, int luckLevel) {
        this.name = name;
        this.health = health;
        this.tempHealth = tempHealth;
        this.level = level;
        this.exp = exp;
        this.weapons = weapons;
        this.equippedIndex = equippedIndex;
        this.shield = shield;
        this.x = x;
        this.y = y;
        this.consumables = consumables;
        this.healthLevel = healthLevel;
        this.damageLevel = damageLevel;
        this.evasionLevel = evasionLevel;
        this.accuracyLevel = accuracyLevel;
        this.luckLevel = luckLevel;
    }

    // Methods
    public int getExpRequired() {
        return (int) (Math.pow(level, 1.75) * 300) + 200;
    }

    public Weapon getWeaponEquipped() {
        return weapons[equippedIndex] != null ? weapons[equippedIndex] : Weapons.fists;
    }

    public String displayConsumables() throws Exception {
        int medkits = 0;
        int smokes = 0;
        int corrosives = 0;
        int targets = 0;
        int amplifiers = 0;
        int flashbangs = 0;

        for (Consumable consumable : consumables) {
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

        return String.format(
        """
        %s: %s
        %s: %s
        %s: %s
        %s: %s
        %s: %s
        %s: %s""", 
        Consumables.medkit.toString(), medkits,
        Consumables.smoke.toString(), smokes,
        Consumables.corrosive.toString(), corrosives,
        Consumables.target.toString(), targets,
        Consumables.amplifier.toString(), amplifiers,
        Consumables.flashbang.toString(), flashbangs
        );
    }

    //save.json is centered around player info, meaning all other loadJson() methods will only be used here
//    public static Player loadJson() {
//        JSONParser jsonParser = new JSONParser();
//
//        // program should have save.json already in files, will throw FileNotFoundException if not and if so thats a you problem
//        try {
//            JSONObject data = (JSONObject) jsonParser.parse(new FileReader(Utils.SAVE_PATH));
//
//            String name = (String) data.get("name");
//            double health = (double) data.get("health");
//            double tempHealth = (double) data.get("tempHealth");
//            int level = Math.toIntExact((Long) data.get("level"));
//            int exp = Math.toIntExact((Long) data.get("exp"));
//            int x = Math.toIntExact((Long) data.get("x"));
//            int y = Math.toIntExact((Long) data.get("y"));
//            int equippedIndex = Math.toIntExact((Long) data.get("equippedIndex"));
//            Shield shield = Shield.loadJson((JSONObject) data.get("shield"));
//            JSONArray JSONweapons = (JSONArray) data.get("weapons");
//
//            ArrayList<Consumable> consumables = new ArrayList<>();
//
//            for (Object consumable : (JSONArray) data.get("consumables")) {
//                consumables.add(Consumable.loadJson((JSONObject) consumable));
//            }
//
//            Weapon[] weapons = new Weapon[4];
//
//            for (int i = 0; i < 4; i++) {
//                if (JSONweapons.get(i) != null) {
//                    weapons[i] = Weapon.loadJson((JSONObject) JSONweapons.get(i));
//                } else {
//                    weapons[i] = null;
//                }
//            }
//
//            int healthLevel = Math.toIntExact((Long) data.get("healthLevel"));
//            int damageLevel = Math.toIntExact((Long) data.get("damageLevel"));
//            int evasionLevel = Math.toIntExact((Long) data.get("evasionLevel"));
//            int accuracyLevel = Math.toIntExact((Long) data.get("accuracyLevel"));
//            int luckLevel = Math.toIntExact((Long) data.get("luckLevel"));
//
//            return new Player(name, health, tempHealth, level, exp, weapons, equippedIndex, shield, x, y, consumables, healthLevel, damageLevel, evasionLevel, accuracyLevel, luckLevel);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
//    }
}
