/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary; 
import adt.ArrayList;
import entity.TutorialGroup;
import entity.Student;
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
    }

    public int mainMenu(){
        System.out.println("Welcome to TARUMT system");
        System.out.println("1. Create new tutorial group");
        System.out.println("2. Delete tutorial group");
        System.out.println("3. Display all tutorial group");
        System.out.println("4. Add a student to a tutorial group");
        System.out.println("5. Remove a student from a tutorial group");
        System.out.println("8. Display all students in a tutorial group");
        System.out.println("Enter your choice: ");
        int choice;
        return choice;
    }
    
    public TutorialGroup createTutGrp(){
        System.out.println("Please enter the tutorial group code:");
        String tutGrpCode;
        return new TutorialGroup(tutGrpCode);
    }
    
    //display all tutGrp available in the arrayList
    public void displayTutGrp(ArrayList<TutorialGroup> tutGrp){
        //TutorialGroup[] tutGrp = grp.toArray(ArrayList<TutorialGroup>);
        //tutGrp.iterator(); //shud be can display all
        for(int i = 1; i <= tutGrp.getNumberOfEntries(); i++){
            System.out.println(i + "  " + tutGrp.toString());
        }
    }
    
    //receive tutGrp arrayList
    public int deleteTutGrp(){
        System.out.println("Enter the index number of tutorial group that you want to delete: ");
        int choice;
        return choice;
    }
    
    //create a new student obj
    public Student addStudent(){
        System.out.print("Please enter the student ID of the student(22PMR05):");
        String studentID;
        System.out.print("Please enter the name of the student: ");
        String name;
        System.out.print("Please enter the age of the student: ");
        int age;
        return new Student(studentID,name,age);
    }
    
    //ask user which way the user want to add the user
    //use after student obj create
    public int choiceOfTutGrp(){
        System.out.println("Where do you want to add the student to:");
        System.out.println("1. Create new tutorial group");
        System.out.println("2. Existing tutorial group");
        int choice;
        return choice;
    }
    
    //ask where the user want to add the user to 
    //use after choiceOfTutGrp()
    public int choiceOfExistGrp(){
        System.out.print("Please enter the index number of tutorial group you want to insert: ");
        int choice;
        return choice;
    }
    
    //display all student in tutGrp
    public void displayStudent(TutorialGroup tutGrp){
        for(int i = 1; i <= tutGrp.getStudent().getNumberOfEntries(); i++){
            System.out.println(i + "  " + tutGrp.getStudent().toString());
        }
    }
    
    //ask which student the user want to delete
    //use after choiceOfTutGrp()
    public int choiceOfStudent(){
        System.out.print("Please enter the index number of student you want to delete: ");
        int choice;
        return choice;
    }
    
    //message to prompt error
    public void errorChoice(){
        System.out.println("The choice enter is not available.");
    }


}
