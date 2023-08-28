package utility;

/**
 * Provides utility function to format strings
 * @author xuanbin
 */
public class Formatter {
    public static String padLeft(String s, int padding) {
        return String.format("%" + padding + "s", s);
    }
    public static String padRight(String s, int padding) {
        return String.format("%-" + padding + "s", s);
    }
}
