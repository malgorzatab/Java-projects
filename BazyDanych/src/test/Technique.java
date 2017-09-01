package test;

public class Technique {
	
	private int tech_id;
	private String technique;
	private String coach;
	private String level;

	public Technique(int tech_id, String technique, String coach, String level) {
		this.tech_id = tech_id;
		this.technique = technique;
		this.coach = coach;
		this.level = level;
	}

	public int getTech_id() {
		return tech_id;
	}

	public void setTech_id(int tech_id) {
		this.tech_id = tech_id;
	}

	public String getTechnique() {
		return technique;
	}

	public void setTechnique(String technique) {
		this.technique = technique;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Technique [tech_id=" + tech_id + ", technique=" + technique + ", coach=" + coach + ", level=" + level
				+ "]";
	}
	
	
}
