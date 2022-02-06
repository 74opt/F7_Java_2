package F7;

import com.googlecode.lanterna.TerminalSize;
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
    private static int initColumn;

    // Values good for 1920x1080
    private static final int STANDARD_COLUMNS = 211;
    private static final int STANDARD_ROWS = 61;

    public static final int SCROLL_TIME = 45;

    public static Screen getScreen() {return screen;}

    public static int getSTANDARD_COLUMNS() {return STANDARD_COLUMNS;}

    public static int getSTANDARD_ROWS() {return STANDARD_ROWS;}
    
    public static void startScreen(int paramInitColumn, int paramInitRow) throws IOException {
        initColumn = paramInitColumn;
        terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(paramInitColumn, paramInitRow)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        textGraphics = screen.newTextGraphics();

        screen.startScreen();
    }

    public static void clear() throws IOException {
        screen.clear();
        screen.refresh();
    }

    public static void clear(int row) throws Exception {
        String spaces = "";

        for (int i = 0; i < initColumn; i++) {
            spaces += " ";
        }

        print(0, row, spaces);
    }

    public static void clear(int row, int column, int length) throws Exception {
        String spaces = "";

        for (int i = 0; i < length; i++) {
            spaces += " ";
        }

        print(column, row, spaces);
    }

    // uses global rows and columns
    public static void print(int column, int row, String text) throws Exception {
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
                        case 'W' -> color = 231; // White
                        case 'w' -> color = 249; // Darker white
                        case 'R' -> color = 196; // Red; To replace 1
                        case 'C' -> color = 50; // Cyan; To replace 14, 6
                        case 'G' -> color = 251; // Grey; Main grey to use
                        case 'g' -> color = 40; // Green; Main green to use; To replace 46, 10
                        case 'O' -> color = 208; // Orange; To replace 9
                        case 'B' -> color = 27; // Blue; Main blue to use; To replace 20
                        case 'P' -> color = 13; // Pink
                        case 'p' -> color = 99; // Purple

                        // The following are codes for specifically for tiles
                        case '1' -> color = 82; // Green
                        case '2' -> color = 28; // Green
                        case '3' -> color = 238; // Grey
                        case '4' -> color = 245; // Grey
                        case '5' -> color = 94; // Brown
                        case '6' -> color = 95; // Brown
                        case '7' -> color = 33; // Blue
                        case '8' -> color = 241; // Grey
                        case '9' -> color = 70; // Green
                        case '0' -> color = 65; // Green

                        // In case I need to use ^
                        case '^' -> {
                            textGraphics.putString(column, row, "^");
                            column++;
                            i++;
                            continue;
                        }

                        default -> throw new Exception("^ not followed by character");
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

    public static void printf(int column, int row, String text, Object... args) throws Exception {
        print(column, row, String.format(text, args));
    }
}
