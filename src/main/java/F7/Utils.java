package F7;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Utils contains static variables and methods to aid in several features in the rest of F7
 */
public class Utils {
    // Time values in milliseconds
    // TODO: make vars below private (mr holmer please why)

    /** .042 seconds, used for the screen's refresh rate */
    public static final int TWENTY_FOUR_FRAMES = 42;
    /** 2 seconds, used as a standard waiting time for Thread.sleep() */
    public static final int STANDARD = 2000;
    /** 1.5 seconds, used as a standard waiting time for Thread.sleep() */
    public static final int QUICK_STANDARD = 1500;
    /** .045 seconds, used for the time between each character appearing in Utils.scrollText() */
    public static final int SCROLL = 45;

    /** Path to save the game */
    public static final String SAVE_PATH = "src\\main\\java\\F7\\save.json";

    /** Standard decimal format used in the game */
    public static final DecimalFormat DOUBLE_DECIMAL = new DecimalFormat("0.0#");

    // Random
    private static Random random = new Random();

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
     * @deprecated Unusable with lanterna
     */
    @Deprecated
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Creates scrolling text where each character appears one at a time.
     * @param text text to print out
     * @param time time between each character in milliseconds
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is
     *                              interrupted, either before or during the activity.
     * @deprecated Unusable with lanterna
     */
    @Deprecated
    public static void scrollText(String text, long time) throws InterruptedException {
        for (char character : text.toCharArray()) {
            System.out.print(character);
            Thread.sleep(time);
        }
    }

    /**
     * Returns a string that contains a percentage of how much of an item left out 
     * of the total possible amount of items, comparing integers.
     * @param value the item that is being compared
     * @param total the total amount of the item
     * @param current the current amount of the item
     * @param color color to display the item and values
     * @return string that displays the item, the current amount out of the total
     *         possible amount, and the percentage.
     */
    public static String outOf(String value, int total, int current, int color) {
        return String.format("%s %s out of %s (%s%%)", Ansi.colorize(value, Attribute.TEXT_COLOR(231)),
                Ansi.colorize(String.valueOf(current), Attribute.TEXT_COLOR(color)),
                Ansi.colorize(String.valueOf(total), Attribute.TEXT_COLOR(color)),
                DOUBLE_DECIMAL.format((((double) current) / total) * 100));
    }

    /**
     * Returns a string that contains a percentage of how much of an item left out 
     * of the total possible amount of items, comparing doubles.
     * @param value the item that is being compared
     * @param total the total amount of the item
     * @param current the current amount of the item
     * @param color color to display the item and values
     * @return string that displays the item, the current amount out of the total
     *         possible amount, and the percentage.
     */
    public static String outOf(String value, double total, double current, int color) {
        return String.format("%s %s out of %s (%s%%)", Ansi.colorize(value, Attribute.TEXT_COLOR(231)),
                Ansi.colorize(String.valueOf(current), Attribute.TEXT_COLOR(color)),
                Ansi.colorize(String.valueOf(total), Attribute.TEXT_COLOR(color)),
                DOUBLE_DECIMAL.format((current / total) * 100));
    }

    /**
     * Generates a random integer from 0 to 100 and returns true or false depending 
     * on the percent chance of success.
     * @param percent the chance of success out of 100
     * @return true if the percentage is greater than or equal to the random integer
     *         generated.
     */
    public static boolean chance(int percent) {
        return random.nextInt(101) <= percent;
    }

    /**
     * Returns a random integer between a given minimum (inclusive) and maximum (exclusive)
     * @param min minimum possible integer to generate
     * @param max maximum possible integer to generate minus one
     * @return random integer between min and max
     */
    public static int randomRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Rounds a double to a specified decimal place.
     * @param doub the double to be rounded
     * @param places the amount of decimal
     * @return double rounded to a specified decimal place
     */
    public static double round(double doub, int places) {
        return Math.round(doub * (Math.pow(10, places)) / Math.pow(10, places));
    }
}
