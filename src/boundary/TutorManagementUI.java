package boundary;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yong
 */
public class TutorManagementUI {
    public int printMenu() {
        System.out.println("1. Add a new tutor");
        System.out.println("2. Remove a tutor");
        System.out.println("3. Find tutor");
        System.out.println("4. Amend tutor details");
        System.out.println("5. List all tutors");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
        return Input.getChoice(
            "Select an option: ",
            new String[] {
                "Add a new tutor",
                    "Remove a tutor",
                    "Find tutor",
                    "Amend tutor details"
            },
            (item) -> item
        )
        
    }
}
