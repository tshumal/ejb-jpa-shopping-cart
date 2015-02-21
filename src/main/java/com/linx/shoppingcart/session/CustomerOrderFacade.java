package com.linx.shoppingcart.session;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linx.shoppingcart.entity.CustomerOrder;

/**
 * 
 * @author lingani tshuma
 * 
 */

@Stateless
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> {

  @PersistenceContext(unitName = "affablebean")
  EntityManager em;

  public CustomerOrderFacade() {
    super(CustomerOrder.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return this.em;
  }

  // manually created
  // in this implementation, there is only one order per customer
  // the data model however allows for multiple orders per customer
  @RolesAllowed("azimboAdmin")
  public CustomerOrder findByCustomer(Object customer){
    return (CustomerOrder) em.createNamedQuery("CustomerOrder.findByCustomer").setParameter("cutomer", customer).getSingleResult();
  }

}
