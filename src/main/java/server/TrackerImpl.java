package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackerImpl extends UnicastRemoteObject implements TrackerInterface {

    //All API calls uses this base URL
    private final String BASE_URL = "https://disease.sh/v3/covid-19";

    protected TrackerImpl() throws RemoteException {
        super();
    }

    //TODO
    @Override
    public HashMap<String, Object> getActiveCases() throws RemoteException {
        return null;
    }

    //Returns cumulative statistics
    @Override
    public HashMap<String, Object> getTotal() throws RemoteException {
        Gson gson = new Gson();
        try {
            //Building query string
            String response = run(BASE_URL + "/all");

            //Building JSON type token
            Type type = new TypeToken<HashMap<String, Object>>(){}.getType();

            return gson.fromJson(response, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Returns world data, sorted as specified
    @Override
    public ArrayList<HashMap<String, Object>> getWorld(String sort) throws RemoteException {
        Gson gson = new Gson();
        try {
            //Building query string
            String response = run(BASE_URL + "/countries?sort=" + sort);

            //Building JSON type token
            Type type = new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType();

            return gson.fromJson(response, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns specified continent data
    @Override
    public HashMap<String, Object> getContinent(String continent) throws RemoteException {
        Gson gson = new Gson();
        try {
            //Building query string
            String response = run(BASE_URL + "/continents/" + continent);

            //Building JSON type token
            Type type = new TypeToken<HashMap<String, Object>>(){}.getType();

            return gson.fromJson(response, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns specified country data
    @Override
    public HashMap<String, Object> getCountry(String country) throws RemoteException {
        Gson gson = new Gson();
        try {
            //Building query string
            String response = run(BASE_URL + "/countries/" + country);

            //Building JSON type token
            Type type = new TypeToken<HashMap<String, Object>>(){}.getType();

            return gson.fromJson(response, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Returns world data as JSON String
    @Override
    public String getWorldJson() throws RemoteException {
        Gson gson = new Gson();
        try {
            //Building query string
            String response = run(BASE_URL + "/countries");

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Handles the API call and return the responseBody as String
    String run(String url) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //if statement to avoid nullPointerException
            if (response.body() != null) {
                return response.body().string();
            }
            return null;
        }
    }
}
