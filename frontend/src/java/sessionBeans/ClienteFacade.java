/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.Cliente;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "BlockChainJavaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public Cliente findById(String buscarPaciente) {
        Query query = em.createNamedQuery("Cliente.findByNumidentifiacion");
        query.setParameter("numidentifiacion",buscarPaciente);
        return (Cliente) query.getSingleResult();
    }

}
