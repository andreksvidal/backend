package controllers;

import entities.Sucursal;


import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("sucursalController")
@SessionScoped
public class SucursalController implements Serializable {

    private Sucursal current;
    private List<Sucursal> items = null;
    private List<Sucursal> itemsFiltrados = null;
    @EJB
    private sessionBeans.SucursalFacade ejbFacade;
    private int tipoVista;

    public SucursalController() {
        current=new Sucursal();
    }

    public Sucursal findById(String id){
        return ejbFacade.findById(id);
    }

    public List<Sucursal> getItems() {
        return ejbFacade.findAll();
    }

    public void setItems(List<Sucursal> items) {
        this.items = items;
    }

    public List<Sucursal> getItemsFiltrados() {
        return itemsFiltrados;
    }

    public void setItemsFiltrados(List<Sucursal> itemsFiltrados) {
        this.itemsFiltrados = itemsFiltrados;
    }
    
    public String elegirSucursal(Sucursal sucursal){
        current=sucursal;
        switch(tipoVista){
            case 1:
                return "/ingresargiro.xhtml?faces-redirect=true";
            case 2:
                return "/pagofacturas.xhtml?faces-redirect=true";
            case 3:
                return "/abonocuenta.xhtml?faces-redirect=true";
        }
        return "vacio";
    }
    
    //1 Giro
    //2 Factura
    //3 Abono
    public String seleccionarDestino(int tipo){
        tipoVista=tipo;
        return "/destinos.xhtml?faces-redirect=true";
    }

    public Sucursal getCurrent() {
        return current;
    }

    public void setCurrent(Sucursal current) {
        this.current = current;
    }
    
    
    

}
