package F7.entities.construction.enemies;

import F7.entities.classes.Enemy;
import F7.entities.classes.Rarity;

public class Nomad extends Enemy {
    public Nomad(String NAME, String SPRITE, int health, int damage, int level, int accuracy, Rarity RARITY) {
        super(NAME, SPRITE, health, damage, level, accuracy, RARITY);
    }
}
