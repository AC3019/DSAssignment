/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

/**
 *
 * @author yong
 */
import entity.Tutor;
import adt.HashMap;
import adt.ArrayList;
import adt.ListInterface;
import utility.Input;
import java.io.Serializable;
import boundary.TutorManagementUI;
import utility.TableBuilder;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.LocalDateTime;

public class TutorManagement implements Serializable {

    HashMap<Integer, Tutor> tutors = new HashMap<>();
    TutorManagementUI tmu = new TutorManagementUI();
    private boolean dataChanged = false;

    public TutorManagement() {
        //data
        Tutor t1 = new Tutor("YONG YI KIT", DepartmentManagement.departments[0], 'M', 20, "0123456789", "001010-07-0441", 50000);
        this.tutors.put(t1.getId(), t1);
    }

    public boolean isDataChanged() {
        return this.dataChanged;
    }

    public void setDataChanged(boolean dataChanged) {
        this.dataChanged = dataChanged;
    }

    public Tutor[] getTutors() {
        Tutor[] ts = this.tutors.getValues(Tutor.class);
        return ts;
    }

    public void displayMenu(TeachingAssignment ta) {
        boolean running = true;

        while (running) {
            tmu.displayHeader();
            int choice = tmu.printMenu();
            switch (choice) {
                case 0:
                    addNewTutor();
                    break;
                case 1:
                    removeTutor(ta);
                    break;
                case 2:
                    searchTutor();
                    break;
                case 3:
                    amendTutorDetails();
                    break;

                case 4:
                    listAllTutor();
                    break;

                case 5:
                    filterTutor();
                    break;
                case 6:
                    getTotalNumOfTutor();
                    break;
                case 7:
                    generateReport();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    tmu.invalidSwitchChoice();
            }

        }

        tmu.exitSysMsg();
    }

    public void addNewTutor() {
        Tutor tutorObj = tmu.tutorInput();
        if (tutors.size() > 0) {
            //cannot add two tutor with same ic
            boolean containsDuplicate = tutors.contains((Integer k, Tutor t) -> t.getIcNO().equals(tutorObj.getIcNO()));

            if (containsDuplicate) {
                tmu.duplicateMsg();
            } else {
                tutors.put(tutorObj.getId(), tutorObj);
                tmu.printPrompt("Tutor added successfully");
            }
        } else {
            // Will only come here for first tutor addition
            tutors.put(tutorObj.getId(), tutorObj);
            tmu.printPrompt("Tutor added successfully ");
        }
    }

    public void removeTutor(TeachingAssignment ta) {

        if (tutors.size() > 0) {
            //show the tutor id and names available in hashmap for user convenient
            Integer[] t = this.tutors.getKeys(Integer.class);
            Tutor[] tn = this.tutors.getValues(Tutor.class);
            String[] tutorNames = new String[tn.length];
            for (int i = 0; i < tn.length; i++) {
                tutorNames[i] = tn[i].getName();
            }
            tmu.printPromptWithoutEmptyLine("Available tutor's id inside the map are ");
            for (int i = 0; i < t.length; i++) {
                tmu.printAvailableNameInMap(t[i] + " " + tutorNames[i] + " ");
            }
            tmu.printEmptyLine();
            int id = tmu.removeTutorData();
            //remove by tutor id
            if (tutors.containsKey(id)) {
                Tutor removed = tutors.remove(id);
                tmu.printPrompt("Tutor removed successfully ");
                ta.cleanUp(removed);
            } else {
                tmu.printPrompt("Invalid tutor id");
            }
        } else {
            tmu.printPrompt("pls add tutor only can removed");
        }

    }

