package utility;

import java.util.function.Consumer;

import adt.HashMap;

/**
 * Every column should have the same number of elements to print, ideally not too long
 * Please use null to pad the column array until same size with every other column to not risk losing display of some data 
 * @ref Box drawing utf-8 chars: https://en.wikipedia.org/wiki/Box-drawing_character
 * However, normal fonts don't support those as seen in ***NETBEANS TERMINAL***
 * @ref **should be font safe** box drawing chars: https://gist.github.com/dsample/79a97f38bf956f37a0f99ace9df367b9
 * Sadly both also unsupported ***BY NETBEANS TERMINAL***
 * 
 * @author xuanbin
 */
public class TableBuilder {

    // key must be string, for the column heading
    private HashMap<String, Object[]> table;

    public TableBuilder() { 
        this.table = new HashMap<>();
    }

    // colsDatas should have same length as colsName
    public TableBuilder(String[] colsName, Object[][] colsDatas) {
        this.table = new HashMap<>();
        
        for (int i = 0; i < colsName.length; i++) {
            this.table.put(colsName[i], colsDatas[i]);
        }
    }

    // return itself to facilitate method chaining
    public TableBuilder addColumn(String colName, Object[] colData) {
        if (this.hasColumn(colName)) {
            System.out.println("Table already has column [" + colName + "], ignoring...");
            return this;
        }
        this.table.put(colName, colData);
        return this;
    }

    public TableBuilder removeColumn(String colName) {
        this.table.remove(colName);

        return this;
    }

    // whether the column already exists in the table
    public boolean hasColumn(String colName) {
        return this.table.containsKey(colName);
    }

    public String generateTableString(boolean showNumber, String tableHeading) {
        StringBuilder sb = new StringBuilder();

        // custom function, lazy write \n every time ;))
        Consumer<String> appendWithNewLine = (String s) -> {
            sb.append(s + "\n");
        };

        int numberColumnSize = 0;
        
        String[] keys = this.table.getKeys(String.class);
        int[] spaceForColumn = new int[keys.length];

        // dou ntg to print tf
        if (keys.length <= 0)
            return "";

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
        int rowLen = 0; // supposed to be 2 for first and last char `|`, but for top and bottom row, dw print these
        
        if (showNumber) {
            // calculate the number column size
            numberColumnSize = String.valueOf(totalRows).length();
            rowLen += numberColumnSize + 2; // + 2 for surrounding space in number column
        }

        // calculate every column's size
        for (int i: spaceForColumn) {
            rowLen += i + 3; // + 1 for the dividers among the columns, + 2 for the padding of the columns
        }

        // table heading
        if (!tableHeading.isBlank())
            appendWithNewLine.accept(
                tableHeading.length() >= numberColumnSize 
                    ? tableHeading : Formatter.centerString(tableHeading, rowLen + 1) // rowLen + 1 for the space in front
            );

        // top row
        appendWithNewLine.accept(" " + StringUtil.fillString("-", rowLen));
        // System.out.print("\u250C\u2500"); // utf-8 encoding
        // if (showNumber)
        //     System.out.print(
        //         (Formatter.padLeft(" ", numberColumnSize) + " \u252C ").replace(" ", "\u2500")
        //     );
        // System.out.print(Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500"));
        // for (int i = 1; i < keys.length; i++) {
        //     System.out.print(
        //         ("\u252C\u2502 " + Formatter.padRight(" ", spaceForColumn[i])).replace(" ", "\u2500")
        //     );
        // }
        // System.out.println("\u2500\u2510"); // close the last column

        // print table heading
        // System.out.print("\u2502 ");
        sb.append("| ");
        if (showNumber)
            sb.append(Formatter.padLeft("#", numberColumnSize) + " | ");

        sb.append(Formatter.padRight(keys[0], spaceForColumn[0]));
        for (int i = 1; i < keys.length; i++) {
            sb.append(" | " + Formatter.padRight(keys[i], spaceForColumn[i]));
        }
        sb.append(" |\n"); // close the last column

        // divider between header row and data rows
        sb.append("|-");
        if (showNumber)
            sb.append(
                // (
                //     Formatter.padLeft("-", numberColumnSize) + "\u2500\u253C\u2500"
                // ).replace(" ", "-")
                StringUtil.fillString("-", numberColumnSize) + "-|-"
            );
        
        sb.append(
            // Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500")
            StringUtil.fillString("-", spaceForColumn[0])
        );

        for (int i = 1; i < keys.length; i++) {
            sb.append(
                // (
                //     "\u2500\u253C\u2500" + Formatter.padRight(" ", spaceForColumn[i])
                // ).replace(" ", "\u2500")
                "-|-" + StringUtil.fillString("-", spaceForColumn[i])
            );
        }
        // System.out.print("\u2500\u2524\n");
        sb.append("-|\n");

        // finally, print the rows
        for (int i = 0; i < totalRows; i++) {
            // System.out.print("\u2502 ");
            sb.append("| ");
            if (showNumber) {
                // System.out.print(Formatter.padLeft(String.valueOf(i), numberColumnSize) + " \u2502 ");
                sb.append(Formatter.padLeft(String.valueOf(i), numberColumnSize) + " | ");
            }

            // first column without show number cannot print ` | `, so make it solo
            sb.append(
                Formatter.padRight(
                    this.table.get(keys[0])[i] == null ? "" : this.table.get(keys[0])[i].toString()
                    , spaceForColumn[0]
                )
            );

            // the other columns
            for (int j = 1; j < keys.length; j++) {
                sb.append(
                    // " \u2502 " +
                    " | " +
                    Formatter.padRight(
                        this.table.get(keys[j])[i] == null ? "" : this.table.get(keys[j])[i].toString()
                        , spaceForColumn[j]
                    )
                );
            }
            // close the row
            // System.out.println(" \u2502");
            appendWithNewLine.accept(" |");
        }

        // bottom row
        appendWithNewLine.accept(" " + StringUtil.fillString("-", rowLen));
        // System.out.print("\u2514\u2500");
        // if (showNumber)
        //     System.out.print(
        //         (Formatter.padLeft(" ", numberColumnSize) + " \u2534 ").replace(" ", "\u2500")
        //     );
        // System.out.print(Formatter.padRight(" ", spaceForColumn[0]).replace(" ", "\u2500"));
        // for (int i = 1; i < keys.length; i++) {
        //     System.out.print(
        //         ("\u2534\u2502 " + Formatter.padRight(" ", spaceForColumn[i])).replace(" ", "\u2500")
        //     );
        // }
        // System.out.println("\u2500\u2518"); // close the last column

        return sb.toString();
    }

