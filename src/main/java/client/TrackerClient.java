package client;

import server.TrackerInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class TrackerClient {

    public static void main(String[] args) throws IOException {
        TrackerClient t = new TrackerClient();

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(8088);

            // Looking up the registry for the remote object
            TrackerInterface stub = (TrackerInterface) registry.lookup("TrackerInterface");

            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to the COVID tracker app, enter /help for help, enter /quit to exit");

            //Infinite loop until /quit command is entered
            while (true) {
                System.out.println("-----------------------------------------");
                System.out.print("Enter:");
                String in = sc.nextLine();

                //Check if user wants to exit
                if (in.contains("/quit")) {
                    System.exit(0);
                }

                //Check if user issued a command
                t.command(in, stub);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    //Handles command issued by the user
    void command(String input, TrackerInterface stub) throws RemoteException {
        if (input.contains("/help")) {
            displayHelp();
        }
        else if (input.contains("/total")) {
            displayTotal(stub);
        }
        else if (input.contains("/continent")) {
            displayContinent(stub, extractArg(input));
        }
        else if (input.contains("/country")) {
            displayCountry(stub, extractArg(input));
        }
        else if (input.contains("/world")) {
            if (input.contains("json")) {
                displayWorldJson(stub);
            }
            else {
                displayWorld(stub, extractArg(input));
            }
        }
        else {
            System.out.println("Not valid input, please try again");
        }
    }

    void displayHelp() {
        System.out.println("/total | obtains up-to-date COVID information");
        System.out.println("/continent <continent-name> | obtains all country information for specified continent");
        System.out.println("/country <country-name> | obtains information for specified country");
        System.out.println("/world <sort-type> (e.g. cases, todayCases, deaths, recovered, active) | obtains information for the world, sorted by specified criteria");
        System.out.println("/world json | obtains information for the world, displayed in JSON format");
        System.out.println("/quit | exits the program");
    }

    //Handles returned data from the server
    void displayTotal(TrackerInterface stub) throws RemoteException {
        HashMap<String, Object> res = stub.getTotal();
        System.out.println("Total cases: " + (new BigDecimal(Double.toString((Double) res.get("cases")))).toPlainString() );
        System.out.println("Today cases: " + (new BigDecimal(Double.toString((Double) res.get("todayCases")))).toPlainString());
        System.out.println("Total deaths: " + (new BigDecimal(Double.toString((Double) res.get("deaths")))).toPlainString());
        System.out.println("Active: " + (new BigDecimal(Double.toString((Double) res.get("active")))).toPlainString());
    }

    //Handles returned data from the server
    void displayContinent(TrackerInterface stub, String continent) throws RemoteException{
        HashMap<String, Object> res = stub.getContinent(continent);
        System.out.println("Continent of " + continent.toUpperCase());
        System.out.println("Continent cases: " + (new BigDecimal(Double.toString((Double) res.get("cases")))).toPlainString() );
        System.out.println("Continent Today cases: " + (new BigDecimal(Double.toString((Double) res.get("todayCases")))).toPlainString());
        System.out.println("Continent Total deaths: " + (new BigDecimal(Double.toString((Double) res.get("deaths")))).toPlainString());
        System.out.println("Continent Active: " + (new BigDecimal(Double.toString((Double) res.get("active")))).toPlainString());

    }

    //Handles returned data from the server
    void displayCountry(TrackerInterface stub, String country) throws RemoteException{
        HashMap<String, Object> res = stub.getCountry(country);
        System.out.println("Country of " + country.toUpperCase());
        System.out.println("Country cases: " + (new BigDecimal(Double.toString((Double) res.get("cases")))).toPlainString() );
        System.out.println("Country Today cases: " + (new BigDecimal(Double.toString((Double) res.get("todayCases")))).toPlainString());
        System.out.println("Country Total deaths: " + (new BigDecimal(Double.toString((Double) res.get("deaths")))).toPlainString());
        System.out.println("Country Active: " + (new BigDecimal(Double.toString((Double) res.get("active")))).toPlainString());

    }

    //Handles returned data from the server
    void displayWorld(TrackerInterface stub, String sort) throws RemoteException {
        ArrayList<HashMap<String, Object>> res = stub.getWorld(sort);
        System.out.println("Display data by country, sorted by " + sort);
        for (HashMap<String, Object> re : res) {
            System.out.println("-----------------------------------------");
            System.out.println(re.get("country"));
            System.out.println("Total cases: " + (new BigDecimal(Double.toString((Double) re.get("cases")))).toPlainString());
            System.out.println("Today cases: " + (new BigDecimal(Double.toString((Double) re.get("todayCases")))).toPlainString());
            System.out.println("Total deaths: " + (new BigDecimal(Double.toString((Double) re.get("deaths")))).toPlainString());
            System.out.println("Active: " + (new BigDecimal(Double.toString((Double) re.get("active")))).toPlainString());
        }
    }

    //Handles returned data from the server
    void displayWorldJson(TrackerInterface stub) throws RemoteException {
        String res = stub.getWorldJson();
        System.out.println("Display data by country in JSON format");
        System.out.println(res);
    }

    //Extracts the second argument
    String extractArg(String input) {
        String[] strArr = input.split(" ",2);
        if (strArr.length < 1) {
            return "";
        }
        return strArr[1];
    }

}
