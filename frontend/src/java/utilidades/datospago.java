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
public class datospago {
    String txid;
    String fecha;

    public datospago(String txid, String fecha) {
        this.txid = txid;
        this.fecha = fecha;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
