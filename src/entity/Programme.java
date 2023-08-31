/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Neoh Soon Chee
 */
public class Programme {
    private String programmeCode;
    private String programmeName;
    private TutorialGroup[] tutGrp;
    private static int numberOfProgramme = 0;
    
    public static int getNumberOfProgramme() {
        return numberOfProgramme;
    }

    public Programme(){};
    
    public Programme(String programmeCode, String ProgrammeName){
        this. programmeCode = programmeCode;
        this.programmeName = ProgrammeName;
        numberOfProgramme++;
    }
    
    public void setProgrammeCode(String programmeCode){
        this.programmeCode = programmeCode;
    }
    
    public void setProgrammeName(String programmeName){
        this.programmeName = programmeName;
    }
    
    public String getProgrammeCode(){
        return programmeCode;
    }
    
    public String getProgrammeName(){
        return programmeName;
    }
    
    public TutorialGroup[] getTutorialGroup(){
        return tutGrp;
    }
    
    @Override
    public String toString(){
        return String.format("Programme Code: %s\nProgramme Name: %s\n", programmeCode, programmeName);
    }
}
