package F7;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import java.util.Locale;

public class Lanterna {
    private static Terminal terminal;
    private static Screen screen;
    private static TextGraphics textGraphics;
    private static int column;
    private static int row;
    // TODO: MAKE SCROLLING TEXT METHOD
    private static final int SCROLL = 45; //taken from Utils.SCROLL

    public static Thread keyboardListen = new Thread(() -> {
        while (true) {
            try {
                KeyStroke keyPressed = terminal.pollInput();

                if (keyPressed != null) {
                    System.out.print(keyPressed.getCharacter());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    public static void startScreen() throws IOException {
        terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(480, 50)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        textGraphics = screen.newTextGraphics();
        column = 0;
        row = 0;

        screen.startScreen();
        keyboardListen.start();
    }

    public static void clear() throws IOException {
        column = 0;
        row = 0;

        screen.clear();
        screen.refresh();
    }



    // Printing to terminal
    // TODO: implement Gordon's ^ thing for coloring
    public static void print(int column, int row, String text) throws IOException {
        final int finalColumn = Lanterna.column;
        final int finalRow = Lanterna.row;

        Lanterna.column = column;
        Lanterna.row = row;

        print(text);

        Lanterna.column = finalColumn;
        Lanterna.row = finalRow;

        screen.refresh();
    }

    // uses global rows and columns
    public static void print(String text) throws IOException {
        final int finalColumn = column;

        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char character = charArray[i];
            switch (character) { // will add more cases with more special chars
                case '\n' -> {
                    row++;
                    column = finalColumn;
                }
                case '^' -> {
                    int color;

                    switch (text.toCharArray()[i + 1]) {
                        case 'W' -> color = 231;
                        case 'R' -> color = 1;
                        case 'C' -> color = 50;
                        case 'G' -> color = 251;
                        case 'g' -> color = 46;
                        default -> color = 0;
                    }
                    textGraphics.setForegroundColor(new TextColor.Indexed(color));

                    i++;
                }
                default -> {
                    textGraphics.putString(column, row, character + "");
                    column++;
                }
            }
        }

        screen.refresh();
    }

    public static void println(String text) throws IOException {
        print(text);
        row++;
        column = 0;
    }

    // supports color text
    //TODO: make version that uses global column and row
    public static void print(int column, int row, String text, int color) throws IOException {
        textGraphics.setForegroundColor(new TextColor.Indexed(color));
        print(column, row, text);
        textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
}
