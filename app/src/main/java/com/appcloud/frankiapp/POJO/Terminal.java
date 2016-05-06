package com.appcloud.frankiapp.POJO;

/**
 * Created by cristian on 27/04/2016.
 */
public class Terminal {

    int codTerminal;
    String descripcion;
    String color;
    String lte;
    String sim;


    public Terminal() {
    }

    public int getCodTerminal() {
        return codTerminal;
    }

    public void setCodTerminal(int codTerminal) {
        this.codTerminal = codTerminal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }
}
