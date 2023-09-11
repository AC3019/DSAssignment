package boundary;

import java.io.Serializable;
import java.util.regex.Pattern;

import adt.ArrayList;
import adt.HashMap;
import control.DepartmentManagement;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.FileStringWriter;
import utility.Input;
import utility.TableBuilder;

public class TeachingAssignmentUI implements Serializable {
    
    public int getMenuChoice() {
        int choice = Input.getChoice("Enter your choice: ", new String[] {
            "Assign tutor to a course",
            "Assign tutorial groups to tutor",
            "Remove tutor from a course",
            "Remove tutorial group from a tutor",
            "Generate report",
            "Exit the module"
        }, (s) -> s);
        return choice;
    }

    public int getReportChoice() {
        int choice = Input.getChoice("Enter your choice: ", new String[] {
            "List all tutors under a course",
            "List all courses under a tutor",
            "List all tutorial group under a tutor",
            "List all tutor for a tutorial group",
            "Back"
        }, (s) -> s);
        return choice;
    }

    public int getCourseChoice(Course[] courses, String ifEmptyArr) {
        if (courses.length <= 0) {
            System.out.println(ifEmptyArr);
            Input.pause();
            return -1;
        }
        return Input.getChoice(
            "Which course to select: ", 
            courses, 
            (Course c) -> c.toString()
        );
    }

    // prompts courses available to user and ask them to select one
    public Course selectCourse(ArrayList<Course> courses) {
        int selection = this.getCourseChoice(
            courses.toArray(Course.class), 
            "There are no courses in the system that matches the filter yet, please add one first"
        );

        if (selection < 0)
            return null;

        return courses.get(selection);
    }

        // since wildcard only me do, this not suitable to put in utils as diff ppl may have diff wildcards
    private Pattern convertWildCardToPattern(String s) {
        return Pattern.compile(
            s
                .replace("%", ".")
                .replace("+", "[^\s]+")
                .replace("*", ".+"),
            Pattern.CASE_INSENSITIVE
        );
    }

    // shud probably be done by TutorManagement module, but I had more complex logics to implement, they don't allow working with custom list of tutors
    // can filter with three criteria, 0 -> ID, 1 -> IC or 2 -> name or 3 -> show all (just return the ori arraylist)
    /** can include wildcards, except for id 
     * % for single character, 
     * + for one or more characters excluding space 
     * * for one or more characters including space 
     * (I will use regex to make them valid)
     */
    public ArrayList<Tutor> filterTutors(ArrayList<Tutor> tutors) {
        int discoverTutorChoice = this.getTutorFindFilter();

        switch (discoverTutorChoice) {
            case 0:
                int id = this.getIDForTutorFilter();
                return tutors.filter((Tutor t) -> t.getId() == id);

            case 1:
                String ic = this.getIcNoForTutorFilter();
                return tutors.filter(
                    (Tutor t) -> this.convertWildCardToPattern(ic).matcher(t.getIcNO()).find()
                );
            
            case 2:
                String name = this.getNameForTutorFilter();
                return tutors.filter(
                    (Tutor t) -> this.convertWildCardToPattern(name).matcher(t.getName()).find()
                );
            
            default:
                return tutors;
        }
    }

    // same logic as filterTutors
    /**
     * allow filter for
     * 0 -> id (allow wildcard)
     * 1 -> name (allow wildcard)
     * 2 -> department (let them choose from list)
     * 3 -> return all
     */
    public ArrayList<Course> filterCourses(ArrayList<Course> courses) {
       int discoverCourseChoice = this.getCourseFindFilter();

        switch (discoverCourseChoice) {
            case 0:
                String id = this.getIdForCourseFilter();
                return courses.filter(
                    (Course c) -> this.convertWildCardToPattern(id).matcher(c.getId()).find()
                );
            case 1:
                String name = this.getNameForCourseFilter();
                return courses.filter(
                    (Course c) -> this.convertWildCardToPattern(name).matcher(c.getName()).find()
                );
            
            case 2:
                String department = this.getDepartmentForCourseFilter();
                return courses.filter(
                    (Course c) -> c.getDepartment().equals(department)
                );
            
            default:
                return courses;
        } 
    }