    public void searchTutor() {

        char continueInput = 'Y';
        int choice = 0;

        if (tutors.size() <= 0) {
            tmu.printPrompt("Pls add tutor first");
        } else {
            do {
                choice = tmu.searchTutorMenu();
                tmu.cleanBuffer();
                switch (choice) {
                    case 0:
                        //show the tutor id and names available in hashmap for user convenient
                        Integer[] t = this.tutors.getKeys(Integer.class);
                        Tutor[] tn = this.tutors.getValues(Tutor.class);
                        String[] tutorNames = new String[tn.length];
                        for (int i = 0; i < tn.length; i++) {
                            tutorNames[i] = tn[i].getName();
                        }
                        tmu.printPromptWithoutEmptyLine("Available tutor's id inside the map are ");
                        for (int i = 0; i < t.length; i++) {
                            tmu.printAvailableNameInMap(t[i] + " " + tutorNames[i] + " ");
                        }
                        tmu.printEmptyLine();
//search by id
                        int id = tmu.searchTutorId();
                        if (tutors.containsKey(id)) {
                            tmu.printKey(tutors.get(id));
                        } else {
                            tmu.printPrompt("Invalid tutor's id");
                        }
                        break;
                    //search by ic
                    case 1:
                        String ic = tmu.searchTutorIc();

                        Integer key = tutors.keyOf((Integer I, Tutor tu) -> tu.getIcNO().equals(ic));

                        if (key == null) {
                            tmu.printPrompt("Invalid ic");
                        } else {
                            tmu.printKey(tutors.get(key));
                        }
                        break;
                    case 2:
                        break;
                    default:
                        tmu.printPrompt("Invalid option ");
                }

                if (choice == 2) {
                    break;
                }
                if (choice == 0) {
                    tmu.cleanBuffer();
                }

                continueInput = tmu.wantToContinue();
                while (continueInput != 'Y' && continueInput != 'N') {
                    tmu.continueMsgError();
                    continueInput = tmu.wantToContinue();
                }
            } while (continueInput == 'Y');

        }
    }

    public void amendTutorDetails() {
        int id;
        String newName;
        String newDepartment;
        String newPhoneNum;
        char continueInput;
        char falseContinue = 'Y';
        if (tutors.size() != 0) {
            Integer[] t = this.tutors.getKeys(Integer.class);
            Tutor[] tn = this.tutors.getValues(Tutor.class);
            String[] tutorNames = new String[tn.length];
            for (int i = 0; i < tn.length; i++) {
                tutorNames[i] = tn[i].getName();
            }
            tmu.printPromptWithoutEmptyLine("Available tutor's id inside the map are ");
            for (int i = 0; i < t.length; i++) {
                tmu.printAvailableNameInMap(t[i] + " " + tutorNames[i] + " ");
            }
            tmu.printEmptyLine();
            id = tmu.getNewId();
            if (tutors.containsKey(id)) {
                Tutor tutor = tutors.get(id);

                do {
                    int choice = tmu.amendTutorMenu();
                    switch (choice) {
                        case 0:
                            tmu.printPrompt("Your current name is " + tutor.getName());
                            newName = tmu.getNewName();
                            if (tutor.getName().equals(newName)) {
                                tmu.printPrompt("Please enter a new name... amend fail");
                                //prompt user error msg if ammend using the same name 
                                do {
                                    falseContinue = Character.toUpperCase(Input.getChar("Do you want to enter a new name (Y/N):"));
                                    if (falseContinue != 'Y') {
                                        break;
                                    } else {
                                        newName = tmu.getNewName();
                                    }
                                } while (tutor.getName().equals(newName));
                                tutor.setName(newName);
                            } else {
                                tutor.setName(newName);
                                tmu.printPrompt("Tutor name amended successfully.");

                            }
                            break;

                        case 1:
                            tmu.printPrompt("Your current department is " + tutor.getDepartment());
                            newDepartment = tmu.getDepartment();
                            if (tutor.getDepartment().equals(newDepartment)) {
                                tmu.printPrompt("Pls enter a new department... amend fail");
                                //prompt user error msg if ammend using same department
                                do {
                                    tmu.cleanBuffer();
                                    falseContinue = Character.toUpperCase(Input.getChar("Do you want to enter a new department(Y/N):"));
                                    if (falseContinue != 'Y') {
                                        break;
                                    } else {
                                        newDepartment = tmu.getDepartment();
                                    }
                                } while (tutor.getDepartment().equals(newDepartment));
                                tutor.setDepartment(newDepartment);
                            } else {
                                tutor.setDepartment(newDepartment);
                                tmu.printPrompt("Tutor department amended successfully.");
                            }

                            break;
                        case 2:
                            tmu.printPrompt("Your current phone Number is " + tutor.getPhoneNum());

                            newPhoneNum = tmu.getNewPhoneNum();

                            if (tutor.getPhoneNum().equals(newPhoneNum)) {
                                tmu.printPrompt("pls enter a new phone num");
                                do {
                                    falseContinue = Character.toUpperCase(Input.getChar("Do you want to enter a new phone num(Y/N):"));
                                    if (falseContinue != 'Y') {
                                        break;
                                    } else {
                                        newPhoneNum = tmu.getNewPhoneNum();
                                    }
                                } while (tutor.getPhoneNum().equals(newPhoneNum));
                                tutor.setPhoneNumber(newPhoneNum);
                            } else {
                                tutor.setPhoneNumber(newPhoneNum);

                                tmu.printPrompt("Tutor's phone number amended successfully. ");
                            }

                            break;
                        case 3:
                            tmu.printPrompt("Exit");
                            break;
                        default:
                            tmu.invalidSwitchChoice();
                    }
                    if (choice == 3) {

                        break;
                    }
                    if (choice == 1) {
                        tmu.cleanBuffer();
                    }
                    continueInput = tmu.wantToContinue();
                    while (continueInput != 'Y' && continueInput != 'N') {
                        tmu.continueMsgError();
                        continueInput = tmu.wantToContinue();
                    }
                } while (continueInput == 'Y');
            } else {
                tmu.printPrompt("Tutor's id invalid ");
            }
        } else {
            tmu.emptyListMsg();
        }

    }

