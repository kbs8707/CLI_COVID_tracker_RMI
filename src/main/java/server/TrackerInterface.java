package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface TrackerInterface extends Remote{
    public HashMap<String, Object> getActiveCases() throws RemoteException;
    public HashMap<String, Object> getTotal() throws RemoteException;
    public ArrayList<HashMap<String, Object>> getWorld(String sort) throws RemoteException;
    public HashMap<String, Object> getContinent(String continent) throws RemoteException;
    public HashMap<String, Object> getCountry(String country) throws RemoteException;
    public String getWorldJson() throws RemoteException;
}
