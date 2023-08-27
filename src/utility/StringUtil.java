package utility;

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
}
