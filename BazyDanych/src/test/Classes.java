package test;

public class Classes {

	private int class_id;
	private int user_id;
	private int tech_id;
	String fname;
	String lname;
	String tech;
	String coach;
	String level;
	int classid;
	

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public Classes(int class_id, int user_id, int tech_id, String firstName, String lastName,String coach,String tech, String level) {
		this.class_id = class_id;
		this.user_id = user_id;
		this.tech_id = tech_id;
		this.fname = firstName;
		this.lname = lastName;
		this.coach = coach;
		this.tech = tech;
		this.level = level;
		
	}
	
	public Classes(int class_id, int user_id, int tech_id){
		this.class_id = class_id;
		this.user_id = user_id;
		this.tech_id = tech_id;
	}
	
	public Classes( int user_id, int tech_id){
	
		this.user_id = user_id;
		this.tech_id = tech_id;
	}

	public Classes(int classid, String firstName, String lastName,String coach,String tech, String level) {
		this.classid = classid;
		this.fname = firstName;
		this.lname = lastName;
		this.coach = coach;
		this.tech = tech;
		this.level = level;
		
	}

	public String getFirstName() {
		return fname;
	}


	public void setFirstName(String firstName) {
		this.fname = firstName;
	}


	public String getLastName() {
		return lname;
	}


	public void setLastName(String lastName) {
		this.lname = lastName;
	}


	public String getCoach() {
		return coach;
	}


	public void setCoach(String coach) {
		this.coach = coach;
	}


	public String getTech() {
		return tech;
	}


	public void setTech(String tech) {
		this.tech = tech;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public int getClass_id() {
		return class_id;
	}


	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getTech_id() {
		return tech_id;
	}


	public void setTech_id(int tech_id) {
		this.tech_id = tech_id;
	}


	@Override
	public String toString() {
		return "Classes [class_id=" + class_id + ", user_id=" + user_id + ", tech_id=" + tech_id + "]";
	}
	
	
}
