package boundary;

import java.text.SimpleDateFormat;
import java.util.Date;

import utility.Input;

public class MainUI {

    public int getMenuChoice() {
        return Input.getChoice("Available Functions", "Enter your choice: ", new String[] {
            "Tutor Management",
            "Tutorial Group Management",
            "Teaching Assignment",
            "Save System Snapshot",
            "Load System Snapshot",
            "Bye bye"
        }, (s) -> s);
    }

    // return desired filename
    public String getSnapshotName() {
        Input.reinit();
        String fileName = Input.getString("Enter the filename of the snap shot (leave blank to use autosave format, current datetime): ");

        if (fileName.isBlank()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
            fileName = sdf.format(new Date());
        }

        return fileName;
    }

    public void snapShotSaved(String location) {
        System.out.println("Saving system snapshot...");
        System.out.println("Snapshot successfully saved to " + location);
    }

}
