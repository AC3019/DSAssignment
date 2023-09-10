package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Allows writing any string to a file
 */
public class FileStringWriter {
    
    /**
     * Writes the csvString to the fileName (.csv will be appended to the end of file name if unspecified), the file will be placed in the folder `exports`
     * @param fileName
     * @param csvString
     * @return false if file cannot be written to
     */
    public static boolean writeToCSV(String fileName, String csvString) {
        // create the directory exports if not exist
        new File("exports").mkdir();
    
        if (!fileName.endsWith(".csv"))
            fileName += ".csv";
        
        try {
            FileWriter writer = new FileWriter("exports/" + fileName);
            writer.write(csvString);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Writes the reportString to the fileName (.txt will be appended to the end of file name if unspecified), the file will be placed in the folder `generated_reports`, generally for generated reports
     * @param fileName
     * @param reportString
     * @return
     */
    public static boolean writeReportToTxt(String fileName, String reportString) {
        // create the directory exports if not exist
        new File("generated_reports").mkdir();
    
        if (!fileName.endsWith(".txt"))
            fileName += ".txt";
        
        try {
            FileWriter writer = new FileWriter("generated_reports/" + fileName);
            writer.write(reportString);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
