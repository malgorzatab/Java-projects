package database;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class DatabaseApp extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private StudentDAO studentDAO;
	private JPanel panel_1;
	private JButton btnAddStudent;
	private JButton btnUpdateStudent;
	private JButton btnDeleteStudent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseApp frame = new DatabaseApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatabaseApp() {
		
		// create the DAO
				try {
					studentDAO = new StudentDAO();
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
		setTitle("Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterLastName = new JLabel("Enter last name");
		panel.add(lblEnterLastName);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		btnSearch = new JButton("Search");
	

	btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// Get last name from the text field

			// Call DAO and get employees for the last name

			// If last name is empty, then get all employees

			// Print out employees				
			
			try {
				String lastName = lastNameTextField.getText();

				List<Student> students = null;

				if (lastName != null && lastName.trim().length() > 0) {
					students = studentDAO.searchStudents(lastName);
				} else {
					students = studentDAO.getAllStudents();
				}
				
				// create the model and update the "table"
				StudentTableModel model = new StudentTableModel(students);
				
				table.setModel(model);
				
				/*
				for (Employee temp : employees) {
					System.out.println(temp);
				}
				*/
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(DatabaseApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
			}
			
		}
	});
	panel.add(btnSearch);
	
	scrollPane = new JScrollPane();
	contentPane.add(scrollPane, BorderLayout.CENTER);
	
	table = new JTable();
	scrollPane.setViewportView(table);
	
	panel_1 = new JPanel();
	contentPane.add(panel_1, BorderLayout.SOUTH);
	
	btnAddStudent = new JButton("Add Student");
	btnAddStudent.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// create dialog
			AddStudentDialog dialog = new AddStudentDialog(DatabaseApp.this, studentDAO);

			// show dialog
			dialog.setVisible(true);
		}
	});
	panel_1.add(btnAddStudent);
	
	btnUpdateStudent = new JButton("Update Student");
	btnUpdateStudent.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			// get the selected item
			int row = table.getSelectedRow();
			
			// make sure a row is selected
			if (row < 0) {
				JOptionPane.showMessageDialog(DatabaseApp.this, "You must select an student", "Error",
						JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
			// get the current employee
			Student tempStudent = (Student) table.getValueAt(row, StudentTableModel.OBJECT_COL);
			
			// create dialog
			UpdateStudentDialog dialog = new UpdateStudentDialog(DatabaseApp.this,studentDAO, 
														tempStudent, true);

			// show dialog
			dialog.setVisible(true);
		
		}
	});
	panel_1.add(btnUpdateStudent);
	
	btnDeleteStudent = new JButton("Delete Student");
	btnDeleteStudent.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try {
				// get the selected row
				int row = table.getSelectedRow();

				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(DatabaseApp.this, 
							"You must select a student", "Error", JOptionPane.ERROR_MESSAGE);				
					return;
				}

				// prompt the user
				int response = JOptionPane.showConfirmDialog(
						DatabaseApp.this, "Delete this student?", "Confirm", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response != JOptionPane.YES_OPTION) {
					return;
				}

				// get the current employee
				Student tempStudent = (Student) table.getValueAt(row, StudentTableModel.OBJECT_COL);

				// delete the employee
				studentDAO.deleteStudent(tempStudent.getId());

				// refresh GUI
				refreshStudentsView();

				// show success message
				JOptionPane.showMessageDialog(DatabaseApp.this,
						"Student deleted succesfully.", "Student Deleted",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception exc) {
				JOptionPane.showMessageDialog(DatabaseApp.this,
						"Error deleting student: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			
		}
	});
	panel_1.add(btnDeleteStudent);
	
}
	
	public void refreshStudentsView() {

		try {
			List<Student> employees = studentDAO.getAllStudents();

			// create the model and update the "table"
			StudentTableModel model = new StudentTableModel(employees);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
