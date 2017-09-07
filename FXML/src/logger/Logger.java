package logger;

import crawler.Crawler.STATUS;
import controller.MainScreenController;
import model.StudentModel;

public interface Logger {

    public void log(STATUS status, StudentModel student);

    public void log(STATUS status, int iteracja);

    public void log(STATUS status);

	
}
