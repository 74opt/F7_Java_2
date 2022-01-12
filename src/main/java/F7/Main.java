package F7;

import F7.entities.construction.*;
import F7.ui.*;

// To run on matt computer
// & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.1.12-hotspot\bin\javaw.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '@C:\Users\selbo\AppData\Local\Temp\cp_53jdaua5u0wcceffzk1yvuxo9.argfile' 'F7.Main'

// To run on compsci computer
// & 'C:\Program Files\Java\jdk-16.0.2\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '@C:\Users\BT_1N3~1\AppData\Local\Temp\cp_6regyakoth4yfj7f7wqq3qpc1.argfile' 'F7.Main' 
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
