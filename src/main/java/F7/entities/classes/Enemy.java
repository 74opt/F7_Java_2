package F7.entities.classes;

import F7.Utils;

public class Enemy {
    // TODO: create a final string var for description of special attack
    protected final String NAME, DESCRIPTION;
    protected int health, tempHealth, damage, level, accuracy;
    protected final Rarity RARITY;

    // Setters and Getters
    public String getNAME() {return NAME;}

    public String getDESCRIPTION() {return DESCRIPTION;}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}

    public int getTempHealth() {return tempHealth;}
    public void setTempHealth(int tempHealth) {this.tempHealth = tempHealth;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}

    public int getLevel() {return level;}
    public void setLevel(int level) {
        this.level = level;

        int damageRandom = (int) (Utils.randomRange(-3, 5) * (((double) this.level) / Utils.randomRange(1, 4)));
        int healthRandom = (int) (Utils.randomRange(-2, 3) * (((double) this.level) / Utils.randomRange(1, 3)) + (((double) damageRandom) / Utils.randomRange(2, 5)));

        health *= this.level;
        health += healthRandom;
        tempHealth = health;
        damage *= this.level;
        damage += damageRandom;
    }

    public int getAccuracy() {return accuracy;}
    public void setAccuracy(int accuracy) {this.accuracy = accuracy;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    public Enemy(String NAME, String DESCRIPTION, int health, int damage, int level, int accuracy, Rarity RARITY) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.health = health;
        this.tempHealth = health;
        this.damage = damage;
        this.level = level;
        this.accuracy = accuracy;
        this.RARITY = RARITY;
    }

    public Enemy(Enemy enemy) {
        this.NAME = enemy.getNAME();
        this.DESCRIPTION = enemy.getDESCRIPTION();
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
            return String.format("%s ^GLevel %s ^W%s", RARITY.toString(), level, NAME);
        } else {
            return String.format(
                """
                %s ^GLevel %s ^W%s
                %s
                
                ^WDescription:
                ^G%s""",
                RARITY.toString(), level, NAME,
                Utils.outOf("Health:", health, tempHealth, "^R"),
                DESCRIPTION
            );
        }
    }

    public int calculateNormalDamage() {
        return damage * (Utils.randomRange(85, 116) / 100);
    }

    // to be overridden
    public int calculateSpecialDamage() {
        return 0;
    }
}
