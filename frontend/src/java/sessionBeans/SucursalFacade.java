/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.Sucursal;
import javax.persistence.Query;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
@Stateless
public class SucursalFacade extends AbstractFacade<Sucursal> {

    @PersistenceContext(unitName = "BlockChainJavaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SucursalFacade() {
        super(Sucursal.class);
    }

    public Sucursal findById(String sucursal) {
        Query query = em.createNamedQuery("Sucursal.findById");
        query.setParameter("id", sucursal);
        return (Sucursal) query.getSingleResult();
    }

}
