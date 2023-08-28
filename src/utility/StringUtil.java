package utility;

/**
 * Provides utility to perform stuffs with String
 * @author xuanbin
 */
public class StringUtil {
    /**
     * Finds the longest string in a string array
     */
    public static String longestStringInArray(Object[] strs) {
        String currLongest = strs[0].toString();

        for (int i = 1; i < strs.length; i++) {
            if (strs[i].toString().length() > currLongest.length())
                currLongest = strs[i].toString();
        }

        return currLongest;
    }

    /**
     * Fills the string with s eg. `fillString("-", 3)` -> "---"
     * @param s
     * @param len
     * @return
     */
    public static String fillString(String s, int len) {
        return String.format("%" + len + "s", " ").replace(" ", s);
    }
}
