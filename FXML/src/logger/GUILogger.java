package logger;

import crawler.Crawler.STATUS;
import controller.MainScreenController;
import controller.TextAreaController;
import model.StudentModel;

public class GUILogger implements Logger {

    private MainScreenController controller;
   

 

    public GUILogger (MainScreenController mainScreenController){
    	this.controller = mainScreenController;
    }
    @Override
    public void log(STATUS status, StudentModel student) {

        switch (status) {
            case ADDED:           
                controller.setTextArea("Status: [ ADDED ]: " + student + "\n");
                controller.addRow(student);
                controller.updateChartAdd(student.getMark());
                break;
            case REMOVED:
                controller.setTextArea("Status: [ REMOVED ]: " + student + "\n");
                controller.deleteRow(student);
                controller.updateChartRemove(student.getMark());
                break;
            case UNCHANGED:
                controller.setTextArea("Status: [ UNCHANGED ]\n");
                break;
		default:
			break;
        }
    }

    @Override
    public void log(STATUS status, int iteracja) {
    }

    @Override
    public void log(STATUS status) {
        StudentModel student = null;
        this.log(status, student);
    }
}
