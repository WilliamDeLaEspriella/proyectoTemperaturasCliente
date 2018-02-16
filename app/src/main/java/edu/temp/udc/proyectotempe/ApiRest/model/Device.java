package edu.temp.udc.proyectotempe.ApiRest.model;

/**
 * Created by Usuario on 16/11/2017.
 */

public class Device {
    private String _id;
    private String name;
    private String nombre;
    private Dato dato;

    public Device(String _id, String name, String nombre, Dato dato) {
        this._id = _id;
        this.name = name;
        this.nombre = nombre;
        this.dato = dato;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Dato getDato() {
        return dato;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }
}
