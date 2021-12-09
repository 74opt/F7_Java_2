package F7;

import F7.entities.classes.*;
import F7.entities.construction.*;
import F7.ui.*;

// Test Imports
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorPalette;
import com.googlecode.lanterna.terminal.virtual.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.*;

import java.awt.*;
import java.util.Random;

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

        String[][] testArray = new String[][]
                {
                        {"a", "b", "c"},
                        {"d", "e", "f"},
                        {"g", "h", "i"},
                };

        String test = "";

        boolean running = true;
        int row = 10;
        while (running) {
            KeyStroke keyPressed = terminal.pollInput();

            if (keyPressed != null) {
                test += keyPressed.getCharacter();
                textGraphics.putString(10, row, test);
                screen.refresh();

                switch (keyPressed.getKeyType()) {
                    case Escape -> {
                        screen.clear();
                        screen.refresh();
                        test = "";
                        row = 10;
                    }
                    case Enter -> {
                        row++;
                        test = "";
                    }
                    case Backspace, Delete -> {
                        running = false;

                        //test = test.substring(0, test.length() - 2);
                    }

                    //System.out.println(keyPressed);
                }
            }
        }

        screen.refresh();

        screen.readInput();
    }
}
