package database;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UpdateStudentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;


	private StudentDAO studentDAO;

	private DatabaseApp databaseApp;

	private Student previousStudent = null;
	private boolean updateMode = false;

	public UpdateStudentDialog(DatabaseApp theDatabaseApp,
			StudentDAO theStudentDAO, Student thePreviousStudent, boolean theUpdateMode) {
		this();
		studentDAO = theStudentDAO;
		databaseApp = theDatabaseApp;

		previousStudent = thePreviousStudent;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Student");
			
			populateGui(previousStudent);
		}
	}


	private void populateGui(Student theStudent) {

		firstNameTextField.setText(theStudent.getFirstName());
		lastNameTextField.setText(theStudent.getLastName());
				
	}

	public UpdateStudentDialog(DatabaseApp theDatabaseApp,
			StudentDAO theStudentDAO) {
		this(theDatabaseApp, theStudentDAO, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public UpdateStudentDialog() {
		setTitle("Add Student");
		setBounds(100, 100, 450, 234);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "2, 2, right, default");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "4, 2, fill, default");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "2, 4, right, default");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "4, 4, fill, default");
			lastNameTextField.setColumns(10);
		}
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveStudent();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	

	protected void saveStudent() {

		// get the employee info from gui
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();


		Student tempSt = null;

		if (updateMode) {
			tempSt = previousStudent;
			
			tempSt.setLastName(lastName);
			tempSt.setFirstName(firstName);
			
			
		} else {
			tempSt = new Student(lastName, firstName);
		}

		try {
			// save to the database
			if (updateMode) {
				studentDAO.updateStudent(tempSt);
			} else {
				studentDAO.addStudent(tempSt);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			databaseApp.refreshStudentsView();

			// show success message
			JOptionPane.showMessageDialog(databaseApp,
					"Employee saved succesfully.", "Employee Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(databaseApp,
					"Error saving employee: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
