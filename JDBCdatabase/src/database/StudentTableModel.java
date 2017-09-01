package database;

import java.util.List;


import javax.swing.table.AbstractTableModel;



class StudentTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;

	private String[] columnNames = { "Last Name", "First Name" };
	private List<Student> students;

	public StudentTableModel(List<Student> theStudents) {
		students = theStudents;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Student tempStudent = students.get(row);

		switch (col) {
		case LAST_NAME_COL:
			return tempStudent.getLastName();
		case FIRST_NAME_COL:
			return tempStudent.getFirstName();
		case OBJECT_COL:
			return tempStudent;
		default:
			return tempStudent.getLastName();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
