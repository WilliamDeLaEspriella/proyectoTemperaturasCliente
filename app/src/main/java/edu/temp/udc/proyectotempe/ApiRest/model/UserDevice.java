package edu.temp.udc.proyectotempe.ApiRest.model;

import java.io.Serializable;

/**
 * Created by Usuario on 16/11/2017.
 */

public class UserDevice implements Serializable {
    private String _id;
    private String nombre;
    private String apellido;
    private String edad;
    private String historial;
    private String device;
    private String dato;

    public UserDevice(String _id, String nombre, String apellido, String edad, String historial, String device, String dato) {
        this._id = _id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.historial = historial;
        this.device = device;
        this.dato = dato;
    }
    public UserDevice() {
        this._id = " ";
        this.nombre =" ";
        this.apellido =" ";
        this.edad =" ";
        this.historial =" ";
        this.device = " ";
        this.dato =" ";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
