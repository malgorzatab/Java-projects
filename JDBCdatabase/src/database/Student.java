package database;

public class Student {
	private int user_id;
	private String firstName;
	private String lastName;

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int id, String firstName, String lastName) {

		this.user_id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return user_id;
	}

	public void setId(int id) {
		this.user_id = id;
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

	@Override
	public String toString() {
		return "User [id=" + user_id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
