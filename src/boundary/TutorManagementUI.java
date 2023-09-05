package boundary;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yong
 */
import control.DepartmentManagement;
import utility.Input;
import entity.Tutor;
import java.util.regex.*;

import java.io.Serializable;
import java.util.Scanner;
import utility.Input;
public class TutorManagementUI implements Serializable {
    Scanner scan=new Scanner(System.in);
   // TutorManagementUI tmu=new TutorManagementUI();
//    TutorManagement tm=new TutorManagement();
    Tutor t=new Tutor();
    public int printMenu() {
        // System.out.println("1. Add a new tutor");
        // System.out.println("2. Remove a tutor");
        // System.out.println("3. Find tutor");
        // System.out.println("4. Amend tutor details");
        // System.out.println("5. List all tutors");
        // System.out.println("6. Exit");
        // System.out.print("Select an option: ");
        int choice= Input.getChoice(
            "Select an option: ",
            new String[] {
                "Add a new tutor",
                "Remove a tutor",
                "Find tutor",
                "Amend tutor details",
                "List all tutors",
                "Filter tutors",
                "Get total Number of Tutors",
                "Exit"
               
            },
            (item) -> item
        );
        Input.cleanBuffer();
         return choice;
    }
    public int funcInput(){
       // Scanner scan = new Scanner(System.in);
        //        System.out.println("Enter the function u want to perform:");
        int choice = scan.nextInt();
           scan.nextLine(); // Consume newline
           //Input.cleanBuffer();
            //Input.cleanBuffer();
        return choice;
    }
    public String getDepartment() {
        return DepartmentManagement.departments[
                Input.getChoice(
                    "Enter department: ", 
                    DepartmentManagement.departments, 
                    (s) -> s
                )
        ];
    }
    public Tutor tutorInput(){
                //Scanner scan = new Scanner(System.in);
                boolean invalidInput ;
                String name ;
                String department ;
                char gender;
                int age;
                String phoneNum="";
                String icNo;
                String regexHp = "^(\\+?6?01)[0-9]{7,9}$";
            Pattern pattern = Pattern.compile(regexHp);
                Matcher m ;
                        //= pattern.matcher(phoneNum);

                do{
                    invalidInput = false;
                    name=Input.getString("Name:",false).toUpperCase();
                    department = getDepartment();
                    //name.toUpperCase();
                    Input.cleanBuffer();
                    //subject.toUpperCase();
                    //gender=tmu.getChar("Gender(M/F): ",false);
                    gender = Character.toUpperCase(Input.getChar("Gender(M/F): ", false));
                    //Character.toUpperCase(gender);
                    //System.out.print("Age: ");
                    //age = scan.nextInt();
                   //Input.cleanBuffer();
                    age=Input.getInt("Age: ");
                    // scan.nextLine();// clear buffer
                    Input.cleanBuffer();
                    //System.out.print("Phone: ");
                    //phoneNum = scan.nextLine();
                    phoneNum=Input.getString("Phone (0123456789):  ", false);
                     m = pattern.matcher(phoneNum);

                    icNo=Input.getString("Ic No:", false);
                    // validate gender
                    
                    if (Character.valueOf(gender).compareTo("M".charAt(0)) != 0
                            && Character.valueOf(gender).compareTo("F".charAt(0)) != 0) {
                        System.out.println("Gender should be either M or F. Please re-enter...");
                        invalidInput = true;
                    }
                    
                    if(!m.matches()){
                        System.out.println("phone num not matched");
                        invalidInput=true;
                    }
                    
                }while(invalidInput);     
                   

                Tutor tutorObj = new Tutor(name, department, gender, age, phoneNum,icNo);
                return tutorObj;
    }
       public int removeTutorData(){
         //Scanner scan = new Scanner(System.in);
        // System.out.println("Enter a id u wan to remove: ");
            int id = Input.getInt("Enter an id you want to remove: ");
            return id;
    }
       public int searchTutorId(){
        //Scanner scan = new Scanner(System.in);
       // System.out.println("Enter id u wan to find: ");
        int id = Input.getInt("Enter tutor's id that you want to find : ");
        
        
       
        
        return id;
    }
    public String searchTutorIc(){
        String ic=Input.getString("Enter tutor's ic that you want to find: ",false);
        return ic;
    }
    public int amendTutorMenu(){
        int choice= Input.getChoice(
            "Select an option: ",
            new String[] {
                "Amend Names:",
                "Amend Department:",
                "Amend Phone Number: ",
                "Exit: "
            },
            (item) -> item
        );
        Input.cleanBuffer();
               return choice;

    }
       public String getNewName(){
           
            String newName=Input.getString("New name: ",false).toUpperCase();
            //scan.nextLine();
            return newName;

    }
    public String getNewDepartment(){
        String newDepartment = Input.getString("New Department: ",false);
        return newDepartment;
    }
    public String getNewPhoneNum(){
        String newPhoneNum=Input.getString("New phone:", false);
        return newPhoneNum;
    }

    public int filterTutorData(){
      //Scanner scan = new Scanner(System.in);
        int choice=Input.getChoice(
                "Select an option: ",
                new String[]{
                    "Filter by gender: ",
                    "Filter by department: ",
                    "Filter by name: ",
                        
                },
                (item)-> item
        );
                Input.cleanBuffer();

        return choice;
         
        
    }
    public int listAllTutorMenu(){
         int choice= Input.getChoice(
            "Select an option: ",
            new String[] {
                "Sort by name in alphabetical order:",
                "Sort by tutor's id in ascending order:",
                
            },
            (item) -> item
        );
         return choice;
    }
    public void invalidSwitchChoice(){
        System.out.println("Ïnvalid choice. Please select again....");
    }
    public char wantToContinue() {
        return Character.toUpperCase(Input.getChar("Do you want to continue (Y/N)?"));
    }
    public void continueMsgError(){
        System.out.println ("Invalid choice.. Please enter only 'Y' or 'N'");
    }
    public String continueMsg(){
        return ("Do you want to continue(y/n)");
    }
}
