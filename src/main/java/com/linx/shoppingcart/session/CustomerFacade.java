package com.linx.shoppingcart.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linx.shoppingcart.entity.Customer;

/**
 * 
 * @author lingani tshuma
 *
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer>{
  
  @PersistenceContext(unitName = "affablebean")
  private EntityManager em;

  protected EntityManager getEntityManager() {
      return em;
  }

  public CustomerFacade() {
      super(Customer.class);
  }

}
