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
        if (tutors.contains(tutorCount)) {
            tutors.remove(tutorCount);
        } else {
            System.out.println("Id invalid");
        }

    }
    public Tutor searchTutor(){
         return tutors.get(tutorCount);

    }
    public void amendTutorDetails(){
        Tutor t=tmu.amendTutorDetailsData();
       
        Tutor tutor = tutors.get(t.getId());
        if (tutor != null) {
            tutor.setName(t.getName());
            tutor.setSubject(t.getSubject());
            System.out.println("Tutor details amended successfully.");
        } else {
            System.out.println("Tutor not found.");
        }
    }
    
}
