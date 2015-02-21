package com.linx.shoppingcart.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linx.shoppingcart.entity.Category;

/**
 * 
 * @author lingani tshuma
 *
 */

@Stateless
public class CategoryFacade extends AbstractFacade<Category>{
  
  @PersistenceContext(unitName = "affablebean")
  EntityManager em;
  
  public CategoryFacade(){
    super(Category.class);
  }

  @Override
  protected EntityManager getEntityManager() {    
    return this.em;
  }
  

}
