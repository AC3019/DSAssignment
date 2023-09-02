package boundary;

import java.io.Serializable;

import entity.Course;
import entity.Tutor;
import entity.TutorialGroup;
import utility.Input;

public class TeachingAssignmentUI implements Serializable {
    
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

    public int getTutorChoice(Tutor[] tutors, String prompt) {
        return Input.getChoice(
            prompt,
            tutors,
            (Tutor t) -> t.getId() + " " + t.getName()
        );
    }

    public int getTutorialGroupChoice(TutorialGroup[] tgs, String prompt) {
        return Input.getChoice(
            prompt,
            tgs,
            (TutorialGroup tg) -> tg.getProgrammeName() + " " + tg.getTutGrpCode()
        );
    }

    /**
     * asks user whether they wanna insert more tutor
     * @param type "TUTOR" or "TUTORIALGROUP"
     * @return
     */
    public boolean wantAssignMore(String type) {
        if (type.equals("TUTOR"))
            return Input.confirm("Do you want to assign more tutor to the course?");
        else 
            return Input.confirm("Do you want to assign more tutorial groups to the tutor?");
    }

}
