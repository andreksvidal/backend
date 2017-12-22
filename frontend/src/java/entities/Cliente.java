/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByTipoidentificacion", query = "SELECT c FROM Cliente c WHERE c.tipoidentificacion = :tipoidentificacion")
    , @NamedQuery(name = "Cliente.findByNumidentifiacion", query = "SELECT c FROM Cliente c WHERE c.numidentifiacion = :numidentifiacion")
    , @NamedQuery(name = "Cliente.findByPrimernombre", query = "SELECT c FROM Cliente c WHERE c.primernombre = :primernombre")
    , @NamedQuery(name = "Cliente.findBySegundonombre", query = "SELECT c FROM Cliente c WHERE c.segundonombre = :segundonombre")
    , @NamedQuery(name = "Cliente.findByPrimerapellido", query = "SELECT c FROM Cliente c WHERE c.primerapellido = :primerapellido")
    , @NamedQuery(name = "Cliente.findBySegundoapellido", query = "SELECT c FROM Cliente c WHERE c.segundoapellido = :segundoapellido")
    , @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular")
    , @NamedQuery(name = "Cliente.findByDepartamento", query = "SELECT c FROM Cliente c WHERE c.departamento = :departamento")
    , @NamedQuery(name = "Cliente.findByCiudad", query = "SELECT c FROM Cliente c WHERE c.ciudad = :ciudad")
    , @NamedQuery(name = "Cliente.findByCuentaahorros", query = "SELECT c FROM Cliente c WHERE c.cuentaahorros = :cuentaahorros")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Size(max = 60)
    @Column(name = "TIPOIDENTIFICACION")
    private String tipoidentificacion;
    @Id
    @Size(max = 30)
    @Column(name = "NUMIDENTIFIACION")
    private String numidentifiacion;
    @Size(max = 60)
    @Column(name = "PRIMERNOMBRE")
    private String primernombre;
    @Size(max = 60)
    @Column(name = "SEGUNDONOMBRE")
    private String segundonombre;
    @Size(max = 60)
    @Column(name = "PRIMERAPELLIDO")
    private String primerapellido;
    @Size(max = 60)
    @Column(name = "SEGUNDOAPELLIDO")
    private String segundoapellido;
    @Size(max = 150)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 60)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 60)
    @Column(name = "CELULAR")
    private String celular;
    @Size(max = 60)
    @Column(name = "DEPARTAMENTO")
    private String departamento;
    @Size(max = 60)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 60)
    @Column(name = "CUENTAAHORROS")
    private String cuentaahorros;

    public Cliente() {
    }

    public Cliente(String numidentifiacion) {
        this.numidentifiacion = numidentifiacion;
    }

    public Cliente(String numidentifiacion, String tipoidentificacion) {
        this.numidentifiacion = numidentifiacion;
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(String tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getNumidentifiacion() {
        return numidentifiacion;
    }

    public void setNumidentifiacion(String numidentifiacion) {
        this.numidentifiacion = numidentifiacion;
    }

    public String getPrimernombre() {
        return primernombre;
    }

    public void setPrimernombre(String primernombre) {
        this.primernombre = primernombre;
    }

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
    }

    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCuentaahorros() {
        return cuentaahorros;
    }

    public void setCuentaahorros(String cuentaahorros) {
        this.cuentaahorros = cuentaahorros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numidentifiacion != null ? numidentifiacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.numidentifiacion == null && other.numidentifiacion != null) || (this.numidentifiacion != null && !this.numidentifiacion.equals(other.numidentifiacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cliente[ numidentifiacion=" + numidentifiacion + " ]";
    }
    
}