    public void listAllTutor() {
        char continueInput = 'Y';

        if (tutors.size() > 0) {
            do {
                int choice = tmu.listAllTutorMenu();

                switch (choice) {
                    case 0:
                        //sort by name in alphabetic order
                        adt.ArrayList<Tutor> arl = new adt.ArrayList<>(this.tutors.getValues(Tutor.class));
                        arl.sort((Tutor t1, Tutor t2) -> t1.getName().compareTo(t2.getName()));
                        for (Tutor t : arl) {
                            tmu.printKey(t);
                        }
                        break;
                    //sort by tutor's id
                    case 1:
                        for (HashMap<Integer, Tutor>.Pair p : this.tutors) {
                            Tutor t = p.getValue();
                            tmu.printKey(t);
                        }
                        break;
                    //sort by salary descending order
                    case 2:
                        adt.ArrayList<Tutor> arl1 = new adt.ArrayList<>(this.tutors.getValues(Tutor.class));

                        arl1.sort((Tutor t2, Tutor t1) -> t1.getSalary() - t2.getSalary());
                        for (Tutor t : arl1) {
                            tmu.printKey(t);
                        }
                        break;

                    case 3:
                        break;
                    default:
                        tmu.invalidSwitchChoice();
                }
                if (choice == 3) {
                    break;
                }

                tmu.cleanBuffer();
                continueInput = tmu.wantToContinue();
                while (continueInput != 'Y' && continueInput != 'N') {
                    tmu.continueMsgError();
                    continueInput = tmu.wantToContinue();
                }
            } while (continueInput == 'Y');
        } else {
            tmu.printPrompt("no tutor inside the system");
        }
    }

