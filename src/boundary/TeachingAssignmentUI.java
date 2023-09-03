package boundary;

import java.io.Serializable;

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

    public int getTutorialGroupChoice(TutorialGroup[] tgs, String prompt) {
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
        System.out.println("List of wildcards that can be used");
        tb.printTable(false);
    }

    // a print anything function for anything that don't rlly need to have their own function
    public void warn(String s) {
        System.out.println(s);
    }

    // allows wildcards
    public String getNameForTutorFilter() {
        printWildCardList();
        return Input.getString(
            "What is the tutor's name (can use wildcards for a wider search, case sensitive): ", 
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
        return Input.getString("What is the ID for the course (can use wildcards for a wider search): ", false);
    }

    public String getNameForCourseFilter() {
        printWildCardList();
        return Input.getString("What is the name for the course (can use wildcards for a wider search): ", false);
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

    public void assignSuccessful(String type) {
        if (type.equals("TUTOR"))
            System.out.println("Successfully assigned tutor to the course");
        else
            System.out.println("Successfully assigned tutorial group to the tutor");
    }

    /**
     * asks user whether they wanna insert more tutor
     * @param type "TUTOR" or "TUTORIALGROUP"
     * @return
     */
    public boolean wantAssignMore(String type) {
        if (type.equals("TUTOR"))
            return Input.confirm("Do you want to assign more tutor to the course?");
        else 
            return Input.confirm("Do you want to assign more tutorial groups to the tutor?");
    }

}
