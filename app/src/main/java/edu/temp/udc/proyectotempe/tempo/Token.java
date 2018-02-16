package edu.temp.udc.proyectotempe.tempo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;

/**
 * Created by Usuario on 14/11/2017.
 */

public class Token extends Observable {
    String token;
    List<UserDevice> userDevices;
    private static final Token ourInstance = new Token();

   public static Token getInstance() {
        return ourInstance;
    }

    private Token() {
        userDevices= new ArrayList<>();
        token=" ";

    }
    public void noty(JSONObject dato){
        setChanged();
        notifyObservers(dato);
    }

    public List<UserDevice> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
