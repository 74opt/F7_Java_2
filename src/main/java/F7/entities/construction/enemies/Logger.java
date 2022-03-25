package F7.entities.construction.enemies;

import F7.entities.classes.Enemy;
import F7.entities.classes.Rarity;

public class Logger extends Enemy {
    public Logger(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        super(NAME, SPRITE, health, damage, level, accuracy, RARITY);
    }

    @Override
    public int calculateSpecialDamage() {
        return calculateNormalDamage() * 2;
    }
}
