/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author yong
 */
public class Tutor implements Comparable<Tutor> {
    private String name;
    private String subject;
    private int id ;
    private char gender;
    // private TutorialGroup tutorialGroup;
    private int age;
    private String phoneNum;
    private String icNo;
    

    // default constructor
    public Tutor() {
    }

    public Tutor(int id,String name, String subject, char gender, /* TutorialGroup tutorialGroup */int age,
            String phoneNum,String icNo) {
        this.id=id;
        this.name = name;
        this.subject = subject;
        this.gender = gender;
        // this.tutorialGroup=tutorialGroup;
        this.age = age;
        this.phoneNum = phoneNum;
        this.icNo=icNo;

    }
    
     public Tutor( String name, String subject, char gender, /* TutorialGroup tutorialGroup */int age,
            String phoneNum,String icNo) {
       
       this.name = name;
       this.subject = subject;
       this.gender = gender;
        // this.tutorialGroup=tutorialGroup;
      this.age = age;
       this.phoneNum = phoneNum;
       this.icNo=icNo;
    }
    

    public Tutor (int id,String name, String subject){
        this.id=id;
        this.name=name;
        this.subject=subject;
    }
    public String getName() {
        return name;

    }

    public String getSubject() {
        return subject;

    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getId() {
        return id;
    }
    public String getIcNO(){
        return icNo;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setIcNo(String icNo){
        this.icNo=icNo;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return String.format(
                "Tutor's id: %d\nTutor's name:%s\nTutor's Subject:%s\nTutor's gender: %s\nTutor's age:%d\nTutor's phone num:%s\n IcNo:%s\n",
                id, name, subject, gender, age, phoneNum, icNo);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Tutor))
            return false;
        Tutor other = (Tutor) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Tutor o) {
        return ((Integer) this.id).compareTo((Integer) o.getId());
    }

}


