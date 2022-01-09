package F7;

import F7.entities.construction.*;
import F7.ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();
        Consumables.setConsumableHashMap();
        CombatMenu.setStatusHashMap();

        Lanterna.startScreen(150, 50);

        //System.out.println(Lanterna.textBox("This is a horrible test", "^G", "^O"));
        //Lanterna.print(Lanterna.textBox("This is\na horrible\ntest", "^G", "^O"));

        MainMenu.menu();
    }
}
