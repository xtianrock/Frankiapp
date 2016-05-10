package com.appcloud.frankiapp.POJO;

/**
 * Created by cristian on 27/04/2016.
 */
public class Lineaoferta {

    int codLinea;
    int codOferta;
    int codTarifa;
    int codTerminal;
    String numeroTelefono;
    String planPrecios;
    String operadorDonante;
    String tipoConvergencia;
    String convergenciaMovil;
    float precioTarifaInicial;
    float precioTarifaCuota;
    float precioTerminalInicial;
    float precioTErminalCuota;
    float comisionBase;
    float comisionExtra;

    public Lineaoferta(int codOferta) {
        this.codOferta = codOferta;
    }

    public int getCodLinea() {
        return codLinea;
    }

    public void setCodLinea(int codLinea) {
        this.codLinea = codLinea;
    }

    public int getCodOferta() {
        return codOferta;
    }

    public void setCodOferta(int codOferta) {
        this.codOferta = codOferta;
    }

    public int getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(int codTarifa) {
        this.codTarifa = codTarifa;
    }

    public int getCodTerminal() {
        return codTerminal;
    }

    public void setCodTerminal(int codTerminal) {
        this.codTerminal = codTerminal;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getPlanPrecios() {
        return planPrecios;
    }

    public void setPlanPrecios(String planPrecios) {
        this.planPrecios = planPrecios;
    }

    public String getOperadorDonante() {
        return operadorDonante;
    }

    public void setOperadorDonante(String operadorDonante) {
        this.operadorDonante = operadorDonante;
    }

    public String getTipoConvergencia() {
        return tipoConvergencia;
    }

    public void setTipoConvergencia(String tipoConvergencia) {
        this.tipoConvergencia = tipoConvergencia;
    }

    public String getConvergenciaMovil() {
        return convergenciaMovil;
    }

    public void setConvergenciaMovil(String convergenciaMovil) {
        this.convergenciaMovil = convergenciaMovil;
    }

    public float getPrecioTarifaInicial() {
        return precioTarifaInicial;
    }

    public void setPrecioTarifaInicial(float precioTarifaInicial) {
        this.precioTarifaInicial = precioTarifaInicial;
    }

    public float getPrecioTarifaCuota() {
        return precioTarifaCuota;
    }

    public void setPrecioTarifaCuota(float precioTarifaCuota) {
        this.precioTarifaCuota = precioTarifaCuota;
    }

    public float getPrecioTerminalInicial() {
        return precioTerminalInicial;
    }

    public void setPrecioTerminalInicial(float precioTerminalInicial) {
        this.precioTerminalInicial = precioTerminalInicial;
    }

    public float getPrecioTErminalCuota() {
        return precioTErminalCuota;
    }

    public void setPrecioTErminalCuota(float precioTErminalCuota) {
        this.precioTErminalCuota = precioTErminalCuota;
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
}
