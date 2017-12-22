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
public class datosTransaccion {

    private datospago datospago;
    private datos datosconsignacion;
    private String txid;
    private datos datos;

    public datosTransaccion(String id, datos datos) {
        this.txid = id;
        this.datos = datos;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public datos getDatos() {
        return datos;
    }

    public void setDatos(datos datos) {
        this.datos = datos;
    }   

    public datospago getDatospago() {
        return datospago;
    }

    public void setDatospago(datospago datospago) {
        this.datospago = datospago;
    }

    public datos getDatosconsignacion() {
        return datosconsignacion;
    }

    public void setDatosconsignacion(datos datosconsignacion) {
        this.datosconsignacion = datosconsignacion;
    }
    
    
    
}