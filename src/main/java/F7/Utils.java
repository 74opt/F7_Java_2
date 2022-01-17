package F7;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Utils contains static variables and methods to aid in several features in the rest of F7
 */
public class Utils {
    /* Time values in milliseconds,
       used as a standard instead of
       having to remember every
       number to use in certain cases
     */

    // TODO: use the getters please and make private
    /** .042 seconds, used for the screen's refresh rate */
    private static final int TWENTY_FOUR_FRAMES = 42;
    /** 2 seconds, used as a standard waiting time for Thread.sleep() */
    private static final int STANDARD = 2000;

    // TODO: these stay public until i deal with DeathMenu
    /** 1.5 seconds, used as a standard waiting time for Thread.sleep() */
    public static final int QUICK_STANDARD = 1500;
    /** .045 seconds, used for the time between each character appearing in Utils.scrollText() */
    public static final int SCROLL = 45;

    /** Paths to save the game */
    private static final String PLAYER_SAVE_PATH = "src\\main\\java\\F7\\save\\player.json";
    private static final String MAP_SAVE_PATH = "src\\main\\java\\F7\\save\\map.json";

    /** Standard decimal format used in the game */
    private static final DecimalFormat DOUBLE_DECIMAL = new DecimalFormat("0.0#");

    // Random
    private static Random random = new Random();

    /**
     * @return Time for 24 frame refresh rate
     */
    public static int getTWENTY_FOUR() {return TWENTY_FOUR_FRAMES;}

    /**
     * @return Time for standard wait time
     */
    public static int getSTANDARD() {return STANDARD;}

    /**
     * @return Time for quicker standard wait time
     */
    public static int getQUICK_STANDARD() {return QUICK_STANDARD;}

    /**
     * @return Time for best scrolling text time
     */
    public static int getSCROLL() {return SCROLL;}

    /**
     * @return File path for where F7 saves the player
     */
    public static String getPLAYER_SAVE_PATH() {return PLAYER_SAVE_PATH;}

    /**
     * @return File path for where F7 saves the map
     */
    public static String getMAP_SAVE_PATH() {return MAP_SAVE_PATH;}

    /**
     * @return Standard DecimalFormat for presenting doubles with 2 decimal places
     */
    public static DecimalFormat getDOUBLE_DECIMAL() {return DOUBLE_DECIMAL;}

    /**
     * Takes in an input from the user with text to prompt was the user should input.
     * @param output text prompted to the user, best if argument isn't empty
     * @param caseSensitive if the text inputted should keep its case
     * @return string that was inputted by the user, put into lowercase if case sensitivity is false
     * @deprecated Unusable with Lanterna
     */
    @Deprecated
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
     * @deprecated Unusable with Lanterna
     */
    @Deprecated
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
     * @deprecated Unusable with Lanterna
     */
    @Deprecated
    public static void invalidOption() throws InterruptedException {
        System.out.println("Invalid Option.");
        Thread.sleep(QUICK_STANDARD);
    }

    /**
     * Clears the terminal.
     * @deprecated Unusable with Lanterna
     */
    @Deprecated
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Creates scrolling text where each character appears one at a time.
     * @param text text to print out, given that text is not an empty string
     * @param time time between each character in milliseconds
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is
     *                              interrupted, either before or during the activity.
     * @deprecated Unusable with Lanterna
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
     * @param total the total amount of the item, being 0 or above
     * @param current the current amount of the item
     * @param color color to display the item and values between 0 and 255
     * @return string that displays the item, the current amount out of the total
     *         possible amount, and the percentage.
     */
    public static String outOf(String value, int total, int current, String color) {
        return String.format("%s %s ^Gout of %s ^G(%s%%)",
                "^W" + value,
                color + current,
                color + total,
                DOUBLE_DECIMAL.format((((double) current) / total) * 100));
    }

    /**
     * Returns a string that contains a percentage of how much of an item left out 
     * of the total possible amount of items, comparing doubles.
     * Precondition: total is not negative, current is not negative, color is between 0 and 255
     * @param value the item that is being compared
     * @param total the total amount of the item, being 0 or above
     * @param current the current amount of the item
     * @param color color to display the item and values between 0 and 255
     * @return string that displays the item, the current amount out of the total
     *         possible amount, and the percentage.
     */
    public static String outOf(String value, double total, double current, String color) {
        return String.format("%s %s ^Gout of %s ^G(%s%%)",
                "^W" + value,
                color + current,
                color + total,
                DOUBLE_DECIMAL.format(((current) / total) * 100));
    }

    public static String percentBar(int length, double total, double current, String color) {
        String bar = color;
        double percentFilled = current / total;

        int fillAmount = (int) (percentFilled * length);

        if (fillAmount == 0 && percentFilled != 0) {
            fillAmount = 1;
        }

        length -= fillAmount;

        for (int i = 0; i < fillAmount; i++) {
            bar += "█";
        }

        for (int i = 0; i < length; i++) {
            bar += "░";
        }

        return bar;
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
     * @param max maximum possible integer to generate minus one, must be greater than min
     * @return random integer between min and max
     */
    public static int randomRange(int min, int max) {
        if (max > min) {
            return (int) ((Math.random() * (max - min)) + min);
        } else {
            return -1;
        }
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
