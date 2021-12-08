package F7;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import java.text.DecimalFormat;
import java.util.*;

public class Utils {
    // Time values in milliseconds
    public static final int TWENTY_FOUR_FRAMES = 42; // .042 seconds
    public static final int STANDARD = 2000; // 2 seconds
    public static final int QUICK_STANDARD = 1500; // 1.5 seconds
    public static final int SCROLL = 45; // .045 seconds

    // Save file path
    public static final String SAVE_PATH = "src\\main\\java\\F7\\save.json";

    // Decimal format
    public static final DecimalFormat DOUBLE_DECIMAL = new DecimalFormat("0.0#");

    // Random
    public static final Random random = new Random();

    /**
     * Takes in an input from the user with text to prompt was the user should input.
     * @param output text prompted to the user
     * @param caseSensitive if the text inputted should keep its case
     * @return string that was inputted by the user, put into lowercase if case sensitivity is false
     */
    public static String input(String output, boolean caseSensitive) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(Ansi.colorize(output, Attribute.TEXT_COLOR(231)) +
                Ansi.colorize("\n> ", Attribute.TEXT_COLOR(40)));

        if (caseSensitive) {
            return scanner.nextLine();
        } else {
            return scanner.nextLine().toLowerCase(Locale.US);
        }
    }

    /**
     * Takes in an input from the user.
     * @param caseSensitive if the text inputted should keep its case
     * @return string that was inputted by the user, put into lowercase if case sensitivity is false
     */
    public static String input(boolean caseSensitive) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(Ansi.colorize("> ", Attribute.TEXT_COLOR(40)));

        if (caseSensitive) {
            return scanner.nextLine();
        } else {
            return scanner.nextLine().toLowerCase(Locale.US);
        }
    }

    /**
     * Tells the user if their input wasn't allowed.
     */
    public static void invalidOption() throws InterruptedException {
        System.out.println("Invalid Option.");
        Thread.sleep(QUICK_STANDARD);
    }

    /**
     * Clears the terminal.
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // made bc bored i dont even know if im gonna use this lmao
    // but if i do i should make a final var for a good scroll time
    /**
     * Creates scrolling text where each character appears one at a time.
     * @param text text to print out
     * @param time time between each character in milliseconds
     * @throws InterruptedException
     */
    public static void scrollText(String text, long time) throws InterruptedException {
        for (char character : text.toCharArray()) {
            System.out.print(character);
            Thread.sleep(time);
        }
    }

    //TODO
    /**
     * Returns a string that contains a percentage of how much is left out of a total.
     * @param value the item that is being compared
     * @param total the total amount of the item
     * @param current the current amount of the item
     * @param color 
     * @return string that displays the 
     */
    public static String outOf(String value, int total, int current, int color) {
        return String.format("%s %s out of %s (%s%%)", Ansi.colorize(value, Attribute.TEXT_COLOR(231)),
                Ansi.colorize(String.valueOf(current), Attribute.TEXT_COLOR(color)),
                Ansi.colorize(String.valueOf(total), Attribute.TEXT_COLOR(color)),
                DOUBLE_DECIMAL.format((((double) current) / total) * 100));
    }

    public static String outOf(String value, double total, double current, int color) {
        return String.format("%s %s out of %s (%s%%)", Ansi.colorize(value, Attribute.TEXT_COLOR(231)),
                Ansi.colorize(String.valueOf(current), Attribute.TEXT_COLOR(color)),
                Ansi.colorize(String.valueOf(total), Attribute.TEXT_COLOR(color)),
                DOUBLE_DECIMAL.format((current / total) * 100));
    }

    public static boolean chance(int percent) {
        if (random.nextInt(101) <= percent) {
            return true;
        } else {
            return false;
        }
    }

    public static int randomRange(int min, int max) { // first value inclusive, second value exclusive
        return random.nextInt(max - min) + min;
    }

    public static double round(double doub, int places) {
        return ((double) ((int) doub * Math.pow(10, places))) / Math.pow(10, places);
    }
}
