package F7;

import F7.entities.classes.*;
import F7.entities.construction.*;
import F7.ui.*;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class Main {
    public static void main(String[] args) throws Exception {
        MapMenu.setCurrentMap(new Map(Maps.plains)); // TODO: Move to MainMenu once i get more maps and ways to move from one to another
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();

        //MainMenu.menu();
        Lanterna.startScreen();

        MainMenu.menu();

        //TODO: jsoncreator annotations for the new final classes


//        boolean running = true;
//        int row = 10;
//        while (running) {
//            KeyStroke keyPressed = terminal.pollInput();
//
//            if (keyPressed != null) {
//                test += keyPressed.getKeyType();
//                textGraphics.putString(10, row, test);
//                screen.refresh();
//
//                switch (keyPressed.getKeyType()) {
//                    case Escape -> { 
//                        screen.clear();
//                        screen.refresh();
//                        test = "";
//                        row = 10;
//                    }
//                    case Enter -> {
//                        row++;
//                        test = "";
//                    }
//                    case Backspace, Delete -> {
//                        running = false;
//
//                        //test = test.substring(0, test.length() - 2);
//                    }
//
//                    //System.out.println(keyPressed);
//                }
//            }
//        }
    }
}
