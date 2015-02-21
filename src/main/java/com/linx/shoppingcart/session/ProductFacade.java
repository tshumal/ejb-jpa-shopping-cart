package com.linx.shoppingcart.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linx.shoppingcart.entity.Product;

/**
 * 
 * @author lingani tshuma
 *
 */

@Stateless
public class ProductFacade extends AbstractFacade<Product>{
  
  @PersistenceContext(unitName = "affablebean")
  EntityManager em;
  
  public ProductFacade(){
    super(Product.class);
  }

  @Override
  protected EntityManager getEntityManager() {
   
    return this.em;
  }

}
