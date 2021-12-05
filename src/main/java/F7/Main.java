package F7;

import F7.entities.classes.Map;
import F7.entities.construction.*;
import F7.ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        MapMenu.setCurrentMap(new Map(Maps.plains));
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();

        MainMenu.menu();
    }
}
