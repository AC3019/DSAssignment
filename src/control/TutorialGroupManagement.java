/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;
import adt.*;
import boundary.TutorialGroupManagementUI;
import entity.Student;
import entity.TutorialGroup;
/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroupManagement {

    //variables
    private ArrayList<TutorialGroup> tutGrpList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    //private TutorialGroupManagementUI display = new TutorialGroupManagementUI();
    
    //main menu display for select function
    public void displayTutGrpManagement() {
        int choice;
        
    }
    
    //function to create a new tutGrp
    public void create(){
        //get grpCode for the new grp
        String grpCode = "haha";//this will be replace with user input
        //create a new group object
        TutorialGroup newGrp = new TutorialGroup(grpCode);
        //add tutGrp in the list
        tutGrpList.insert(newGrp);
    }
    //function to add student in a tutGrp
    public void add(Student student){
        //display available tutGrp
        int choice = -1;//stall tutGrp selected
        //add student in a specific tutGrp
        studentList.insert(choice,student);
    }
    
    //main method
    public static void main(String[] args) {
        TutorialGroupManagement tutGrpManagement = new TutorialGroupManagement();
        tutGrpManagement.displayTutGrpManagement();
    }
}
