package test;

import javafx.event.ActionEvent;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.ControlScreen;
import controller.ScreensController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable, ControlScreen{
	
	private ScreensController myController;
	@FXML private TableView<Student> tableStudents;
	@FXML private TableView<Technique> tableTechnique;
	@FXML private Button loadButton;
	@FXML private Button loadClassButton;
	@FXML private TableColumn<Student, Integer> idColumn;
	@FXML private TableColumn<Student, String> firstNameColumn;
	@FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Technique,String> levelColumn;
    @FXML private TableColumn<Technique,Integer> techIdColumn;
    @FXML private TableColumn<Technique,String> coachColumn;
    @FXML private TableColumn<Technique,String> techColumn;
    @FXML private TableColumn<Classes, String> classlevelColumn;
    @FXML private TableColumn<Classes, String> fnameColumn;
    @FXML private TableColumn<Classes, String> classcoachColumn;
    @FXML private TableView<Classes> tableClasses;
    @FXML private TableColumn<Classes, String> classtechColumn;
    @FXML private TableColumn<Classes, String> lnameColumn;
    @FXML private TableColumn<Classes, Integer> classIdColumn;
    
    @FXML private TextField classTechIdInput;
    @FXML private TextField classUserIdInput;
    @FXML private TextField classIdInput;
    @FXML private Button addclassBtn;
    @FXML private Button updateclassBtn;
    @FXML private Button deleteclassBtn;
    @FXML private Button classcancelBtn;
   
    @FXML private TextField studentIdInput;
    @FXML private TextField studentFirstNameInput;
    @FXML private TextField studentLastNameInput; 
    @FXML private Button studentDeleteButton; 
    @FXML private Button addButton;
    @FXML private Button studentUpdateButton;  
    @FXML private Button cancelButton;
    
    @FXML private Button loadTechButton;
    @FXML private TextField techNameInput;  
    @FXML private Button techCancelButton;
    @FXML private TextField techLevelInput; 
    @FXML private Button techUpdateButton;
    @FXML private Button addTechButton;
    @FXML private TextField techIdInput;
    @FXML private TextField techCoachInput;  
    @FXML private Button techDeleteButton; 
    @FXML private Label errorLabel;
    @FXML private Label techErrorLabel;
    
    TechValidate techVal;
    UsersValidate studentVal;
    
    Classes classes = new Classes(0, null, null, null, null, null);
	
	private ObservableList<Student>studentList;
	private ObservableList<Technique>techList;
	private ObservableList<Classes>classList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		
	}
	
	@FXML
	    private void loadStudents(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		try{
		Statement myStm= null;
		Connection myConn =null;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
		if(myConn != null){
		studentList=FXCollections.observableArrayList();
		myStm = myConn.createStatement();
		ResultSet res = myStm.executeQuery("SELECT * FROM users ORDER BY user_id");
		
		while(res.next()){
			studentList.add(new Student(res.getInt("user_id"), res.getString("first_name"), res.getString("last_name")));
		}
		}
		} catch(SQLException ex){
			ex.printStackTrace();
		
		}
		
		idColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("user_id"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("lastName"));
		
		tableStudents.setItems(null);
		tableStudents.setItems(studentList);

	    }
	
	  @FXML
	    private void loadTechniques(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		  try{
				Statement myStm= null;
				Connection myConn =null;
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				if(myConn != null){
				techList=FXCollections.observableArrayList();
				myStm = myConn.createStatement();
				ResultSet res = myStm.executeQuery("SELECT * FROM techniques ");
				
				while(res.next()){
					techList.add(new Technique(res.getInt("tech_id"), res.getString("technique"), res.getString("coach"), res.getString("level")));
				}
				}
				} catch(SQLException ex){
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
				
				techIdColumn.setCellValueFactory(new PropertyValueFactory<Technique,Integer>("tech_id"));
				techColumn.setCellValueFactory(new PropertyValueFactory<Technique,String>("technique"));
				coachColumn.setCellValueFactory(new PropertyValueFactory<Technique,String>("coach"));
				levelColumn.setCellValueFactory(new PropertyValueFactory<Technique,String>("level"));
				
				
				tableTechnique.setItems(null);
				tableTechnique.setItems(techList);
	    }
	  
	  @FXML
	    private void loadClasses(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		  try{
				Statement myStm= null;
				Connection myConn =null;
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				if(myConn != null){
				classList=FXCollections.observableArrayList();
				myStm = myConn.createStatement();
				ResultSet res = myStm.executeQuery("SELECT * FROM classes,users,techniques WHERE users.user_id=classes.user_id AND classes.tech_id=techniques.tech_id");
				
				while(res.next()){
					//classList.add(new Classes(res.getInt("class_id"),res.getInt("user_id"),res.getInt("tech_id")));
					
					
					classes.classid=res.getInt("class_id");
					classes.fname=res.getString("first_name") ;
					classes.lname=res.getString("last_name");
					classes.tech=res.getString("technique");
					classes.coach=res.getString("coach");
					classes.level=res.getString("level");
					classList.add(new Classes(classes.classid,classes.fname,classes.lname,classes.tech,classes.coach,classes.level));

				}
				}
				} catch(SQLException ex){
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
		
				classIdColumn.setCellValueFactory(new PropertyValueFactory<Classes,Integer>("classid"));
				fnameColumn.setCellValueFactory(new PropertyValueFactory<Classes,String>("fname"));
				lnameColumn.setCellValueFactory(new PropertyValueFactory<Classes,String>("lname"));
				classtechColumn.setCellValueFactory(new PropertyValueFactory<Classes,String>("tech"));
				classcoachColumn.setCellValueFactory(new PropertyValueFactory<Classes,String>("coach"));
				classlevelColumn.setCellValueFactory(new PropertyValueFactory<Classes,String>("level"));	
				
				tableClasses.setItems(null);
				tableClasses.setItems(classList);
	    }
	  

	    @FXML
	    void handleAddStudent(ActionEvent event) {
	    	
	    	String id=studentIdInput.getText().trim();
	    	String fname=studentFirstNameInput.getText().trim();   	
	    	String lname= studentLastNameInput.getText().trim();	    	    	
	    	Student tempStudent = new Student(Integer.parseInt(id),fname,lname);    
	    	if(UsersValidate.ValidateUser(tempStudent) == false){
	    		errorLabel.setText("First name and last name must be string");	  
	    	}
	    	else{		
	    		addStudent(tempStudent);
		    	errorLabel.setText(""); 
	    	}
	    		    		    		    		    	   
	    }
	    
	    public void addStudent(Student theStudent){
	    	try{
	    		PreparedStatement myStmt= null;
				Connection myConn =null;
				
				String query = "insert into users" + " (user_id, first_name, last_name)" + " values (?, ?, ?)";				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				if(myConn != null){		
				myStmt=myConn.prepareStatement(query);
			
				// set params
				((PreparedStatement) myStmt).setInt(1, theStudent.getUser_id());
				((PreparedStatement) myStmt).setString(2,theStudent.getFirstName() );
				((PreparedStatement) myStmt).setString(3,theStudent.getLastName());
				
				myStmt.executeUpdate();	
				}
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	} 
	    	
	    }
	    
	    @FXML
	    void handleDeleteStudent(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	
	    	Student tempStudent = tableStudents.getSelectionModel().getSelectedItem();
	     	deleteStudent(tempStudent);
	     	
	    }
	    
	    public void deleteStudent(Student theStudent) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	PreparedStatement myStmt = null;
	    	Connection myConn =null;
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");

			try {
				myStmt = myConn.prepareStatement("delete from users where user_id=?");
				myStmt.setInt(1, theStudent.getUser_id());
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt,myConn);
			}
	    }

	    @FXML
	    void studentClearInput(ActionEvent event) {
	    	studentIdInput.clear();
	    	studentFirstNameInput.clear();
	    	studentLastNameInput.clear();
	    	errorLabel.setText("");
	    }
	   
	    @FXML
	    void handleUpdateStudent(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	Student tempStudent = tableStudents.getSelectionModel().getSelectedItem();
	    	String id=studentIdInput.getText();
	    	String fname=studentFirstNameInput.getText();
	    	String lname= studentLastNameInput.getText();
	    	tempStudent = new Student(Integer.parseInt(id),fname,lname);
	    	updateStudent(tempStudent);
	    }
	    
	    public void updateStudent(Student theStudent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		PreparedStatement myStmt = null;
		Connection myConn =null;
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");

		try {
			myStmt = myConn.prepareStatement("update users"
					+ " set first_name=?, last_name=?"
					+ " where user_id=?");

			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());		
			myStmt.setInt(3, theStudent.getUser_id());

			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,myConn);
		}
	    }

	    @FXML
	    void handleAddTech(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	String id=techIdInput.getText();
	    	String tname=techNameInput.getText();      	   
	    	String coach=techCoachInput.getText(); 
	    	String lvl=techLevelInput.getText(); 
	    	Technique tempTech= new Technique(Integer.parseInt(id),tname,coach,lvl);  
	    	if(TechValidate.ValidateTech(tempTech)== false){
	    		techErrorLabel.setText("Name, coach and level must be string");
	    	}
	    	else {
	    		addTech(tempTech);	 
	    		techErrorLabel.setText("");
	    	}
	    }
	    
	    public void addTech(Technique tech) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	PreparedStatement myStmt = null;
	    	Connection myConn =null;
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");

			try {

				myStmt = myConn.prepareStatement("insert into techniques"
						+ " (tech_id,technique, coach, level)"
						+ " values (?, ?, ?, ?)");

				myStmt.setInt(1, tech.getTech_id());
				myStmt.setString(2, tech.getTechnique());
				myStmt.setString(3, tech.getCoach());
				myStmt.setString(4, tech.getLevel());
				
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt,myConn);
			}
	    }
	    

	    @FXML
	    void handleUpdateTech(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	Technique tempTech = tableTechnique.getSelectionModel().getSelectedItem();
	    	String id=techIdInput.getText();
	    	String tname=techNameInput.getText();      	   
	    	String coach=techCoachInput.getText(); 
	    	String lvl=techLevelInput.getText(); 
	    	tempTech= new Technique(Integer.parseInt(id),tname,coach,lvl);  
	    	updateTech(tempTech);
	    	
	    }
	    public void updateTech(Technique theTechnique) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	PreparedStatement myStmt = null;
	    	Connection myConn =null;
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			try {
				myStmt = myConn.prepareStatement("update techniques"
						+ " set technique=?, coach=?, level=?"
						+ " where tech_id=?");
				
				myStmt.setString(1, theTechnique.getTechnique());
				myStmt.setString(2, theTechnique.getCoach());
				myStmt.setString(3, theTechnique.getLevel());
				myStmt.setInt(4, theTechnique.getTech_id());
				
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt,myConn);
			}
	    }
	    

	    @FXML
	    void handleDeleteTech(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	Technique tempTech = tableTechnique.getSelectionModel().getSelectedItem();
	    	deleteTech(tempTech);
	    }
	    
	    public void deleteTech(Technique theTech) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	    	PreparedStatement myStmt = null;
	    	Connection myConn =null;
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			try {
				myStmt = myConn.prepareStatement("delete from techniques where tech_id=?");			
				myStmt.setInt(1, theTech.getTech_id());
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt,myConn);
			}
	    }
	    

	    @FXML
	    void techClear(ActionEvent event){
	    	techIdInput.clear();
	    	techNameInput.clear();     	   
	    	techCoachInput.clear(); 
	    	techLevelInput.clear();
	    	techErrorLabel.setText("");
	    }


		  @FXML
		    private void handleAddClass(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

			    String tid=classTechIdInput.getText();
			    String uid=classUserIdInput.getText();
			    String cid=classIdInput.getText();
			    Classes tempClass= new Classes(Integer.parseInt(cid),Integer.parseInt(uid),Integer.parseInt(tid));
			    addClass(tempClass);
		  }
		  
		  public void addClass(Classes theClasses) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			  PreparedStatement myStmt = null;
			  Connection myConn =null;
		    	Class.forName("com.mysql.jdbc.Driver").newInstance();
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				try {
					myStmt = myConn.prepareStatement("insert into classes"
							+ " (class_id,user_id, tech_id)"
							+ " values (?, ?, ?)");

					myStmt.setInt(1, theClasses.getClass_id());
					myStmt.setInt(2, theClasses.getUser_id());
					myStmt.setInt(3, theClasses.getTech_id());
					
					myStmt.executeUpdate();			
				}
				finally {
					close(myStmt,myConn);
				}
		  }
		  
		  @FXML
		    private void handleDeleteClass(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			 Classes tempClasses = tableClasses.getSelectionModel().getSelectedItem();
			 deleteClass(tempClasses);
		  }
		  
		  public void deleteClass(Classes theClasses) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			  PreparedStatement myStmt = null;
		      Connection myConn =null;
		      Class.forName("com.mysql.jdbc.Driver").newInstance();
			  myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				try {
					myStmt = myConn.prepareStatement("delete from classes where class_id=?");
					myStmt.setInt(1, theClasses.getClassid());
					myStmt.executeUpdate();			
				}
				finally {
					close(myStmt,myConn);
				}
		  }

		 	  
		  @FXML
		    private void handleUpdateClass(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			  Classes tempClasses = tableClasses.getSelectionModel().getSelectedItem();
			  String tid=classTechIdInput.getText();
			  String uid=classUserIdInput.getText();
			  String cid=classIdInput.getText();
			  tempClasses= new Classes(Integer.parseInt(cid),Integer.parseInt(uid),Integer.parseInt(tid));
			    
			  updateClass(tempClasses);
		  }
		  
		  public void updateClass( Classes theClasses) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			  PreparedStatement myStmt = null;
			  Connection myConn =null;
		      Class.forName("com.mysql.jdbc.Driver").newInstance();
			  myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
				try {
					myStmt = myConn.prepareStatement("update classes"
							+ " set user_id=?, tech_id=?"
							+ " where class_id=?");

					myStmt.setInt(1, theClasses.getUser_id());
					myStmt.setInt(2, theClasses.getTech_id());		
					myStmt.setInt(3, theClasses.getClass_id());

					myStmt.executeUpdate();			
				}
				finally {
					close(myStmt,myConn);
				}
		  }

		  @FXML
		    private void classClearInput(ActionEvent event){
			  classUserIdInput.clear();
			  classTechIdInput.clear();
			  classIdInput.clear();
			  
		  }
	    
	    @FXML 
	    void handleClose(){
	    	Platform.exit();
	    }
	    
	@Override
	public void setParentScreen(ScreensController screenPage) {
		 myController = screenPage;
		
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

private void close(Statement myStmt, Connection myConn) throws SQLException {
	close(myConn, myStmt, null);		
}

private void close(Statement myStmt) throws SQLException {
	close(null, myStmt, null);		
}


}
