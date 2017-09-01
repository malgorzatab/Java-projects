package test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TechValidate {
	
	public static boolean ValidateTech(Technique t){
		
		if(techValidate(t.getTechnique()) && coachValidate(t.getCoach())&& levelValidate(t.getLevel())){
			return true;
		}
		else{
			return false;
		}
					
	}
	
	public static boolean techValidate( String tech){
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$" );
		Matcher matcher = pattern.matcher(tech);
		return matcher.find();
	}
	
	public static boolean coachValidate( String coach){
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$" );
		Matcher matcher = pattern.matcher(coach);
		return matcher.find();
	}
	
	public static boolean levelValidate( String level){
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$" );
		Matcher matcher = pattern.matcher(level);
		return matcher.find();
	}

}
