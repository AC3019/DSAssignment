/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

/**
 *
 * @author yong
 */
import entity.Tutor;
import adt.HashMap;
import adt.MapInterface;
import boundary.TutorManagementUI;
public class TutorManagement {
    MapInterface<Integer, Tutor> tutors = new HashMap<>();
    Tutor tutorObj=new Tutor();
    public static void main(String[] args) {
        TutorManagement tm = new TutorManagement();
        
    }
    public static void addNewTutor(MapInterface<Integer,Tutor>tutors){
         if (tutors.size() > 0) {
            boolean containsDuplicate = false;
            for (MapInterface.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
                if (tutor.getValue().getName().equals(tutorObj.getName())) {// Duplicate name
                    containsDuplicate = true;
                    break;
                }
            }

            if (containsDuplicate) {
                System.out.println("This tutor already exists in the list.");
            } else {
                tutors.put(id, tutorObj);
                id++;
            }
        } else {
            // Will only come here for first tutor addition
            tutors.put(id, tutorObj);
            id++;
        }
    }
    
}
