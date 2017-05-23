package threads;

import threads.Crawler.STATUS;

public class ConsoleLogger implements Logger{
     public ConsoleLogger() {}

   @Override
   public void log(STATUS status, Student student){
       switch(status){
            case ADDED:
                System.out.println(Thread.currentThread().getName()+"[ADDED]: "+student);
                break;
            case REMOVED:
                System.out.println(Thread.currentThread().getName()+"[REMOVED]: "+student);
                break;
        }
       
   }
   @Override
   public void log(STATUS status, int iteration){
       switch(status){
            case ITER_STARTED:
                System.out.println(Thread.currentThread().getName()+"[ITERATION STARTED]: "+iteration);
                break;
            case ITER_COMPLETED:
                System.out.println(Thread.currentThread().getName()+"[ITERATION COMPLETED]: "+iteration);
                break;
        }
   }
   @Override
   public void log(STATUS status){
       System.out.println(Thread.currentThread().getName()+"[UNCHANGED]");
   }

}
