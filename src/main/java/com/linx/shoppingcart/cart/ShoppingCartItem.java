package com.linx.shoppingcart.cart;

import com.linx.shoppingcart.entity.Product;

/**
 * 
 * @author Lingani Tshuma - May the source be with you.
 *
 */

public class ShoppingCartItem {

  private Product product;
  private short quantity;

  public ShoppingCartItem(Product product) {
    this.product = product;
    quantity = 1;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(short quantity) {
    this.quantity = quantity;
  }

  public void incrementQuantity() {
    quantity++;
  }

  public void decrementQuantity() {
    quantity--;
  }

  public double getTotal() {
    double amount = 0;
    amount = (this.getQuantity() * this.getProduct().getPrice().doubleValue());
    return amount;
  }

}
