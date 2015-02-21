package com.linx.shoppingcart.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linx.shoppingcart.entity.OrderedProduct;

/**
 * 
 * @author lingani tshuma
 *
 */

@Stateless
public class OrderedProductFacade extends AbstractFacade<OrderedProduct> {

  @PersistenceContext(unitName = "affablebean")
  private EntityManager em;

  protected EntityManager getEntityManager() {
    return em;
  }

  public OrderedProductFacade() {
    super(OrderedProduct.class);
  }
  
//manually created
  public List<OrderedProduct> findByOrderId(Object id) {
      return em.createNamedQuery("OrderedProduct.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
  }

}
