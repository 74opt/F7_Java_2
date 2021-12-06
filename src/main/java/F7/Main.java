package F7;

import F7.entities.classes.*;
import F7.entities.construction.*;
import F7.ui.*;

// Test Imports
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.virtual.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.*;

public class Main {
    public static void main(String[] args) throws Exception {
        MapMenu.setCurrentMap(new Map(Maps.plains)); // TODO: Move to MainMenu once i get more maps and ways to move from one to another
        Enemies.setEnemyHashMap();
        Weapons.setWeaponHashMap();
        Shields.setShieldHashMap();
        Rarities.setRaritiesArrayList();

        //MainMenu.menu();
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);

        TextGraphics textGraphics = screen.newTextGraphics();

        screen.startScreen();

        textGraphics.putString(10, 10, "Hello World!");

        screen.refresh();
    }
}
