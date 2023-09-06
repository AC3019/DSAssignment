package boundary;

import java.io.Serializable;

import adt.ArrayList;
import control.DepartmentManagement;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.Input;
import utility.TableBuilder;

public class TeachingAssignmentUI implements Serializable {
    
    public int getMenuChoice() {
        return Input.getChoice("Enter your choice: ", new String[] {
            "Assign tutor to a course",
            "Assign tutorial groups to tutor",
            "Remove tutor from a course",
            "Remove tutorial group from a tutor",
            "List all tutors under a course",
            "List all courses under a tutor",
            "Generate report",
            "Exit teaching assignment"
        }, (s) -> s);
    }

    public int getCourseChoice(Course[] courses, String ifEmptyArr) {
        if (courses.length <= 0) {
            System.out.println(ifEmptyArr);
            Input.pause();
            return -1;
        }
        return Input.getChoice(
            "Which course to assign: ", 
            courses, 
            (Course c) -> c.toString()
        );
    }

    public int getTutorChoice(Tutor[] tutors, String prompt, String ifEmptyArr) {
        if (tutors.length <= 0) {
            System.out.println(ifEmptyArr);
            Input.pause();
            return -1;
        }
        return Input.getChoice(
            prompt,
            tutors,
            (Tutor t) -> t.getId() + " " + t.getName()
        );
    }

    // for those want to anotate smtg above the table of choices
    public int getTutorChoice(Tutor[] tutors, String tableHeading, String prompt, String ifEmptyArr) {
        if (tutors.length <= 0) {
            System.out.println(ifEmptyArr);
            Input.pause();
            return -1;
        }
        System.out.println(tableHeading);
        return Input.getChoice(
            tableHeading,
            prompt,
            tutors,
            (Tutor t) -> t.getId() + " " + t.getName()
        );
    }

    public int getTutorialGroupChoice(TutorialGroup[] tgs, String prompt, String ifEmptyArr) {
        if (tgs.length <= 0) {
            System.out.println(ifEmptyArr);
            return -1;
        }
        return Input.getChoice(
            prompt,
            tgs,
            (TutorialGroup tg) -> tg.getProgrammeName() + " " + tg.getTutGrpCode()
        );
    }

    public int getTutorFindFilter() {
        return Input.getChoice(
            "How do you want to decide which tutor to select: ",
            new String[] {
                "I already know their ID, let me find by ID",
                "I already know their name, let me find by name",
                "I already know their IC, let me find by IC",
                "Show me all of them, let me pick"
            },
            (s) -> s
        );
    }

    public int getIDForTutorFilter() {
        return Input.getInt("Enter the desired tutor ID: ");
    }

    public void printWildCardList() {
        TableBuilder tb = new TableBuilder(
            new String[] { "Character", "Meaning", "Examples" },
            new String[][] {
                new String[] { "%", "+", "*" },
                new String[] { 
                    "One exact character, including spaces", 
                    "One or more characters, excluding spaces", 
                    "One or more characters, including spaces"
                },
                new String[] {
                    "A%C -> ABC, AcC, ADC",
                    "A+C -> ABC, ABBC, ABCC",
                    "A*C -> A C, AB C, ABBC"
                }
            }
        );
        tb.printTable(false, "List of wildcards that can be used");
    }

    // a print anything function for anything that don't rlly need to have their own function
    // warns the user then wait for their action to let the system proceed
    public void warn(String s) {
        System.out.println(s);
        Input.pause();
    }

    // allows wildcards
    public String getNameForTutorFilter() {
        printWildCardList();
        return Input.getString(
            "What is the tutor's name (can use wildcards for a wider search, case insensitive): ", 
            false
        );
    }

    public String getIcNoForTutorFilter() {
        printWildCardList();
        return Input.getString(
            "What is the tutor's IC (can use wildcards for a wider search): ",
            false
        );
    }

    public int getCourseFindFilter() {
        return Input.getChoice(
            "How do you want to decide which course to select: ",
            new String[] {
                "I already know the ID, let me find by ID",
                "I already know the name, let me find by name",
                "I already know the department, let me choose from them",
                "Show me all of them, let me pick"
            },
            (s) -> s
        );
    }

