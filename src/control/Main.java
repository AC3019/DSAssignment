package control;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

    public static void main(String[] args) {
        MainUI ui = new MainUI();
        Main m = new Main(); // to be able to save snapshot

        while (true) {
            int choice = ui.getMenuChoice();
            switch (choice) {
                case 0:
                    m.tutorManagementControl.displayMenu(m.teachingAssignmentControl);
                    break;
                case 1:
                    m.tutorialaGroupManagementControl.displayTutGrpManagement(m.teachingAssignmentControl);
                    break;
                case 2:
                    m.teachingAssignmentControl.main(m.tutorManagementControl, m.tutorialaGroupManagementControl, m.courseManagementControl);
                    break;
                case 3: // bye bye
                    return;
            }
        }
        // STUB, saving system snapshot
        // m.saveSnapshot(m);
        // STUB, loading system snapshot
        // m = m.loadSnapshot();
    }
    
    // TODO: make save and load snapshot functions
    public void saveSnapshot(Main state) {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(
                new FileOutputStream("autosave/snapshot.dat")
            );
            writer.writeObject(state);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main loadSnapshot() {
        try {
            ObjectInputStream reader = new ObjectInputStream(
                new FileInputStream("autosave/snapshot.dat")
            );
            Main loaded = (Main) reader.readObject();
            reader.close();
            return loaded;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
