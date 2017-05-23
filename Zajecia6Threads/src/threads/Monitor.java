package threads;

import static threads.Crawler.STATUS.ADDED;
import static threads.Crawler.STATUS.ITER_COMPLETED;
import static threads.Crawler.STATUS.ITER_STARTED;

import static threads.Crawler.STATUS.REMOVED;
import static threads.Crawler.STATUS.UNCHANGED;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
	private final List<String> links;
	private final List<MyThread> threads;
	private final int threadsNumber;
	public boolean condition;
	
	public static Logger[] loggers= new Logger[]{
			new ConsoleLogger()
	};

	
	public Monitor(){
	links = new ArrayList<>();
	threads= new ArrayList<>();
	links.add("students.txt");
	links.add("students2.txt");
	links.add("students3.txt");
	
	threadsNumber=3;
	}
	
	  public void cancel(){
	        try {
	            condition = false;
	            for(MyThread t : threads){
	                t.postCancel();
	                t.join();
	            }
	         
	      }catch( InterruptedException e) {
	         System.out.println("Interrupted thread!\n");
	      } 
	    }
	
	  
    public void iterationStartedEvent(Logger listener, int i){
	listener.log(ITER_STARTED,i);
    }
    
    public void iterationComplitedEvent(Logger listener, int i){
	listener.log(ITER_COMPLETED,i);
    }
    
    public void studentAddedEvent(Logger listener, Student s){
	listener.log(ADDED, s);
    }
	
    public void studentRemovedEvent(Logger listener, Student s){
	listener.log(REMOVED, s);
    }
    
    public void unchangedEvent(Logger listener){
	listener.log(UNCHANGED);
    }
		
	
	public void start() throws MonitorException{
		condition=true;
		int i=0;
		
		while(condition){
			
			  if(i == threadsNumber-1){
	               condition = false;
	               break;
	            }
			  if(links.size() < threadsNumber){
	                throw new MonitorException("Zbyt duza ilosc watkow!");
	            }
			 String link = links.get(i);
	            int index = i+1;
	            Crawler crawler = new Crawler(this);
	            
	            crawler.addNewStudentListener(new ParallelLogger(loggers)); 
	            crawler.addRemoveStudentListener(new ParallelLogger(loggers)); 
	            crawler.addUnchangedListener(new ParallelLogger(loggers));
	            crawler.addIterationStartedListener(new ParallelLogger(loggers));
	            crawler.addIterationComplitedListener(new ParallelLogger(loggers));
	            String threadName = "THREAD "+index;
	            
	            MyThread thread= new MyThread(threadName, crawler, link);
	            threads.add(thread);
	            thread.start();
	            i++;
		}
	}

	
	
}
