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
            (Tutor t) -> {
                ArrayList<Tutor> cts = this.courseTutorMap.get(c);
                if (cts == null)
                    return false;
                return !cts.contains(t) && t.getDepartment().equals(c.getDepartment());
            }
        );
    }

    private Tutor selectTutorForCourse(Course c, ArrayList<Tutor> tutors) {
        int selection = ui.getTutorChoice(
            tutors.filter(
                // TODO: this requires tweaking Tutor class's `.equals()` method
                (Tutor t) -> !this.courseTutorMap.get(c).contains(t)
            ).toArray(Tutor.class),
            "Which tutor to assign for this course[" + c.getId() + " " + c.getName() + "]: "
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
     * those three are subbed to another two methods above
     */
    public void assignTutorsToCourse(CourseManagement cm, TutorManagement tm) {
        Course selectedCourse = this.selectCourse(cm.getCourses());
        while (true) {
            ArrayList<Tutor> filteredTutors = this.filterTutorSuitableForCourse(
                selectedCourse, 
                new ArrayList<Tutor>(tm.getTutors())
            );
            Tutor selectedTutor = this.selectTutorForCourse(selectedCourse, filteredTutors);

            this.assignTutorToCourse(selectedCourse, selectedTutor);
            
            if (!ui.wantAssignMore("TUTOR"))
                break;
        }
    }

    public Tutor selectTutor(ArrayList<Tutor> ts) {
        int selection = ui.getTutorChoice(ts.toArray(Tutor.class), "Which tutor to assign tutorial groups to: ");
        return ts.get(selection);
    }

    public TutorialGroup selectTutorialGroupForTutor(Tutor t, ArrayList<TutorialGroup> tgs) {
        int selection = ui.getTutorialGroupChoice(
            tgs.filter((TutorialGroup tg) -> {
                ArrayList<TutorialGroup> hmTg = this.tutorTutorialGroupMap.get(t);
                if (hmTg == null) 
                    return false;
                return hmTg.contains(tg);
            }).toArray(TutorialGroup.class),
            "Which tutorial group to assign to this tutor[" + t.getId() + " " + t.getName() + "]: "
        );

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
        Tutor selectedTutor = this.selectTutor(new ArrayList<Tutor>(tm.getTutors()));

        while (true) {
            TutorialGroup tg = this.selectTutorialGroupForTutor(selectedTutor, tgm.getTutorialGroups());

            this.assignTutorialGroupToTutor(selectedTutor, tg);

            if (!ui.wantAssignMore("TUTORIALGROUP"))
                break;
        }
    }

    public Course[] searchCoursesUnderTutor() {
        ArrayList<Course> coursesOfTutor = new ArrayList<>();

        return coursesOfTutor.toArray(Course.class);
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
