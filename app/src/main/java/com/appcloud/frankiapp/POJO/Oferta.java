package com.appcloud.frankiapp.POJO;

import com.appcloud.frankiapp.Utils.Configuration;

/**
 * Created by cristian on 27/04/2016.
 */
public class Oferta {



    int codOferta;
    int codCliente;
    String estado;
    long fechaOferta;
    long fechaFirma;
    long fechaKO;
    long fechaOK;
    float pagoInicialTarifa;
    float cuotaTarifa;
    float pagoInicialTerminal;
    float cuotaTerminal;
    float comisionBaseTotal;
    float puntosTotal;
    float puntosLineas;
    float commisionEmpresa;
    int lineasMoviles;
    String poblacion;
    String nombre;
    String apellidos;

    public Oferta() {
        this.estado = Configuration.BORRADOR;
    }

    public int getCodOferta() {
        return codOferta;
    }

    public void setCodOferta(int codOferta) {
        this.codOferta = codOferta;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getFechaOferta() {
        return fechaOferta;
    }

    public void setFechaOferta(long fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    public long getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(long fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public long getFechaKO() {
        return fechaKO;
    }

    public void setFechaKO(long fechaKO) {
        this.fechaKO = fechaKO;
    }

    public long getFechaOK() {
        return fechaOK;
    }

    public void setFechaOK(long fechaOK) {
        this.fechaOK = fechaOK;
    }

    public float getPagoInicialTarifa() {
        return pagoInicialTarifa;
    }

    public void setPagoInicialTarifa(float pagoInicialTarifa) {
        this.pagoInicialTarifa = pagoInicialTarifa;
    }

    public float getCuotaTarifa() {
        return cuotaTarifa;
    }

    public void setCuotaTarifa(float cuotaTarifa) {
        this.cuotaTarifa = cuotaTarifa;
    }

    public float getPagoInicialTerminal() {
        return pagoInicialTerminal;
    }

    public void setPagoInicialTerminal(float pagoInicialTerminal) {
        this.pagoInicialTerminal = pagoInicialTerminal;
    }

    public float getCuotaTerminal() {
        return cuotaTerminal;
    }

    public void setCuotaTerminal(float cuotaTerminal) {
        this.cuotaTerminal = cuotaTerminal;
    }

    public float getComisionBaseTotal() {
        return comisionBaseTotal;
    }

    public void setComisionBaseTotal(float comisionBaseTotal) {
        this.comisionBaseTotal = comisionBaseTotal;
    }

    public float getPuntosTotal() {
        return puntosTotal;
    }

    public void setPuntosTotal(float puntosTotal) {
        this.puntosTotal = puntosTotal;
    }

    public float getPuntosLineas() {
        return puntosLineas;
    }

    public void setPuntosLineas(float puntosLineas) {
        this.puntosLineas = puntosLineas;
    }

    public float getCommisionEmpresa() {
        return commisionEmpresa;
    }

    public void setCommisionEmpresa(float commisionEmpresa) {
        this.commisionEmpresa = commisionEmpresa;
    }

    public int getLineasMoviles() {
        return this.lineasMoviles;
    }

    public void setLineasMoviles(int lineasMoviles) {
        this.lineasMoviles = lineasMoviles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
}
