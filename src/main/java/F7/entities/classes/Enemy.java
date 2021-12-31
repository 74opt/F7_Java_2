package F7.entities.classes;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import F7.Utils;

public class Enemy {
    private final String NAME, DESCRIPTION;
    private int health, tempHealth, damage, level, accuracy;
    private final Rarity RARITY;

    // Setters and Getters
    public String getNAME() {return NAME;}

    public String getSPRITE() {return DESCRIPTION;}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}

    public int getTempHealth() {return tempHealth;}
    public void setTempHealth(int tempHealth) {this.tempHealth = tempHealth;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getAccuracy() {return accuracy;}
    public void setAccuracy(int accuracy) {this.accuracy = accuracy;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    public Enemy(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        this.NAME = NAME;
        this.DESCRIPTION = SPRITE;
        this.health = health;
        this.tempHealth = health;
        this.damage = damage;
        this.level = level;
        this.accuracy = accuracy;
        this.RARITY = RARITY;
    }

    public Enemy(Enemy enemy) {
        this.NAME = enemy.getNAME();
        this.DESCRIPTION = enemy.getSPRITE();
        this.health = enemy.getHealth();
        this.tempHealth = enemy.getHealth();
        this.damage = enemy.getDamage();
        this.level = enemy.getLevel();
        this.accuracy = enemy.getAccuracy();
        this.RARITY = enemy.getRARITY();
    }

    // Methods
    public String toString(boolean simple) {
        if (simple) {
            return String.format("%s Level %s %s", RARITY.toString(), level, Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)));
        } else {
            return String.format(
                """
                %s Level %s %s
                %s
                %s""", 
                RARITY.toString(), level, Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)), 
                Utils.outOf("Health:", health, tempHealth, "^R"),
                DESCRIPTION
            );
        }
    }
}
