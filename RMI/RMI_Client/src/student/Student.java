package student;

import java.io.Serializable;

public class Student implements Serializable {

    private double mark;
    private String firstName;
    private String lastName;
    private int age;

    public Student() {
        this.mark = 3.0;
        this.firstName = "Jan";
        this.lastName = "Nowak";
        this.age = 21;
    }

    public Student(double mark, String firstName, String lastName, int age) {
        this.mark = mark;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Student(Student s) {
        this.mark = s.getMark();
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.age = s.getAge();

    }

    @Override
    public String toString() {
        String result = this.firstName;
        result = new StringBuilder(result).append("   " + this.lastName).toString();
        result = new StringBuilder(result).append("   " + this.mark).toString();
        result = new StringBuilder(result).append("   " + this.age).toString();
        return result;

    }

    public double getMark() {
        return this.mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + age;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());

        long temp;
        temp = Double.doubleToLongBits(mark);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Student other = (Student) obj;

        if (age != other.age) {
            return false;
        }

        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }

        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }

        if (Double.doubleToLongBits(mark) != Double.doubleToLongBits(other.mark)) {
            return false;
        }

        return true;
    }

}
