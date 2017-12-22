/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
public class Giro implements Serializable{

    private String id;
    private Date fecha;
    private String idRemitente;
    private String idReceptora;
    private String cantidad;
    private Cliente emisor;
    private Cliente beneficiario;

    public Giro() {
        emisor=new Cliente();
        beneficiario=new Cliente();
    }

    
    public Giro(String id, Date fecha, String idRemitente, String idReceptora, String cantidad, Cliente emisor, Cliente beneficiario) {
        this.id = id;
        this.fecha = fecha;
        this.idRemitente = idRemitente;
        this.idReceptora = idReceptora;
        this.cantidad = cantidad;
        this.emisor = emisor;
        this.beneficiario = beneficiario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getIdReceptora() {
        return idReceptora;
    }

    public void setIdReceptora(String idReceptora) {
        this.idReceptora = idReceptora;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getEmisor() {
        return emisor;
    }

    public void setEmisor(Cliente emisor) {
        this.emisor = emisor;
    }

    public Cliente getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Cliente beneficiario) {
        this.beneficiario = beneficiario;
    }

    
}
