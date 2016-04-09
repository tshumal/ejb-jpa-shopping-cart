package com.linx.shoppingcart.test;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractPersistenceUnitTest extends TestCase {

  private static Logger logger = Logger.getLogger(AbstractPersistenceUnitTest.class);
  private EntityManagerFactory emf;
  private EntityManager em;
  private Connection connection;

  public AbstractPersistenceUnitTest(String testName) {
    super(testName);
  }

  @Override
  @BeforeClass
  public void setUp() throws Exception {
    super.setUp();

    try {
      logger.info("Starting MySQL database for unit tests");
      Class.forName("com.mysql.jdbc.Driver");
      connection =
          DriverManager
              .getConnection("jdbc:mysql://localhost:3306/affablebean", "root", "linx_001");
    } catch (Exception ex) {
      logger.error(ex);
      fail("Exception during HSQL database startup.");
    }

    try {
      logger.info("Building JPA EntityManager for unit tests");
      emf = Persistence.createEntityManagerFactory("affablebean");
      em = emf.createEntityManager();
    } catch (Exception ex) {
      logger.error(ex);
      fail("Exception during JPA EntityManager instantiation.");
    }
  }

  @Override
  @AfterClass
  public void tearDown() throws Exception {
    super.tearDown();

    logger.info("Shuting down Hibernate JPA layer.");
    if (em != null) {
      em.close();
    }
    if (emf != null) {
      emf.close();
    }

    logger.info("Stopping MySQL database.");
    try {
      connection.createStatement().execute("SHUTDOWN");
    } catch (Exception ex) {
    }
  }
}
