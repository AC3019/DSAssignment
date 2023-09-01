package control;

import java.io.Serializable;

import adt.ArrayList;
import entity.Course;

/**
 * Just a stub to simulate the course module, since teaching assignment needs it
 *  @author xuan bin
 */
public class CourseManagement implements Serializable {
    private ArrayList<Course> courses = new ArrayList<>(new Course[] {

    });
    
    public ArrayList<Course> getCourses() {
        return this.courses;
    }
}
