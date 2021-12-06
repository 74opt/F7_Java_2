package F7.entities.classes;

import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    // public Weapon() {
    //     super();
    // }

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
}
