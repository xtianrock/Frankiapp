package com.appcloud.frankiapp.POJO;

import com.appcloud.frankiapp.Utils.Configuration;

/**
 * Created by cristian on 27/04/2016.
 */
public class Oferta {


    public final static String PRESENTADA = "PRESENTADA";
    public final static String EN_PROCESO = "EN_PROCESO";
    public final static String KO = "KO";
    public final static String FINALIZADA = "FINALIZADA";


    int codOferta;
    int codCliente;
    String estado;
    long fechaOferta;
    long fechaFirma;
    long fechaKO;
    long fechaOK;
    float pagoInicial;
    float cuotaMenseual;
    float comisionBaseTotal;
    float puntosTotal;
    float commisionEmpresa;

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

    public float getPagoInicial() {
        return pagoInicial;
    }

    public void setPagoInicial(float pagoInicial) {
        this.pagoInicial = pagoInicial;
    }

    public float getCuotaMenseual() {
        return cuotaMenseual;
    }

    public void setCuotaMenseual(float cuotaMenseual) {
        this.cuotaMenseual = cuotaMenseual;
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

    public float getCommisionEmpresa() {
        return commisionEmpresa;
    }

    public void setCommisionEmpresa(float commisionEmpresa) {
        this.commisionEmpresa = commisionEmpresa;
    }
}
