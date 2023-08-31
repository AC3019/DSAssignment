package boundary;

import entity.Course;
import entity.Tutor;
import utility.Input;

public class TeachingAssignmentUI {
    
    public int getMenuChoice() {
        return Input.getChoice("Enter your choice: ", new String[] {
            "Assign tutor to a course",
            "Assign tutorial groups to tutor",
            "List tutors for a course",
            "List all courses under a tutor",
            "Generate report",
            "Exit teaching assignment"
        }, (s) -> s);
    }

    public int getCourseChoice(Course[] courses) {
        return Input.getChoice(
            "Which course to assign: ", 
            courses, 
            (Course c) -> c.toString()
        );
    }

    public int getTutorChoice(Tutor[] tutors) {
        return Input.getChoice(
            "Which tutor to assign for this course: ",
            tutors,
            // TODO: maybe can add one years of experience to tutor
            (Tutor t) -> t.getId() + " " + t.getName()
        );
    }

    // asks user whether they wanna insert more tutor
    public boolean wantAssignMoreTutor() {
        return Input.confirm("Do you want to assign more tutor to the course?");
    }

}
