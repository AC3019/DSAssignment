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
import utility.Input;
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
         if (tutors.size() > 0) {
           boolean containsDuplicate = tutors.containsValue((Integer k, Tutor t) -> t.getIcNO().equals(tutorObj.getIcNO()));

           if (containsDuplicate) {
              System.out.println("This tutor already exists in the list.");
            } else {
                tutors.put(tutorObj.getId(), tutorObj);
                System.out.println("Tutor added successfully");
           }
         }else {
            // Will only come here for first tutor addition
           tutors.put(tutorObj.getId(),tutorObj);
           System.out.println("Tutor added successfully ");
           // id++;
       }
    }
    //add remove by ic
    //add chk ic and id
    public void removeTutor(){
        //int id=tmu.removeTutorData();
       if(tutors.size()>0){
                      int id=tmu.removeTutorData();
                     if (tutors.containsKey(id)) {
                         tutors.remove(id);
                         System.out.println("Tutor removed successfully ");

                     }else {
                         System.out.println("Id invalid");
                      }

                    
                             
        }        
    else{
           System.out.println("pls add tutor only can removed");
       }

    }
    //got prob
    //add search by ic
    //add loop search few
    
    public void searchTutor(){
        //int id=tmu.searchTutorId();
        //Tutor t=new Tutor();
        char continueInput='Y';
        do{
        if(tutors.size()<=0){
         //return tutors.get(id);  
            System.out.println("Pls add tutor first");
        }else{
              int choice= Input.getChoice(
            "Select an option: ",
                 new String[] {
                "Search by id:",
                "Search by ic no:",                
            },
            (item) -> item
        );
              Input.cleanBuffer();
              switch(choice){
                  case 0:
                          
                     int id=tmu.searchTutorId();
                     if(tutors.containsKey(id)){
                      System.out.println(tutors.get(id));
                     }else{
                         System.out.println("Invalid tutor's id");
                     }
                 //got prob                   
                     break;     
                     //clear buffer error
                  case 1:
                      String ic=tmu.searchTutorIc();
                      Integer key = tutors.keyOf((Integer I, Tutor t) -> t.getIcNO().equals(ic));
                      System.out.println(tutors.get(key));
                      break;
                  default:
                      System.out.println("Invalid option ");                     
              }
        }       
        Input.cleanBuffer();
            continueInput=Character.toUpperCase(Input.getChar("Do you want to continue to search for other tutor: "));
            //Input.cleanBuffer();
               }while(continueInput=='Y');
     
    }
    //let user choose amend by ic or id
    //validate id
    public void  amendTutorDetails(){
        //int choice=tmu.amendTutorMenu();
         int id=0;
         String newName="";
         String newDepartment=" ";
         String newPhoneNum=" ";
         
        // id=Input.getInt("Enter the id you want to find: ");
         //Input.cleanBuffer();
     // Tutor t=new Tutor(id,newName,newDepartment,newPhoneNum);
             
       char continueInput='Y';
       // Tutor tutor = tutors.get(t.getId());
        if (tutors.size()!=0) {
             id=Input.getInt("Enter the id you want to ammend: ");
             if(tutors.containsKey(id)){
                   Tutor t=new Tutor(id,newName,newDepartment,newPhoneNum);
                           Tutor tutor = tutors.get(t.getId());

            do{
                int choice=tmu.amendTutorMenu();
            switch(choice){
                case 0:
                //newName=Input.getString("New name: ",false);
                    newName=tmu.getNewName();
                tutor.setName(newName);
                System.out.println("Tutor name amended successfully.");
                break;

                case 1:
                //tutor.setDepartment(t.getDepartment());
                      newDepartment=tmu.getNewDepartment();
                tutor.setDepartment(newDepartment);
                System.out.println("Tutor department amended successfully.");
                break;
                case 2:
                  newPhoneNum=tmu.getNewPhoneNum();

                tutor.setPhoneNumber(newPhoneNum);

                System.out.println("Tutor's phone number amended successfully. ");
                break;
                default:
                    System.out.println("Invalid choice.. Pls select again");
            }
                    continueInput=Character.toUpperCase(Input.getChar("Do u wan to amend other fields : "));

            }while(continueInput=='Y');
        }else{
                 System.out.println("Tutor's id invalid ");
             }
        } else {
            System.out.println("Tutor not found.");
        }
 
    }

     public void listAllTutor() {
         char continueInput='Y';
        do{
         if(tutors.size()>0){
                int choice=tmu.listAllTutorMenu();
         
         switch(choice){
          case 0:
                //if(tutors.size()>0){
               adt.ArrayList<Tutor> arl = new adt.ArrayList<>(this.tutors.getValues(Tutor.class));
               arl.sort((Tutor t1, Tutor t2) -> t1.getName().compareTo(t2.getName()));
                    for (Tutor t : arl) {
                        System.out.println(t);
                 }
             break;
          case 1:
              for(HashMap<Integer,Tutor>.Pair p:this.tutors){
                  Tutor t=p.getValue();
                  System.out.println(t);
                  }
              break;
          default:
              System.out.println("Invalid choice");
         }  
                  }        
        
         else{
             System.out.println("no tutor inside the system");
         }
         Input.cleanBuffer();
        continueInput=Character.toUpperCase(Input.getChar("Do you want to continue? ", false));
         }while(continueInput=='Y');
        }

     //add loop
     public  void filterTutor() {
         char continueInput='Y';
         do{
         if(tutors.size()>0){
       
        int choice=tmu.filterTutorData();
        switch (choice) {
            case 1:
                findByGender();
                break;
            case 2:
                findByDepartment();
                break;
            case 3:
                findByName();
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
         }else{
             System.out.println("Pls add tutor first");
         }
         Input.cleanBuffer();
         continueInput=Character.toUpperCase(Input.getChar("Do you want to continue filter? "));
         
         }while(continueInput=='Y');
    }
     private  void findByGender() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Gender to filter: ");
        char genderToFilter = scan.next().toUpperCase().charAt(0);
        scan.nextLine();
        //Character.toUpperCase(genderToFilter);
        //ArrayList <Tutor> matchedTutors = new ArrayList<>();
        /*
        for (Map.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
            if (Character.valueOf(genderToFilter).compareTo(tutor.getValue().getGender()) == 0) {
                matchedTutors.add(tutor.getValue());
            }
        }
*/
        Tutor [] matchedTutors=tutors.filter(
                (Integer k,Tutor v)-> v.getGender()==(genderToFilter)
        ).getValues(Tutor.class);
       // System.out.println("Do you want to ");
        for(int i=0;i<matchedTutors.length;i++){
            System.out.println(matchedTutors[i]);
        }
        System.out.println("The total number of tutors in this gender are "+matchedTutors.length);
    }
     private void findByDepartment(){
        //Scanner scan=new Scanner(System.in);
        //System.out.print("Department to filter: ");
        String departmentToFilter = tmu.getDepartment();
        //subjectToFilter.toUpperCase();
        Tutor[] matchedTutors = tutors.filter(
                (Integer k, Tutor v) -> v.getDepartment().equals(departmentToFilter)
        ).getValues(Tutor.class);
        //for (Map.Entry<Integer, Tutor> tutor : tutors.entrySet()) {
            
        //}
        for(int i=0;i<matchedTutors.length;i++){
            System.out.println(matchedTutors[i]);
        }
         System.out.println("The total number of tutors in this subject are "+matchedTutors.length);

     }
     private void findByName(){
         //System.out.print("name to filter: ");
         String nameToFilter=Input.getString("Name to filter: ", false).toUpperCase();
         Tutor[] matchedTutors=tutors.filter(
                 (Integer k,Tutor v)-> v.getName().contains(nameToFilter)
         ).getValues(Tutor.class);
         for(int i=0;i<matchedTutors.length;i++){
            System.out.println(matchedTutors[i]);
        }
         System.out.println("The total number of tutors with this name are "+matchedTutors.length);

     }
     public void getTotalNumOfTutor(){
         System.out.println("The total number of tutors are"+tutors.size());
     }
    
}