    // same logic as filterTutors
    /**
     * allow filter for
     * 0 -> programme code (allow wildcard)
     * 1 -> programme name (allow wildcard)
     * 2 -> tutorial group code (allow wildcard)
     * 3 -> return all
     */
    public ArrayList<TutorialGroup> filterTutorialGroups(ArrayList<TutorialGroup> tutGrps) {
        int discoverTutorialGroupChoice = this.getTutorialGroupFindFilter();

        switch (discoverTutorialGroupChoice) {
            case 0:
                String progCode = this.getProgCodeForTutGrpFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(progCode).matcher(t.getProgrammeCode()).find()
                );
            case 1:
                String progName = this.getNameForCourseFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(progName).matcher(t.getProgrammeName()).find()
                );
            
            case 2:
                String tutGrpCode = this.getTutGrpCodeForTutGrpFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(tutGrpCode).matcher(t.getTutGrpCode()).find()
                );
            
            default:
                return tutGrps;
        } 
    }

    public Tutor selectTutorForCourse(Course c, ArrayList<Tutor> tutors, HashMap<Course, ArrayList<Tutor>> courseTutorMap) {
        int selection = this.getTutorChoice(
            tutors.filter(
                (Tutor t) -> {
                    if (courseTutorMap.get(c) == null)
                        return true;
                    return !courseTutorMap.get(c).contains(t);
                }
            ).toArray(Tutor.class),
            "Tutors (Department: " + c.getDepartment() + ")",
            "Which tutor to assign for this course[" 
                + c.getId() + " " 
                + c.getName() +  " " 
                + c.getDepartment() + "]: ",
            "There are no tutors in the system that matches the filters yet, please add one first"
        );

        if (selection < 0)
            return null;

        return tutors.get(selection);
    }

    public Tutor selectTutor(ArrayList<Tutor> ts) {
        int selection = this.getTutorChoice(
            ts.toArray(Tutor.class), 
            "Which tutor to select: ", 
            "There is currently no any tutors in the system that matches the filter, please add one first"
        );

        if (selection < 0)
            return null;
        return ts.get(selection);
    }

    public TutorialGroup selectTutorialGroupForTutor(Tutor t, ArrayList<TutorialGroup> tgs, HashMap<Tutor, ArrayList<TutorialGroup>> tutorTutorialGroupMap) {
        int selection = this.getTutorialGroupChoice(
            // filter out tutorial groups that are already assigned to this tutor
            tgs.filter((TutorialGroup tg) -> {
                ArrayList<TutorialGroup> hmTg = tutorTutorialGroupMap.get(t);
                if (hmTg == null) 
                    return false;
                return !hmTg.contains(tg);
            }).toArray(TutorialGroup.class),
            "Which tutorial group to assign to this tutor[" + t.getId() + " " + t.getName() + "]: ",
            "There is no tutorial groups in the system that matches the filter yet, please add one first"
        );

        if (selection < 0) 
            return null;

        return tgs.get(selection);
    }

    public TutorialGroup selectTutorialGroup(ArrayList<TutorialGroup> tgs) {
        int selection = this.getTutorialGroupChoice(
            tgs.toArray(TutorialGroup.class), 
            "Which tutorial group to select: ", 
            "There is currently no any tutorial group in the system that matches the filter, please add one first"
        );

        if (selection < 0)
            return null;
        return tgs.get(selection);
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
            (Tutor t) -> t.getName()
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
            "How do you want to decide which tutor to see: ",
            new String[] {
                "Filter by ID",
                "Filter by IC",
                "Filter by name",
                "Show me all of them"
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
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        Input.pause();
    }

    // a print anything function for anything that don't need to have their own function
    // unlike warn, this is for stuffs that don't require attention, just a casual log to tell user that something has happened
    public void log(String s) {
        System.out.println(s);
    }

    // allows wildcards
    public String getNameForTutorFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString(
            "What is the tutor's name (can use wildcards for a wider search, case insensitive): ", 
            false
        );
    }

    public String getIcNoForTutorFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString(
            "What is the tutor's IC (can use wildcards for a wider search): ",
            false
        );
    }

    public int getCourseFindFilter() {
        return Input.getChoice(
            "How do you want to decide which course to see: ",
            new String[] {
                "Filter by ID",
                "Filter by name",
                "Filter by department",
                "Show me all of them"
            },
            (s) -> s
        );
    }

    public String getIdForCourseFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString("Enter ID filter (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getNameForCourseFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString("Enter name filter (can use wildcards for a wider search, case insensitive): ", false);
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
            "How do you want to decide which tutorial group to see: ",
            new String[] {
                "Filter by programme code",
                "Filter by programme name",
                "Filter by tutorial group code",
                "Show me all of them"
            },
            (s) -> s
        );
    }

    public String getProgCodeForTutGrpFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString("Enter Programme Code filter (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getProgNameForTutGrpFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString("Enter Programme Name filter (can use wildcards for a wider search, case insensitive): ", false);
    }

    public String getTutGrpCodeForTutGrpFilter() {
        printWildCardList();
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.getString("Enter Tutorial Group Code filter (can use wildcards for a wider search, case insensitive): ", false);
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
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        if (type.equals("TUTOR"))
            return Input.confirm("Do you want to assign more tutor to the course?");
        else 
            return Input.confirm("Do you want to assign more tutorial groups to the tutor?");
    }

    public boolean restartFilter() {
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.confirm("Do you want to restart the filter?");
    }

    public void displayNumberOfElementsInList(String what, int number) {
        System.out.println("There are currently [" + number + "] " + what + " in the list");
    }

    public boolean continueFilter() {
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        return Input.confirm("Do you want to continue filter?");
    }

    // Used only by me, I guarantee sortByChoices will NEVER be empty
    public int getSortBy(String[] sortByChoices) {
        return Input.getChoice("Which column do you want to sort by: ", sortByChoices, (s) -> s);
    }

    // will manipulate the arraylist passed in
    public void sortCourses(ArrayList<Course> coursesArrayList) {
        int sortByChoice = this.getSortBy(new String[] {
            "Course ID (ASC)",
            "Course ID (DESC)",
            "Course Name (ASC)",
            "Course Name (DESC)",
            "Department (ASC)",
            "Department (DESC)",
            "Credit Hour (ASC)",
            "Credit Hour (DESC)",
            "Don't sort by anything"
        });

        coursesArrayList.sort((Course c1, Course c2) -> {
            switch (sortByChoice) {
                case 0:
                    return c2.getId().compareTo(c1.getId());
                case 1:
                    return c1.getId().compareTo(c2.getId());
                case 2:
                    return c2.getName().compareTo(c1.getName());
                case 3:
                    return c1.getName().compareTo(c2.getName());
                case 4:
                    return c2.getDepartment().compareTo(c1.getDepartment());
                case 5:
                    return c1.getDepartment().compareTo(c2.getDepartment());
                case 6:
                    return c2.getCreditHour() - c1.getCreditHour();
                case 7:
                    return c1.getCreditHour() - c2.getCreditHour();
            }
            return 1; // always return positive to keep same position
        });
    }

    // manipulates the list passed in
    public void sortTutor(ArrayList<Tutor> tutors) {
        int sortByChoice = this.getSortBy(new String[] {
            "Tutor ID (ASC)",
            "Tutor ID (DESC)",
            "Tutor Name (ASC)",
            "Tutor Name (DESC)",
            "Department (ASC)",
            "Department (DESC)",
            "Don't sort by anything"
        });

        switch (sortByChoice) {
            case 0:
                tutors.sort((Tutor t1, Tutor t2) -> t2.getId() - t1.getId());
                break;
            
            case 1:
                tutors.sort((Tutor t1, Tutor t2) -> t1.getId() - t2.getId());
                break;
            
            case 2:
                tutors.sort((Tutor t1, Tutor t2) -> t2.getName().compareTo(t1.getName()));
                break;

            case 3:
                tutors.sort((Tutor t1, Tutor t2) -> t1.getName().compareTo(t2.getName()));
                break;
            
            case 4:
                tutors.sort((Tutor t1, Tutor t2) -> t2.getDepartment().compareTo(t1.getDepartment()));
                break;

            case 5:
                tutors.sort((Tutor t1, Tutor t2) -> t1.getDepartment().compareTo(t2.getDepartment()));
                break;
        
            default:
                break;
        }
    }

    // manipulates the list passed in
    public void sortTutorialGroup(ArrayList<TutorialGroup> tutorialGroups) {
        int sortByChoice = this.getSortBy(new String[] {
            "Tutorial Group Code (ASC)",
            "Tutorial Group Code (DESC)",
            "Number of Students (ASC)",
            "Number of Students (DESC)",
            "Don't sort by anything"
        });

        switch (sortByChoice) {
            case 0:
                tutorialGroups.sort((TutorialGroup tg1, TutorialGroup tg2) -> tg2.getTutGrpCode().compareTo(tg1.getTutGrpCode()));
                break;

            case 1:
                tutorialGroups.sort((TutorialGroup tg1, TutorialGroup tg2) -> tg1.getTutGrpCode().compareTo(tg2.getTutGrpCode()));
                break;
            
            case 2:
                tutorialGroups.sort(
                    (TutorialGroup tg1, TutorialGroup tg2) -> tg2.getStudent().getNumberOfEntries() - tg1.getStudent().getNumberOfEntries()
                );
                break;

            case 3:
                tutorialGroups.sort(
                    (TutorialGroup tg1, TutorialGroup tg2) -> tg1.getStudent().getNumberOfEntries() - tg2.getStudent().getNumberOfEntries()
                );
                break;

            default:
                break;
        }
    }

    // won't use tiok this for tutorial group (only for Tutor cuz Tutor and Course related only)
    public void buildAndPrintCourseTable(ArrayList<Course> coursesArrayList, Tutor t) {
        TableBuilder tb = new TableBuilder();
        boolean showNumber = false;
        while (true) {
            int choice = this.tableDisplayConfig(
                "Table configuration to display courses",
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
                // choice is 5 means we want show, don't remove
                if (tb.hasColumn("Course ID") && choice != 5)
                        tb.removeColumn("Course ID");
                else
                    tb.addColumn("Course ID", coursesArrayList.map((Course c) -> c.getId()).toArray(String.class));
            } 
            
            if (choice == 1 || choice == 5) {
                if (tb.hasColumn("Course Name") && choice != 5)
                    tb.removeColumn("Course Name");
                else
                    tb.addColumn("Course Name", coursesArrayList.map((Course c) -> c.getName()).toArray(String.class));
            } 
            
            if (choice == 2 || choice == 5) {
                if (tb.hasColumn("Department") && choice != 5)
                    tb.removeColumn("Department");
                else
                    tb.addColumn("Department", coursesArrayList.map((Course c) -> c.getDepartment()).toArray(String.class));
            } 
            
            if (choice == 3 || choice == 5) {
                if (tb.hasColumn("Credit Hour") && choice != 5)
                    tb.removeColumn("Credit Hour");
                else
                    tb.addColumn("Credit Hour", coursesArrayList.map((Course c) -> c.getDepartment()).toArray(String.class));
            } 
            
            if (choice == 4 || choice == 5) {
                showNumber = !showNumber || choice == 5; // choice == 5 means always show number column
            } 
            
            if (choice == 6) {
                // finished config, exit loop
                break;
            }
        }
        String tableHeading = "Courses Assigned to Tutor [" + 
                t.getId() + " " + t.getName() + 
                "(" + t.getDepartment() + ")" + 
            "]";
        tb.printTable(showNumber, tableHeading);

        Input.cleanBuffer();
        boolean wantToSaveToFile = Input.confirm("Do you want to export data to a file?");
        if (wantToSaveToFile) {
            this.saveToFile(tb, showNumber, tableHeading);
        }
    }

    /**
     * Allow user to build and print the tutor table
     * @param tutorArrayList
     * @param obj can only be instanceof Course or TutorialGroup, other instances will be ignored
     */
    public void buildAndPrintTutorTable(ArrayList<Tutor> tutorArrayList, Object obj) {
        TableBuilder tb = new TableBuilder();
        boolean showNumber = false;
        
        while (true) {
            int choice = this.tableDisplayConfig(
                "Table configuration to display tutors",
                new String[] {
                    tb.hasColumn("Tutor ID") ? "Remove column 'Tutor ID'" : "Add column 'Tutor ID",
                    tb.hasColumn("Tutor Name") ? "Remove column 'Tutor Name'" : "Add column 'Tutor Name",
                    tb.hasColumn("IC No.") ? "Remove column 'IC. No'" : "Add column 'IC. No'",
                    tb.hasColumn("Age") ? "Remove column 'Age'" : "Add column 'Age'",
                    tb.hasColumn("Phone No.") ? "Remove column 'Phone No.'" : "Add column 'Phone No.'",
                    tb.hasColumn("Department") ? "Remove column 'Department'" : "Add column 'Department",
                    showNumber ? "Disable data number" : "Show data number",
                    "Show all column",
                    "Done configuration"
                }
            );

            if (choice == 0 || choice == 7) {
                // choice is 5 means we want show, don't remove
                if (tb.hasColumn("Tutor ID") && choice != 7)
                        tb.removeColumn("Tutor ID");
                else
                    tb.addColumn("Tutor ID", tutorArrayList.map((Tutor t) -> (Integer)t.getId()).toArray(Integer.class));
            } 
            
            if (choice == 1 || choice == 7) {
                if (tb.hasColumn("Tutor Name") && choice != 7)
                    tb.removeColumn("Tutor Name");
                else
                    tb.addColumn("Tutor Name", tutorArrayList.map((Tutor t) -> t.getName()).toArray(String.class));
            } 
            
            if (choice == 2 || choice == 7) {
                if (tb.hasColumn("IC No.") && choice != 7)
                    tb.removeColumn("IC. No.");
                else
                    tb.addColumn("IC No.", tutorArrayList.map((Tutor t) -> t.getIcNO()).toArray(String.class));
            } 
            
            if (choice == 3 || choice == 7) {
                if (tb.hasColumn("Age") && choice != 7)
                    tb.removeColumn("Age");
                else
                    tb.addColumn("Age", tutorArrayList.map((Tutor t) -> (Integer) t.getAge()).toArray(Integer.class));
            } 

            if (choice == 4 || choice == 7) {
                if (tb.hasColumn("Phone No.") && choice != 7)
                    tb.removeColumn("Phone No.");
                else
                    tb.addColumn("Phone No.", tutorArrayList.map((Tutor t) -> t.getPhoneNum()).toArray(String.class));
            } 

            if (choice == 5 || choice == 7) {
                if (tb.hasColumn("Department") && choice != 7)
                    tb.removeColumn("Department");
                else
                    tb.addColumn("Department", tutorArrayList.map((Tutor t) -> t.getDepartment()).toArray(String.class));
            } 

            if (choice == 6 || choice == 7) {
                showNumber = !showNumber || choice == 7;
            }

            if (choice == 8)
                break; // done config
        }
        String tableHeading = "";
        if (obj instanceof Course) {
            Course c = (Course) obj;
            tableHeading = "Tutors Assigned to the Course [" + 
                c.getId() + " " + c.getName() + 
                "(" + c.getDepartment() +")" + 
            "]";
        } else if (obj instanceof TutorialGroup) {
            TutorialGroup tg = (TutorialGroup) obj;
            tableHeading = "Tutors Assigned to the Tutorial Group [" +
                tg.getProgrammeCode() + tg.getTutGrpCode() + " " + tg.getProgrammeName() +
            "]";
        }
        tb.printTable(showNumber, tableHeading);

        Input.cleanBuffer();
        boolean wantToSaveToFile = Input.confirm("Do you want to export data to a file?");
        if (wantToSaveToFile) {
            this.saveToFile(tb, showNumber, tableHeading);
        }
    }

    // won't use tiok this for course
    public void buildAndPrintTutorialGroupTable(ArrayList<TutorialGroup> tutorialGroupArrayList, Tutor t) {
        TableBuilder tb = new TableBuilder();
        boolean showNumber = false;

        while (true) {
            int choice = this.tableDisplayConfig(
                "Table configuration to display tutorial groups",
                new String[] {
                    tb.hasColumn("Programme Code") ? "Remove column 'Programme Code'" : "Add column 'Programme Code'",
                    tb.hasColumn("Tutorial Group Code") ? "Remove column 'Tutorial Group Code'" : "Add column 'Tutorial Group Code'",
                    tb.hasColumn("Programme Name") ? "Remove column 'Programme Name'" : "Add column 'Programme Name'",
                    tb.hasColumn("Number of Students") ? "Remove column 'Number of Students'" : "Add column 'Number of Students'",
                    showNumber ? "Disable data numbers" : "Show data numbers",
                    "Show all columns",
                    "Done configuration"
                }
            );
            
            if (choice == 0 || choice == 5) {
                if (tb.hasColumn("Programme Code") && choice != 5)
                    tb.removeColumn("Programme Code");
                else
                    tb.addColumn("Programme Code", tutorialGroupArrayList.map((TutorialGroup tg) -> tg.getProgrammeCode()).toArray(String.class));
            }

            if (choice == 1 || choice == 5) {
                if (tb.hasColumn("Tutorial Group Code") && choice != 5)
                    tb.removeColumn("Tutorial Group Code");
                else
                    tb.addColumn("Tutorial Group Code", tutorialGroupArrayList.map((TutorialGroup tg) -> tg.getTutGrpCode()).toArray(String.class));
            }

            if (choice == 2 || choice == 5) {
                if (tb.hasColumn("Programme Name") && choice != 5)
                    tb.removeColumn("Programme Name");
                else
                    tb.addColumn("Programme Name", tutorialGroupArrayList.map((TutorialGroup tg) -> tg.getProgrammeName()).toArray(String.class));
            }

            if (choice == 3 || choice == 5) {
                if (tb.hasColumn("Number of Students") && choice != 5)
                    tb.removeColumn("Number of Students");
                else
                    tb.addColumn("Number of Students", tutorialGroupArrayList.map((TutorialGroup tg) -> (Integer) tg.getStudent().getNumberOfEntries()).toArray(Integer.class));
            }

            if (choice == 4 || choice == 5) {
                showNumber = !showNumber || choice == 5;
            }

            if (choice == 6)
                break;
        }
        String tableHeading = "Tutorial Groups Assigned to Tutor [" + 
                t.getId() + " " + t.getName() + 
                "(" + t.getDepartment() + ")" + 
            "]";
        tb.printTable(showNumber, tableHeading);

        Input.cleanBuffer();
        boolean wantToSaveToFile = Input.confirm("Do you want to export data to a file?");
        if (wantToSaveToFile) {
            this.saveToFile(tb, showNumber, tableHeading);
        }
    }

    // ui to save generated stuff to a file, used only internally
    // reportFor is what is being generated
    private void saveToFile(TableBuilder tb, boolean showNumber, String possibleReportTitle) {
        // ask want save csv or report
        int choice = Input.getChoice("Enter your choice: ", new String[] {
            "Generate CSV file (includes field names as header row)",
            "Save report to txt file"
        }, (s) -> s);

        Input.cleanBuffer(); // use cleanBuffer here because is very certain this is getLine after getInt, so cleanBuffer will be more performant and don't need to be very fool proof and explicit
        String fileName = Input.getString("Enter filename (" + (choice == 0 ? ".csv" : ".txt") + " will be appended to end of file name): ", false);

        String dataToWrite = choice == 0 ? tb.generateCSVString(showNumber, true) : tb.generateTableString(showNumber, possibleReportTitle);

        boolean success = false;
        if (choice == 0) {
            success = FileStringWriter.writeToCSV(fileName, dataToWrite);
        } else {
            success = FileStringWriter.writeReportToTxt(fileName, dataToWrite);
        }

        if (success) {
            System.out.println(
                "Successfully written to file, the file is located at " + 
                (choice == 0 ? "export/" : "generated_reports/") + 
                fileName + (choice == 0 ? ".csv" : ".txt")
            );
            Input.pause();
        } else {
            this.warn("Failed to write to file, please try again later");
        }
    }

    public void removeSuccessful(String what, String assignedTo) {
        System.out.println("Successfully removed [" + what + "] assigned to [" + assignedTo + "]");
        Input.reinit(); // although not very performant but this is a fool proof way to make sure there is no buffer problems
        Input.pause();
    }

}
