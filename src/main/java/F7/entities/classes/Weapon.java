package F7.entities.classes;

import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Crits
//  Abstract keyword may come nicely
public class Weapon {
    private final String NAME;
    private int damage, accuracy, level, rof, critChance, reloadTime; // critChance is out of 100
    private double critMultiplier;
    private final Rarity RARITY;

    // Setters and Getters
    public String getNAME() {return NAME;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}

    public int getAccuracy() {return accuracy;}
    public void setAccuracy(int accuracy) {this.accuracy = accuracy;} // might need a setter idk

    public int getLevel() {return level;}
    public void setLevel(int level) {
        this.level = level;
        damage *= Math.pow(this.level, 1.6);
    }

    public int getRof() {return rof;}
    public void setRof(int rof) {this.rof = rof;}

    public int getCritChance() {return critChance;}
    public void setCritChance(int critChance) {this.critChance = critChance;}

    public double getCritMultiplier() {return critMultiplier;}
    public void setCritMultiplier(int critMultiplier) {this.critMultiplier = critMultiplier;}

    public int getReloadTime() {return reloadTime;}
    public void setReloadTime(int reloadTime) {this.reloadTime = reloadTime;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    @JsonCreator
    public Weapon(@JsonProperty("name") String NAME,
                  @JsonProperty("damage") int damage,
                  @JsonProperty("accuracy") int accuracy,
                  @JsonProperty("level") int level,
                  @JsonProperty("rof") int rof,
                  @JsonProperty("rarity") Rarity RARITY,
                  @JsonProperty("critChance") int critChance,
                  @JsonProperty("critMultiplier") double critMultiplier,
                  @JsonProperty("reloadTime") int reloadTime) {
        this.NAME = NAME;
        this.damage = damage;
        this.accuracy = accuracy;
        this.level = level;
        this.rof = rof;
        this.RARITY = RARITY;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.reloadTime = reloadTime;
    }

    public Weapon(Weapon weapon) {
        this.NAME = weapon.getNAME();
        this.damage = weapon.getDamage();
        this.accuracy = weapon.getAccuracy();
        this.level = weapon.getLevel();
        this.rof = weapon.getRof();
        this.RARITY = weapon.getRARITY();
        this.critChance = weapon.getCritChance();
        this.critMultiplier = weapon.getCritMultiplier();
        this.reloadTime = weapon.getReloadTime();
    }

    // Methods 
    public String toString(boolean simple) {
        if (simple) {
            return String.format("%s ^GLevel %s ^W%s", RARITY.toString(), level, NAME);
        } else {
            return String.format(
                    """
                    %s ^GLevel %s ^W%s
                    ^WDamage: ^G%s
                    ^WAccuracy: ^G%s%%
                    ^WRate of Fire: ^G%s
                    ^WCritical Chance: ^G%s%%
                    ^WCritical Multiplier: ^Gx%s
                    ^WReload Speed: ^G%s %s""",
                    RARITY.toString(), level, NAME,
                    damage,
                    accuracy,
                    rof,
                    critChance,
                    critMultiplier,
                    reloadTime, (reloadTime == 1 ? "Second" : "Seconds")
            );
        }
    }

    // to be overrided
    public void special() {

    }
}
