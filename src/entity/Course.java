package entity;

import utility.NotSameInstanceException;

/**
 * Just a stub entity
 * @author xuanbin
 */
public class Course implements Comparable<Course> {

    private String id; 
    private String name;
    private int creditHour;
    private static final int DEFAULT_CREDIT_HOUR = 1;

    public Course(String id, String name, int creditHour) {
        this.id = id;
        this.name = name;
        this.creditHour = creditHour;
    }

    public Course(String id, String name) { this(id, name, Course.DEFAULT_CREDIT_HOUR); }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHour() {
        return this.creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }

    /**
     * HashCode contract for this class: id same considered same and should generate same hashcode
     * 
     * @ref General algorithm to generate hashcode (Effective Java) https://stackoverflow.com/a/113600
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * ID is unique, if id equals then consider equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Course))
            return false;
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int compareTo(Course o) {
        if (!(o instanceof Course))
            throw new NotSameInstanceException("o is not an instance of Course, can't compare");
        
        // id will need to be unique, compare id will do
        return this.id.compareTo(o.getId());
    }
    
}
