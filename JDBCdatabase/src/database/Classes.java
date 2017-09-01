package database;

public class Classes {

	private int class_id;
	private int user_id;
	private int tech_id;
	

	public Classes(int class_id, int user_id, int tech_id) {
		this.class_id = class_id;
		this.user_id = user_id;
		this.tech_id = tech_id;
		
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
	
	
}
