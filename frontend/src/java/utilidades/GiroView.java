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
public class GiroView {

    private datospago datospago;
    private boolean pagado;
    private String txid;
    private datos datos;
    private String sucursalRemitente;
    private String sucursalBeneficiaria;
    private String nombreRemitente;
    private String nombreBeneficiario;

    public GiroView(String txid, datos datos, String sucursalRemitente, String sucursalBeneficiaria, String nombreRemitente, String nombreBeneficiario) {
        this.pagado=false;
        this.txid = txid;
        this.datos = datos;
        this.sucursalRemitente = sucursalRemitente;
        this.sucursalBeneficiaria = sucursalBeneficiaria;
        this.nombreRemitente = nombreRemitente;
        this.nombreBeneficiario = nombreBeneficiario;
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

    public String getSucursalRemitente() {
        return sucursalRemitente;
    }

    public void setSucursalRemitente(String sucursalRemitente) {
        this.sucursalRemitente = sucursalRemitente;
    }

    public String getSucursalBeneficiaria() {
        return sucursalBeneficiaria;
    }

    public void setSucursalBeneficiaria(String sucursalBeneficiaria) {
        this.sucursalBeneficiaria = sucursalBeneficiaria;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public datospago getDatospago() {
        return datospago;
    }

    public void setDatospago(datospago datospago) {
        this.datospago = datospago;
    }

    
    
   
}