    public String getIdForCourseFilter() {
        printWildCardList();
        return Input.getString("What is the ID for the course (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getNameForCourseFilter() {
        printWildCardList();
        return Input.getString("What is the name for the course (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getDepartmentForCourseFilter() {
        return DepartmentManagement.departments[
            Input.getChoice(
                "Please select the department: ", 
                DepartmentManagement.departments, 
                (s) -> s
            )
        ];
    }

    public int getTutorialGroupFindFilter() {
        return Input.getChoice(
            "How do you want to decide which tutorial group to select: ",
            new String[] {
                "I already know the programme code, let me find by programme code",
                "I already know the programme name, let me find by programme name",
                "I already know the tutorial group code, let me find by tutorial group code",
                "Show me all of them, let me pick"
            },
            (s) -> s
        );
    }

    public String getProgCodeForTutGrpFilter() {
        printWildCardList();
        return Input.getString("What is the Programme Code for the tutorial group (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getProgNameForTutGrpFilter() {
        printWildCardList();
        return Input.getString("What is the Programme Name for the tutorial group (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getTutGrpCodeForTutGrpFilter() {
        printWildCardList();
        return Input.getString("What is the Tutorial Group Code for the tutorial group (can use wildcards for a wider search, case insensitive): ", false);
    }

    public int tableDisplayConfig(String tableHeading, String[] configs) {
        return Input.getChoice(tableHeading, "How would you like to configure the table: ", configs, (s) -> s);
    }

    /**
     * prompts user assignment successful
     * @param type "TUTOR" or "TUTORIALGROUP"
     * @return
     */
    public void assignSuccessful(String type) {
        if (type.equals("TUTOR"))
            System.out.println("Successfully assigned tutor to the course");
        else
            System.out.println("Successfully assigned tutorial group to the tutor");
    }

    /**
     * asks user whether they wanna insert more
     * @param type "TUTOR" or "TUTORIALGROUP"
     * @return
     */
    public boolean wantAssignMore(String type) {
        if (type.equals("TUTOR"))
            return Input.confirm("Do you want to assign more tutor to the course?");
        else 
            return Input.confirm("Do you want to assign more tutorial groups to the tutor?");
    }

    public boolean restartFilter() {
        return Input.confirm("Do you want to restart the filter?");
    }

    public void displayNumberOfElementsInList(String what, int number) {
        System.out.println("There are currently [" + number + "] " + what + " in the list");
    }

    public boolean continueFilter() {
        return Input.confirm("Do you want to continue filter?");
    }

    // Used only by me, I guarantee sortByChoices will NEVER be empty
    public int getSortBy(String[] sortByChoices) {
        System.out.println("NOTE: Sorted by column will always displayed as the first one and can never be removed");
        return Input.getChoice("Which column do you want to sort by: ", sortByChoices, (s) -> s);
    }

    public void buildAndPrintCourseTable(ArrayList<Course> coursesArrayList) {
        TableBuilder tb = new TableBuilder();
        boolean showNumber = false;
        while (true) {
            int choice = this.tableDisplayConfig(
                "Table configuration to display courses under a tutor",
                new String[] {
                    tb.hasColumn("Course ID") ? "Remove column 'Course ID'" : "Add column 'Course ID",
                    tb.hasColumn("Course Name") ? "Remove column 'Course Name'" : "Add column 'Course Name",
                    tb.hasColumn("Department") ? "Remove column 'Department'" : "Add column 'Department",
                    tb.hasColumn("Credit Hour") ? "Remove column 'Credit Hour'" : "Add column 'Credit Hour'",
                    showNumber ? "Disable data number" : "Show data number",
                    "Show all column",
                    "Done configuration"
                }
            );
            if (choice == 0 || choice == 5) {
                if (tb.hasColumn("Course ID"))
                        tb.removeColumn("Course ID");
                else
                    tb.addColumn("Course ID", coursesArrayList.map((Course c) -> c.getId()).toArray(String.class));
            } else if (choice == 1 || choice == 5) {
                if (tb.hasColumn("Course Name"))
                    tb.removeColumn("Course Name");
                else
                    tb.addColumn("Course Name", coursesArrayList.map((Course c) -> c.getName()).toArray(String.class));
            } else if (choice == 2 || choice == 5) {
                if (tb.hasColumn("Department"))
                    tb.removeColumn("Department");
                else
                    tb.addColumn("Department", coursesArrayList.map((Course c) -> c.getDepartment()).toArray(String.class));
            } else if (choice == 3 || choice == 5) {
                if (tb.hasColumn("Credit Hour"))
                    tb.removeColumn("Credit Hour");
                else
                    tb.addColumn("Credit Hour", coursesArrayList.map((Course c) -> c.getDepartment()).toArray(String.class));
            } else if (choice == 4 || choice == 5) {
                showNumber = !showNumber;
            } else if (choice == 6) {
                // finished config, exit loop
                break;
            }
        }
        tb.printTable(showNumber);
    }

}
