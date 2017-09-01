package test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	
	private int user_id;
	private String firstName;
	private String lastName;
	
	public Student(int user_id, String firstName, String lastName){
		this.user_id=user_id;
		this.firstName=firstName;
		this.lastName=lastName;
	}

	public Student(String firstname, String lastname){
		this.firstName=firstname;
		this.lastName=lastname;
	}
	@Override
	public String toString() {
		return "Student [user_id=" + user_id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	

	/*private SimpleIntegerProperty user_id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;

	public Student(int user_id, String firstName, String lastName) {
		this.user_id = new SimpleIntegerProperty(user_id);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}

	public SimpleIntegerProperty getId() {
		return user_id;
	}

	public void setId(SimpleIntegerProperty id) {
		this.user_id = id;
	}

	public StringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(SimpleStringProperty firstName) {
		this.firstName = firstName;
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Student [id=" + user_id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	
	public SimpleIntegerProperty idProperty(){return user_id;}
	public SimpleStringProperty firstnameProperty(){return firstName;}
	public SimpleStringProperty lastnameProperty(){return lastName;}*/
	
}
