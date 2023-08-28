/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Neoh Soon Chee
 */
public class Student {
    private String studentID;
    private String studentName;
    private int age;
    private static int numberOfStudents = 1;
    
    public Student(){};
    
    public Student (String studentID, String studentName, int age){
        this.studentID = studentID + numberOfStudents;
        this.studentName = studentName;
        this.age = age;
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
    
    public String getStudentID(){
        return studentID;
    }
    
    public String getStudentName(){
        return studentName;
    }
    
    public int getStudentAge(){
        return age;
    }
}
