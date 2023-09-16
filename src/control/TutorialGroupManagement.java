/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;
import adt.*;
import boundary.TutorialGroupManagementUI;
import entity.Student;
import entity.TutorialGroup;
import utility.TableBuilder;
import java.io.Serializable;
/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroupManagement implements Serializable {

    //variables
    private ArrayList<TutorialGroup> tutGrpList = new ArrayList<>();
    private static TutorialGroupManagementUI display = new TutorialGroupManagementUI();
    public ArrayList<TutorialGroup> getTutorialGroups() {
        return this.tutGrpList;
    }

    public TutorialGroupManagement() {
        TutorialGroup tg1 = new TutorialGroup("RSW", "Degree in Software Engineering", "230101");
        tutGrpList.insert(tg1);
        Student s1 = new Student("2205","James",21,"Male");
        Student s2 = new Student("2205","Nicole",22,"Female");
        tutGrpList.get(0).getStudent().insert(s1);
        tutGrpList.get(0).getStudent().insert(s2);
    }
    
    //main menu display for select function
    public void displayTutGrpManagement(TeachingAssignment ta) {
        display.header();
        int choice = 999;
        do {
        choice = display.mainMenu();  
            switch(choice) {
                case 0:
                    createTutGrp();
                    break;
                case 1:
                    removeTutGrp(ta);
                    break;
                case 2:
                    sortTutGrpList();
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
                case 7:
                    displayAndEditAllStudent();
                    break;
                case 8:
                    filterTutGrp();
                    break;
                default:
                    continue;
            }
        } while (choice != 9);
        System.out.println("Exiting tutorial group management....");
    }
    
    //function to create a new tutGrp
    public void createTutGrp() {
        //add tutGrp in the list
        tutGrpList.insert(display.createTutGrp());
        display.createTutGrpSuccess();
    }
    
    //function to delete a tutGrp
    public void removeTutGrp(TeachingAssignment ta) {
        if (!tutGrpList.isEmpty()) {
            ArrayList<TutorialGroup> passingList = new ArrayList<>();
            ArrayList<Student> studentList = new ArrayList<>();
            //remove tutGrp selected
            TutorialGroup tutGrpRemoved = tutGrpList.remove(display.deleteTutGrp(tutGrpList));
            display.deleteTutGrpSuccess();
            //ask want to move the group removed into passing list
            char decision;
            do {//valiadation check to get only Y/N
                display.cleanBuffer();
                decision = display.moveToPassList().toUpperCase().charAt(0);
                while (decision != 'Y' && decision != 'N') {
                    display.errorInput();
                    decision = display.moveToPassList().toUpperCase().charAt(0);
                }
            } while (decision != 'Y' && decision != 'N');
            if (decision == 'Y') {
                passingList.insert(tutGrpRemoved);
                studentList = tutGrpRemoved.getStudent();
                studentList = studentList.filter((Student s) -> s.getStudentDemeritMark() >= 50);
                display.moveToPassingListSuccess();
                display.passingList(passingList.get(passingList.getNumberOfEntries()-1));
                display.saveFile(display.displayAllSelectedStudent(studentList));
                ta.cleanUp(tutGrpRemoved);
            }
        } else
            display.noTutGrp();
    }
    
    public void sortTutGrpList() {
        //display all tutGrp first
        display.printAllSelectedTutGrp(tutGrpList);
        if (!tutGrpList.isEmpty()) {
            //ask whether want to sort
            char decision; 
            do {
                decision = display.sortChoice().toUpperCase().charAt(0);
                while (decision != 'Y' && decision != 'N') {
                    display.errorInput();
                    decision = display.sortChoice().toUpperCase().charAt(0);
                }
            } while (decision != 'Y' && decision != 'N');
            if (decision == 'Y') {
                //ask for sort type
                int choice = display.sortChoiceType();
                if (choice == 0) {//sort by ProgrammeCode
                    tutGrpList.sort((TutorialGroup t1, TutorialGroup t2) -> t1.getProgrammeCode().compareTo(t2.getProgrammeCode()));
                        if (!tutGrpList.isEmpty()) {
                            //print in table form
                            display.afterSort();
                            display.cleanBuffer();
                        } else {
                            display.noTutGrp();
                        }
                } else if (choice == 1) {//sort by numOfStudents
                    display.cleanBuffer();
                    tutGrpList.sort((TutorialGroup t1, TutorialGroup t2) -> t2.getStudent().getNumberOfEntries() - t1.getStudent().getNumberOfEntries());
                        if (!tutGrpList.isEmpty()) {
                            //print in table form
                            display.afterSort();
                        } else {
                            display.noTutGrp();
                        }
                } else {
                    display.errorChoice();
                }
            }
            display.saveFile(display.printAllSelectedTutGrp(tutGrpList));
        } else {
            display.noTutGrp();
        }
        display.continueEnter();//if dw sort direct ask for input to continue
    }
    
    //function to add student in a tutGrp
    public void addStudent() {
        Student student = display.addStudent();//stall the student obj created
        display.addStudentSuccess();
        //display where to stall the student 
        int choice = display.choiceOfTutGrp();
        if (choice == 0) {//to check whether there has a tutGrp
            if (tutGrpList.isEmpty()) {//if no tutGrp
                display.noTutGrp();
                display.creatingNewTutGrp();
                choice = 1;//change to create new tutGrp
            }
        }
        int tutGrp = 999;//declare variable to stall tutGrp
        switch (choice) {
            //insert in exist tutGrp
            case 0: 
                tutGrp = display.choiceOfExistGrp(tutGrpList);//stall the tutGrp selected
                break;
            //insert in new tutGrp
            case 1: 
                display.cleanBuffer();
                createTutGrp();//create a new tutGrp
                //since new tutGrp will be added at last so just get numOfEntries
                tutGrp = tutGrpList.getNumberOfEntries() - 1;//stall the tutGrp
                break;
            default: display.errorChoice();//prompt error, should not be reach
        }
        //stall tutGrp obj selected
        TutorialGroup selectedTutGrp = tutGrpList.get(tutGrp);
        //add student to tutGrp obj selected
        selectedTutGrp.getStudent().insert(student);
        display.addStudentIntoListSuccess();
    }
    
    //function to remove a student in a tutGrp
    public void removeStudent() {
        //return and stall the tutGrp obj
        TutorialGroup tutGrp = tutGrpList.get(display.choiceOfExistGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        //check is the tutGrp selected got student
        if (student.length > 0) {
            //display all the students and remove the student based on selected in the tutGrp selected
            tutGrp.getStudent().remove(display.displayAllStudentForRemove(student));
            display.removeStudentSuccess();
        } else {
            display.noStudent();
        }
    }
    
    //function to change a tutGrp for a student
    public void changeTutGrp() {
        //display all tutGrp and find the tutGrp
        TutorialGroup tutGrp = tutGrpList.get(display.findStudentInTutGrp(tutGrpList));
        Student[] student = tutGrp.getStudent().toArray(Student.class);
        //check is the tutGrp selected got student
        if (student.length > 0) {
            //return and remove the student remove
            Student selectedStudent = tutGrp.getStudent().remove(display.displayAllStudent(student));
            tutGrp = tutGrpList.get(display.insertStudentInTutGrp(tutGrpList));
            tutGrp.getStudent().insert(selectedStudent);
            display.changeTutGrpSuccess();
        } else {
            display.noStudent();
            changeTutGrp();
        }
    }
    
    public void findStudent() {
        int choice = display.choiceOfSearchingStudent();
        String tempFilterWith = "";
        
        while (true)  {
            if (choice == 0) {
                tempFilterWith = display.findStudentID();
                break;
            } else if (choice == 1) {
                tempFilterWith = display.findStudentName();
                break;
            } else {
                display.errorChoice();
            }
        }

        final String filterWith = tempFilterWith;
        ArrayList<Student> studentList = new ArrayList<>();
        for (int i = 0; i < tutGrpList.getNumberOfEntries(); i++) {
            int index = tutGrpList.get(i).getStudent().indexOf((t) -> {
                if (choice == 0) {
                    return t.getStudentID().equals(filterWith);
                } else {
                    return t.getStudentName().equals(filterWith);
                }
            });
            if (index >= 0) {
                studentList.insert(tutGrpList.get(i).getStudent().get(index));
                break; // alrd found the student, pointless looping over other tutGrps
            }
        }

        if (studentList.isEmpty()) {
            display.studentNotFound();
        } else {
            display.displayAllSelectedStudent(studentList);
            display.continueEnter();
        }
    }
    
    public void displayAndEditAllStudent() {
        //create a new arrayList to stall the filter result
        ArrayList<Student> selectedStudentList = new ArrayList<>();
        if (tutGrpList.getNumberOfEntries() > 0) {
            int choice = display.choiceOfTutGrp(tutGrpList);
            TutorialGroup tutGrp = tutGrpList.get(choice);
            display.displayAllSelectedStudent(tutGrp.getStudent());
            //if number of student > 0
            if (tutGrp.getStudent().getNumberOfEntries() > 0) {
                //edit demerit mark function
                char editMark;
                do {
                    display.cleanBuffer();
                    editMark = display.editMark().toUpperCase().charAt(0);
                    while (editMark != 'Y' && editMark != 'N') {
                        display.errorInput();
                        editMark = display.editMark().toUpperCase().charAt(0);
                    }
                    if (editMark == 'Y') { 
                        //ask for index of student to edit
                        int indexStudent = display.editStudent(tutGrp.getStudent());
                        tutGrp.getStudent().get(indexStudent).setMark(display.getStudentMark());
                        display.studentEditMarkSuccess();
                    }
                } while (editMark != 'Y' && editMark != 'N');
                //edit student detail function
                char decision;
                do {
                    if (editMark == 'Y')
                        display.cleanBuffer();
                    decision = display.edit().toUpperCase().charAt(0);
                    while (decision != 'Y' && decision != 'N') {
                         display.errorInput();
                         decision = display.edit().toUpperCase().charAt(0);
                    }
                } while (decision != 'Y' && decision != 'N');
                if (decision == 'Y') {
                    //ask for index of student to edit
                    int indexStudent = display.editStudent(tutGrp.getStudent());
                    tutGrp.getStudent().get(indexStudent).setStudentName(display.findStudentName());
                    tutGrp.getStudent().get(indexStudent).setAge(display.findStudentAge());
                    display.studentEditSuccess();
                }
                //filter function
                char filterDecision;
                do {
                    if (decision == 'Y')
                        display.cleanBuffer();
                    filterDecision = display.filterStudent().toUpperCase().charAt(0);
                    while (filterDecision != 'Y' && filterDecision != 'N') {
                        display.errorInput();
                        filterDecision = display.filterStudent().toUpperCase().charAt(0);
                    }
                } while (filterDecision != 'Y' && filterDecision != 'N');
                if (filterDecision == 'Y') {
                    int genderChoice;
                    String gender;
                    genderChoice = display.getGender();
                    if (genderChoice == 0) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }
                    selectedStudentList = tutGrp.getStudent().filter((Student item) -> item.getStudentGender().equals(gender));
                    display.afterFilter();        
                    display.cleanBuffer();
                } else
                    selectedStudentList = tutGrp.getStudent();
                display.saveFile(display.displayAllSelectedStudent(selectedStudentList));
            } else {
                display.noStudent();
            }
        } else {
            display.noTutGrp();
        }
    }
    
    public void filterTutGrp() {
        //create a new arrayList to stall filter result
        ArrayList<TutorialGroup> selectedTutGrpList = new ArrayList<>();
        //clean buffer
        selectedTutGrpList.clear();
        char decision = 'Y';
        int choice = display.choiceOfFilterTutGrp();
        //filter first time
        switch (choice) {
            case 0://filter by progCode
                String progCode = display.getProgCode().toUpperCase();
                selectedTutGrpList = tutGrpList.filter((TutorialGroup item) -> item.getProgrammeCode().equals(progCode));
                break;
            case 1://filter by tutGrpCode
                display.cleanBuffer();
                String tutGrpCode = display.getTutGrpCode();
                selectedTutGrpList = tutGrpList.filter((TutorialGroup item) -> item.getTutGrpCode().equals(tutGrpCode));
                break;
            case 2://filter by all criteria above
                String reProgCode = display.getProgCode().toUpperCase();
                String reTutGrpCode = display.getTutGrpCode();
                selectedTutGrpList = tutGrpList.filter((TutorialGroup item) -> item.getProgrammeCode().equals(reProgCode));
                selectedTutGrpList = selectedTutGrpList.filter((TutorialGroup item) -> item.getTutGrpCode().equals(reTutGrpCode));
                break;
            default:
        }
        if (choice != 2) {
            //ask whether want to contiue filter
            do {
                decision = display.continueFilter().toUpperCase().charAt(0);
            } while (decision != 'Y' && decision != 'N');
            //if yes 
            while (decision == 'Y') {
                choice = display.reChoiceOfFilterTutGrp();
                switch (choice) {
                    case 0:
                        String progCode = display.getProgCode();
                        selectedTutGrpList = selectedTutGrpList.filter((TutorialGroup item) -> item.getProgrammeCode().equals(progCode));
                        break;
                    case 1:
                        display.cleanBuffer();
                        String tutGrpCode = display.getTutGrpCode();
                        selectedTutGrpList = selectedTutGrpList.filter((TutorialGroup item) -> item.getTutGrpCode().equals(tutGrpCode));
                        break;
                    default:
                }
                //ask whether want to continue filter
                do {
                    decision = display.continueFilter().toUpperCase().charAt(0);
                } while (decision != 'Y' && decision != 'N');
            }
        }
        //display the filter result
        if (!selectedTutGrpList.isEmpty()) {
            TableBuilder tb = display.printAllSelectedTutGrp(selectedTutGrpList);//display the result
            //ask for report needed?
           display.saveFile(tb); 
        } else
            display.noTutGrpFound();
    }
}
