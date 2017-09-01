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


public class ClassesDAO {

private Connection myConn;
	
	public ClassesDAO() throws Exception {
		
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
	
	public void deleteClasses(int classId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from classes where id=?");
			
			// set param
			myStmt.setInt(1, classId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void updateClasses(Classes theClasses) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update classes"
					+ " set user_id=?, tech_id=?"
					+ " where id=?");
			
			// set params
			myStmt.setInt(1, theClasses.getUser_id());
			myStmt.setInt(2, theClasses.getTech_id());		
			myStmt.setInt(3, theClasses.getClass_id());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	
	public void addClasses(Classes theClasses) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into classes"
					+ " (user_id, tech_id)"
					+ " values (?, ?)");
			
			// set params
			myStmt.setInt(1, theClasses.getUser_id());
			myStmt.setInt(2, theClasses.getTech_id());
			
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Classes> getAllClasses() throws Exception {
		List<Classes> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM classes,users,techniques WHERE users.user_id=classes.user_id AND classes.tech_id=techniques.tech_id");
			
			while (myRs.next()) {
				Classes tempClasses = convertRowToClasses(myRs);
				list.add(tempClasses);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Classes> searchClasses(String class_id) throws Exception {
		List<Classes> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			class_id += "%";
			myStmt = myConn.prepareStatement("select * from classes where class_id like ?  order by class_id");
			
			myStmt.setString(1, class_id);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Classes tempClasses = convertRowToClasses(myRs);
				list.add(tempClasses);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
private Classes convertRowToClasses(ResultSet myRs) throws SQLException {
		
		int classid = myRs.getInt("class_id");
		int userid = myRs.getInt("user_id");
		int techid = myRs.getInt("tech_id");
		
		
		
		Classes tempClasses = new Classes(classid, userid, techid);
		
		return tempClasses;
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
	
	ClassesDAO dao = new ClassesDAO();
	//System.out.println(dao.searchEmployees("thom"));

	System.out.println(dao.getAllClasses());
}


}
