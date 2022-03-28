package F7.entities.construction.enemies;

import F7.Utils;
import F7.entities.classes.Enemy;
import F7.entities.classes.Rarity;

public class Hawk extends Enemy {
    public Hawk(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        super(NAME, SPRITE, health, damage, level, accuracy, RARITY);
    }

    public Hawk(Enemy enemy) {
        super(enemy);
    }

    @Override
    public int calculateSpecialDamage() {
        return (int) (calculateNormalDamage() * Math.sin(Utils.randomRange(30, 91)));
    }
}
