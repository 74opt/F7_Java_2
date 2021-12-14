package F7;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public class Lanterna {
    private static Terminal terminal;
    private static Screen screen;
    private static TextGraphics textGraphics;
    private static int column;
    private static int row;
    // TODO: MAKE SCROLLING TEXT METHOD
    private static final int SCROLL = 45; //taken from Utils.SCROLL

    // Test
    private static Thread keyboardListen = new Thread(() -> {
        while (true) {
            try {
                KeyStroke keyPressed = terminal.pollInput();

                if (keyPressed != null) {
                    if (keyPressed.getKeyType().equals(KeyType.F7)) {
                        System.exit(0);
                    }

                    System.out.print(keyPressed.getCharacter());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Deprecated
    public static Terminal getTerminal() {return terminal;}

    public static Screen getScreen() {return screen;}

    public static int getGlobalColumn() {return column;}

    public static int getGlobalRow() {return row;}
    
    public static void startScreen() throws IOException {
        terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(480, 50)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        textGraphics = screen.newTextGraphics();
        column = 1;
        row = 1;

        screen.startScreen();
        //keyboardListen.start();
    }

    public static void clear() throws IOException {
        column = 1;
        row = 1;

        screen.clear();
        screen.refresh();
    }



    // Printing to terminal
    // TODO: implement Gordon's ^ thing for coloring
    public static void print(int column, int row, String text) throws Exception {
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
    public static void print(String text) throws Exception {
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

    public static void println(String text) throws Exception {
        print(text);
        row++;
        column = 1;
    }
}
