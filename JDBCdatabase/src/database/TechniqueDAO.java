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


public class TechniqueDAO {

private Connection myConn;
	
	public TechniqueDAO() throws Exception {
		
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
	
	public void deleteTechnique(int techId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from techniques where id=?");
			
			// set param
			myStmt.setInt(1, techId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void updateTechnique(Technique theTechnique) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update techniques"
					+ " set technique=?, coach=?, level=?"
					+ " where id=?");
			
			// set params
			myStmt.setString(1, theTechnique.getTechnique());
			myStmt.setString(2, theTechnique.getCoach());
			myStmt.setString(3, theTechnique.getLevel());
			myStmt.setInt(4, theTechnique.getTech_id());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	
	public void addTechnique(Technique theTechnique) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into techniques"
					+ " (technique, coach, level)"
					+ " values (?, ?, ?)");
			
			// set params
			myStmt.setString(1, theTechnique.getTechnique());
			myStmt.setString(2, theTechnique.getCoach());
			myStmt.setString(3, theTechnique.getLevel());
			
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Technique> getAllTechniques() throws Exception {
		List<Technique> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from techniques order by tech_id");
			
			while (myRs.next()) {
				Technique tempTechnique = convertRowToTechnique(myRs);
				list.add(tempTechnique);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Technique> searchTechnique(String technique) throws Exception {
		List<Technique> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			technique += "%";
			myStmt = myConn.prepareStatement("select * from techniques where techniquee like ?  order by technique");
			
			myStmt.setString(1, technique);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Technique tempTechnique = convertRowToTechnique(myRs);
				list.add(tempTechnique);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
private Technique convertRowToTechnique(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("tech_id");
		String technique = myRs.getString("technique");
		String coach = myRs.getString("coach");
		String level = myRs.getString("level");
		
		
		Technique tempTechnique = new Technique(id, technique, coach, level);
		
		return tempTechnique;
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
	
	TechniqueDAO dao = new TechniqueDAO();
	//System.out.println(dao.searchEmployees("thom"));

	System.out.println(dao.getAllTechniques());
}


}
