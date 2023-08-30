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
    private ArrayList<Student> studentList = new ArrayList<>(new Student[] {
        new Student("12345", "ABCD", 12),
    });//if wan will be student management
    private static TutorialGroupManagementUI display = new TutorialGroupManagementUI();
    
    //main menu display for select function
    public void displayTutGrpManagement() {
        display.header();
        int choice = 999;
        do {
        choice = display.mainMenu();  
            switch(choice){
                case 0:
                    createTutGrp();
                    break;
                case 1:
                    removeTutGrp();
                    break;
                case 2:
                    //display.displayTutGrp(tutGrpList);
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    changeTutGrp();
                    break;
                case 6:
                    findStudent();
                    break;
                default:
                    continue;
            }
        } while(choice != 9);
    }
    
    //function to create a new tutGrp
    public void createTutGrp(){
        display.header();
        //add tutGrp in the list
        tutGrpList.insert(display.createTutGrp());
    }
    
    //function to delete a tutGrp
    public void removeTutGrp(){
        display.header();
        //remove tutGrp selected
        tutGrpList.remove(display.deleteTutGrp(tutGrpList));
    }
    
    //function to add student in a tutGrp
    public void addStudent(){
        display.header();
        //display output and get input required
        Student student = display.addStudent();//stall the student obj created
        //display where to stall the student 
        int choice = display.choiceOfTutGrp();
        int tutGrp = 999;//declare variable to stall tutGrp
        switch (choice) {
            //insert in exist tutGrp
            case 0: 
                tutGrp = display.choiceOfExistGrp(tutGrpList);//stall the tutGrp selected
                break;
            //insert in new tutGrp
            case 1: 
                createTutGrp();//create a new tutGrp
                //since new tutGrp will be added at last so just get numOfEntries
                tutGrp = tutGrpList.getNumberOfEntries() - 1;//stall the tutGrp
                break;
            default: display.errorChoice();//prompt error, should not be reach
        }
        //stall tutGrp obj selected
        TutorialGroup selectedTutGrp = tutGrpList.get(tutGrp);
        //add student to tutGrp obj selected
        selectedTutGrp.getStudent().insert(student);//ERROR occur
        //Cannot invoke "adt.ArrayList.insert(Object)" because the return value of "entity.TutorialGroup.getStudent()" is null
    }
    
    //function to remove a student in a tutGrp
    public void removeStudent(){
        display.header();
        //return and stall the tutGrp obj
        TutorialGroup tutGrp = tutGrpList.get(display.choiceOfExistGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        //display all the students and remove the student based on selected in the tutGrp selected
        tutGrp.getStudent().remove(display.displayAllStudent(student));
    }
    
    
    //function to change a tutGrp for a student
    public void changeTutGrp(){
        display.header();
        //display all tutGrp and find the tutGrp
        TutorialGroup tutGrp = tutGrpList.get(display.findStudentInTutGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        //return and remove the student remove
        Student selectedStudent = tutGrp.getStudent().remove(display.displayAllStudent(student));
        tutGrp = tutGrpList.get(display.insertStudentInTutGrp(tutGrpList));
        tutGrp.getStudent().insert(selectedStudent);
    }
    
    public void findStudent(){
        display.header();
        TutorialGroup tutGrp = tutGrpList.get(display.findStudentInTutGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        int choice = display.choiceOfSearchingStudent();
        switch (choice){
            //search using studentID
            case 1:
                String selectedStudentID = display.findStudentID();
                //loop through the whole student array to find the student that match the condition
                for (int i = 0; i < student.length; i++){
                    //if studentID match 
                    if (student[i].getStudentID().equals(selectedStudentID))
                        studentList.insert(student[i]);//insert the student into student ArrayList
                    //else
                        //display.studentNotFound();
                }
                int studentIndex = tutGrp.getStudent().indexOf((Student s) -> s.getStudentID().equals(selectedStudentID));
                tutGrp.getStudent().get(studentIndex);
                break;
            //search using studentName
            case 2:
                String selectedStudentName = display.findStudentName();
                for (int i = 0; i < student.length; i++){
                    //if studentName match 
                    if (student[i].getStudentName().equals(selectedStudentName))
                        studentList.insert(student[i]);//insert the student into student ArrayList
                }
            case 3:
                int selectedStudentAge = display.findStudentAge();
                for (int i = 0; i < student.length; i++){
                    //if studentAge match 
                    if (student[i].getStudentAge() == selectedStudentAge)//cannot use .equals bcuz is primitive type
                        studentList.insert(student[i]);//insert the student into student ArrayList
                }
            default:
                break;
        }
        //pass the student ArrayList into the method to print out in table form
        display.displayAllSelectedStudent(studentList);
    }
    
    //main method
    public static void main(String[] args) {
        TutorialGroupManagement tutGrpManagement = new TutorialGroupManagement();
        tutGrpManagement.displayTutGrpManagement();
        //display.displayAllStudent(tutGrpManagement.studentList.toArray(Student.class));
    }
}
