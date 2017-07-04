package main;

import crawler.Crawler;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.naming.NamingException;
import java.rmi.RMISecurityManager;
import interfaces.RMICrawlerProxyInterface;
import java.util.Scanner;
import server.RMICrawlerProxy;

public class Main {

    private static final int PORT = 1099;

    public static void main(String[] args) throws NamingException, RemoteException, InterruptedException, AlreadyBoundException {
      /*  if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/

        String name = "rmi://"+PORT+"/CrawlerProxy";		//pod tak¹ nazw¹ zapisany jest obiekt zdalny w rejestrze
        Registry registry = LocateRegistry.createRegistry(PORT); // uruchomienie rejestru dla rmi na wybranym porcie

        try {
            RMICrawlerProxy crawler = new RMICrawlerProxy(); // tworzenie obiektu  na serwerze
            //RMICrawlerProxy stub = (RMICrawlerProxy) UnicastRemoteObject.exportObject(crawler, 0); //namiastka
            registry.bind(name, crawler); // bindowanie obiektu 
            System.out.println("Server ready. \n");

            System.out.println("Type 'exit' to exit server.");
         
            //A simple text scanner which can parse primitive types and strings using regular expressions
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    if (scanner.hasNextLine()) {
                        if ("exit".equals(scanner.nextLine())) {
                            break;
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("\nRemoteException was thrown.");
        } finally {
            UnicastRemoteObject.unexportObject(registry, true); // zwalnianie rejestru
            System.out.println("Server stopped."); // komunikat zakonczenia
        }
    }
}
