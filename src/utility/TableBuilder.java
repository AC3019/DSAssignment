package utility;

import adt.ArrayList;

public class TableBuilder {

    private ArrayList<String> colsName;
    private ArrayList<String[]> cols;

    public TableBuilder() { 
        this.colsName = new ArrayList<>();
        this.cols = new ArrayList<>();
    }

    public TableBuilder(String[] colsName, String[][] colsDatas) {
        this.colsName = new ArrayList<>(colsName);
        this.cols = new ArrayList<>(colsDatas);
    }

    public TableBuilder addColumn(String colName, String[] colData) {
        this.colsName.insert(colName);
        this.cols.insert(colData);

        return this;
    }

    public void printTable(boolean showNumber) {

        

        if (showNumber) {

        }
    }

}
