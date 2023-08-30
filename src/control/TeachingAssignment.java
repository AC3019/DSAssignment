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
        
        ArrayList<Integer> arr = new ArrayList<>(new Integer[] {
            20, 50, 10, 30, 25
        });

        arr.sort((i1, i2) -> {System.out.println(i1 + " " + i2); return i1 - i2;});

        for (Integer i: arr) {
            System.out.println(i);
        }
    }
}
