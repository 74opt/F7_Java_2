package F7;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class LanternaUtils {
    private static Terminal terminal;
    private static Screen screen;

    static {
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static final TextGraphics textGraphics = screen.newTextGraphics();

    public static void render() {

    }

    public static void print(int column, int row, String text) {
        for (char character : text.toCharArray()) {
            if (character == '\n') {
                row++;
                column = 0;
            } else {
                textGraphics.putString(column, row, character + "");
                column++;
            }
        }
    }
}
