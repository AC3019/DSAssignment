/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary; 
import adt.ArrayList;
import entity.Student;
import entity.TutorialGroup;
import utility.Input;
import utility.TableBuilder;

/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroupManagementUI {
    
    public void header(){
        System.out.println("_|_|_|_|_|    _|_|    _|_|_|    _|    _|  _|      _|  _|_|_|_|_|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|_|  _|_|      _|");
        System.out.println("    _|      _|_|_|_|  _|_|_|    _|    _|  _|  _|  _|      _|");
        System.out.println("    _|      _|    _|  _|    _|  _|    _|  _|      _|      _|");
        System.out.println("    _|      _|    _|  _|    _|    _|_|    _|      _|      _|");
        System.out.println();
    }

    public int mainMenu(){
        System.out.println("Welcome to TARUMT system");
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
        String programmeCode = Input.getString("Please enter the programme code of the tutorial group: ", false);
        String programmeName = Input.getString("Please enter the name of the programme: ",false);
        String tutGrpCode = Input.getString("Please enter the tutorial group code: ", false); 
        return new TutorialGroup(programmeCode, programmeName, tutGrpCode);
    }
    
    //this is not required
    //display all tutGrp available in the arrayList
    public void displayTutGrp(ArrayList<TutorialGroup> tutGrp) {
        for(int i = 1; i <= tutGrp.getNumberOfEntries(); i++){
            System.out.println(i + "  " + tutGrp.toArray(TutorialGroup.class).toString());
        }
    }
    
    //receive tutGrp arrayList
    public int deleteTutGrp(ArrayList<TutorialGroup> tutGrp) {
        return this.getTutGrpChoice(
            "Enter the index number of tutorial group that you want to delete: ", tutGrp
        );
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
        String studentID = Input.getString("Please enter the student ID of the student(22PMR05): ", false);
        String name = Input.getString("Please enter the name of the student: ", false);
        int age = Input.getInt("Please enter the age of the student: ");
        return new Student(studentID,name,age);
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
        return this.getTutGrpChoice("Please enter the index number of tutorial group you want to display", tutGrp);
    }
    
    //display all student in tutGrp
    public int displayAllStudent(Student[] student){
        //printAllStudent(new ArrayList<>(student));
        //Input.getInt("ashjdga", 0, student.getNumberOfEntries());
        Input.cleanBuffer();
        int choice = Input.getChoice(
                "Please enter the index number of the student you want to remove: ", 
                student, 
                (Student item) -> item.getStudentID() + item.getStudentName()
        );
        return choice;
        //TableBuilder tb = new TableBuilder();
        //tb.addColumn("StudentID", student[i].getStudentID().toString());
        // for(int i = 1; i <= tutGrp.getStudent().getNumberOfEntries(); i++){
        //     System.out.println(i + "  " + tutGrp.getStudent().toArray(Student.class)[i].toString());
        // }

        //int choice = Input.getChoice("", new Student[] {
            
        //}, (Student item) -> { return item.getStudentID() + item.getStudentName(); });
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
            "By Name",
            "By Age"
        }, (String item) -> item);
        return choice;
    }
    
    public String findStudentID(){
        String studentID = Input.getString("Enter the student ID of the student: ", false);
        return studentID;
    }
    
    public String findStudentName(){
        String studentName = Input.getString("Enter the name of the student: ",false);
        return studentName;
    }
    
    public int findStudentAge(){
        int studentAge = Input.getInt("Enter the age of the student: ");
        return studentAge;
    }
    
    public void studentNotFound(){
        System.out.println("There is no match record found in the selected tutorial group");
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
        tb.printTable(true);
    }
    
    //choice of filter tutGrp
    public int choiceOfFilterTutGrp(){
        int choice = Input.getChoice("Please enter the index number of the way you want to filter with: ", new String[] {
            "By Programme Code",
            "By Programme Name",
            "By Tutorial Group Code",
            "All criteria above"
        }, (String item) -> item);
        return choice;
    }
    
    public String getProgCode(){
        String progCode = Input.getString("Please enter the programme code you want to filter: ", false);
        return progCode;
    }
    
    public String getProgName(){
        String progName = Input.getString("Please enter the programme name you want to filter: ",false);
        return progName;
    }
    
    public String getTutGrpCode(){
        String tutGrpCode = Input.getString("Please enter the tutorial group code you want to filter: ", false);
        return tutGrpCode;
    }
    
    //get choice of filter tutGrp again
    public int reChoiceOfFilterTutGrp(){
        int choice = Input.getChoice("Please enter the index number of the way you want to filter with: ", new String[] {
            "By Programme Code",
            "By Programme Name",
            "By Tutorial Group Code",
        }, (String item) -> item);
        return choice;
    }
    public String continueFilter(){
        String choice = Input.getString("Do you want to continue filter(Y/N): ", false);
        return choice;
    }
    
    public void printAllSelectedTutGrp(ArrayList<TutorialGroup> tutGrp){
        TableBuilder tb = new TableBuilder();
        String[] progCodes = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getProgrammeCode()).toArray(String.class);
        tb.addColumn("Program Code", progCodes);
        String[] progNames = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getProgrammeName()).toArray(String.class);
        tb.addColumn("Program Name", progNames);
        String[] tutGrpCodes = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getTutGrpCode()).toArray(String.class);
        tb.addColumn("Tutorial Group Code", tutGrpCodes);
        String[] numOfStudents = tutGrp.map((TutorialGroup tutGrps) -> tutGrps.getStudent().getNumberOfEntries()).toArray(String.class);
        tb.addColumn("Number of Students", numOfStudents);
    }
    
    //message to prompt error for choice
    public void errorChoice(){
        System.out.println("The choice enter is not available.");
    }
    

}
