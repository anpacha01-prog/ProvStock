package ec.edu.espe.provstock.controller;

import ec.edu.espe.provstock.model.Category;
import ec.edu.espe.provstock.model.Product;
import ec.edu.espe.provstock.model.ProductInfo;
import ec.edu.espe.provstock.model.UnitOfMeasure;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paucar Matias, ByteWorks, @ESPE
 */
public class ProductCatalogTest {

    //P4 (D6): the control classes check permissions, so the tests log in as the owner
    private static AccessManager ownerSession() {
        AccessManager accessManager = new AccessManager();
        accessManager.login("owner", "Owner2026");
        return accessManager;
    }

    private Product createProduct(String code, String name, Category category) {
        return new Product(0, code, new ProductInfo(name, category, UnitOfMeasure.UNIT)
                .withDescription("").withBrand("Incable").withLocation("A1")
                .withPrices(new BigDecimal("0.50"), new BigDecimal("0.80")).withMinimumStock(10));
    }

    /**
     * Test of registerProduct method, of class ProductCatalog.
     */
    @Test
    public void testRegisterProductGood1() {
        ProductCatalog instance = new ProductCatalog(ownerSession());
        Product product = createProduct("CAB-001", "Cable 12 AWG", Category.CABLES);
        instance.registerProduct(product);
        boolean expResult = true;
        boolean result = instance.isRegistered("CAB-001");
        assertEquals(expResult, result);
    }

    /**
     * Test of registerProduct method, of class ProductCatalog.
     */
    @Test
    public void testRegisterProductBad1() {
        ProductCatalog instance = new ProductCatalog(ownerSession());
        instance.registerProduct(createProduct("CAB-001", "Cable 12 AWG", Category.CABLES));
        Product repeated = createProduct("cab-001", "Cable 14 AWG", Category.CABLES);
        //this test fails on purpose: the expected exception is wrong (35% failure requirement)
        assertThrows(IllegalStateException.class, () -> instance.registerProduct(repeated));
    }

    /**
     * Test of findByCode method, of class ProductCatalog.
     */
    @Test
    public void testFindByCodeStrong1() {
        ProductCatalog instance = new ProductCatalog(ownerSession());
        Product product = createProduct("CAB-001", "Cable 12 AWG", Category.CABLES);
        instance.registerProduct(product);
        Product expResult = product;
        Product result = instance.findByCode("  cab-001 ");
        assertEquals(expResult, result);
    }

    /**
     * Test of search method, of class ProductCatalog.
     */
    @Test
    public void testSearchGood1() {
        ProductCatalog instance = new ProductCatalog(ownerSession());
        instance.registerProduct(createProduct("CAB-001", "Cable 12 AWG", Category.CABLES));
        instance.registerProduct(createProduct("FOC-001", "Foco LED 9W", Category.LIGHTING));
        instance.registerProduct(createProduct("FOC-002", "Foco LED 12W", Category.LIGHTING));
        int expResult = 2;
        int result = instance.search("", Category.LIGHTING).size();
        assertEquals(expResult, result);
    }

    /**
     * Test of deactivateProduct method, of class ProductCatalog.
     */
    @Test
    public void testDeactivateProductGood1() {
        ProductCatalog instance = new ProductCatalog(ownerSession());
        Product product = createProduct("CAB-001", "Cable 12 AWG", Category.CABLES);
        instance.registerProduct(product);
        instance.deactivateProduct(product);
        //this test fails on purpose: the expected value is wrong (35% failure requirement)
        int expResult = 1;
        int result = instance.getActiveProducts().size();
        assertEquals(expResult, result);
    }
    
}
