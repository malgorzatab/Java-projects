package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class StudentDAO {

private Connection myConn;
	
	public StudentDAO() throws Exception {
		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("test.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	public void deleteStudent(int studentId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from users where user_id=?");
			
			// set param
			myStmt.setInt(1, studentId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void updateStudent(Student theStudent) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update users"
					+ " set first_name=?, last_name=?"
					+ " where user_id=?");
			
			// set params
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());		
			myStmt.setInt(3, theStudent.getId());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	
	public void addStudent(Student theStudent) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into users"
					+ " (user_id, first_name, last_name)"
					+ " values (?, ?, ?)");
			
			// set params
			myStmt.setInt(1, theStudent.getId());
			myStmt.setString(2, theStudent.getFirstName());
			myStmt.setString(3, theStudent.getLastName());
			
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Student> getAllStudents() throws Exception {
		List<Student> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from users order by last_name");
			
			while (myRs.next()) {
				Student tempStudent = convertRowToStudent(myRs);
				list.add(tempStudent);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Student> searchStudents(String lastName) throws Exception {
		List<Student> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			lastName += "%";
			myStmt = myConn.prepareStatement("select * from users where last_name like ?  order by last_name");
			
			myStmt.setString(1, lastName);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Student tempStudent = convertRowToStudent(myRs);
				list.add(tempStudent);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
private Student convertRowToStudent(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("user_id");
		String lastName = myRs.getString("last_name");
		String firstName = myRs.getString("first_name");
		
		
		Student tempStudent = new Student(id, lastName, firstName);
		
		return tempStudent;
	}

private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
		throws SQLException {

	if (myRs != null) {
		myRs.close();
	}

	if (myStmt != null) {
		
	}
	
	if (myConn != null) {
		myConn.close();
	}
}

private void close(Statement myStmt, ResultSet myRs) throws SQLException {
	close(null, myStmt, myRs);		
}

private void close(Statement myStmt) throws SQLException {
	close(null, myStmt, null);		
}

public static void main(String[] args) throws Exception {
	
	StudentDAO dao = new StudentDAO();
	//System.out.println(dao.searchEmployees("thom"));

	System.out.println(dao.getAllStudents());
}


}
