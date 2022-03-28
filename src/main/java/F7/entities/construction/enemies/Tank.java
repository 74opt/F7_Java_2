package F7.entities.construction.enemies;

import F7.Utils;
import F7.entities.classes.Enemy;
import F7.entities.classes.Rarity;

public class Tank extends Enemy {
    public Tank(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        super(NAME, SPRITE, health, damage, level, accuracy, RARITY);
    }

    public Tank(Enemy enemy) {
        super(enemy);
    }

    @Override
    public int calculateSpecialDamage() {
        return (int) (calculateNormalDamage() * Math.cos(Utils.randomRange(30, 91)));
    }
}
