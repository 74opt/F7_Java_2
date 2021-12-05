package F7.entities.classes;

import com.diogonunes.jcolor.*;
//import org.json.simple.*;

public class Weapon {
    private final String NAME;
    private int damage, accuracy, level, rof;
    private final Rarity RARITY;

    // Setters and Getters
    public String getNAME() {return NAME;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}

    public int getAccuracy() {return accuracy;}
    public void setAccuracy(int accuracy) {this.accuracy = accuracy;} // might need a setter idk

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getRof() {return rof;}
    public void setRof(int rof) {this.rof = rof;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    public Weapon(String NAME, int damage, int accuracy, int level, int rof, Rarity RARITY) {
        this.NAME = NAME;
        this.damage = damage;
        this.accuracy = accuracy;
        this.level = level;
        this.rof = rof;
        this.RARITY = RARITY;
    }

    public Weapon(Weapon weapon) {
        this.NAME = weapon.getNAME();
        this.damage = weapon.getDamage();
        this.accuracy = weapon.getAccuracy();
        this.level = weapon.getLevel();
        this.rof = weapon.getRof();
        this.RARITY = weapon.getRARITY();
    }

    // Methods 
    public String toString(boolean simple) {
        if (simple) {
            return String.format("%s Level %s %s", RARITY.toString(),level, Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)));
        } else {
            return String.format(
                "%s Level %s %s\n%s: %s\n%s: %s\n%s: %s", RARITY.toString(), level, Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)),
                Ansi.colorize("Damage", Attribute.TEXT_COLOR(231)), damage,
                Ansi.colorize("Accuracy", Attribute.TEXT_COLOR(231)), accuracy,
                Ansi.colorize("Rate of Fire", Attribute.TEXT_COLOR(231)), rof
            );
        }
    }

//    public static Weapon loadJson(JSONObject jsonObject) {
//        return new Weapon((String) jsonObject.get("NAME"), Math.toIntExact((Long) jsonObject.get("damage")), Math.toIntExact((Long) jsonObject.get("accuracy")), Math.toIntExact((Long) jsonObject.get("level")), Math.toIntExact((Long) jsonObject.get("rof")), Rarity.loadJson((JSONObject) jsonObject.get("RARITY")));
//    }
}