    public String generateCSVString(boolean includeNumber, boolean includeHeading) {
        StringBuilder sb = new StringBuilder();
        String[] keys = this.table.getKeys(String.class);
        if (keys.length <= 0)
            return ""; // gud job ntg agn
        
        // class design: assume all data has the same length
        int dataLength = this.table.get(keys[0]).length;

        if (includeHeading) {
            if (includeNumber)
                sb.append("#,");
            for (int i = 0; i < keys.length - 1; i++) // don't want include last one
                sb.append(keys[i] + ",");
            sb.append(keys[keys.length - 1]); // append the last one without the last comma
        }

        for (int i = 0; i < dataLength; i++) { // don't want include last one
            for (int j = 0; j < keys.length - 1; j++) { // don't want include last one
                Object tempObj = this.table.get(keys[j])[i];
                String temp = tempObj == null ? "" : tempObj.toString();
                // for simple CSV, if content has ",", need wrap content with ""
                if (temp.contains(","))
                    temp = "\"" + temp + "\"";
                sb.append(temp + ",");
            }
            
            // same handling for the last column of data, just without comma
            Object tempObj = this.table.get(keys[keys.length - 1])[i];
            String temp = tempObj == null ? "" : tempObj.toString();
            if (temp.contains(","))
                temp = "\"" + temp + "\"";
            sb.append(temp); // append the last one without the last comma
        }

        return sb.toString();
    }

    public void printTable(boolean showNumber, String tableHeading) {
        System.out.println(this.generateTableString(showNumber, tableHeading));
    }

    public void printTable(boolean showNumber) {
        this.printTable(showNumber, "");
    }

}
