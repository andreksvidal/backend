/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */


public class GiroJson {

    private String estado;
    private List<datosTransaccion> resultado;

    public GiroJson(String estado, List<datosTransaccion> resultado) {
        this.estado = estado;
        this.resultado = resultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<datosTransaccion> getResultado() {
        return resultado;
    }

    public void setResultado(List<datosTransaccion> resultado) {
        this.resultado = resultado;
    }

   

    
}
