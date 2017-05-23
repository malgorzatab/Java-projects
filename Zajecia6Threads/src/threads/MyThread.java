package threads;

public class MyThread extends Thread{
	
	  private final Crawler crawler;
	  private Thread thread;
	  private String adress;//="C:\\Users\\Gosia\\Desktop\\Ma³gosi\\Java\\Zajecia6Threads\\Students.txt";
	  private final String threadName;
	

	public MyThread(String threadName, Crawler crawler, String adress) {
		this.crawler = crawler;
        this.adress  = adress;
        this.threadName = threadName;
	}


    @Override
    public void run() {
        crawler.run(this.adress);
    }

    @Override
    public void start() {
      if (thread == null) {
         thread = new Thread(this, threadName);
         thread.start();
      }
    }
	

	public void postCancel() {
		crawler.postCancel();
		
	}



}
