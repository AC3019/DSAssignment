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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Scanner;
import utility.Input;

public class TutorManagementUI implements Serializable {

    Scanner scan = new Scanner(System.in);
  
    Tutor t = new Tutor();
    
    public void displayHeader(){
        System.out.println();
        System.out.println("_|_|_|_|_|    _|_|    _|_|_|    _|    _|  _|      _|  _|_|_|_|_|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|_|  _|_|      _|");
        System.out.println("    _|      _|_|_|_|  _|_|_|    _|    _|  _|  _|  _|      _|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|      _|      _|");
        System.out.println("    _|      _|    _|  _|    _|    _|_|    _|      _|      _|");
        System.out.println();
    }
    public int printMenu() {
               int choice = Input.getChoice(
                "Select an option: ",
                new String[]{
                    "Add a new tutor",
                    "Remove a tutor",
                    "Find tutor",
                    "Amend tutor details",
                    "List all tutors",
                    "Filter tutors",
                    "Get total Number of Tutors",
                    "Generate Report",
                    "Exit"

                },
                (item) -> item
        );
        Input.cleanBuffer();
        return choice;
    }

    public int funcInput() {
      
        int choice = scan.nextInt();
        scan.nextLine(); // Consume newline
        
        return choice;
    }

    public String getDepartment() {
        return DepartmentManagement.departments[Input.getChoice(
                "Enter department: ",
                DepartmentManagement.departments,
                (s) -> s
        )];
    }

    public Tutor tutorInput() {
        //Scanner scan = new Scanner(System.in);
        boolean invalidInput;
        String name;
        String department;
        char gender;
        int age;
        String phoneNum = "";
        String icNo;
        String regexHp = "^(\\+?6?01)[0-9]{7,9}$";
        Pattern pattern = Pattern.compile(regexHp);
        Matcher m;
        String regexIc="\\d{6}-\\d{2}-\\d{4}";
        Pattern pattern1=Pattern.compile(regexIc);
        //= pattern.matcher(phoneNum);
        Matcher m1;
        do {
            invalidInput = false;
            name = Input.getString("Name:", false).toUpperCase();
            department = getDepartment();
            //name.toUpperCase();
            Input.cleanBuffer();
           
            gender = Character.toUpperCase(Input.getChar("Gender(M/F): ", false));
             age = Input.getInt("Age: ");
            Input.cleanBuffer();
           
            phoneNum = Input.getString("Phone (0123456789):  ", false);
            m = pattern.matcher(phoneNum);

            icNo = Input.getString("Ic No (xxxxxx-xx-xxxx):", false);
            m1=pattern1.matcher(icNo);
            // validate gender
            if (Character.valueOf(gender).compareTo("M".charAt(0)) != 0
                    && Character.valueOf(gender).compareTo("F".charAt(0)) != 0) {
                System.out.println("Gender should be either M or F. Please re-enter...");
                invalidInput = true;
            }

            if (!m.matches()) {
                System.out.println("phone num not matched");
                invalidInput = true;
                
            }
            if(!m1.matches()){
                System.out.println("Ic Num not matched");
                invalidInput=true;
            }

        } while (invalidInput);

        Tutor tutorObj = new Tutor(name, department, gender, age, phoneNum, icNo);
        return tutorObj;
    }

    public int removeTutorData() {
       
        int id = Input.getInt("Enter an id you want to remove: ");
        return id;
    }

    public int searchTutorId() {
       
        int id = Input.getInt("Enter tutor's id that you want to find : ");

        return id;
    }

    public String searchTutorIc() {
        String ic = Input.getString("Enter tutor's ic that you want to find: ", false);
        return ic;
    }

    public int amendTutorMenu() {
        int choice = Input.getChoice(
                "Select an option: ",
                new String[]{
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

    public String getNewName() {

        String newName = Input.getString("New name: ", false).toUpperCase();
        //scan.nextLine();
        return newName;

    }

    public String getNewDepartment() {
        String newDepartment = Input.getString("New Department: ", false);
        return newDepartment;
    }

    public String getNewPhoneNum() {
        String newPhoneNum = Input.getString("New phone:", false);
        return newPhoneNum;
    }

    public int filterTutorData() {
        //Scanner scan = new Scanner(System.in);
        int choice = Input.getChoice(
                "Select an option: ",
                new String[]{
                    "Filter by gender: ",
                    "Filter by department: ",
                    "Filter by name: ",
                    "Exit "

                },
                (item) -> item
        );
        Input.cleanBuffer();

        return choice;

    }

    public int listAllTutorMenu() {
        int choice = Input.getChoice(
                "Select an option: ",
                new String[]{
                    "Sort by name in alphabetical order:",
                    "Sort by tutor's id in ascending order:",
                    "Exit "

                },
                (item) -> item
        );
        return choice;
    }

    public void invalidSwitchChoice() {
        System.out.println("Ïnvalid choice. Please select again....");
    }

    public char wantToContinue() {
        return Character.toUpperCase(Input.getChar("Do you want to continue (Y/N)?"));
    }

    public void continueMsgError() {
        System.out.println("Invalid choice.. Please enter only 'Y' or 'N'");
    }

    public String continueMsg() {
        return ("Do you want to continue(y/n)");
    }

    public void exitSysMsg() {
        System.out.println("Exiting tutor management system ");
    }

    public void duplicateMsg() {
        System.out.println("This tutor already exist in the list");
    }

    public void emptyListMsg() {
        System.out.println("Please add tutor first..");
    }

    public void printPrompt(String e) {
        System.out.println(e);
    }

       public void getReportTitle() {
        String title = Input.getString("What report title do you want to set: ", false);
        System.out.print("\t\t\t\t"+title);
    }

    public LocalDateTime printDateTime(LocalDateTime e) {
        return (e);
    }

    public void printFormatDateTime(String e) {
        System.out.print( "\t\t"+ e);
    }

    public void cleanBuffer() {
        Input.cleanBuffer();
    }

    public void printPromptWithoutEmptyLine(String e) {
        System.out.print(e);
    }

    public void printEmptyLine() {
        System.out.println();
    }
    public void printAvailableNameInMap(String s){
        System.out.print(s);
        
    }
    public void printKey(Tutor k) {
        System.out.println(k);
    }
    public int searchTutorMenu(){
        int choice = Input.getChoice(
                        "Select an option: ",
                        new String[]{
                            "Search by id:",
                            "Search by ic no:",
                            "Exit "
                        },
                        (item) -> item
                );
                return choice;
    }
    public Integer getNewId(){
           Integer id = Input.getInt("Enter the tutor's id you want to ammend: ");
           return id;
    }
    public char wantToContinueFilter() {
        return Character.toUpperCase(Input.getChar("Do you want to continue filter on second criteria based on above result(Y/N)?"));
    }

}
