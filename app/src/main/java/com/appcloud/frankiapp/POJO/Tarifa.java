package com.appcloud.frankiapp.POJO;

/**
 * Created by cristian on 29/04/2016.
 */
public class Tarifa {

    public static final String ADSL= "ADSL";
    public static final String FIBRA= "FIBRA";
    public static final String MOVIL= "MOVIL";
    public static final String ONE= "FIBRA-MOVIL";
    public static final String[] TIPOSCONVERGENCIA = {"MINI", "S", "M"," L", "XL", "XXL"};

    int codTarifa;
    String planPrecios;
    String tipoPlan;
    String planPrecioTerminal;
    String desCorta;
    String desLarga;
    float precioConTerminal;
    float precioSinTerminal;
    float precioSinConv;
    float precioConvXXL;
    float precioConvXL;
    float precioConvL;
    float precioConvM;
    float precioConvS;
    float precioConvMINIS;
    float costeAlta;
    float comisionBase;
    float comisionExtra;
    float comisionPorta;


    public Tarifa() {
    }

    public int getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(int codTarifa) {
        this.codTarifa = codTarifa;
    }

    public String getPlanPrecios() {
        return planPrecios;
    }

    public void setPlanPrecios(String planPrecios) {
        this.planPrecios = planPrecios;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public String getDesLarga() {
        return desLarga;
    }

    public String getPlanPrecioTerminal() {
        return planPrecioTerminal;
    }

    public void setPlanPrecioTerminal(String planPrecioTerminal) {
        this.planPrecioTerminal = planPrecioTerminal;
    }

    public void setDesLarga(String desLarga) {
        this.desLarga = desLarga;
    }

    public String getDesCorta() {
        return desCorta;
    }

    public void setDesCorta(String desCorta) {
        this.desCorta = desCorta;
    }

    public float getPrecioConTerminal() {
        return precioConTerminal;
    }

    public void setPrecioConTerminal(float precioConTerminal) {
        this.precioConTerminal = precioConTerminal;
    }

    public float getPrecioSinTerminal() {
        return precioSinTerminal;
    }

    public void setPrecioSinTerminal(float precioSinTerminal) {
        this.precioSinTerminal = precioSinTerminal;
    }

    public float getPrecioSinConv() {
        return precioSinConv;
    }

    public void setPrecioSinConv(float precioSinConv) {
        this.precioSinConv = precioSinConv;
    }

    public float getPrecioConvXXL() {
        return precioConvXXL;
    }

    public void setPrecioConvXXL(float precioConvXXL) {
        this.precioConvXXL = precioConvXXL;
    }

    public float getPrecioConvXL() {
        return precioConvXL;
    }

    public void setPrecioConvXL(float precioConvXL) {
        this.precioConvXL = precioConvXL;
    }

    public float getPrecioConvL() {
        return precioConvL;
    }

    public void setPrecioConvL(float precioConvL) {
        this.precioConvL = precioConvL;
    }

    public float getPrecioConvM() {
        return precioConvM;
    }

    public void setPrecioConvM(float precioConvM) {
        this.precioConvM = precioConvM;
    }

    public float getPrecioConvS() {
        return precioConvS;
    }

    public void setPrecioConvS(float precioConvS) {
        this.precioConvS = precioConvS;
    }

    public float getPrecioConvMINIS() {
        return precioConvMINIS;
    }

    public void setPrecioConvMINIS(float precioConvMINIS) {
        this.precioConvMINIS = precioConvMINIS;
    }

    public float getCosteAlta() {
        return costeAlta;
    }

    public void setCosteAlta(float costeAlta) {
        this.costeAlta = costeAlta;
    }

    public float getComisionBase() {
        return comisionBase;
    }

    public void setComisionBase(float comisionBase) {
        this.comisionBase = comisionBase;
    }

    public float getComisionExtra() {
        return comisionExtra;
    }

    public void setComisionExtra(float comisionExtra) {
        this.comisionExtra = comisionExtra;
    }

    public float getComisionPorta() {
        return comisionPorta;
    }

    public void setComisionPorta(float comisionPorta) {
        this.comisionPorta = comisionPorta;
    }
}
