package F7;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Utils contains static variables and methods to aid in several features in the rest of F7.
 */
public class Utils {
    /** .042 seconds, used for the screen's refresh rate */
    public static final int TWENTY_FOUR_FRAMES = 42;
    /** 2 seconds, used as a standard waiting time for Thread.sleep() */
    public static final int STANDARD = 2000;

    /** 1.5 seconds, used as a standard waiting time for Thread.sleep() */
    public static final int QUICK_STANDARD = 1500;
    /** .045 seconds, used for the time between each character appearing in Utils.scrollText() */
    public static final int SCROLL = 45;

    /** Paths to save the game */
    public static final String PLAYER_SAVE_PATH = "src\\main\\java\\F7\\save\\player.json";
    public static final String MAP_SAVE_PATH = "src\\main\\java\\F7\\save\\map.json";

    /** Standard decimal format used in the game */
    public static final DecimalFormat DOUBLE_DECIMAL = new DecimalFormat("0.0#");

    // Random
    private static Random random = new Random();

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
     * @param value the item that is being compared
     * @param total the total amount of the item, being 0 or above
     * @param current the current amount of the item, 0 or above
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

    /**
     * Returns a bar highlighting the percentage of a value versus the percentage of that value filled
     * @param length length of the bar
     * @param total the total amount of the value
     * @param current the current amount of the value
     * @param color color of the bar
     * @return bar that displays the percentage of the value versus how much of that value is filled
     */
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
        return randomRange(0, 101) <= percent;
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
