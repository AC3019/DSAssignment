/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import adt.ArrayList;
/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroup {
    private String tutGrpCode;
    private ArrayList<Student> student;
    
    public TutorialGroup (String tutGrpCode){
        this.tutGrpCode = tutGrpCode;
    }
    
    public void setTutGrpCode(String tutGrpCode){
        this.tutGrpCode = tutGrpCode;
    }
    
    public String getTutGrpCode(){
        return tutGrpCode;
    }
    
    public ArrayList<Student> getStudent(){
        return student;
    }
}
