// default package
// Generated 25 Aug 2014 8:52:00 PM by Hibernate Tools 3.4.0.CR1
package com.linx.shoppingcart.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 *@author Lingani Tshuma - May the source be with you.
 *
 */
@Entity
@Table(name = "customer_order", catalog = "shoppingcart")
@NamedQueries({
  @NamedQuery(name = "CustomerOrder.findByCustomer", query = "SELECT c FROM CustomerOrder c WHERE c.customer = :customer")
})
public class CustomerOrder implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private Customer customer;
  private BigDecimal amount;  
  private Date dateCreated;
  private int confirmationNumber;
  private Set<OrderedProduct> orderedProducts = new HashSet<OrderedProduct>(0);

  public CustomerOrder() {}


  public CustomerOrder(Customer customer, BigDecimal amount, Date dateCreated,
      int confirmationNumber) {
    this.customer = customer;
    this.amount = amount;
    this.dateCreated = dateCreated;
    this.confirmationNumber = confirmationNumber;
  }  

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id", nullable = false)
  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }


  @Column(name = "amount", nullable = false, precision = 5)
  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }


  @Column(name = "date_created", nullable = false, insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getDateCreated() {
    return this.dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }


  @Column(name = "confirmation_number", nullable = false)
  public int getConfirmationNumber() {
    return this.confirmationNumber;
  }

  public void setConfirmationNumber(int confirmationNumber) {
    this.confirmationNumber = confirmationNumber;
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerOrder")
  public Set<OrderedProduct> getOrderedProducts() {
    return this.orderedProducts;
  }

  public void setOrderedProducts(Set<OrderedProduct> orderedProducts) {
    this.orderedProducts = orderedProducts;
  }



}
