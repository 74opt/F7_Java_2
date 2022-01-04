package F7;

import F7.entities.construction.*;
import F7.ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();

        Lanterna.startScreen(150, 50);

        MainMenu.menu();

        // TODO: the construction classes are full of public attributes, how fix.
    }
}
