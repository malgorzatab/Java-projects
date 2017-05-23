package threads;

import threads.Crawler.STATUS;

public class ParallelLogger implements Logger{
	 private Logger[] loggers;

	 
	 public ParallelLogger(Logger[] loggers){
		this.loggers=loggers;
	}
	 
	@Override
	public void log(STATUS status, Student student) {
		for(Logger l: loggers){
            l.log(status,student);
        }
		
	}

	@Override
	public void log(STATUS status, int iteracja) {
		// TODO Auto-generated method stub
		for(Logger l: loggers){
            l.log(status,iteracja);
        }
		
	}

	@Override
	public void log(STATUS status) {
		// TODO Auto-generated method stub
		for(Logger l: loggers){
            l.log(status);
        }
		
	}
	
	
	

}
