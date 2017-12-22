package controllers;

import entities.Cliente;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    private Cliente current;
    private List<Cliente> items = null;
    @EJB
    private sessionBeans.ClienteFacade clienteFacade;


    public ClienteController() {
        
    }

    public Cliente getCurrent() {
        return current;
    }

    public void setCurrent(Cliente current) {
        this.current = current;
    }

    public List<Cliente> getItems() {
        if(items==null){
            items=clienteFacade.findAll();
        }
        return items;
    }

    public void setItems(List<Cliente> items) {
        this.items = items;
    }    

    public Cliente encontrarCliente(String id){
       return clienteFacade.findById(id);
    }
    

}
