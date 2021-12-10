package F7;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Lanterna {
    private static Terminal terminal;
    private static Screen screen;
    private static TextGraphics textGraphics;
    private static int column;
    private static int row;
    // TODO: MAKE SCROLLING TEXT METHOD
    private static final int SCROLL = 45; //taken from Utils.SCROLL

    public static void startScreen() throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        textGraphics = screen.newTextGraphics();
        column = 0;
        row = 0;

        screen.startScreen();
    }

    public static void clear() throws IOException {
        column = 0;
        row = 0;

        screen.clear();
        screen.refresh();
    }

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

        for (char character : text.toCharArray()) {
            switch (character) { // will add more cases with more special chars
                case '\n' -> {
                    row++;
                    column = finalColumn;
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
    public static void print(int column, int row, String text, int color) throws IOException {
        textGraphics.setForegroundColor(new TextColor.Indexed(color));
        print(column, row, text);
        textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
}
