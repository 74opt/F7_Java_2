package F7;

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

    public static void startScreen() throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        textGraphics = screen.newTextGraphics();

        screen.startScreen();
    }

    //? do i need?
    public static void render() { }

    public static void clear() throws IOException {
        screen.clear();
        screen.refresh();
    }

    //? should i make a global variable to keep track of current column and row?
    //TODO: implement color text http://mabe02.github.io/lanterna/apidocs/3.0/com/googlecode/lanterna/class-use/TextColor.html
    public static void print(int column, int row, String text) throws IOException {
        final int finalColumn = column;

        for (char character : text.toCharArray()) {
            switch (character) { // will add more cases with more escape chars
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
}
