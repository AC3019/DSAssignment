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
import adt.ArrayList;
import adt.ListInterface;
//import java.util.List;
//import java.util.HashMap;
import java.io.Serializable;
//import java.util.ArrayList;
import boundary.TutorManagementUI;
import java.util.Scanner;
//import java.util.Map;
public class TutorManagement implements Serializable {
    HashMap<Integer, Tutor> tutors=new HashMap<>();
    TutorManagementUI tmu = new TutorManagementUI();
    //TutorManagement tm=new TutorManagement();
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

    // TODO: gud job why use java default collection, sila convert to ours
    public Tutor[] getTutors() {
        return this.tutors.getValues(Tutor.class);
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
        System.out.println("7. Get total number of tutors");
        System.out.println("8. Exit");

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
                    searchTutor();
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
                    getTotalNumOfTutor();
                    break;
                case 8:
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
                tutors.put(tutorObj.getId(), tutorObj);
            
         //}else {
            // Will only come here for first tutor addition
          //  tutors.put(id, tutorObj);
           // id++;
       // }
    }
    public void removeTutor(){
        //int id=tmu.removeTutorData();
       if(tutors.size()>0){
           int id=tmu.removeTutorData();
        if (tutors.containsKey(id)) {
            tutors.remove(id);
            System.out.println("Tutor removed successfully ");
        } else {
            System.out.println("Id invalid");
        }
       }else{
           System.out.println("pls add tutor only can removed");
       }

    }
    public void searchTutor(){
        //int id=tmu.searchTutorId();
        if(tutors.size()<=0){
         //return tutors.get(id);  
            System.out.println("Pls add tutor first");
            
        }else{
                int id=tmu.searchTutorId();

                 System.out.println(tutors.get(id)); 
        }
    }
    
    public void amendTutorDetails(){
        Tutor t=tmu.amendTutorDetailsData();
       
        Tutor tutor = tutors.get(t.getId());
        if (tutor != null) {
            tutor.setName(t.getName());
            tutor.setDepartment(t.getDepartment());
            System.out.println("Tutor details amended successfully.");
        } else {
            System.out.println("Tutor not found.");
        }
    }
     public  void listAllTutor() {
        adt.ArrayList<Tutor> arl = new adt.ArrayList<>(this.tutors.getValues(Tutor.class));
        arl.sort((Tutor t1, Tutor t2) -> t2.getName().compareTo(t1.getName()));
         if(tutors.size()>0){
        System.out.println(tutors.toString());
         }else{
             System.out.println("no tutor inside the system");
         }
    }
     public  void filterTutor() {
         if(tutors.size()>0){
     
        int choice=tmu.filterTutorData();
        switch (choice) {
            case 1:
                findByGender();
                break;
            case 2:
                findBySubject();
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
         }else{
             System.out.println("Pls add tutor first");
         }

    }
     private  void findByGender() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Gender to filter: ");
        char genderToFilter = scan.next().charAt(0);
        //Character.toUpperCase(genderToFilter);
        ListInterface <Tutor> matchedTutors = new ArrayList<>();
        for (Map.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
            if (Character.valueOf(genderToFilter).compareTo(tutor.getValue().getGender()) == 0) {
                matchedTutors.add(tutor.getValue());
            }
        }
        System.out.println(matchedTutors);
        System.out.println("The total number of tutors in this gender are "+matchedTutors.size());
    }
     private void findBySubject(){
        Scanner scan=new Scanner(System.in);
        System.out.print("Subject to filter: ");
        String subjectToFilter = scan.nextLine();
        //subjectToFilter.toUpperCase();
        Tutor[] matchedTutors = tutors.filter(
                (Integer k, Tutor v) -> v.getDepartment().equals(subjectToFilter)
        ).getValues(Tutor.class);
        for (Map.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
            
        }
        System.out.println(matchedTutors);
         System.out.println("The total number of tutors in this subject are "+matchedTutors.size());

     }
     public void getTotalNumOfTutor(){
         System.out.println("The total number of tutors are"+tutors.size());
     }
    
}
