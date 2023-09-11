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
        new Course("BACS 2023", "Data Structures and Algorithms", DepartmentManagement.departments[0], 3),
        new Course("BACS 1023", "Introduction to Computing", DepartmentManagement.departments[0], 3),
        new Course("BACS 1014", "Introduction to Computer Systems", DepartmentManagement.departments[0], 4),
    });
    
    public ArrayList<Course> getCourses() {
        return this.courses;
    }
}
