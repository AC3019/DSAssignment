package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import adt.ArrayList;
import adt.HashMap;
import boundary.TeachingAssignmentUI;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.Input;

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

    // prompts courses available to user and ask them to select one
    private Course selectCourse(ArrayList<Course> courses) {
        int selection = ui.getCourseChoice(courses.toArray(Course.class));

        return courses.get(selection);
    }

    private ArrayList<Tutor> filterTutorSuitableForCourse(Course c, ArrayList<Tutor> tutors) {
        // filter out the tutors that has already been assigned to the course
        return tutors.filter(
            // TODO: might wanna change subject of tutor to become department
            (Tutor t) -> !this.courseTutorMap.get(c).contains(t) && t.getDepartment().equals(c.getDepartment())
        );
    }

    private Tutor selectTutor(Course c, ArrayList<Tutor> tutors) {
        int selection = ui.getTutorChoice(
            tutors.filter(
                // TODO: this requires tweaking Tutor class's `.equals()` method
                (Tutor t) -> !this.courseTutorMap.get(c).contains(t)
            ).toArray(Tutor.class)
        );

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
     * asking , those three are subbed to another two methods above
     */
    public void assignTutorsToCourse(CourseManagement cm, TutorManagement tm) {
        Course selectedCourse = this.selectCourse(cm.getCourses());
        while (true) {
            // TODO: see yi kit got change to use our own eh or not
            ArrayList<Tutor> filteredTutors = this.filterTutorSuitableForCourse(
                selectedCourse, 
                new ArrayList<Tutor>(tm.getTutors())
            );
            Tutor selectedTutor = this.selectTutor(selectedCourse, filteredTutors);

            this.assignTutorToCourse(selectedCourse, selectedTutor);
            
            if (!ui.wantAssignMoreTutor())
                break;
        }
    }

    public void assignTutorialGroupsToTutor() {

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
        int option = ui.getMenuChoice();
        switch (option) {
            case 0:
                this.assignTutorsToCourse(cm, tm);
                break;
        
            case 1:
                break;
            case 2:
                break;
            case 3: 
                break;
            case 4:
                break;
            case 5:
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
