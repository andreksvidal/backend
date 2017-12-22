/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
public class datos {

    private String idremitente;
    private String idreceptora;
    private String numerorefer;
    private String cantidad;
    private infoclientes infoclientes;
    private String fecha;

    public datos(String idremitente, String idreceptora, String numerorefer, String cantidad, infoclientes infoclientes, String fecha) {
        this.idremitente = idremitente;
        this.idreceptora = idreceptora;
        this.numerorefer = numerorefer;
        this.cantidad = cantidad;
        this.infoclientes = infoclientes;
        this.fecha = fecha;
    }

    public String getIdremitente() {
        return idremitente;
    }

    public void setIdremitente(String idremitente) {
        this.idremitente = idremitente;
    }

    public String getIdreceptora() {
        return idreceptora;
    }

    public void setIdreceptora(String idreceptora) {
        this.idreceptora = idreceptora;
    }

    public String getNumerorefer() {
        return numerorefer;
    }

    public void setNumerorefer(String numerorefer) {
        this.numerorefer = numerorefer;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public infoclientes getInfoclientes() {
        return infoclientes;
    }

    public void setInfoclientes(infoclientes infoclientes) {
        this.infoclientes = infoclientes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



}