package control;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import adt.ArrayList;
import boundary.MainUI;

/**
 * Implements serializable to allow saving of snapshots
 * @author xuanbin, Neoh Soon Chee, yong
 */
public class Main implements Serializable {

    private TutorManagement tutorManagementControl = new TutorManagement();
    private TutorialGroupManagement tutorialaGroupManagementControl = new TutorialGroupManagement();
    private CourseManagement courseManagementControl = new CourseManagement();
    private TeachingAssignment teachingAssignmentControl = new TeachingAssignment();
    private static MainUI ui = new MainUI();

    private void autosaveSnapshot() {
        // autosave snapshot of system once exit any subsystem, filename will be autosave/dd-MM-yy hhmmss.dat
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hhmmss");
        String fileName = sdf.format(new Date());
        saveSnapshot(this, "autosave/" + fileName);
    }

    public static void main(String[] args) {
        Main m = new Main(); // to be able to save snapshot

        while (true) {
            int choice = ui.getMenuChoice();
            switch (choice) {
                case 0:
                    m.tutorManagementControl.displayMenu(m.teachingAssignmentControl);
                    m.autosaveSnapshot();
                    break;
                case 1:
                    m.tutorialaGroupManagementControl.displayTutGrpManagement(m.teachingAssignmentControl);
                    m.autosaveSnapshot();
                    break;
                case 2:
                    m.teachingAssignmentControl.main(m.tutorManagementControl, m.tutorialaGroupManagementControl, m.courseManagementControl);
                    m.autosaveSnapshot();
                    break;
                case 3:
                    // user save, save to snapshots/name
                    String fileName = ui.getSnapshotName();
                    m.saveSnapshot(m, "snapshots/" + fileName);
                    break;
                case 4:
                    // snapshots can be saved to two locations only, get all the files out
                    // snapshots/ folder
                    File[] snapshotDir = new File("snapshots/").listFiles();
                    // autosave/ folder
                    File[] autosaveDir = new File("autosave/").listFiles();
                    ArrayList<String> snapShotFileNames = new ArrayList<>() {{
                        if (snapshotDir != null) {
                            for (File f: snapshotDir) {
                                insert("snapshots/" + f.getName());
                            }
                        }
                        if (autosaveDir != null) {
                            for (File f: autosaveDir) {
                                insert("autosave/" + f.getName());
                            }
                        }
                    }};
                    if (snapShotFileNames.getNumberOfEntries() <= 0) {
                        ui.warn("There is no saved snapshot in the system yet");
                        break;
                    }
                    String[] snapshotChoices = snapShotFileNames.toArray(String.class);
                    int snapshotChoice = ui.getSnapshotChoice(snapshotChoices);
                    Main temp = m.loadSnapshot(snapshotChoices[snapshotChoice]);
                    if (temp != null) {
                        m = temp;
                        ui.snapShotLoaded(snapshotChoices[snapshotChoice]);
                    }
                    break;
                case 5: // bye bye
                    return;
            }
        }
    }
    
    public void saveSnapshot(Main state, String fileName) {
        try {
            // handle directory as well (denoted by a /) shud be always a level one dir only
            // cuz shud be snapshots for user saved and autosave for autosaved
            String[] dirs = fileName.split("/");
            if (dirs.length > 1) {
                String dir = dirs[0];
                new File(dir).mkdir(); // create the directory if not present
            }
            ObjectOutputStream writer = new ObjectOutputStream(
                new FileOutputStream(fileName + ".dat")
            );
            writer.writeObject(state);
            writer.close();
            ui.snapShotSaved(fileName + ".dat");
        } catch (Exception _e) {
            System.out.println("Can't save snapshot at this time, please try again later");
        }
    }

    public Main loadSnapshot(String fileName) {
        try {
            ObjectInputStream reader = new ObjectInputStream(
                new FileInputStream(fileName)
            );
            Main loaded = (Main) reader.readObject();
            reader.close();
            return loaded;
        } catch (Exception _e) {
            System.out.println("Can't load this snapshot, possible system version incompatible, please try again with another snapshot");
            return null;
        }
    }

}
