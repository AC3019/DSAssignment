package utility;

import adt.HashMap;

/**
 * Every column should have the same number of elements to print, ideally not too long
 * Please use null to pad the column array until same size with every other column to not risk losing display of some data 
 * Box drawing ascii chars: https://en.wikipedia.org/wiki/Box-drawing_character
 */
public class TableBuilder {

    // key must be string, for the column heading
    private HashMap<String, Object[]> table;

    public TableBuilder() { 
        this.table = new HashMap<>();
    }

    public TableBuilder(String[] colsName, Object[][] colsDatas) {
        this.table = new HashMap<>();
        
        for (String s: colsName) {
            for (Object[] ss: colsDatas) {
                this.table.put(s, ss);
            }
        }
    }

    public TableBuilder addColumn(String colName, Object[] colData) {
        this.table.put(colName, colData);

        return this;
    }

    public void printTable(boolean showNumber) {

        int numberColumnSize = 0;
        
        String[] keys = this.table.getKeys(String.class);
        int[] spaceForColumn = new int[keys.length];

        // dou ntg to print tf
        if (keys.length <= 0)
            return;

        // get how long should a row be
        /**
         * 1. Loop over each column and their values
         * 2. find the longest one of them
         * 3. Assign respective index to spaceForColumn for later apply padding
         */
        for (int i = 0; i < keys.length; i++) {
            // get the ori array associated
            Object[] colData = this.table.get(keys[i]);
            // create a new array where the key and the element is together
            Object[] newArr = new Object[colData.length + 1];
            newArr[0] = keys[i];
            System.arraycopy(colData, 0, newArr, 1, colData.length);

            String longestInCol = StringUtil.longestStringInArray(newArr);
            spaceForColumn[i] = longestInCol.length();
        }

        // all columns should have the same number of elements, so can simply get one (same array size, dc if use null to pad the array)
        // already stated very clearly on top of this class, so if different, just risk some data won't display
        int totalRows = this.table.get(keys[0]).length;
        
        if (showNumber) {
            // calculate the number column size
            numberColumnSize = String.valueOf(totalRows).length();
        }

        // top row
        System.out.print("\u250C\u2500");
        if (showNumber)
            System.out.print(
                (Formatter.padLeft(" ", numberColumnSize) + " \u252C ").replace(" ", "\u2500")
            );
        System.out.print(Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500"));
        for (int i = 1; i < keys.length; i++) {
            System.out.print(
                ("\u252C\u2502 " + Formatter.padRight(" ", spaceForColumn[i])).replace(" ", "\u2500")
            );
        }
        System.out.println("\u2500\u2510"); // close the last column

        // print table heading
        System.out.print("\u2502 ");
        if (showNumber)
            System.out.print(Formatter.padLeft("#", numberColumnSize) + " \u2502 ");
        System.out.print(Formatter.padRight(keys[0], spaceForColumn[0]));
        for (int i = 1; i < keys.length; i++) {
            System.out.print(" \u2502 " + Formatter.padRight(keys[i], spaceForColumn[i]));
        }
        System.out.print(" \u2502\n"); // close the last column

        // divider between header row and data rows
        System.out.print("\u251C\u2500");
        if (showNumber)
            System.out.print(
                (
                    Formatter.padLeft("\u2500", numberColumnSize) + "\u2500\u253C\u2500"
                ).replace(" ", "-")
            );
        System.out.print(
            Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500")
        );
        for (int i = 1; i < keys.length; i++) {
            System.out.print(
                (
                    "\u2500\u253C\u2500" + Formatter.padRight(" ", spaceForColumn[i])
                ).replace(" ", "\u2500")
            );
        }
        System.out.print("\u2500\u2524\n");

        // finally, print the rows
        for (int i = 0; i < totalRows; i++) {
            System.out.print("\u2502 ");
            if (showNumber) {
                System.out.print(Formatter.padLeft(String.valueOf(i), numberColumnSize) + " \u2502 ");
            }

            // first column without show number cannot print ` | `, so make it solo
            System.out.print(
                Formatter.padRight(
                    this.table.get(keys[0])[i] == null ? "" : this.table.get(keys[0])[i].toString()
                    , spaceForColumn[0]
                )
            );

            // the other columns
            for (int j = 1; j < keys.length; j++) {
                System.out.print(
                    " \u2502 " +
                    Formatter.padRight(
                        this.table.get(keys[j])[i] == null ? "" : this.table.get(keys[j])[i].toString()
                        , spaceForColumn[j]
                    )
                );
            }
            // close the row
            System.out.println(" \u2502");
        }

        // bottom row
        System.out.print("\u2514\u2500");
        if (showNumber)
            System.out.print(
                (Formatter.padLeft(" ", numberColumnSize) + " \u2534 ").replace(" ", "\u2500")
            );
        System.out.print(Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500"));
        for (int i = 1; i < keys.length; i++) {
            System.out.print(
                ("\u2534\u2502 " + Formatter.padRight(" ", spaceForColumn[i])).replace(" ", "\u2500")
            );
        }
        System.out.println("\u2500\u2518"); // close the last column

    }

}
