package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TrackerServer {
    public static void main(String[] args) {
        try {
            TrackerInterface ti = new TrackerImpl();

            Registry registry = LocateRegistry.createRegistry(8088);

            registry.bind("TrackerInterface", ti);

            System.err.println("Server ready");

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
