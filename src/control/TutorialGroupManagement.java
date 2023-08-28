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
    private ArrayList<Student> studentList = new ArrayList<>();//if wan will be student management
    private TutorialGroupManagementUI display = new TutorialGroupManagementUI();
    
    //main menu display for select function
    public void displayTutGrpManagement() {
        display.header();
        int choice = 999;
        do {
        choice = display.mainMenu();  
            switch(choice){
                case 0:
                    create();
                    break;
                case 1:
                    delete();
                    break;
                case 2:
                    //display.displayTutGrp(tutGrpList);
                default:
            }
        } while(choice != 9);
    }
    
    //function to create a new tutGrp
    public void create(){
        display.header();
        //add tutGrp in the list
        tutGrpList.insert(display.createTutGrp());
    }
    
    //function to delete a tutGrp
    public void delete(){
        display.header();
        //remove tutGrp selected
        tutGrpList.remove(display.deleteTutGrp(tutGrpList));
    }
    
    //function to add student in a tutGrp
    public void add(){
        display.header();
        //display output and get input required
        Student student = display.addStudent();//stall the student obj created
        //display where to stall the student 
        int choice = display.choiceOfTutGrp();
        int tutGrp = 0;//declare variable to stall tutGrp
        switch (choice) {
            case 1: 
                tutGrp = display.choiceOfExistGrp(tutGrpList);//stall the tutGrp selected
                break;
            case 2: 
                create();//create a new tutGrp
                //since new tutGrp will be added at last so just get numOfEntries
                tutGrp = tutGrpList.getNumberOfEntries() - 1;//stall the tutGrp
                break;
            default: display.errorChoice();//prompt error
        }
        //stall tutGrp obj selected
        TutorialGroup selectedTutGrp = tutGrpList.get(tutGrp);
        //add student to tutGrp obj selected
        selectedTutGrp.getStudent().insert(student);
    }
    
    //function to remove a student in a tutGrp
    public void remove(){
        display.header();
        //return and stall the tutGrp obj
        TutorialGroup tutGrp = tutGrpList.get(display.choiceOfExistGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        //display all the students in the tutGrp selected
        //tutGrp.getStudent().iterator();
        display.displayAllStudent(student);
        //remove the student based on selected
        //tutGrp.getStudent().remove(display.choiceOfStudent());
    }
    
    //main method
    public static void main(String[] args) {
        TutorialGroupManagement tutGrpManagement = new TutorialGroupManagement();
        tutGrpManagement.displayTutGrpManagement();
    }
}
