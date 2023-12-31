/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import adt.ArrayList;
import java.io.Serializable;
/**
 *
 * @author Neoh Soon Chee
 */
public class TutorialGroup implements Serializable {
    private String programmeCode;
    private String programmeName;
    private String tutGrpCode;
    private ArrayList<Student> student;
    
    public TutorialGroup (String programmeCode, String programmeName, String tutGrpCode){
        this.programmeCode = programmeCode;
        this.programmeName = programmeName;
        this.tutGrpCode = tutGrpCode;
        // init student to be an empty arraylist
        this.student = new ArrayList<>();
    }
    
    public void setProgrammeCode(String programmeCode){
        this.programmeCode = programmeCode;
    }
    
    public void setProgrammeName(String programmeName){
        this.programmeName = programmeName;
    }
    
    public void setTutGrpCode(String tutGrpCode){
        this.tutGrpCode = tutGrpCode;
    }
    
    public String getProgrammeCode(){
        return programmeCode;
    }
    
    public String getProgrammeName(){
        return programmeName;
    }
    
    public String getTutGrpCode(){
        return tutGrpCode;
    }
     
    public ArrayList<Student> getStudent(){
        return student;
    }
}
