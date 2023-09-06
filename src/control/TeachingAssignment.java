package control;

import java.io.Serializable;
import java.util.regex.Pattern;

import adt.ArrayList;
import adt.HashMap;
import boundary.TeachingAssignmentUI;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;

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
        return ui.filterTutors(filtered);
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
            ArrayList<Course> filteredCourses = ui.filterCourses(cm.getCourses());
            selectedCourse = ui.selectCourse(filteredCourses);
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

            Tutor selectedTutor = ui.selectTutorForCourse(selectedCourse, filteredTutors, this.courseTutorMap);
            if (selectedTutor == null) {
                if (ui.restartFilter())
                    continue;
            }
            
            this.assignTutorToCourse(selectedCourse, selectedTutor);
            
            if (!ui.wantAssignMore("TUTOR"))
                break;
        }
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
            ArrayList<Tutor> filteredTutors = ui.filterTutors(tutorsAssignedToCourse);
            selectedTutor = ui.selectTutorForTutorialGroup(filteredTutors);

            if (selectedTutor == null) {
                if (ui.restartFilter())
                    continue;
                return;
            }

            break;
        }

        while (true) {
            ArrayList<TutorialGroup> filteredTutorialGroups = ui.filterTutorialGroups(tgm.getTutorialGroups());
            TutorialGroup tg = ui.selectTutorialGroupForTutor(selectedTutor, filteredTutorialGroups, this.tutorTutorialGroupMap);

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
            coursesOfTutor = ui.filterCourses(coursesOfTutor);
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
            ArrayList<Tutor> filteredTutors = ui.filterTutors(tutorsAssignedToAnyCourse);
    
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
        this.sortCourses(coursesArrayList);

        ui.buildAndPrintCourseTable(coursesArrayList);
    }


    // will manipulate the arraylist passed in
    private void sortCourses(ArrayList<Course> coursesArrayList) {
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

    // only handles removing the tutor from the course map
    private void removeTutorFromCourse(Course c, Tutor t) {
        ArrayList<Tutor> tutorAssignedToCourse = this.courseTutorMap.get(c);

        tutorAssignedToCourse.remove(tutorAssignedToCourse.indexOf(t));
    }

    // the whole process of removing a tutor from a course
    public void removeTutorFromCourseProcess() {
        // display all the course to pick from
        ArrayList<Course> filteredCourseList = new ArrayList<Course>(this.courseTutorMap.getKeys(Course.class));
        
        filteredCourseList = ui.filterCourses(filteredCourseList);
        Course selectedCourse = ui.selectCourse(filteredCourseList);


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
                this.listCoursesUnderTutor();
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
