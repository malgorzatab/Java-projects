package test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.StringProperty;

public class UsersValidate {
	
	
	public static boolean ValidateUser(Student st){
		
		if(nameValidate(st.getFirstName()) && surnameValidate(st.getLastName())){
			return true;
		}
		else{
			return false;
		}
					
	}
	
	public static boolean nameValidate( String string){
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$" );
		Matcher matcher = pattern.matcher((CharSequence) string);
		return matcher.find();
	}
	
	public static boolean surnameValidate( String string){
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$" );
		Matcher matcher = pattern.matcher((CharSequence) string);
		return matcher.find();
	}

	
}


