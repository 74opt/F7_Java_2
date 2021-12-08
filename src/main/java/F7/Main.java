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
        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(100, 100))
                .setTerminalEmulatorColorConfiguration(TerminalEmulatorColorConfiguration.newInstance(new TerminalEmulatorPalette(
                        Color.BLACK,
                        Color.BLACK,
                        Color.WHITE,
                        Color.BLACK,
                        Color.BLACK,
                        new Color(133, 66, 45),
                        new Color(229, 69, 46),
                        new Color(25, 112, 25),
                        new Color(27, 199, 27),
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.BLUE,
                        Color.MAGENTA,
                        Color.MAGENTA,
                        Color.CYAN,
                        Color.CYAN,
                        Color.WHITE,
                        Color.WHITE
                )))
                .createTerminal();
        Screen screen = new TerminalScreen(terminal);

        TextGraphics textGraphics = screen.newTextGraphics();

        screen.startScreen();

        String[][] testArray = new String[][]
                {
                        {"a", "b", "c"},
                        {"d", "e", "f"},
                        {"g", "h", "i"},
                };

        // how to use send help
//        SwingTerminalFontConfiguration stfc = new SwingTerminalFontConfiguration(true,
//                AWTTerminalFontConfiguration.BoldMode.NOTHING,
//                new Font("test", Font.PLAIN, 16));

        int row = 0;
        int column = 0;

        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray.length; j++) {
                textGraphics.putString(column, row, testArray[i][j]);
                column++;
            }
            row++;
            column = 0;
        }

        screen.refresh();

        //screen.stopScreen();

        //textGraphics.putString(10, 15, Maps.plains.toString());

        //textGraphics.putString(0, 1, "Hello World!\nThis is a test\n\nBecause :)");
        //textGraphics.drawRectangle(new TerminalPosition(5, 5), new TerminalSize(10, 10), '#');
//        String test = "";
//
//        boolean running = true;
//        int row = 10;
//        while (running) {
//            KeyStroke keyPressed = terminal.pollInput();
//
//            if (keyPressed != null) {
//                test += keyPressed.getCharacter();
//                textGraphics.putString(10, row, test);
//                screen.refresh();
//
//                switch (keyPressed.getKeyType()) {
//                    case Escape -> screen.clear();
//                    case Enter -> {
//                        row++;
//                        test = "";
//                    }
//                    case Backspace, Delete -> test = test.substring(0, test.length() - 2);
//
//                    //System.out.println(keyPressed);
//                }
//            }
//        }

//        screen.refresh();
//
//        screen.readInput();

    }
}
