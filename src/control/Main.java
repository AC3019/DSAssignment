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
        Main m = new Main(); // to be able to save snapshot
        m.tutorManagementControl.displayMenu(m.teachingAssignmentControl);

        // STUB, saving system snapshot
        m.saveSnapshot(m);
        // STUB, loading system snapshot
        m = m.loadSnapshot();
        System.out.println("I hate Netbeans, faQ");
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
