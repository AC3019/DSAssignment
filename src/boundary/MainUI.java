package boundary;

import utility.Input;

public class MainUI {

    public int getMenuChoice() {
        return Input.getChoice("Available subsystems", "Enter your choice: ", new String[] {
            "Tutor Management",
            "Tutorial Group Management",
            "Teaching Assignment",
            "Bye bye"
        }, (s) -> s);
    }

}
