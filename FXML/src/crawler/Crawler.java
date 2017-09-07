package crawler;

import model.StudentModel;
import controller.MainScreenController;
import student.StudentsListener;
import student.StudentsParser;
import logger.Logger;
import static crawler.Crawler.STATUS.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Crawler {

    private final List<Logger> iterationStartedListeners = new ArrayList<>();
    private final List<Logger> iterationComplitedListeners = new ArrayList<>();
    private final List<Logger> addRemoveStudentListeners = new ArrayList<>();
    private final List<Logger> addUnchangedListeners = new ArrayList<>();
    private final List<Logger> addNewStudentListeners = new ArrayList<>();
    private List<StudentModel> previousStudents;
    private List<StudentModel> currentStudents;
    private final String adress = "students.txt";
    private final MainScreenController control;

    public enum STATUS {
        STARTING,
        I_STARTED,
        I_COMPLETED,
        ADDED,
        REMOVED,
        UNCHANGED
    }

    public Crawler(MainScreenController controller) {
        this.control = controller;   
        currentStudents = new ArrayList<>();
    }

    public void addIterationStartedListener(Logger listener) {
        iterationStartedListeners.add(listener);
    }

    public void removeIterationStartedListener(Logger listener) {
        iterationStartedListeners.remove(listener);
    }

    public void addIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.add(listener);
    }

    public void removeIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.remove(listener);
    }

    public void addNewStudentListener(Logger listener) {
        addNewStudentListeners.add(listener);
    }

    public void removeNewStudentListener(Logger listener) {
        addNewStudentListeners.remove(listener);
    }

    public void addRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.add(listener);
    }

    public void removeRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.remove(listener);
    }

    public void addUnchangedListener(Logger listener) {
        addUnchangedListeners.add(listener);
    }

    public void removeUnchangedListener(Logger listener) {
        addUnchangedListeners.remove(listener);
    }

    public void run() {
        StudentsListener handler;
       
        int i=1;
       // for (int i = 1;; ++i) {
        while(true){
            for (Logger l : iterationStartedListeners) {
                l.log(I_STARTED, i);
            }
            File f = new File(adress);
            currentStudents = new ArrayList<>();
            try { 
            	
                currentStudents = StudentsParser.parse(f);
            } catch (IOException ex) {
                System.out.println("Studentsparser exception");
            }

            if (previousStudents == null) { 
                if (!currentStudents.isEmpty()) {
                    for (Logger l : addNewStudentListeners) {
                        for (StudentModel s : currentStudents) {
                            l.log(ADDED, s);
                        }
                    }
                }
            } else if (currentStudents.isEmpty()) { 
                if (!previousStudents.isEmpty()) {
                    for (Logger l : addRemoveStudentListeners) {
                        for (StudentModel s : previousStudents) {
                            l.log(REMOVED, s);
                        }
                    }
                }
            } else if (previousStudents.size() > currentStudents.size()) {
                
                handler = new StudentsListener();
                List<StudentModel> st = handler.removed(previousStudents, currentStudents);
                for (StudentModel s : st) {
                    for (Logger l : addRemoveStudentListeners) {
                        l.log(REMOVED, s);
                    }
                }
            } else if (previousStudents.size() < currentStudents.size()) {
                //dodano
                handler = new StudentsListener();
                List<StudentModel> st = handler.added(previousStudents, currentStudents);

                for (StudentModel s : st) {
                    for (Logger l : addNewStudentListeners) {
                        l.log(ADDED, s);
                    }
                }
            } else {
                // nie zmodyfikowano  
                for (Logger l : addUnchangedListeners) {
                    l.log(UNCHANGED);
                }
            }

            previousStudents = currentStudents;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            i++;
            for (Logger l : iterationComplitedListeners) {
                l.log(I_COMPLETED, i);
            }

        }
    }
}//class

