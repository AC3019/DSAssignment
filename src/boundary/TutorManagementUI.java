package boundary;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yong
 */
import utility.Input;
import entity.Tutor;
import java.util.Scanner;
import utility.Input;
public class TutorManagementUI {
    Scanner scan=new Scanner(System.in);
   // TutorManagementUI tmu=new TutorManagementUI();
    Tutor tutor=new Tutor();
    public int printMenu() {
        // System.out.println("1. Add a new tutor");
        // System.out.println("2. Remove a tutor");
        // System.out.println("3. Find tutor");
        // System.out.println("4. Amend tutor details");
        // System.out.println("5. List all tutors");
        // System.out.println("6. Exit");
        // System.out.print("Select an option: ");
        return Input.getChoice(
            "Select an option: ",
            new String[] {
                "Add a new tutor",
                "Remove a tutor",
                "Find tutor",
                "Amend tutor details"
            },
            (item) -> item
        );
         
    }
    public int funcInput(){
       // Scanner scan = new Scanner(System.in);
        //        System.out.println("Enter the function u want to perform:");
        int choice = scan.nextInt();
            scan.nextLine(); // Consume newline
            //Input.cleanBuffer();
        return choice;
    }
    public Tutor tutorInput(){
                //Scanner scan = new Scanner(System.in);
                boolean invalidInput ;
                String name ;
                String subject ;
                char gender;
                int age;
                String phoneNum;
                String icNo;
                do{
                    invalidInput = false;
                    name=Input.getString("Name:",false);
                    subject=Input.getString("Subject: ",false);
                    //name.toUpperCase();
                   
                    //subject.toUpperCase();
                    //gender=tmu.getChar("Gender(M/F): ",false);
                    System.out.print("Gender(M/F): ");
                    gender = scan.next().toUpperCase().charAt(0);
                    //Character.toUpperCase(gender);
                    //System.out.print("Age: ");
                    //age = scan.nextInt();
                    age=Input.getInt("Age: ");
                    scan.nextLine();// clear buffer
                    //System.out.print("Phone: ");
                    //phoneNum = scan.nextLine();
                    phoneNum=Input.getString("Phone: ", false);
                    icNo=Input.getString("Ic No:", false);
                    // validate gender
                    
                    if (Character.valueOf(gender).compareTo("M".charAt(0)) != 0
                            && Character.valueOf(gender).compareTo("F".charAt(0)) != 0) {
                        System.out.println("Gender should be either M or F. Please re-enter...");
                        invalidInput = true;
                    }
                }while(invalidInput);     
                   

                Tutor tutorObj = new Tutor( tutor.getId(),name, subject, gender, age, phoneNum,icNo);
                return tutorObj;
    }
    public int removeTutorData(){
         //Scanner scan = new Scanner(System.in);
         System.out.println("Enter a id u wan to remove: ");
            int id = scan.nextInt();
            return id;
    }
    public int searchTutorId(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter id u wan to find: ");
        int id = scan.nextInt();
        scan.nextLine();
        return id;
    }
    
    public Tutor amendTutorDetailsData(){
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Tutor ID: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.print("New name: ");
        String newName = scan.nextLine();
        System.out.print("New subject: ");
        String newSubject = scan.nextLine();
        Tutor tu=new Tutor(id,newName,newSubject);
        return tu ;
    }
    public int filterTutorData(){
      Scanner scan = new Scanner(System.in);
        int choice;
         
        System.out.println("1. Filter by gender");
        System.out.println("2. Filter by subject");
        System.out.print("Select an option: ");

        choice = scan.nextInt();
        scan.nextLine();
               return choice;
    }
}
