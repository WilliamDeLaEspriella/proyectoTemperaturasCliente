package edu.temp.udc.proyectotempe.ApiRest.model;

import java.util.Date;

/**
 * Created by Usuario on 16/11/2017.
 */

public class Dato {
    private Double temperatura;
    private Date date;

    public Dato(Double temperatura, Date date) {
        this.temperatura = temperatura;
        this.date = date;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
