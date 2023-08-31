package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import adt.ArrayList;
import adt.HashMap;
import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.Input;

/**
 * @author hanyue1014
 */
public class TeachingAssignment implements Serializable {

    // private HashMap<Course, ArrayList<Tutor>> courseTutorMap;
    // private HashMap<Tutor, ArrayList<TutorialGroup>> tutorTutorialGrouMap;
    ArrayList<Integer> arr = new ArrayList<>(new Integer[] {
        20, 50, 10, 30, 25
    });

    // public TeachingAssignment() {
    //     this.courseTutorMap = new HashMap<>();
    //     this.tutorTutorialGrouMap = new HashMap<>();
    // }

    public void saveSnapshot() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(
                new FileOutputStream("autosave/snapshot.dat")
            );
            writer.writeObject(this);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSnapshot() {
        try {
            ObjectInputStream reader = new ObjectInputStream(
                new FileInputStream("autosave/snapshot.dat")
            );
            TeachingAssignment ta = (TeachingAssignment) reader.readObject();
            this.arr = ta.arr;
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TeachingAssignment ta = new TeachingAssignment();
        
        String s = Input.getString("Save snapshot?: ", false);

        if (s.equals("y")) {
            ta.saveSnapshot();
            return;
        }

        ta.arr.sort((i1, i2) -> {System.out.println(i1 + " " + i2); return i1 - i2;});

        ta.loadSnapshot();
        for (Integer i: ta.arr) {
            System.out.println(i);
        }
    }
}
