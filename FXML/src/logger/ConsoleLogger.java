package logger;

import crawler.Crawler.STATUS;
import model.StudentModel;

public class ConsoleLogger implements Logger {
	  public ConsoleLogger() {}
	  
    @Override
    public void log(STATUS status, StudentModel student) {
        switch (status) {
            case ADDED:
                System.out.println("[  ADDED  ]: " + student);
                break;
            case REMOVED:
                System.out.println("[  REMOVED  ]: " + student);
                break;
		default:
			break;
        }

    }

    @Override
    public void log(STATUS status, int iteration) {
        switch (status) {
            case I_STARTED:
                System.out.println("   ITERATION STARTED: " + iteration);
                break;
            case I_COMPLETED:
                System.out.println("   ITERATION COMPLETED: " + iteration);
                break;
		default:
			break;
        }
    }

    @Override
    public void log(STATUS status) {
        System.out.println("    UNCHANGED    ");
    }

}
