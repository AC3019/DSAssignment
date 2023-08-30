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
//import adt.HashMap;
import adt.ArrayList;
import adt.ListInterface;
import java.util.HashMap;
import boundary.TutorManagementUI;
import java.util.Scanner;
import java.util.Map;
public class TutorManagement {
    HashMap<Integer, Tutor> tutors=new HashMap<>();
    TutorManagementUI tmu = new TutorManagementUI();
    //TutorManagement tm=new TutorManagement();
    static int id=1;
    Scanner scan=new Scanner(System.in);
    /*
    public TutorManagement() {
        tutors = new HashMap<>();
    }
    */
    
    public static void main(String[] args) {
        TutorManagement tm = new TutorManagement();
        tm.displayMenu();

    }
    
    public void displayMenu() {
        boolean running = true;

        while (running) {  
        System.out.println("1. Add a new tutor");
        System.out.println("2. Remove a tutor");
        System.out.println("3. Find tutor");
        System.out.println("4. Amend tutor details");
        System.out.println("5. List all tutors");
        System.out.println("6. Filter Tutor");
        System.out.println("7. Exit");

        System.out.print("Select an option: ");
        int choice=tmu.funcInput();

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
                
                case 5:
                    listAllTutor();
                    break;
                
                case 6:
                    filterTutor();
                    break;
                    
                case 7:
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
        // if (tutors.size() > 0) {
           // boolean containsDuplicate = tutors.contains(id);

           // if (containsDuplicate) {
              // System.out.println("This tutor already exists in the list.");
           // } else {
                tutors.put(id, tutorObj);
                id++;
            
         //}else {
            // Will only come here for first tutor addition
          //  tutors.put(id, tutorObj);
           // id++;
       // }
    }
    public void removeTutor(){
        int id=tmu.removeTutorData();
        if (tutors.containsKey(id)) {
            tutors.remove(id);
            System.out.println("Tutor removed successfully ");
        } else {
            System.out.println("Id invalid");
        }

    }
    public Tutor searchTutor(){
        int id=tmu.searchTutorId();
         return tutors.get(id);

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
     public  void listAllTutor() {
        System.out.println(tutors.toString());
    }
     public  void filterTutor() {
        Scanner scan = new Scanner(System.in);

        int choice = scan.nextInt();
        scan.nextLine();
        switch (choice) {
            case 1:
                findByGender();
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }

    }
     private  void findByGender() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Gender to filter: ");
        char genderToFilter = scan.next().charAt(0);
        ListInterface <Tutor> matchedTutors = new ArrayList<>();
        for (Map.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
            if (Character.valueOf(genderToFilter).compareTo(tutor.getValue().getGender()) == 0) {
                matchedTutors.insert(tutor.getValue());
            }
        }
        System.out.println(matchedTutors);
    }
    
}
