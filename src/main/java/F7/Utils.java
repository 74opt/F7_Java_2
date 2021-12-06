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

    public static String input(boolean caseSensitive) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(Ansi.colorize("> ", Attribute.TEXT_COLOR(40)));

        if (caseSensitive) {
            return scanner.nextLine();
        } else {
            return scanner.nextLine().toLowerCase(Locale.US);
        }
    }

    public static void invalidOption() throws InterruptedException {
        System.out.println("Invalid Option.");
        Thread.sleep(QUICK_STANDARD);
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // made bc bored i dont even know if im gonna use this lmao
    // but if i do i should make a final var for a good scroll time
    public static void scrollText(String text, long time) throws InterruptedException {
        for (char character : text.toCharArray()) {
            System.out.print(character);
            Thread.sleep(time);
        }
    }

    // public static String toJson(Object object) { //TODO: move this crap to mapmenu and fix please and also make it specific pls ty
    //     if (object == null) {
    //         return "\nnull\n"; 
    //     }

    //     Field[] fields = object.getClass().getDeclaredFields();
    //     String json = "{";

    //     // for each variable of the class
    //     for (Field field : fields) {
    //         field.setAccessible(true); // accesses private variables

    //         try {
    //             Object value = field.get(object); // value of variable

    //             // following if statements changes the value if needed
    //             // if value is string
    //             if (value instanceof String) {
    //                 value = "\"" + value + "\"";
    //             }

    //             // if value is array
    //             if (value.toString().charAt(0) == '[') { // RECORDS HAVE []
    //                 System.out.println(field.get(object));
    //                 ArrayList<String> array = new ArrayList<>();
    //                 String arrayString = "[";

    //                 for (Object item : (Object[]) field.get(object)) {
    //                     array.add(toJson(item));
    //                 }

    //                 for (String item : array) {
    //                     arrayString += item + ", ";
    //                 }

    //                 arrayString = arrayString.substring(0, arrayString.length() - 2) + "]";

    //                 for (int i = field.toString().length() - 1; i > 0; i--) { // TODO: work on formatting
    //                     if (field.toString().toCharArray()[i] == '.') {
    //                         json += String.format("\n\"%s\": %s,", field.toString().substring(i + 1), arrayString);
    //                         break;
    //                     }
    //                 }

    //                 continue;
    //             }

    //             // if value not primitive or a string or an array
    //             if (value.toString().contains(".") && !(field.get(object) instanceof Double) && !(field.get(object).toString().charAt(0) == '[')) {
    //                 value = toJson(field.get(object));
    //             }

    //             for (int i = field.toString().length() - 1; i > 0; i--) {
    //                 if (field.toString().toCharArray()[i] == '.') {
    //                     json += String.format("\n\"%s\": %s,", field.toString().substring(i + 1), value);
    //                     break;
    //                 }
    //             }
    //         } catch (Exception exception) {
    //             exception.printStackTrace();
    //         }
    //     }

    //     return json.substring(0, json.length() - 1) + "\n}";
    // }

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
