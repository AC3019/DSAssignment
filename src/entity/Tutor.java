/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author yong
 */
public class Tutor implements Comparable<Tutor>, Serializable{
    private String name;
    private String department;
    private int id ;
    private char gender;
    // private TutorialGroup tutorialGroup;
    private int age;
    private String phoneNum;
    private String icNo;
    private static int currentID = 1;
   // private double salary;

    // default constructor
    public Tutor() {
    }

    public Tutor(int id,String name, String department, char gender, /* TutorialGroup tutorialGroup */int age,
            String phoneNum,String icNo) {
        this.id=id;
        this.name = name;
        this.department = department;
        this.gender = gender;
        // this.tutorialGroup=tutorialGroup;
        this.age = age;
        this.phoneNum = phoneNum;
        this.icNo=icNo;
       // this.salary=salary;

    }
    
     public Tutor( String name, String department, char gender, /* TutorialGroup tutorialGroup */int age,
            String phoneNum,String icNo) {
       
        this(currentID++, name, department, gender, age, phoneNum, icNo);
    }
    

    public Tutor (int id,String name, String department,String phoneNum){
        this.id=id;
        this.name=name;
        this.department=department;
        this.phoneNum=phoneNum;
       // this.salary=salary;
    }
    public String getName() {
        return name;

    }

    public String getDepartment() {
        return department;

    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;

    }
    /*
    public double getSalary(){
        return salary;
    }
    */

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
    /*
    public void setSalary(double salary){
        this.salary=salary;
    }
*/
    public void setDepartment(String department) {
        this.department = department;
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
                "Tutor's id: %d\nTutor's name:%s\nTutor's department:%s\nTutor's gender: %s\nTutor's age:%d\nTutor's phone num:%s\n IcNo:%s\n ",
                id, name, department, gender, age, phoneNum, icNo);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((icNo == null) ? 0 : icNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tutor other = (Tutor) obj;
        if (id != other.id)
            return false;
        if (icNo == null) {
            if (other.icNo != null)
                return false;
        } else if (!icNo.equals(other.icNo))
            return false;
        return true;
    }

    @Override
    public int compareTo(Tutor o) {
        return ((Integer) this.id).compareTo((Integer) o.getId());
    }

}


