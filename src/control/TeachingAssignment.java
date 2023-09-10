package control;

import java.io.Serializable;

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
                ui.warn("No course selected, exiting...");
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
                ui.warn("No tutor selected, exiting...");
                return;
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
            selectedTutor = ui.selectTutor(filteredTutors);

            if (selectedTutor == null) {
                if (ui.restartFilter())
                    continue;
                ui.warn("No tutor selected, exiting...");
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
                ui.warn("No tutorial group selected, exiting...");
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
        do {
            coursesOfTutor = ui.filterCourses(coursesOfTutor);
            ui.displayNumberOfElementsInList("courses", coursesOfTutor.getNumberOfEntries());
            // filtered out list no more thing, quit
            if (coursesOfTutor.getNumberOfEntries() < 1)
                return new Course[0]; // return empty array
        } while (ui.continueFilter());

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

        Tutor selectedTutor = null;
        do {
            // ask if user want to select from the list or if they alrd had something in mind
            // only let filter once, the loop is just for restart filter in case filter dao empty list
            ArrayList<Tutor> filteredTutors = ui.filterTutors(tutorsAssignedToAnyCourse);
            selectedTutor = ui.selectTutor(filteredTutors);

            if (selectedTutor == null && !ui.restartFilter()) {
                ui.warn("No tutor selected, exiting...");
                return;
            }
        } while (selectedTutor == null);

        Course[] coursesAfterFilter = searchCoursesUnderTutor(selectedTutor);
        while (coursesAfterFilter.length < 1) {
            ui.warn("There are no courses assigned to this tutor that match the filter");
            if (ui.restartFilter())
                coursesAfterFilter = searchCoursesUnderTutor(selectedTutor);
            else {
                ui.warn("No courses found, exiting...");
                return; // if user dw restart filter, ntg we can do, alrd no courses to display d, so just exit lo
            }
        }

        ArrayList<Course> coursesArrayList = new ArrayList<>(coursesAfterFilter); // convert to arraylist to use ma sweeeeet sweeeeet methods

        // allow to sort by smtg, as long as it implements comparable, sort by's column will be always displayed first 
        ui.sortCourses(coursesArrayList);

        ui.buildAndPrintCourseTable(coursesArrayList, selectedTutor);
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

    // handles filtering
    public Tutor[] searchTutorsUnderCourse(Course c) {
        ArrayList<Tutor> tutorsUnderCourse = this.courseTutorMap.get(c);

        do {
            tutorsUnderCourse = ui.filterTutors(tutorsUnderCourse);
            if (tutorsUnderCourse.getNumberOfEntries() < 1)
                return new Tutor[0]; // just return empty array, calling method should handle
        } while (ui.continueFilter());

        return tutorsUnderCourse.toArray(Tutor.class);
    }

    public void listTutorsUnderCourse() {
        ArrayList<Course> coursesAssigned = new ArrayList<Course>(this.courseTutorMap.getKeys(Course.class));

        Course selectedCourse = null;
        do {
            // only allow filter once, this loop is just to make sure no null course
            ArrayList<Course> filteredCoursesAssigned = ui.filterCourses(coursesAssigned);
            selectedCourse = ui.selectCourse(filteredCoursesAssigned);
            if (selectedCourse == null && !ui.restartFilter()){
                ui.warn("No course selected, exiting...");   
                return; // dou no selected course and user dw restart the filter, how ah, return nia lo
            }
        } while (selectedCourse == null);

        Tutor[] tutorsAssignedToCourse = this.searchTutorsUnderCourse(selectedCourse);
        while (tutorsAssignedToCourse.length < 1) {
            ui.warn("There are no tutors assigned to this course that matches the filter");
            if (ui.restartFilter())
                tutorsAssignedToCourse = this.searchTutorsUnderCourse(selectedCourse);
            else {
                ui.warn("No tutors found, exiting...");
                return; // exit if user refuses to restart the filter
            }
        }

        ArrayList<Tutor> tutorArrayList = new ArrayList<>(tutorsAssignedToCourse);

        ui.sortTutor(tutorArrayList);

        ui.buildAndPrintTutorTable(tutorArrayList, selectedCourse);
    }

    // handles filtering
    public TutorialGroup[] searchTutorialGroupsUnderTutor(Tutor t) {
        ArrayList<TutorialGroup> tutorialGroupUnderTutor = this.tutorTutorialGroupMap.get(t);

        do {
            tutorialGroupUnderTutor = ui.filterTutorialGroups(tutorialGroupUnderTutor);
            if (tutorialGroupUnderTutor.getNumberOfEntries() < 1)
                return new TutorialGroup[0]; // calling method should handle this
        } while(ui.continueFilter());

        return tutorialGroupUnderTutor.toArray(TutorialGroup.class);
    }

    public void listTutorialGroupUnderTutor() {
        ArrayList<Tutor> tutorsAssigned = new ArrayList<>(this.tutorTutorialGroupMap.getKeys(Tutor.class));

        Tutor selectedTutor = null;
        do {
            // only allow filter once, this is to make sure the user cannot select null tutor
            ArrayList<Tutor> filteredTutors = ui.filterTutors(tutorsAssigned);
            selectedTutor = ui.selectTutor(filteredTutors);
            if (selectedTutor == null && !ui.restartFilter()) {
                ui.warn("No tutor selected, exiting...");
                return;
            }
        } while (selectedTutor == null);

        TutorialGroup[] tutorialGroupsAssignedToTutor = this.searchTutorialGroupsUnderTutor(selectedTutor);
        while (tutorialGroupsAssignedToTutor.length < 1) {
            ui.warn("There are no tutorial groups that match this filter");
            if (ui.restartFilter())
                tutorialGroupsAssignedToTutor = this.searchTutorialGroupsUnderTutor(selectedTutor);
            else {
                ui.warn("No tutorial groups found, exiting...");
                return;
            }
        }

        ArrayList<TutorialGroup> tutorialGroupArrayList = new ArrayList<>(tutorialGroupsAssignedToTutor);

        ui.sortTutorialGroup(tutorialGroupArrayList);

        ui.buildAndPrintTutorialGroupTable(tutorialGroupArrayList, selectedTutor);
    }

    public Tutor[] searchTutorUnderTutorialGroup(TutorialGroup tg) {
        ArrayList<Tutor> tutorOfTutorialGroup = new ArrayList<>();

        for (
            HashMap<Tutor, ArrayList<TutorialGroup>>.Pair p : 
                this.tutorTutorialGroupMap
                    .filter((Tutor _t, ArrayList<TutorialGroup> tgs) -> tgs.contains(tg))
        ) {
            tutorOfTutorialGroup.insert(p.getKey());
        }

        do {
            tutorOfTutorialGroup = ui.filterTutors(tutorOfTutorialGroup);
            if (tutorOfTutorialGroup.getNumberOfEntries() < 1)
                return new Tutor[0]; // directly return, calling function should handle
        } while (ui.continueFilter());

        return tutorOfTutorialGroup.toArray(Tutor.class);
    }

    // only used internally, gets every tutorial groups that has already been assigned
    private ArrayList<TutorialGroup> getTutorialGroupsAssignedToAnyTutor() {
        ArrayList<TutorialGroup> tutorialGroupsAssignedToAnyTutor = new ArrayList<>();

        for (HashMap<Tutor, ArrayList<TutorialGroup>>.Pair p : this.tutorTutorialGroupMap) {
            for (TutorialGroup tg : p.getValue()) {
                if (!tutorialGroupsAssignedToAnyTutor.contains(tg))
                    tutorialGroupsAssignedToAnyTutor.insert(tg);
            }
        }

        return tutorialGroupsAssignedToAnyTutor;
    }

    public void listTutorUnderTutorialGroup() {
        ArrayList<TutorialGroup> tutorialGroupsAssigned = this.getTutorialGroupsAssignedToAnyTutor();

        TutorialGroup selectedTutorialGroup = null;
        do {
            ArrayList<TutorialGroup> filteredTutorialGroup = ui.filterTutorialGroups(tutorialGroupsAssigned);
            selectedTutorialGroup = ui.selectTutorialGroup(filteredTutorialGroup);

            if (selectedTutorialGroup == null && !ui.restartFilter()) {
                ui.warn("No tutorial groups selected, exiting...");
                return;
            }
        } while (selectedTutorialGroup == null);
    }

    // only handles removing the tutor from the course map
    private void removeTutorFromCourse(Course c, Tutor t) {
        ArrayList<Tutor> tutorAssignedToCourse = this.courseTutorMap.get(c);

        tutorAssignedToCourse.remove(tutorAssignedToCourse.indexOf(t));
    }

    // the whole process of removing a tutor from a course
    public void removeTutorFromCourseProcess() {
        ArrayList<Course> filteredCourseList = new ArrayList<Course>(this.courseTutorMap.getKeys(Course.class));

        // filter and select course from a list of course that has already been assigned
        Course selectedCourse = null;
        do {
            do {
                filteredCourseList = ui.filterCourses(filteredCourseList);
            } while (ui.continueFilter());
            selectedCourse = ui.selectCourse(filteredCourseList);
            if (selectedCourse == null && !ui.restartFilter())
                return; // if alrd null and user refuses to actually select smtg, can directly exit from this function
        } while (selectedCourse == null);

        // filter and select tutor from the list of tutors assigned to the selected course
        ArrayList<Tutor> filteredTutorAssignedToCourse = this.courseTutorMap.get(selectedCourse);
        Tutor selectedTutor = null;
        do {
            do {
                filteredTutorAssignedToCourse = ui.filterTutors(filteredTutorAssignedToCourse);
            } while (ui.continueFilter());
            if (selectedTutor == null && !ui.restartFilter())
                return;
        } while (selectedTutor == null);

        this.removeTutorFromCourse(selectedCourse, selectedTutor);
        ui.removeSuccessful("Tutor", "Course");
    }

    // only handles removing the tutorial group from a tutor from a hashmap
    private void removeTutorialGroupFromTutor(Tutor t, TutorialGroup tg) {
        ArrayList<TutorialGroup> tutorialGroupAssignedToTutor = this.tutorTutorialGroupMap.get(t);

        tutorialGroupAssignedToTutor.remove(tutorialGroupAssignedToTutor.indexOf(tg));
    }

    // the whole process of removing a tutorial group from a tutor
    public void removeTutorialGroupFromTutorProcess() {
        ArrayList<Tutor> filteredTutorList = new ArrayList<Tutor>(this.tutorTutorialGroupMap.getKeys(Tutor.class));

        // filter and select tutor from the list of tutors already assigned
        Tutor selectedTutor = null;
        do {
            do {
                filteredTutorList = ui.filterTutors(filteredTutorList);
            } while (ui.continueFilter());
            selectedTutor = ui.selectTutor(filteredTutorList);
            if (selectedTutor == null && !ui.restartFilter())
                return;
        } while (selectedTutor == null);

        // filter and select the tutorial group assigned to the tutor
        ArrayList<TutorialGroup> filteredTutorialGroupAssignedToTutor = this.tutorTutorialGroupMap.get(selectedTutor);

        TutorialGroup selectedTutorialGroup = null;
        do {
            do {
                filteredTutorialGroupAssignedToTutor = ui.filterTutorialGroups(filteredTutorialGroupAssignedToTutor);
            } while (ui.continueFilter());
            selectedTutorialGroup = ui.selectTutorialGroup(filteredTutorialGroupAssignedToTutor);
            if (selectedTutorialGroup == null && !ui.restartFilter())
                return;
        } while (selectedTutorialGroup == null);
        this.removeTutorialGroupFromTutor(selectedTutor, selectedTutorialGroup);
        ui.removeSuccessful("Tutorial Group", "Tutor");
    }

    // due to java's referencing properties, we need to clean up data when tutor is removed by the tutor management submodule
    // don't need to implement cleanUp for Course for now becuz the course subsystem is just a stub
    // TODO
    public void cleanUp(Tutor tutor) {

    }

    // due to java's referencing properties, we need to clean up data when tutorial group is removed
    // TODO:
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
            TODO: Both TODO below is if got time
            TODO: "List all tutorial group under a tutor",
            TODO: "List all tutor for a tutorial group",
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
                this.removeTutorFromCourseProcess();
                break;

            case 3:
                this.removeTutorialGroupFromTutorProcess(); 
                break;

            case 4:
                this.listTutorsUnderCourse();
                break;

            case 5:
                this.listCoursesUnderTutor();
                break;
            case 6: // exit
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
