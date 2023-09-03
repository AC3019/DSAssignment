/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import utility.Formatter;
import java.io.Serializable;
/**
 *
 * @author Neoh Soon Chee
 */
public class Student implements Serializable{
    private String studentID;
    private String studentName;
    private int age;
    private String gender;
    private int mark;
    private static int numberOfStudents = 1;
    
    public Student(){};
    
    public Student (String studentID, String studentName, int age, String gender){
        //this.studentID = studentID + numberOfStudents;
        //formatter is to set the studentID from int to string and fill the gap of 3 digits
        //e.g. int 1 -> string "001"
        this.studentID = studentID + Formatter.padLeft(String.valueOf(numberOfStudents), 3).replace(" ", "0");
        this.studentName = studentName;
        this.age = age;
        this.gender = gender;
        this.mark = 100;
        numberOfStudents++;
    }
    
    public void setStudentID(String studentID){
        this.studentID = studentID;
    }
    
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    
    public void setAge(int age){
        this.age = age;
    }
    
    public void setMark(int mark){
        this.mark = mark;
    }
    
    public String getStudentID(){
        return studentID;
    }
    
    public String getStudentName(){
        return studentName;
    }
    
    public int getStudentAge(){
        return age;
    }
    
    public String getStudentGender(){
        return gender;
    }
    
    public int getStudentDemeritMark(){
        return mark;
    }
}
