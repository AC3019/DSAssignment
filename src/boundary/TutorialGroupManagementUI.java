/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary; 
import java.io.Serializable;

import adt.ArrayList;
import entity.Student;
import entity.TutorialGroup;
import utility.Input;
import utility.TableBuilder;

/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroupManagementUI implements Serializable {
    
    public void header(){
        System.out.println();
        System.out.println("_|_|_|_|_|    _|_|    _|_|_|    _|    _|  _|      _|  _|_|_|_|_|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|_|  _|_|      _|");
        System.out.println("    _|      _|_|_|_|  _|_|_|    _|    _|  _|  _|  _|      _|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|      _|      _|");
        System.out.println("    _|      _|    _|  _|    _|    _|_|    _|      _|      _|");
        System.out.println();
    }

    public int mainMenu(){
        System.out.println();
        System.out.println("              Welcome to TARUMT system");
        int choice = Input.getChoice("Enter your choice: ", new String[] {
            "Create new tutorial group",
            "Delete tutorial group",
            "Display all tutorial group",
            "Add a student to a tutorial group",
            "Remove a student from a tutorial group",
            "Change a student to another tutorial group",
            "Find a student in a tutorial group",
            "Display all students in a tutorial group",
            "Filter tutorial group",
            "Exit tutorial group management"
        }, (item) -> item);
        Input.cleanBuffer();
        return choice;
    }
    
    public TutorialGroup createTutGrp(){
        String programmeCode = Input.getString("Please enter the programme code for the tutorial group: ", false);
        String programmeName = Input.getString("Please enter the name of the programme: ",false);
        String tutGrpCode = Input.getString("Please enter the year, month and group number of the tutorial group for tutorial group code(230101): ", false); 
        programmeCode = programmeCode.toUpperCase();
        return new TutorialGroup(programmeCode, programmeName, tutGrpCode);
    }
    
    public String sortChoice(){
        String choice = Input.getString("Did you want to sort the result(Y/N): ", false);
        return choice;
    }
    

    public int sortChoiceType(){
        int choice = Input.getChoice("Please enter the sort choice: ", new String[] {
            "Sort by Programme Code",
            "Sort by Number of Students"
        }, (String item) -> item);
        return choice;
    }
    
    //receive tutGrp arrayList
    public int deleteTutGrp(ArrayList<TutorialGroup> tutGrp) {
        return this.getTutGrpChoice(
            "Enter the index number of tutorial group that you want to delete: ", tutGrp
        );
    }
    
    public void passingList(TutorialGroup tutGrp){
        System.out.println("Final passing list for " + tutGrp.getProgrammeCode() + " " + tutGrp.getTutGrpCode() + " :");
    }
    
    public String moveToPassList(){
        //Input.cleanBuffer();
        String choice = Input.getString("Did you want to stall the tutorial group removed into passing list(Y/N): ", false);
        return choice;
    }
    
    public String failStudent(){
        String failStudent = Input.getString("Did student in the tutorial group fail the course(Y/N): ", false);
        return failStudent;
    }
    
    public String reportChoice(){
        String choice = Input.getString("Did you want to generate the result into report(Y/N): ", false);
        return choice;
    }
    
    public String reportTitle(){
        String title = Input.getString("Please enter the report title: ", false);
        return title;
    }
    
    public void printReportTitle(String title){
        System.out.println();
        System.out.println();
        System.out.println(title + "         " + "Time Generated: " + java.time.LocalTime.now());
    }
        
    private int getTutGrpChoice(String prompt, ArrayList<TutorialGroup> tutGrp) {
        int choice = Input.getChoice(
            prompt,
            tutGrp.toArray(TutorialGroup.class),
            (item) -> item.getProgrammeCode() + " " + item.getProgrammeName() + " " + item.getTutGrpCode()
        );
        return choice;
    }
    
    //create a new student obj
    public Student addStudent(){
        String studentID = Input.getString("Please enter the intake year and month for student ID of the student(Year: 2022 Month: 01 -> 2201): ", false);
        String name = Input.getString("Please enter the name of the student: ", false);
        int age = Input.getInt("Please enter the age of the student: ", 0, 120);
        Input.cleanBuffer();
        String gender;
        int genderChoice = Input.getChoice("Please select the gender of the student: ", new String[] {
                "Male",
                "Female"
            }, (item) -> item);
        if(genderChoice == 0){
            gender = "Male";
        }else{
            gender = "Female";
        }
        return new Student(studentID,name,age, gender);
    }
    
    public String filterStudent(){
        //Input.cleanBuffer();
        String decision = Input.getString("Did you want to filter the student by gender(Y/N): ", false);
        return decision;
    }
    
    public void afterFilter(){
        System.out.println("Results after filter: ");
    }
    
    public int getGender(){
        int choice = Input.getChoice("Please select the gender of the student: ", new String[] {
                                    "Male",
                                    "Female"
                                    }, (item) -> item);
        return choice;
    }
    
    //ask user which way the user want to add the user
    //use after student obj create
    public int choiceOfTutGrp(){
        System.out.println("Where do you want to add the student to:");
        int choice = Input.getChoice("Enter your choice: ", new String[]{
            "Existing tutorial group",
            "Create new tutorial group"
        }, (item) -> item);
        return choice;
    }
    
    //ask where the user want to add the user to 
    //use after choiceOfTutGrp()
    public int choiceOfExistGrp(ArrayList<TutorialGroup> tutGrp){
        return this.getTutGrpChoice(
                "Please enter the index number of tutorial group you want to insert: ", tutGrp
        );
    }
    
    //ask which tutGrp user want to display the student
    public int choiceOfTutGrp(ArrayList<TutorialGroup> tutGrp){
        return this.getTutGrpChoice("Please enter the index number of tutorial group you want to display: ", tutGrp);
    }
    
    //display all student in tutGrp for remove the student purpose
    public int displayAllStudentForRemove(Student[] student){
        Input.cleanBuffer();
        int choice = Input.getChoice(
                "Please enter the index number of the student you want to remove: ", 
                student, 
                (Student item) -> item.getStudentID() + " " + item.getStudentName()
        );
        return choice;
    }
    
    //display all student in tutGrp for change the student tutGrp purpose
    public int displayAllStudent(Student[] student){
        Input.cleanBuffer();
        int choice = Input.getChoice(
                "Please enter the index number of the student you want to switch tutorial group: ", 
                student, 
                (Student item) -> item.getStudentID() + " " + item.getStudentName()
        );
        return choice;
    }
    
    public int findStudentInTutGrp(ArrayList<TutorialGroup> tutGrp){
        return this.getTutGrpChoice("Enter the index number of the tutorial group you want to find the student: ", tutGrp);
    }
    
    public int insertStudentInTutGrp(ArrayList<TutorialGroup> tutGrp){
        return this.getTutGrpChoice("Enter the index number of the tutorial group you want to enter the student that just selected: ", tutGrp);
    }
    
    public int choiceOfSearchingStudent(){
        int choice = Input.getChoice("Please enter the index number of the way you want to search the student with: ", new String []{
            "By Student ID",
            "By Name"
        }, (String item) -> item);
        return choice;
    }

    //choice of filter tutGrp
    public int choiceOfFilterTutGrp(){
        int choice = Input.getChoice("Please enter the index number of the way you want to filter with: ", new String[] {
            "By Programme Code",
            "By Tutorial Group Code",
            "All criteria above"
        }, (String item) -> item);
        return choice;
    }
    
    public String getProgCode(){
        Input.cleanBuffer();
        String progCode = Input.getString("Please enter the programme code you want to filter: ", false);
        return progCode;
    }
    
    /*
    public String getProgName(){
        String progName = Input.getString("Please enter the programme name you want to filter: ",false);
        return progName;
    }*/
    
    public String getTutGrpCode(){
        //Input.cleanBuffer();
        String tutGrpCode = Input.getString("Please enter the tutorial group code you want to filter: ", false);
        return tutGrpCode;
    }
    
    //get choice of filter tutGrp again
    public int reChoiceOfFilterTutGrp(){
        int choice = Input.getChoice("Please enter the index number of the way you want to filter with: ", new String[] {
            "By Programme Code",
            "By Tutorial Group Code"
        }, (String item) -> item);
        return choice;
    }
    public String continueFilter(){
        String choice = Input.getString("Do you want to continue filter(Y/N): ", false);
        return choice;
    }

//edit message
    public String edit(){
        //Input.cleanBuffer();
        String decision = Input.getString("Did you want to edit the student detail(Y/N): ", false);
        return decision;
    }
    
    public String editMark(){
        //Input.cleanBuffer();
        String decision = Input.getString("Did you want to edit the student's demerit mark(Y/N): ", false);
        return decision;
    }
    
    public int editStudent(ArrayList<Student> studentList){
        //Input.cleanBuffer();
        int choice = Input.getInt("Enter the index number of the student you want to edit: ", 0, studentList.getNumberOfEntries()-1);
        return choice;
    }
    
    public String findStudentID(){
        Input.cleanBuffer();
        String studentID = Input.getString("Please enter the student ID of the student: ", false);
        return studentID;
    }
    
    public String findStudentName(){
        Input.cleanBuffer();
        String studentName = Input.getString("Please enter the name of the student: ",false);
        return studentName;
    }
    
    public int findStudentAge(){
        int studentAge = Input.getInt("Please enter the age of the student: ", 0, 120);
        return studentAge;
    }
    
    public int getStudentMark(){
        //Input.cleanBuffer();
        int mark = Input.getInt("Please enter the demerit mark of the student: ", 0, 100);
        return mark;
    }
    
//information message
    public void createTutGrpSuccess(){
        System.out.println("Tutorial group has been created successfully.");
    }
    
    public void addStudentSuccess(){
        System.out.println("The student has been created successfully.");
    }
    
    public void addStudentIntoListSuccess(){
        System.out.println("The student has been insert into tutorial group successfully.");
    }
    
    public void removeStudentSuccess(){
        System.out.println("The student has been remove out the tutorial group successfully.");
    }
    
    public void changeTutGrpSuccess(){
        System.out.println("The student has change to the selected tutorial group successfully.");
    }
    
    public void studentEditSuccess(){
        System.out.println("The student details has updated successfully.");
    }
    
    public void studentEditMarkSuccess(){
        System.out.println("The student's demerit mark has updated successfully.");
    }
    
    public void deleteTutGrpSuccess(){
        System.out.println("Tutorial group has been delete successfully.");
    }
    
    public void moveToPassingListSuccess(){
        System.out.println("Tutorial group has been move to passing list successfully.");
    }
        
    public void noTutGrp(){
        System.out.println("No tutorial group being stored in the system.");
    }
    
    public void noTutGrpFound(){
        System.out.println("No tutorial group found.");
    }
    
    public void creatingNewTutGrp(){
        System.out.println("Creating new tutorial group to stall the student.");
    }
    
    public void studentNotFound(){
        System.out.println("There is no match record found in the all tutorial group.");
    }
    
    public void afterSort(){
        System.out.println("Result after sorting: ");
    }
    
    public void noStudent(){
        System.out.println("No student found in the selected tutorial group.");
    }

//output for print table
    public void printAllSelectedTutGrp(ArrayList<TutorialGroup> tutGrp){
        TableBuilder tb = new TableBuilder();
        String[] progCodes = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getProgrammeCode()).toArray(String.class);
        tb.addColumn("Program Code", progCodes);
        String[] progNames = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getProgrammeName()).toArray(String.class);
        tb.addColumn("Program Name", progNames);
        String[] tutGrpCodes = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getTutGrpCode()).toArray(String.class);
        tb.addColumn("Tutorial Group Code", tutGrpCodes);
        Integer[] numOfStudents = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getStudent().getNumberOfEntries()).toArray(Integer.class);
        tb.addColumn("Number of Students", numOfStudents);
        tb.printTable(true);
    }
    
    //print all selected student
    public void displayAllSelectedStudent(ArrayList<Student> stud) {
        TableBuilder tb = new TableBuilder();
        String[] ids = stud.map((Student s) -> s.getStudentID()).toArray(String.class);
        tb.addColumn("Student ID", ids);
        String[] names = stud.map((Student s) -> s.getStudentName()).toArray(String.class);
        tb.addColumn("Student Name", names);
        Integer[] ages = stud.map((Student s) -> s.getStudentAge()).toArray(Integer.class);
        tb.addColumn("Student Age", ages);
        String[] genders = stud.map((Student s) -> s.getStudentGender()).toArray(String.class);
        tb.addColumn("Student Gender", genders);
        Integer[] marks = stud.map((Student s) -> s.getStudentDemeritMark()).toArray(Integer.class);
        tb.addColumn("Student Demerit Mark", marks);
        tb.printTable(true);
    }

//function output
    public void cleanBuffer(){
        Input.cleanBuffer();
    }
    
    public void continueEnter(){
        Input.pause();
    }
        
//error message
    public void errorChoice(){
        System.out.println("The choice enter is not available.");
    }
    
    public void errorInput(){
        System.out.println("Only 'Y' or 'N' will be accepted. Please try again.");
    }

}
