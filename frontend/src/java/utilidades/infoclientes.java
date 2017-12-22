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
public class infoclientes {

    private emisor emisor;
    private beneficiario beneficiario;

    public infoclientes(emisor emisor, beneficiario beneficiario) {
        this.emisor = emisor;
        this.beneficiario = beneficiario;
    }

    public emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(emisor emisor) {
        this.emisor = emisor;
    }

    public beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    
}




