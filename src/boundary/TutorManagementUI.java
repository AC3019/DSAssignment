package boundary;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yong
 */
import utility.Input;
import entity.Tutor;
import java.util.Scanner;
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
        );
         
    }
    public Tutor tutorInput(){
        Scanner scan = new Scanner(System.in);
        boolean invalidInput = false;
        String name = "";
        String subject = "";
        char gender;
        int age;
        String phoneNum;
        do {
            invalidInput = false;
            System.out.print("Name: ");
            name = scan.nextLine();

            System.out.print("Subject: ");
            subject = scan.nextLine();

            System.out.print("Gender(M/F): ");
            gender = scan.next().toUpperCase().charAt(0);

            System.out.print("Age: ");
            age = scan.nextInt();
            scan.nextLine();// clear buffer
            System.out.print("Phone: ");
            phoneNum = scan.nextLine();

            // validate gender
            if (Character.valueOf(gender).compareTo("M".charAt(0)) != 0
                    && Character.valueOf(gender).compareTo("F".charAt(0)) != 0) {
                System.out.println("Gender should be either M or F. Please re-enter...");
                invalidInput = true;
            }
             } while (invalidInput);

        Tutor tutorObj = new Tutor(getId(), name, subject, gender, age, phoneNum);
        return tutorObj;
    }
}
