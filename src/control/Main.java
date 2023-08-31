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

    private static TutorManagement tutorManagementControl = new TutorManagement();
    private static TutorialGroupManagement tutorialaGroupManagementControl = new TutorialGroupManagement();
    private static CourseManagement courseManagementControl = new CourseManagement();
    private static TeachingAssignment teachingAssignmentControl = new TeachingAssignment();

    public static void main(String[] args) {
        System.out.println("I hate Netbeans, faQ");
    }
    
    // TODO: make save and load snapshot functions
    public void saveSnapshot() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(
                new FileOutputStream("autosave/snapshot.dat")
            );
            writer.writeObject(this);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSnapshot() {
        try {
            ObjectInputStream reader = new ObjectInputStream(
                new FileInputStream("autosave/snapshot.dat")
            );
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
