package F7.entities.classes;

import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Crits
public class Weapon {
    private final String NAME;
    private int damage, accuracy, level, rof, critChance, critMultiplier;
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

    public int getCritChance() {return critChance;}
    public void setCritChance(int critChance) {this.critChance = critChance;}

    public int getCritMultiplier() {return critMultiplier;}
    public void setCritMultiplier(int critMultiplier) {this.critMultiplier = critMultiplier;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    @JsonCreator
    public Weapon(@JsonProperty("name") String NAME, @JsonProperty("damage") int damage, @JsonProperty("accuracy") int accuracy, @JsonProperty("level") int level, @JsonProperty("rof") int rof, @JsonProperty("rarity") Rarity RARITY) {
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
            return String.format("%s ^GLevel %s ^W%s", RARITY.toString(), level, NAME);
        } else {
            return String.format(
                    """
                    ^G%s Level %s ^W%s
                    ^WDamage: ^G%s
                    ^WAccuracy: ^G%s
                    ^WRate of Fire: ^G%s
                    ^WCritical Chance: ^G%s%%
                    ^WCritical Multiplier: ^Gx%s""",
                    RARITY.toString(), level, NAME,
                    damage,
                    accuracy,
                    rof,
                    critChance,
                    critMultiplier
            );

//            return String.format(
//                "%s Level %s %s\n%s: %s\n%s: %s\n%s: %s", RARITY.toString(), level, Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)),
//                Ansi.colorize("Damage", Attribute.TEXT_COLOR(231)), damage,
//                Ansi.colorize("Accuracy", Attribute.TEXT_COLOR(231)), accuracy,
//                Ansi.colorize("Rate of Fire", Attribute.TEXT_COLOR(231)), rof
//            );
        }
    }

    //? should be void?
    // to be overrided
    public void special() {
        System.out.println();
    }
}
