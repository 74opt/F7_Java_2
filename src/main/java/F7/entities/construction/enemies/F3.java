package F7.entities.construction.enemies;

import F7.entities.classes.Enemy;
import F7.entities.classes.Rarity;

public class F3 extends Enemy {
    public F3(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        super(NAME, SPRITE, health, damage, level, accuracy, RARITY);
    }

    public F3(Enemy enemy) {
        super(enemy);
    }

    @Override
    public int calculateSpecialDamage() { // TODO
        return (int) Math.log(calculateNormalDamage() * 100);
    }
}
