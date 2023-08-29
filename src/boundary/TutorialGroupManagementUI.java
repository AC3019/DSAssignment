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
        System.out.println("");
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
    
    private int getTutGrpChoice(String prompt, ArrayList<TutorialGroup> tutGrp) {
        int choice = Input.getChoice(
            prompt,
            tutGrp.toArray(TutorialGroup.class),
            (item) -> item.getTutGrpCode()
        );
        return choice;
    }

    //print all student
    private void printAllStudent(ArrayList<Student> stud) {
        TableBuilder tb = new TableBuilder();
        String[] ids = stud.map((Student s) -> s.getStudentID()).toArray(String.class);
        tb.addColumn("Student ID", ids);
        String[] names = stud.map((Student s) -> s.getStudentName()).toArray(String.class);
        tb.addColumn("Student Name", names);
        Integer[] ages = stud.map((Student s) -> s.getStudentAge()).toArray(Integer.class);
        tb.addColumn("Student Age", ages);
        tb.printTable(true);
    }
    
    //receive tutGrp arrayList
    public int deleteTutGrp(ArrayList<TutorialGroup> tutGrp) {
        return this.getTutGrpChoice(
            "Enter the index number of tutorial group that you want to delete: ", tutGrp
        );
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
            "Create new tutorial group",
            "Existing tutorial group",
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
    
    //display all student in tutGrp
    public void displayAllStudent(Student[] student){
        printAllStudent(new ArrayList<>(student));
        Input.getInt("ashjdga", 0, student.getNumberOfEntries());

        //TableBuilder tb = new TableBuilder();
        //tb.addColumn("StudentID", student[i].getStudentID().toString());
        // for(int i = 1; i <= tutGrp.getStudent().getNumberOfEntries(); i++){
        //     System.out.println(i + "  " + tutGrp.getStudent().toArray(Student.class)[i].toString());
        // }

        //int choice = Input.getChoice("", new Student[] {
            
        //}, (Student item) -> { return item.getStudentID() + item.getStudentName(); });
    }
    
    //ask which student the user want to delete
    public void choiceOfStudent(){//should be int to return choice
        //int choice = Input.getChoice("Please enter the index number of student you want to delete: ", choices, c);
        //return choice;
    }
    
    //message to prompt error
    public void errorChoice(){
        System.out.println("The choice enter is not available.");
    }

}
