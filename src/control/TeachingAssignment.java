package control;

import adt.ArrayList;
import adt.HashMap;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;

/**
 * @author hanyue1014
 */
public class TeachingAssignment {

    private HashMap<Course, ArrayList<Tutor>> courseTutorMap;
    private HashMap<Tutor, ArrayList<TutorialGroup>> tutorTutorialGrouMap;

    public TeachingAssignment() {
        this.courseTutorMap = new HashMap<>();
        this.tutorTutorialGrouMap = new HashMap<>();
    }

    public static void main(String[] args) {
        
    }
}
