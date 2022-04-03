/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Biography;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aleksei
 */
@Stateless
public class BiographyFacade extends AbstractFacade<Biography> {

    @PersistenceContext(unitName = "web1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BiographyFacade() {
        super(Biography.class);
    }
    public Biography findid(int id) {
        try {
            return (Biography) em.createQuery("SELECT u FROM Biography u WHERE u.id=:id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
