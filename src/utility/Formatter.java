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

    public static String centerString(String s, int n) {
        int spaceSize = n - s.length();
        int prefixSize = spaceSize / 2;
        int suffixSize = (spaceSize + 1) / 2;

        if (prefixSize != 0 && suffixSize != 0)
           return String.format("%" + prefixSize + "s%s%" + suffixSize + "s", "", s, "");
        else if (prefixSize == 0 && suffixSize != 0)
            return String.format("%s%" + suffixSize + "s", "", s, "");
        else if (prefixSize != 0 && suffixSize == 0)
            return String.format("%" + prefixSize + "s%s", "", s, "");
        else
            return s;
    }
    
}
