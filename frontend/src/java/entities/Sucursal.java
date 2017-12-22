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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
@Entity
@Table(name = "sucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")
    , @NamedQuery(name = "Sucursal.findById", query = "SELECT s FROM Sucursal s WHERE s.id = :id")
    , @NamedQuery(name = "Sucursal.findByDepartamento", query = "SELECT s FROM Sucursal s WHERE s.departamento = :departamento")
    , @NamedQuery(name = "Sucursal.findByCiudad", query = "SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad")
    , @NamedQuery(name = "Sucursal.findByInstitucion", query = "SELECT s FROM Sucursal s WHERE s.institucion = :institucion")
    , @NamedQuery(name = "Sucursal.findByAgencia", query = "SELECT s FROM Sucursal s WHERE s.agencia = :agencia")
    , @NamedQuery(name = "Sucursal.findByDireccion", query = "SELECT s FROM Sucursal s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "Sucursal.findByTelefono", query = "SELECT s FROM Sucursal s WHERE s.telefono = :telefono")
    , @NamedQuery(name = "Sucursal.findByHorario", query = "SELECT s FROM Sucursal s WHERE s.horario = :horario")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(max = 60)
    @Column(name = "ID")
    private String id;
    @Size(max = 60)
    @Column(name = "DEPARTAMENTO")
    private String departamento;
    @Size(max = 60)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 60)
    @Column(name = "INSTITUCION")
    private String institucion;
    @Size(max = 60)
    @Column(name = "AGENCIA")
    private String agencia;
    @Size(max = 150)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 60)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 120)
    @Column(name = "HORARIO")
    private String horario;

    public Sucursal() {
    }

    public Sucursal(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Sucursal[ id=" + id + " ]";
    }
    
}
