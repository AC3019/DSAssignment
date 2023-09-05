package control;

import java.io.Serializable;
import java.util.regex.Pattern;

import adt.ArrayList;
import adt.HashMap;
import boundary.TeachingAssignmentUI;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.TableBuilder;

/**
 * @author hanyue1014
 */
public class TeachingAssignment implements Serializable {

    private TeachingAssignmentUI ui = new TeachingAssignmentUI();

    private HashMap<Course, ArrayList<Tutor>> courseTutorMap;
    private HashMap<Tutor, ArrayList<TutorialGroup>> tutorTutorialGroupMap;

    public TeachingAssignment() {
        this.courseTutorMap = new HashMap<>();
        this.tutorTutorialGroupMap = new HashMap<>();
    }

    // since wildcard only me do, this not suitable to put in utils as diff ppl may have diff wildcards
    private Pattern convertWildCardToPattern(String s) {
        return Pattern.compile(
            s
                .replace("%", ".")
                .replace("+", "[^\s]+")
                .replace("*", ".+")
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
    private ArrayList<Tutor> filterTutors(ArrayList<Tutor> tutors) {
        int discoverTutorChoice = ui.getTutorFindFilter();

        switch (discoverTutorChoice) {
            case 0:
                int id = ui.getIDForTutorFilter();
                return tutors.filter((Tutor t) -> t.getId() == id);
            case 1:
                String ic = ui.getIcNoForTutorFilter();
                return tutors.filter(
                    (Tutor t) -> this.convertWildCardToPattern(ic).matcher(t.getIcNO()).matches()
                );
            
            case 2:
                String name = ui.getNameForTutorFilter();
                return tutors.filter(
                    (Tutor t) -> this.convertWildCardToPattern(name).matcher(t.getName()).matches()
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
    private ArrayList<Course> filterCourses(ArrayList<Course> courses) {
       int discoverCourseChoice = ui.getCourseFindFilter();

        switch (discoverCourseChoice) {
            case 0:
                String id = ui.getIdForCourseFilter();
                return courses.filter(
                    (Course c) -> this.convertWildCardToPattern(id).matcher(c.getId()).matches()
                );
            case 1:
                String name = ui.getNameForCourseFilter();
                return courses.filter(
                    (Course c) -> this.convertWildCardToPattern(name).matcher(c.getName()).matches()
                );
            
            case 2:
                String department = ui.getDepartmentForCourseFilter();
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
    private ArrayList<TutorialGroup> filterTutorialGroups(ArrayList<TutorialGroup> tutGrps) {
        int discoverTutorialGroupChoice = ui.getTutorialGroupFindFilter();

        switch (discoverTutorialGroupChoice) {
            case 0:
                String progCode = ui.getProgCodeForTutGrpFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(progCode).matcher(t.getProgrammeCode()).matches()
                );
            case 1:
                String progName = ui.getNameForCourseFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(progName).matcher(t.getProgrammeName()).matches()
                );
            
            case 2:
                String tutGrpCode = ui.getTutGrpCodeForTutGrpFilter();
                return tutGrps.filter(
                    (TutorialGroup t) -> this.convertWildCardToPattern(tutGrpCode).matcher(t.getTutGrpCode()).matches()
                );
            
            default:
                return tutGrps;
        } 
    }

    // prompts courses available to user and ask them to select one
    private Course selectCourse(ArrayList<Course> courses) {
        int selection = ui.getCourseChoice(
            courses.toArray(Course.class), 
            "There are no courses in the system that matches the filter yet, please add one first"
        );

        if (selection < 0)
            return null;

        return courses.get(selection);
    }

    private ArrayList<Tutor> filterTutorSuitableForCourse(Course c, ArrayList<Tutor> tutors) {
        // filter out the tutors that has already been assigned to the course
        ArrayList<Tutor> filtered = tutors.filter(
            (Tutor t) -> {
                ArrayList<Tutor> cts = this.courseTutorMap.get(c);
                if (cts == null)
                    return false;
                return !cts.contains(t) && t.getDepartment().equals(c.getDepartment());
            }
        );
        return this.filterTutors(filtered);
    }

    private Tutor selectTutorForCourse(Course c, ArrayList<Tutor> tutors) {
        int selection = ui.getTutorChoice(
            tutors.filter(
                // TODO: this requires tweaking Tutor class's `.equals()` method
                (Tutor t) -> !this.courseTutorMap.get(c).contains(t)
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

    private void assignTutorToCourse(Course c, Tutor t) {
        // possible the course has already been assigned to a tutor before, if already got the tutor, append the other tutor to it
        if (!this.courseTutorMap.containsKey(c)) {
            ArrayList<Tutor> ts = new ArrayList<>() {{ insert(t); }};
            this.courseTutorMap.put(c, ts);
        } else {
            this.courseTutorMap.get(c).insert(t);
        }
    }

    // one method only has one responsibility, for this one, assign tutor, 
    /**
     * although it requires 
     *   - selecting course
     *   - finding tutor (filter tutor for that subject)
     *   - selecting that tutor 
     *   - ask if want to assign more tutor
     * those three are subbed to another two methods above
     */
    public void assignTutorsToCourse(CourseManagement cm, TutorManagement tm) {
        // in reality this won't happen since course management is just a stub
        if (cm.getCourses().isEmpty()) {
            ui.warn("Courses is empty, please add some course first");
            return;
        }
        Course selectedCourse;
        while (true) {
            ArrayList<Course> filteredCourses = this.filterCourses(cm.getCourses());
            selectedCourse = this.selectCourse(filteredCourses);
            if (selectedCourse == null) {
                if (ui.restartFilter())
                    continue;
                return;
            }
            break;
        }
        while (true) {
            ArrayList<Tutor> filteredTutors = this.filterTutorSuitableForCourse(
                selectedCourse, 
                new ArrayList<Tutor>(tm.getTutors())
            );

            Tutor selectedTutor = this.selectTutorForCourse(selectedCourse, filteredTutors);
            if (selectedTutor == null) {
                if (ui.restartFilter())
                    continue;
            }
            
            this.assignTutorToCourse(selectedCourse, selectedTutor);
            
            if (!ui.wantAssignMore("TUTOR"))
                break;
        }
    }

    public Tutor selectTutorForTutorialGroup(ArrayList<Tutor> ts) {
        int selection = ui.getTutorChoice(
            ts.toArray(Tutor.class), 
            "Which tutor to assign tutorial groups to: ", 
            "There is currently no any tutors in the system that matches the filter, please add one first"
        );

        if (selection < 0)
            return null;
        return ts.get(selection);
    }

    public TutorialGroup selectTutorialGroupForTutor(Tutor t, ArrayList<TutorialGroup> tgs) {
        int selection = ui.getTutorialGroupChoice(
            // filter out tutorial groups that are already assigned to this tutor
            tgs.filter((TutorialGroup tg) -> {
                ArrayList<TutorialGroup> hmTg = this.tutorTutorialGroupMap.get(t);
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

    private void assignTutorialGroupToTutor(Tutor t, TutorialGroup tg) {
        if (!this.tutorTutorialGroupMap.containsKey(t)) {
            this.tutorTutorialGroupMap.put(t, new ArrayList<TutorialGroup>() {{ insert(tg); }});
        } else {
            this.tutorTutorialGroupMap.get(t).insert(tg);
        }
    }

    /**
     * although it requires 
     *   - selecting tutor
     *   - selecting tutorial group for a list of groups
     *   - ask if want to assign more tutorial groups
     * those three are subbed to another two methods above
     */
    public void assignTutorialGroupsToTutor(TutorManagement tm, TutorialGroupManagement tgm) {
        // only can assign to the tutors who has already assigned to a course
        // get all tutors that are assigned to a course
        ArrayList<Tutor> tutorsAssignedToCourse = new ArrayList<>() {{
            for (HashMap<Course, ArrayList<Tutor>>.Pair p : courseTutorMap) {
                for (Tutor t : p.getValue())
                    insert(t);
            }
        }};

        if (tutorsAssignedToCourse.isEmpty()) {
            ui.warn("There are no tutors assigned to any course yet, please assign one first");
            return;
        }

        Tutor selectedTutor;
        while (true) {
            ArrayList<Tutor> filteredTutors = filterTutors(tutorsAssignedToCourse);
            selectedTutor = this.selectTutorForTutorialGroup(filteredTutors);

            if (selectedTutor == null) {
                if (ui.restartFilter())
                    continue;
                return;
            }

            break;
        }

        while (true) {
            ArrayList<TutorialGroup> filteredTutorialGroups = this.filterTutorialGroups(tgm.getTutorialGroups());
            TutorialGroup tg = this.selectTutorialGroupForTutor(selectedTutor, filteredTutorialGroups);

            if (tg == null) {
                if (ui.restartFilter())
                    continue;
                return;
            }

            this.assignTutorialGroupToTutor(selectedTutor, tg);

            if (!ui.wantAssignMore("TUTORIALGROUP"))
                break;
        }
    }

    // how is this any different from list courses under a tutor if they both involve filtering wtf
    // the best I can think of is this will become the supporting method of listAllCoursesUnderTutor
    public Course[] searchCoursesUnderTutor(Tutor t) {
        ArrayList<Course> coursesOfTutor = new ArrayList<>();

        for (
            HashMap<Course, ArrayList<Tutor>>.Pair p : 
                this.courseTutorMap
                    .filter((Course _c, ArrayList<Tutor> ts) -> ts.contains(t))
        ) {
            coursesOfTutor.insert(p.getKey());
        }
        
        // allow for repeated filter
        while (true) {
            coursesOfTutor = this.filterCourses(coursesOfTutor);
            ui.displayNumberOfElementsInList("courses", coursesOfTutor.getNumberOfEntries());
            if (coursesOfTutor.getNumberOfEntries() < 1 && ui.restartFilter())
                continue;
            if (!ui.continueFilter())
                break;
        }

        return coursesOfTutor.toArray(Course.class);
    }

    /**
     * Ask if user want see whole list of tutor to pick from or alrd has someone in mind
     * Only allow user to select from the tutors that are already assigned to a course, so need populate the list first (to remove duplicates, this is a good use case for a set, but not good enough to be able to justify creating a set adt since i only need it to store and loop through it, and it is only used for very specific reasons like this)
     * @param tm
     */
    public void listCoursesUnderTutor() {
        // get all tutors assigned to a course and populate them
        ArrayList<Tutor> tutorsAssignedToAnyCourse = getTutorsAssignedToAnyCourse();

        Tutor selectedTutor;
        while (true) {
            // ask if user want to select from the list or if they alrd had something in mind
            ArrayList<Tutor> filteredTutors = filterTutors(tutorsAssignedToAnyCourse);
    
            int tutorChoice = ui.getTutorChoice(
                filteredTutors.toArray(Tutor.class), 
                "Which tutor to select: ", 
                "There is no tutors assigned to any course that matches the filter yet, please add/assign one"
            );
            if (tutorChoice < 0) {
                if (ui.restartFilter())
                    continue;
                return;
            }
            selectedTutor = filteredTutors.get(tutorChoice);
            break;
        }
        Course[] coursesAfterFilter = searchCoursesUnderTutor(selectedTutor);
        while (coursesAfterFilter.length < 1) {
            ui.warn("There are no courses that match the filter");
            if (ui.restartFilter())
                coursesAfterFilter = searchCoursesUnderTutor(selectedTutor);
            return; // if user dw restart filter, ntg we can do, alrd no courses to display d, so just exit lo
        }
        ArrayList<Course> coursesArrayList = new ArrayList<>(coursesAfterFilter); // convert to arraylist to use ma sweeeeet sweeeeet methods

        // allow to sort by smtg, as long as it implements comparable, sort by's column will be always displayed first 
        int sortByChoice = ui.getSortBy(new String[] {
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

        TableBuilder tb = new TableBuilder();
        boolean showNumber = false;
        while (true) {
            int choice = ui.tableDisplayConfig(
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

    private ArrayList<Tutor> getTutorsAssignedToAnyCourse() {
        ArrayList<Tutor> tutorsAssignedToAnyCourse = new ArrayList<>();

        for (HashMap<Course, ArrayList<Tutor>>.Pair p : this.courseTutorMap) {
            for (Tutor t : p.getValue()) {
                if (!tutorsAssignedToAnyCourse.contains(t))
                    tutorsAssignedToAnyCourse.insert(t);
            }
        }
        return tutorsAssignedToAnyCourse;
    }

    public Tutor[] searchTutorsUnderCourse(Course c) {
        ArrayList<Tutor> tutorsUnderCourse = this.courseTutorMap.get(c);

        return tutorsUnderCourse.toArray(Tutor.class);
    }

    // due to java's referencing properties, we need to clean up data when tutor is removed by the tutor management submodule
    // don't need to implement cleanUp for Course for now becuz the course subsystem is just a stub
    public void cleanUp(Tutor tutor) {

    }

    // due to java's referencing properties, we need to clean up data when tutorial group is removed
    public void cleanUp(TutorialGroup tutGrp) {

    }

    // main function to this module, handles everything
    // need to take in controls of other 2 subsystem as need to integrate with them
    public void main(TutorManagement tm, TutorialGroupManagement tgm, CourseManagement cm) {
        /*
         * "Assign tutor to a course",
            "Assign tutorial groups to tutor",
            "Remove tutor from a course",
            "Remove tutorial group from a tutor",
            "List all tutors under a course",
            "List all courses under a tutor",
            "Generate report",
            "Exit teaching assignment"
         */
        int option = ui.getMenuChoice();
        switch (option) {
            case 0:
                this.assignTutorsToCourse(cm, tm);
                break;
        
            case 1:
                this.assignTutorialGroupsToTutor(tm, tgm);
                break;
            
            case 2:
                break;
            case 3: 
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;

            // won't reach
            default:
                break;
        }
    }

    public static void main(String[] args) {
        TeachingAssignment ta = new TeachingAssignment();
        // temp
        ta.main(new TutorManagement(), new TutorialGroupManagement(), new CourseManagement());
    }
}
