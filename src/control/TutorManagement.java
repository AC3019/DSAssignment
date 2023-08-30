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
//import adt.MapInterface;
import boundary.TutorManagementUI;
public class TutorManagement {
    HashMap<Integer, Tutor> tutors;
    TutorManagementUI tmu = new TutorManagementUI();
    //TutorManagement tm=new TutorManagement();
    static int tutorCount=1;
    
    public TutorManagement() {
        tutors = new HashMap<>();
    }
    
    public static void main(String[] args) {
        TutorManagement tm = new TutorManagement();
        tm.displayMenu();
//        tm.addNewTutor();
//        tm.removeTutor();
//        tm.searchTutor();
//        tm.amendTutorDetails();
        
    }
    
    public void displayMenu() {

        System.out.println("1. Add a new tutor");
        System.out.println("2. Remove a tutor");
        System.out.println("3. Find tutor");
        System.out.println("4. Amend tutor details");
        System.out.println("5. List all tutors");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
        boolean running = true;
        int choice=tmu.funcInput();
        while (running) {
           

                switch (choice) {
                case 1:
                    addNewTutor();
                    break;
                case 2:
                    removeTutor();
                    break;
                case 3:
                    System.out.print(searchTutor());
                    break;
                case 4:
                    amendTutorDetails();
                    break;
                /*
                case 5:
                    listAllTutor();
                    break;
                case 6:
                    filterTutor();
                    break;
                    */
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }

        }

        System.out.println("Exiting Tutor Management System.");
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
        tmu.removeTutorData();
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
