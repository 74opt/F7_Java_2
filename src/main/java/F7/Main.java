package F7;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import F7.entities.construction.*;
import F7.ui.*;

// To run on matt computer
// & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.1.12-hotspot\bin\javaw.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '@C:\Users\selbo\AppData\Local\Temp\cp_53jdaua5u0wcceffzk1yvuxo9.argfile' 'F7.Main'

// To run on compsci computer
// & 'C:\Program Files\Java\jdk-16.0.2\bin\javaw.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '@C:\Users\BT_1N3~1\AppData\Local\Temp\cp_6regyakoth4yfj7f7wqq3qpc1.argfile' 'F7.Main' 
public class Main {
    public static void main(String[] args) throws Exception {
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();
        Consumables.setConsumableHashMap();
        CombatMenu2.setCombatHashMaps();

        Lanterna.startScreen(Lanterna.getSTANDARD_COLUMNS(), Lanterna.getSTANDARD_ROWS());

        MainMenu.menu();

        //* Code to quit at any time
//        new Thread(() -> {
//            boolean running = true;
//
//            while (running) {
//                try {
//                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();
//                    if (keyPressed != null) {
//                        if (keyPressed.getKeyType().equals(KeyType.Escape)) {
//                            try {
//                                running = false;
//                                System.exit(0);
//                            } catch (Exception ignored) {}
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        // TODO: Replace Combat2.png with new image in TeXit discord bot dms

        // for (int i = 100; i >= 0; i--) {
        //     Lanterna.println(Utils.percentBar(100, 100, i, "^G"));
        //     Thread.sleep(Utils.getTWENTY_FOUR());
        //     Lanterna.clear();
        // }
        //System.out.println(System.currentTimeMillis());

        // Base Keyboard Listening Thread:
        /*
        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        */
    }
}