    public void filterTutor() {
        char continueInput = 'Y';
        int choice = 0;
        HashMap<Integer, Tutor> ts = this.tutors;

        if (tutors.size() > 0) {
            do {
                choice = tmu.filterTutorData();

                switch (choice) {
                    case 0:
                        ts = findByGender(ts);

                        if (ts.size() == 0) {
                            tmu.printPrompt("Invalid criteria... don't have tutor with this criteria");
                            break;
                        }

                        break;
                    case 1:
                        ts = findByDepartment(ts);
                        if (ts.size() == 0) {
                            tmu.printPrompt("Invalid criteria... don't have tutor with this criteria");
                            break;
                        }

                        break;
                    case 2:
                        ts = findByName(ts);
                        if (ts.size() == 0) {
                            tmu.printPrompt("Invalid criteria... don't have tutor with this criteria");
                            break;
                        }

                        break;
                    case 3: // show all tutors
                        break;
                    default:
                        tmu.invalidSwitchChoice();
                }
                if (choice == 3) {
                    break;
                }

                if (ts.size() > 0) {
                    if (choice == 1) {
                        tmu.cleanBuffer();
                    }
                    continueInput = tmu.wantToContinueFilter();
                    while (continueInput != 'Y' && continueInput != 'N') {
                        tmu.continueMsgError();
                        continueInput = tmu.wantToContinueFilter();
                    }
                }
            } while (continueInput == 'Y' && ts.size() != 0);
        } else {
            tmu.printPrompt("Pls add tutor first");
        }

        Tutor[] filteredTutors = ts.getValues(Tutor.class);
        for (Tutor t : filteredTutors) {
            tmu.printKey(t);
        }

    }

    private HashMap<Integer, Tutor> findByGender(HashMap<Integer, Tutor> tutors) {
        char genderToFilter;
        boolean continueInput = true;
        do {
            genderToFilter = Character.toUpperCase(Input.getChar("Gender to filter(M/F): "));
            //chk only 'M' or 'F' 
            if (genderToFilter != 'M' && genderToFilter != 'F') {
                tmu.printPrompt("Error,gender should be only M or F... Please reenter");
                genderToFilter = Character.toUpperCase(Input.getChar("Gender to filter(M/F): "));
            }
        } while (genderToFilter != 'M' && genderToFilter != 'F');

        char finalGender = genderToFilter;
        //filter gender
        HashMap<Integer, Tutor> matchedTutors = tutors.filter(
                (Integer k, Tutor v) -> v.getGender() == (finalGender)
        );

        return matchedTutors;
    }

    private HashMap<Integer, Tutor> findByDepartment(HashMap<Integer, Tutor> tutors) {

        String departmentToFilter = tmu.getDepartment();
        //filter if same department 
        HashMap<Integer, Tutor> matchedTutors = tutors.filter(
                (Integer k, Tutor v) -> v.getDepartment().equals(departmentToFilter)
        );
        return matchedTutors;
    }

    private HashMap<Integer, Tutor> findByName(HashMap<Integer, Tutor> tutors) {
        String nameToFilter = Input.getString("Name to filter: ", false).toUpperCase();
        //filter by name
        HashMap<Integer, Tutor> matchedTutors = tutors.filter(
                (Integer k, Tutor v) -> v.getName().contains(nameToFilter)
        );

        return matchedTutors;
    }

    public void getTotalNumOfTutor() {
        tmu.printPrompt("The total number of tutors are " + tutors.size());
    }

    public void generateReport() {
        ArrayList<Tutor> ts = new ArrayList(tutors.getValues(Tutor.class));
        TableBuilder tb = new TableBuilder();
        LocalDateTime obj = LocalDateTime.now();
        DateTimeFormatter myObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = obj.format(myObj);

        if (tutors.size() > 0) {
            
            tmu.printEmptyLine();

            tb.addColumn("Tutor ID", ts.map((t) -> String.valueOf(t.getId())).toArray(String.class));
            tb.addColumn("Tutor Name", ts.map((t) -> t.getName()).toArray(String.class));
            tb.addColumn("Tutor Department", ts.map((t) -> t.getDepartment()).toArray(String.class));
            tb.addColumn("Tutor Contact Number", ts.map((t) -> t.getPhoneNum()).toArray(String.class));
            tb.addColumn("Tutor Gender", ts.map((t) -> t.getGender()).toArray(Character.class));
            tb.addColumn("Tutor Age", ts.map((t) -> t.getAge()).toArray(Integer.class));
            tb.addColumn("Tutor Salary", ts.map((t) -> t.getSalary()).toArray(Integer.class));

        } else {
            tmu.printPrompt("No tutor inside the list");
        }
        
        String x=tmu.printFormatDateTime(formattedDate);
        tb.printTable(true);
       tmu.saveToFile(tb, true, x);

    }

}
