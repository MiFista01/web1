/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Order_user;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aleksei
 */
@Stateless
public class Order_userFacade extends AbstractFacade<Order_user> {

    @PersistenceContext(unitName = "web1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Order_userFacade() {
        super(Order_user.class);
    }
    public Order_user findid(int id) {
        try {
            return (Order_user) em.createQuery("SELECT u FROM Order_user u WHERE u.id=:id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
