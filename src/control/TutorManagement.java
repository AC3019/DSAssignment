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
    HashMap<Integer, Tutor> tutors = new HashMap<>();
    TutorManagementUI tmu = new TutorManagementUI();
    static int tutorCount=1;
    
    public static void main(String[] args) {
        TutorManagement tm = new TutorManagement();
        
    }
    public  void addNewTutor(){
        Tutor tutorObj = tmu.tutorInput();
         if (tutors.size() > 0) {
            boolean containsDuplicate = tutors.contains(tutorCount);

            if (containsDuplicate) {
                System.out.println("This tutor already exists in the list.");
            } else {
                tutors.put(tutorCount, tutorObj);
                tutorCount++;
            }
        } else {
            // Will only come here for first tutor addition
            tutors.put(tutorCount, tutorObj);
            tutorCount++;
        }
    }
    public void removeTutor(){
        if (tutors.containsKey(tutorCount)) {
            tutors.remove(tutorCount);
        } else {
            System.out.println("Id invalid");
        }

    }
    public Tutor searchTutor(){
         return tutors.get(tutorCount);

    }TutorMan
    public void amendTutorDetails(){
        Tutor tutor = tutors.get(tutorCount);
        TutorManagementUI.amendTutorDetailsData();
        if (tutor != null) {
            tutor.setName(newName);
            tutor.setSubject(newSubject);
            System.out.println("Tutor details amended successfully.");
        } else {
            System.out.println("Tutor not found.");
        }
    }
    public void find
}
