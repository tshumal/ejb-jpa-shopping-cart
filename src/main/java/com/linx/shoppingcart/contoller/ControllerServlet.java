package com.linx.shoppingcart.contoller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.linx.shoppingcart.cart.ShoppingCart;
import com.linx.shoppingcart.entity.Category;
import com.linx.shoppingcart.entity.Product;
import com.linx.shoppingcart.session.CategoryFacade;
import com.linx.shoppingcart.session.OrderManager;
import com.linx.shoppingcart.session.ProductFacade;

/**
 * @author Lingani Tshuma - May the source be with you.
 * 
 * Servlet implementation class ControllerServlet
 */
@WebServlet(name = "ControllerServlet", loadOnStartup = 1, urlPatterns = {"/category",
    "/addToCart", "/viewCart", "/updateCart", "/chooseLanguage", "/purchase", "/checkout"})
public class ControllerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private String surcharge;

  @EJB
  private CategoryFacade categoryFacade;

  @EJB
  private ProductFacade productFacade;

  @EJB
  private OrderManager orderManager;

  /**
   * Default constructor.
   */
  public ControllerServlet() {
    // TODO Auto-generated constructor stub
  }

  public void init() throws ServletException {

    // store category list in servlet context
    getServletContext().setAttribute("categories", categoryFacade.findAll());

    surcharge = getServletContext().getInitParameter("deliverySurcharge");

  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();

    String userPath = request.getServletPath();

    Category selectedCategory;

    List<Product> categoryProducts;

    // if category page is requested
    if (userPath.equals("/category")) {
      // get categoryID from request
      String categoryID = request.getQueryString();

      if (categoryID != null) {

        selectedCategory = categoryFacade.find(Integer.parseInt(categoryID));
        session.setAttribute("selectedCategory", selectedCategory);

        categoryProducts = selectedCategory.getProducts();
        session.setAttribute("categoryProducts", categoryProducts);

      }
    }

    // if view cart is requested
    else if (userPath.equals("/viewCart")) {

      String clear = request.getParameter("clear");

      if ((clear != null && clear.equals("true"))) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        cart.clear();
      }

      userPath = "/cart";
    }

    // if checkout page is requested
    else if (userPath.equals("/checkout")) {
      ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

      // calculate total
      cart.calculateTotal(surcharge);

      // forward to checkout page and switch to a secure channel

    }

    // if user switches language
    else if (userPath.equals("/chooseLanguage")) {
      // TODO: Implement language request

    }

    // Use RequestDispatcher to forward request internally
    String url = "WEB-INF/view" + userPath + ".jsp";
    try {
      request.getRequestDispatcher(url).forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String userPath = request.getServletPath();
    HttpSession session = request.getSession();
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

    // if user adds to cart
    if (userPath.equals("/addToCart")) {

      // if user is adding item to cart for first time
      // create cart object and attach it to user session
      if (cart == null) {

        cart = new ShoppingCart();
        session.setAttribute("cart", cart);
      }

      // get user input from request
      String productId = request.getParameter("productId");

      if (!productId.isEmpty()) {

        Product product = productFacade.find(Integer.parseInt(productId));
        cart.addItem(product);
      }

      userPath = "/category";
    }

    // if update cart action is called
    if (userPath.equals("/updateCart")) {
      // get input from request
      String productId = request.getParameter("productId");
      String quantity = request.getParameter("quantity");

      Product product = productFacade.find(Integer.parseInt(productId));
      cart.update(product, quantity);

      userPath = "/cart";
    }

    // if purchase action is called
    if (userPath.equals("/purchase")) {

      if (cart != null) {

        // extract user data from request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String cityRegion = request.getParameter("cityRegion");
        String ccNumber = request.getParameter("creditcard");

        // validate user data
        boolean validationErrorFlag = false;
        // validationErrorFlag = validator.validateForm(name, email, phone, address, cityRegion,
        // ccNumber, request);

        // if validation error found, return user to checkout
        if (validationErrorFlag == true) {
          request.setAttribute("validationErrorFlag", validationErrorFlag);
          userPath = "/checkout";

          // otherwise, save order to database
        } else {

          int orderId =
              orderManager.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

          // if order processed successfully send user to confirmation page
          if (orderId != 0) {

            // dissociate shopping cart from session
            cart = null;

            // end session
            session.invalidate();

            // get order details
            @SuppressWarnings("rawtypes")
            Map orderMap = orderManager.getOrderDetails(orderId);

            // place order details in request scope
            request.setAttribute("customer", orderMap.get("customer"));
            request.setAttribute("products", orderMap.get("products"));
            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
            request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));

            userPath = "/confirmation";

            // otherwise, send back to checkout page and display error
          } else {
            userPath = "/checkout";
            request.setAttribute("orderFailureFlag", true);
          }
        }
      }
    }
    // use RequestDispatcher to forward request internally
    String url = "/WEB-INF/view" + userPath + ".jsp";

    try {
      request.getRequestDispatcher(url).forward(request, response);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
